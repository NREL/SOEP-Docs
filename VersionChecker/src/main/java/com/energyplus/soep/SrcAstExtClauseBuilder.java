package com.energyplus.soep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class SrcAstExtClauseBuilder
{
    private String nameOfExtendedClass;
    private List<SrcAstMod> modifications;
    private Map<String,String> annotations;
    SrcAstExtClauseBuilder() {}
    SrcAstExtClauseBuilder setNameOfExtendedClass(String newName)
    {
        nameOfExtendedClass = newName;
        return this;
    }
    SrcAstExtClauseBuilder addModification(SrcAstMod newMod)
    {
        if (modifications == null)
            modifications = new ArrayList<>();
        modifications.add(newMod);
        return this;
    }
    SrcAstExtClauseBuilder addAnnotation(String key, String value)
    {
        if (annotations == null)
            annotations = new HashMap<>();
        annotations.put(key, value);
        return this;
    }
    SrcAstExtClause build()
    {
        return new SrcAstExtClause(
                nameOfExtendedClass,
                modifications,
                annotations);
    }
}
