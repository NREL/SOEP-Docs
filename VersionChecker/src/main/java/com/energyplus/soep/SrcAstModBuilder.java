package com.energyplus.soep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class SrcAstModBuilder
{
    private String name;
    private String comment;
    private List<String> qualifiers;
    private String value;
    private List<SrcAstMod> modifications;
    private Map<String,String> annotations;
    SrcAstModBuilder() {}
    SrcAstModBuilder setName(String newName)
    {
        name = newName;
        return this;
    }
    SrcAstModBuilder setComment(String newComment)
    {
        comment = newComment;
        return this;
    }
    SrcAstModBuilder addQualifier(String newQualifier)
    {
        if (qualifiers == null)
            qualifiers = new ArrayList<>();
        qualifiers.add(newQualifier);
        return this;
    }
    SrcAstModBuilder setValue(String newValue)
    {
        value = newValue;
        return this;
    }
    SrcAstModBuilder addModification(SrcAstMod newMod)
    {
        if (modifications == null)
            modifications = new ArrayList<>();
        modifications.add(newMod);
        return this;
    }
    SrcAstModBuilder addAnnotation(String key, String value)
    {
        if (annotations == null)
            annotations = new HashMap<>();
        annotations.put(key, value);
        return this;
    }
    SrcAstMod build()
    {
        return new SrcAstMod(
                name,
                comment,
                qualifiers,
                value,
                modifications,
                annotations);
    }
}
