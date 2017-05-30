package com.energyplus.soep;

import java.util.*;

/**
 * Flat AST for a Class (model, package, connector, etc.)
 *
 * Extending a class means that we will merge the entire public API of the class being
 * extended into the existing class. During merge, if there is a conflict between the
 * existing class and an extended class, the existing class "wins".
 *
 * We need to make a choice as to whether all included components should have their
 * components fully expanded.
 */
public class FlatAstClass
{
    private String name;
    private String comment;
    private List<String> qualifiers;
    private List<SrcAstCompDecl> components;
    private Map<String,String> annotations;
    FlatAstClass(SrcAstClass sac, Map<String,FlatAstClass> supers) throws DependencyNotPresentException
    {
        name = sac.getName();
        comment = sac.getComment();
        qualifiers = sac.getQualifiers();
        components = sac.getComponents();
        annotations = sac.getAnnotations();
        if (sac.hasSuperClasses())
        {
            for (SrcAstExtClause ec : sac.getSuperClasses())
            {
                FlatAstClass sc = supers.get(ec.getNameOfExtendedClass());
                if (sc != null)
                {
                    mergeComponents(sc.components);
                    applyModifications(ec);
                }
                else
                {
                    throw new DependencyNotPresentException(ec.getNameOfExtendedClass());
                }
            }
        }
        // if we don't have superClasses, there is nothing to do. We have already defined our FlatAstClass!
    }
    FlatAstClass(
            String nameIn,
            String commentIn,
            List<String> qualifiersIn,
            List<SrcAstCompDecl> componentsIn,
            Map<String,String> annotationsIn
    )
    {
        name = nameIn;
        comment = commentIn;
        qualifiers = qualifiersIn;
        components = componentsIn;
        annotations = annotationsIn;
    }
    private void applyModifications(SrcAstExtClause ec)
    {
        SrcAstCompDecl target;
        for (SrcAstMod m : ec.getModifications())
        {
            String name = m.getName();
            target = getComponentByName(name);
            if (target == null)
                continue;
            String value = m.getValue();
            target.setValue(value);
        }
    }
    SrcAstCompDecl getComponentByName(String name)
    {
        if (components == null)
            return null;
        if (components.isEmpty())
            return null;
        for (SrcAstCompDecl c : components)
        {
            if (c.getName().equals(name))
                return c;
        }
        return null;
    }
    /**
     * Merges other components onto this object's components. If an incoming object
     * has the same name as an existing component, then it is NOT added. The existing
     * component "wins" in a dispute.
     * @param otherComponents, the other components to merge in
     */
    private void mergeComponents(List<SrcAstCompDecl> otherComponents)
    {
        if (otherComponents == null)
            return;
        if (otherComponents.isEmpty())
            return;
        if (components == null)
            components = new ArrayList<>();
        Set<String> ns = getComponentNameSet();
        for (SrcAstCompDecl c : otherComponents)
        {
            if (!ns.contains(c.getName()))
                components.add(c);
        }
    }
    private Set<String> getComponentNameSet()
    {
        if (components == null)
            components = new ArrayList<>();
        Set<String> names = new HashSet<>();
        for (SrcAstCompDecl c : components)
        {
            names.add(c.getName());
        }
        return names;
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
    List<SrcAstCompDecl> getComponents()
    {
        return App.cloneComponents(components);
    }
    Map<String,String> getAnnotations()
    {
        return App.cloneAnnotations(annotations);
    }
    boolean equals(FlatAstClass other)
    {
        return ((other != null) &&
                App.stringEquals(name, other.name) &&
                App.stringEquals(comment, other.comment) &&
                App.listOfStringEquals(qualifiers, other.qualifiers) &&
                App.listOfSrcAstCompDeclEquals(components, other.components) &&
                App.mapStrStrEquals(annotations, other.annotations));
    }
}
