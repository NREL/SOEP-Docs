package com.energyplus.soep;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class SrcAstExtClause
{
    private String nameOfExtendedClass;
    private List<SrcAstMod> modifications;
    private Map<String,String> annotations;
    SrcAstExtClause(
            String nameOfExtendedClassIn,
            List<SrcAstMod> modificationsIn,
            Map<String,String> annotationsIn)
    {
        nameOfExtendedClass = nameOfExtendedClassIn;
        modifications = modificationsIn;
        annotations = annotationsIn;
    }
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder()
                .append(nameOfExtendedClass)
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
        if (!(other instanceof SrcAstExtClause))
            return false;
        SrcAstExtClause rhs = (SrcAstExtClause)other;
        return new EqualsBuilder()
                .append(nameOfExtendedClass, rhs.nameOfExtendedClass)
                .append(modifications, rhs.modifications)
                .append(annotations, rhs.annotations)
                .isEquals();
    }
    public String getNameOfExtendedClass()
    {
        return nameOfExtendedClass;
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
