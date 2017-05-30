package com.energyplus.soep;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;
import java.util.Map;

/**
 *
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
            Map<String,String> annotationsIn)
    {
        name = nameIn;
        comment = commentIn;
        qualifiers = qualifiersIn;
        value = valueIn;
        modifications = modificationsIn;
        annotations = annotationsIn;
    }
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(17, 31)
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
        if (other == this)
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
    public String getValue()
    {
        return value;
    }
    public List<SrcAstMod> getModifications()
    {
        return modifications;
    }
    public Map<String,String> getAnnotations()
    {
        return annotations;
    }
}
