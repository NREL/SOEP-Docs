package com.energyplus.soep;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class SrcAstClass
{
    private String name;
    private String comment;
    private List<String> qualifiers;
    private List<SrcAstExtClause> superClasses;
    private List<SrcAstCompDecl> components;
    private List<SrcAstClass> classes;
    private Map<String,String> annotations;
    private String redeclareAs;
    SrcAstClass(
            String nameIn,
            String commentIn,
            List<String> qualifiersIn,
            List<SrcAstExtClause> superClassesIn,
            List<SrcAstCompDecl> componentsIn,
            List<SrcAstClass> classesIn,
            Map<String,String> annotationsIn,
            String redeclareAsIn)
    {
        name = nameIn;
        comment = commentIn;
        qualifiers = qualifiersIn;
        superClasses = superClassesIn;
        components = componentsIn;
        classes = classesIn;
        annotations = annotationsIn;
        redeclareAs = redeclareAsIn;
    }
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder()
                .append(name)
                .append(comment)
                .append(qualifiers)
                .append(superClasses)
                .append(components)
                .append(classes)
                .append(annotations)
                .append(redeclareAs)
                .toHashCode();
    }
    @Override
    public boolean equals(Object other)
    {
        if (other == null)
            return false;
        if (other == this)
            return true;
        if (!(other instanceof SrcAstClass))
            return false;
        SrcAstClass rhs = (SrcAstClass)other;
        return new EqualsBuilder()
                .append(name, rhs.name)
                .append(comment, rhs.comment)
                .append(qualifiers, rhs.qualifiers)
                .append(superClasses, rhs.superClasses)
                .append(components, rhs.components)
                .append(classes, rhs.classes)
                .append(annotations, rhs.annotations)
                .append(redeclareAs, rhs.redeclareAs)
                .isEquals();
    }
    public String getName()
    {
        return name;
    }
    public String getComment()
    {
        return comment;
    }
    public List<String> getQualifiers()
    {
        return qualifiers;
    }
    /**
    public boolean isParameter()
    {
        if (qualifiers == null)
            return false;
        return qualifiers.contains("parameter");
    }
     **/
    public List<SrcAstExtClause> getSuperClasses()
    {
        return superClasses;
    }
    public List<SrcAstCompDecl> getComponents()
    {
        return components;
    }
    public List<SrcAstClass> getClasses()
    {
        return classes;
    }
    public Map<String,String> getAnnotations()
    {
        return annotations;
    }
    public String getRedeclareAs()
    {
        return redeclareAs;
    }
}
