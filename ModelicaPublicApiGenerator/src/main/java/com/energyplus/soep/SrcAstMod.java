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
package com.energyplus.soep;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jmodelica.modelica.compiler.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Source AST Modification
 */
public class SrcAstMod
{
    private String name;
    private String comment;
    private List<String> qualifiers;
    private String value;
    private List<SrcAstMod> modifications;
    private Map<String,String> annotations;
    SrcAstMod(
            String nameIn,
            String commentIn,
            List<String> qualifiersIn,
            String valueIn,
            List<SrcAstMod> modificationsIn,
            Map<String,String> annotationsIn
    )
    {
        name = nameIn;
        comment = commentIn;
        qualifiers = qualifiersIn;
        value = valueIn;
        modifications = modificationsIn;
        annotations = annotationsIn;
    }
    SrcAstMod(SrcAstMod m)
    {
        name = m.name;
        comment = m.comment;
        qualifiers = App.cloneQualifiers(m.qualifiers);
        value = m.value;
        modifications = App.cloneModifications(m.modifications);

    }
    SrcAstMod(ComponentModification cm)
    {
        name = cm.getName().qualifiedName();
        addQualifiers(cm);
        addComment(cm);
        addValue(cm);
        addArguments(cm);
        annotations = (new SrcAstAnnotations(cm)).getAnnotations();
    }
    SrcAstMod(ClassRedeclare cr)
    {
        name = cr.getName().qualifiedName();
        addQualifiers(cr);
        annotations = (new SrcAstAnnotations(cr)).getAnnotations();
        addValue(cr);
    }
    SrcAstMod(ComponentRedeclare cr)
    {
        name = cr.getName().qualifiedName();
        addQualifiers(cr);
    }
    private void addQualifiers(ComponentRedeclare cr)
    {
        List<String> qs = new ArrayList<>();
        ComponentDecl cd = cr.getComponentDecl();
        if (cd != null)
        {
            if (cd.hasReplaceable())
                qs.add("replaceable");
            if (cd.hasRedeclare())
                qs.add("redeclare");
            if (cd.hasVarArraySubscripts())
                qs.add("array");
            if (cd.hasOuter())
                qs.add("outer");
            if (cd.hasInner())
                qs.add("inner");
            if (cd.isFlow())
                qs.add("flow");
            if (cd.isStream())
                qs.add("stream");
            if (cd.isDiscrete())
                qs.add("discrete");
            if (cd.isOutput())
                qs.add("output");
            if (cd.isInput())
                qs.add("input");
            if (cd.isConstant())
                qs.add("constant");
            if (cd.isParameter())
                qs.add("parameter");
        }
        if (cr.hasEach())
            qs.add("each");
        if (cr.hasFinal())
            qs.add("final");
        if (qs.size() > 0)
            qualifiers = qs;
    }
    private void addQualifiers(ClassRedeclare cr)
    {
        List<String> qs = new ArrayList<>();
        BaseClassDecl bcd = cr.getBaseClassDecl();
        if (bcd != null)
        {
            if (bcd.hasRedeclare())
                qs.add("redeclare");
            if (bcd.hasOuter() || bcd.isOuter())
                qs.add("outer");
            if (bcd.hasInner() || bcd.isInner())
                qs.add("inner");
            if (bcd.hasEncapsulated() || bcd.isEncapsulated())
                qs.add("encapsulated");
            if (bcd.hasPartial() || bcd.isPartial())
                qs.add("partial");
            if (bcd.hasReplaceable())
                qs.add("replaceable");
            if (bcd.isBlock())
                qs.add("block");
            if (bcd.isClass())
                qs.add("class");
            if (bcd.isModel())
                qs.add("model");
            if (bcd.isPackage())
                qs.add("package");
            // don't add "public", we only take public fields so it is redundant
            //if (bcd.isPublic())
            //    qs.add("public");
            if (bcd.isProtected())
                qs.add("protected");
        }
        if (cr.hasFinal())
            qs.add("final");
        if (cr.hasEach())
            qs.add("each");
        if (qs.size() > 0)
            qualifiers = qs;
    }
    private void addArguments(ComponentModification cm)
    {
        if (cm == null)
            return;
        if (cm.hasModification())
        {
            Modification md = cm.getModification();
            addArguments(md);
        }
    }
    private void addArguments(ClassModification cm)
    {
        ArrayList<SrcAstMod> args = new ArrayList<>();
        for (Argument arg : cm.getArguments())
        {
            if (arg instanceof ComponentModification)
            {
                ComponentModification subCompMod = (ComponentModification)arg;
                args.add(new SrcAstMod(subCompMod));
            }
            else if (arg instanceof ClassRedeclare)
            {
                ClassRedeclare cr = (ClassRedeclare)arg;
                args.add(new SrcAstMod(cr));
            }
            else if (arg instanceof ComponentRedeclare)
            {
                ComponentRedeclare cr = (ComponentRedeclare)arg;
                args.add(new SrcAstMod(cr));
            }
            else
            {
                if (arg != null)
                    System.out.println("Warning! 201705181508 unhandled arg: " + arg.getClass().toString());
            }
        }
        if (args.size() > 0)
            modifications = args;
    }
    private void addArguments(Modification md)
    {
        if (md == null)
        {
            return;
        }
        if (md instanceof ClassModification)
        {
            ClassModification clsMod = (ClassModification)md;
            addArguments(clsMod);
        }
        else if (md instanceof CompleteModification)
        {
            CompleteModification compMod = (CompleteModification)md;
            ClassModification clsMod = compMod.getClassModification();
            addArguments(clsMod);
        }
        else if (md instanceof ValueModification)
        {
            // do nothing. ValueModification has not arguments
            // we match here just so as to make the next clause more valuable
        }
        else
        {
            System.out.println("Warning! 201705171213 Unhandled modification: " + md.getClass().toString());
        }
    }
    private void addValue(ClassRedeclare cr)
    {
        if (cr == null)
            return;
        String val = cr.toString();
        int idx = val.indexOf("=");
        if ((idx > -1) && ((idx+1) < val.length()))
            value = val.substring(idx+1).trim();
    }
    private void addValue(ComponentModification cm)
    {
        if (cm.hasModification())
        {
            Modification md = cm.getModification();
            addValue(md);
        }
    }
    private void addValue(Modification md)
    {
        if (md == null)
            return;
        if (md instanceof ValueModification)
        {
            ValueModification vm = (ValueModification)md;
            addValue(vm);
        }
        else if (md instanceof CompleteModification)
        {
            ValueModification vm = ((CompleteModification) md).getValueModification();
            addValue(vm);
        }
        else
        {
            System.out.print("Warning! 2017050171118: Unknown modification: " + md.getClass().toString());
        }
    }
    private void addValue(ValueModification vm)
    {
        if (vm == null)
        {
            return;
        }
        addValue(vm.getExp());
    }
    private void addValue(Exp e)
    {
        if (e == null)
        {
            return;
        }
        String valStr = e.toString();
        if (valStr.length() > 0)
        {
            value = valStr;
        }
    }
    private void addComment(ComponentModification cm)
    {
        Comment c = cm.getComment();
        addComment(c);
    }
    private void addComment(Comment c)
    {
        if (c == null)
            return;
        StringComment sc = c.getStringComment();
        if (sc != null)
        {
            String strCmnt = sc.getComment();
            if (strCmnt.length() != 0)
                comment = strCmnt;
        }
    }
    private void addQualifiers(ComponentModification cm)
    {
        ArrayList<String> qs = new ArrayList<>();
        if (cm.hasFinal())
            qs.add("final");
        if (cm.hasEach())
            qs.add("each");
        if (qs.size() > 0)
            qualifiers = qs;
    }
    String getName()
    {
        return name;
    }
    String getComment()
    {
        return comment;
    }
    List<String> getQualifiers()
    {
        return App.cloneQualifiers(qualifiers);
    }
    String getValue()
    {
        return value;
    }
    List<SrcAstMod> getModifications()
    {
        return App.cloneModifications(modifications);
    }
    Map<String,String> getAnnotations()
    {
        return App.cloneAnnotations(annotations);
    }
    boolean hasModifications()
    {
        if (modifications == null)
            return false;
        if (modifications.isEmpty())
            return false;
        return true;
    }
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(217,11)
                .append(name)
                .append(comment)
                .append(qualifiers)
                .append(value)
                .append(modifications)
                .append(annotations)
                .toHashCode();
    }
    @Override
    public boolean equals(Object other)
    {
        if (other == null)
            return false;
        if (this == other)
            return true;
        if (!(other instanceof SrcAstMod))
            return false;
        SrcAstMod rhs = (SrcAstMod)other;
        return new EqualsBuilder()
                .append(name, rhs.name)
                .append(comment, rhs.comment)
                .append(qualifiers, rhs.qualifiers)
                .append(value, rhs.value)
                .append(modifications, rhs.modifications)
                .append(annotations, rhs.annotations)
                .isEquals();
    }
}
