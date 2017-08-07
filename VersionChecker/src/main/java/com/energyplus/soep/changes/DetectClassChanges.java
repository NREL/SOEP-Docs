// Copyright 2017 Big Ladder Software LLC, All rights reserved.
// 
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
// 
// (1) Redistributions of source code must retain the above copyright notice,
// this list of conditions and the following disclaimer.
// 
// (2) Redistributions in binary form must reproduce the above copyright notice,
// this list of conditions and the following disclaimer in the documentation
// and/or other materials provided with the distribution.
// 
// (3) Neither the name of the copyright holder nor the names of its
// contributors may be used to endorse or promote products derived from this
// software without specific prior written permission. 
// 
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER, THE UNITED STATES
// GOVERNMENT, OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
// SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
// PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
// OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
// WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
// OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
// ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
package com.energyplus.soep.changes;

import com.energyplus.soep.SrcAstClass;
import com.energyplus.soep.SrcAstCompDecl;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 */
public class DetectClassChanges
{
    DetectClassChanges() {}
    public void
    getBackwardIncompatibleChanges(
            List<String> changes, Map<String,SrcAstClass> oldVer, Map<String,SrcAstClass> newVer)
    {
        if (oldVer.equals(newVer))
            return;
        Set<String> keySet = oldVer.keySet();
        for (String k : keySet)
        {
            SrcAstClass oldClass = oldVer.get(k);
            if (!newVer.containsKey(k))
            {
                changes.add(String.format("ClassNoLongerExists(class_name = \"%s\")", k));
                continue;
            }
            SrcAstClass newClass = newVer.get(k);
            if (newClass.equals(oldClass))
                continue;
            checkCompForBackwardIncompatibleChanges(
                    changes,
                    k,
                    oldClass.getComponents(),
                    newClass.getComponents());
        }
    }
    /**
     * Check component for backwards incompatible changes. Specifically, the kinds of changes we would be looking for
     * would include removing a default value on a parameter, changing a parameter's name, addition of new parameters
     * that do NOT have a default value, etc.
     * @param changes, the list of changes to append to if found
     * @param oldComps, the list of old components
     * @param newComps, the list of new components
     */
    private void
    checkCompForBackwardIncompatibleChanges(
            List<String> changes, String className, List<SrcAstCompDecl> oldComps, List<SrcAstCompDecl> newComps)
    {
        checkForRemovalOfComponents(changes, className, oldComps, newComps);
        checkForRemovalOfDefaultValueInComponents(changes, className, oldComps, newComps);
        checkForNewParametersWithoutDefaults(changes, className, oldComps, newComps);
    }
    private List<SrcAstCompDecl>
    getParameters(List<SrcAstCompDecl> comps)
    {
        List<SrcAstCompDecl> out = new ArrayList<>();
        if (comps == null)
            return out;
        for (SrcAstCompDecl c : comps)
        {
            if (c.isParameter())
                out.add(c);
        }
        return out;
    }
    private List<SrcAstCompDecl>
    eliminateComponentsByName(List<SrcAstCompDecl> comps, Set<String> nameSet)
    {
        List<SrcAstCompDecl> out = new ArrayList<>();
        if (comps == null)
            return out;
        for (SrcAstCompDecl c : comps)
        {
            if (nameSet.contains(c.getName()))
                continue;
            out.add(c);
        }
        return out;
    }
    private String
    buildStringFromSet(Set<String> ss)
    {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        List<String> list = new ArrayList<>(ss);
        Collections.sort(list);
        for (String n : list)
        {
            if (first)
            {
                sb.append(n);
                first = false;
            }
            else
            {
                sb.append(String.format(", %s", n));
            }
        }
        return sb.toString();
    }
    private void
    checkForNewParametersWithoutDefaults(
            List<String> changes, String className, List<SrcAstCompDecl> oldComps, List<SrcAstCompDecl> newComps)
    {
        // get all parameter objects in newComps
        List<SrcAstCompDecl> newParams = getParameters(newComps);
        // eliminate parameters that were also in old by name
        Set<String> oldParamNameSet = getNamesFromComponents(getParameters(oldComps));
        List<SrcAstCompDecl> actualNewParams = eliminateComponentsByName(newParams, oldParamNameSet);
        // check each new parameter to ensure it has a default value
        Set<String> paramsWithoutDefaults = new HashSet<>();
        for (SrcAstCompDecl c : actualNewParams)
        {
            String value = c.getValue();
            if (value == null || value.isEmpty())
            {
                paramsWithoutDefaults.add(c.getName());
            }
        }
        if (paramsWithoutDefaults.isEmpty())
            return;
        String outStr = buildStringFromSet(paramsWithoutDefaults);
        changes.add(
                String.format(
                        "ComponentsAddedWithoutDefault(class_name = \"%s\", component_names = \"%s\")",
                        className, outStr));
    }
    private Optional<SrcAstCompDecl>
    findComponentByName(List<SrcAstCompDecl> comps, String name)
    {
        if (comps == null)
            return Optional.empty();
        for (SrcAstCompDecl c : comps)
        {
            if (c.getName().equals(name))
            {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }
    private boolean
    qualifiersEqual(List<String> qs1, List<String> qs2)
    {
        if ((qs1 == null) && (qs2 == null))
            return true;
        if ((qs1 == null) || (qs2 == null))
            return false;
        if (qs1.size() != qs2.size())
            return false;
        return qs2.containsAll(qs1);
    }
    private Set<String> getNamesFromComponents(List<SrcAstCompDecl> comps)
    {
        Set<String> names = new HashSet<>();
        if (comps == null)
            return names;
        for (SrcAstCompDecl c : comps)
        {
            names.add(c.getName());
        }
        return names;
    }
    private void
    checkForRemovalOfComponents(
            List<String> changes, String className, List<SrcAstCompDecl> oldComps, List<SrcAstCompDecl> newComps)
    {
        // 1. get set of component names in old
        Set<String> oldNames = getNamesFromComponents(oldComps);
        // 2. get set of component names in new
        Set<String> newNames = getNamesFromComponents(newComps);
        // 3. do set comparison to ensure all old names are in new. If not, return set of missing names
        if (newNames.containsAll(oldNames))
            return;
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        oldNames.removeAll(newNames);
        String missingNamesStr = buildStringFromSet(oldNames);
        changes.add(
                String.format(
                        "ComponentsRemoved(class_name = \"%s\", component_names = \"%s\")",
                        className, missingNamesStr));
    }
    /**
     * For each of the parameter components in the old component list that have non-null values,
     * find the corresponding component in the new component list and determine if value has been removed.
     * If it has, add a change object to changes.
     * @param changes, list of changes to add to if changes found
     * @param className, the class name that the changes apply to
     * @param oldComps, list of components from the "old" version
     * @param newComps, list of components from the "new" version
     */
    private void
    checkForRemovalOfDefaultValueInComponents(
            List<String> changes, String className, List<SrcAstCompDecl> oldComps, List<SrcAstCompDecl> newComps)
    {
        if (oldComps == null)
            return;
        for (SrcAstCompDecl c : oldComps)
        {
            if (c.isParameter())
            {
                if (c.getValue() != null)
                {
                    // 1. Find component with same name in newComps
                    Optional<SrcAstCompDecl> maybeC = findComponentByName(newComps, c.getName());
                    if (!maybeC.isPresent())
                    {
                        // Note: change detection for presence will occur elsewhere
                        continue;
                    }
                    SrcAstCompDecl c_ = maybeC.get();
                    // 2. ensure that the new component still has a value, even if its different
                    String value = c_.getValue();
                    if (value == null || value.isEmpty())
                    {
                        changes.add(String.format(
                                "ComponentDefaultValueRemoved(" +
                                        "class_name = \"%s\", " +
                                        "component_name = \"%s\", " +
                                        "old_default_value = \"%s\")",
                                className, c.getName(), c.getValue()));
                    }
                    // 3. ensure we still have the same type and qualifiers
                    if (!c_.getClassName().equals(c.getClassName()))
                    {
                        changes.add(String.format(
                                "ClassNameChange(component_name = \"%s\", " +
                                        "old_class_name = \"%s\", new_class_name = \"%s\")",
                                c.getName(), c.getClassName(), c_.getClassName()));
                    }
                }
            }
        }
    }
}
