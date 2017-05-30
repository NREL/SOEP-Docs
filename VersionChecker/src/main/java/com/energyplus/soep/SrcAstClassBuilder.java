package com.energyplus.soep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class SrcAstClassBuilder
{
    private String name;
    private String comment;
    private List<String> qualifiers;
    private List<SrcAstExtClause> superClasses;
    private List<SrcAstCompDecl> components;
    private List<SrcAstClass> classes;
    private Map<String,String> annotations;
    private String redeclareAs;
    SrcAstClassBuilder() {}
    SrcAstClassBuilder setName(String newName)
    {
        name = newName;
        return this;
    }
    SrcAstClassBuilder setComment(String newComment)
    {
        comment = newComment;
        return this;
    }
    SrcAstClassBuilder addQualifier(String newQualifier)
    {
        if (qualifiers == null)
            qualifiers = new ArrayList<>();
        qualifiers.add(newQualifier);
        return this;
    }
    SrcAstClassBuilder addSuperClass(SrcAstExtClause newSuperClass)
    {
        if (superClasses == null)
            superClasses = new ArrayList<>();
        superClasses.add(newSuperClass);
        return this;
    }
    SrcAstClassBuilder addComponent(SrcAstCompDecl newComp)
    {
        if (components == null)
            components = new ArrayList<>();
        components.add(newComp);
        return this;
    }
    SrcAstClassBuilder addClass(SrcAstClass newClass)
    {
        if (classes == null)
            classes = new ArrayList<>();
        classes.add(newClass);
        return this;
    }
    SrcAstClassBuilder addAnnotation(String key, String val)
    {
        if (annotations == null)
            annotations = new HashMap<>();
        annotations.put(key, val);
        return this;
    }
    SrcAstClassBuilder setRedeclareAs(String redecl)
    {
        redeclareAs = redecl;
        return this;
    }
    SrcAstClass build()
    {
        return new SrcAstClass(
                name,
                comment,
                qualifiers,
                superClasses,
                components,
                classes,
                annotations,
                redeclareAs);
    }
}
