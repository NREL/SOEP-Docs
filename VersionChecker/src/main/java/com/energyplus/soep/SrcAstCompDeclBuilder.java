package com.energyplus.soep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class SrcAstCompDeclBuilder
{
    private String className;
    private List<String> qualifiers;
    private String name;
    private String comment;
    private String value;
    private List<SrcAstMod> modifications;
    private String arraySubscripts;
    private String conditionalClause;
    private String constrainingClause;
    private Map<String,String> annotations;
    SrcAstCompDeclBuilder() {}
    SrcAstCompDeclBuilder setClassName(String newClassName)
    {
        className = newClassName;
        return this;
    }
    SrcAstCompDeclBuilder addQualifier(String newQualifier)
    {
        if (qualifiers == null)
            qualifiers = new ArrayList<>();
        qualifiers.add(newQualifier);
        return this;
    }
    SrcAstCompDeclBuilder setName(String newName)
    {
        name = newName;
        return this;
    }
    SrcAstCompDeclBuilder setComment(String newComment)
    {
        comment = newComment;
        return this;
    }
    SrcAstCompDeclBuilder setValue(String newValue)
    {
        value = newValue;
        return this;
    }
    SrcAstCompDeclBuilder addModification(SrcAstMod newMod)
    {
        if (modifications == null)
            modifications = new ArrayList<>();
        modifications.add(newMod);
        return this;
    }
    SrcAstCompDeclBuilder setArraySubscripts(String newArraySubscripts)
    {
        arraySubscripts = newArraySubscripts;
        return this;
    }
    SrcAstCompDeclBuilder setConditionalClause(String newClause)
    {
        conditionalClause = newClause;
        return this;
    }
    SrcAstCompDeclBuilder setConstrainingClause(String newClause)
    {
        constrainingClause = newClause;
        return this;
    }
    SrcAstCompDeclBuilder addAnnotation(String key, String annotation)
    {
        if (annotations == null)
            annotations = new HashMap<>();
        annotations.put(key, annotation);
        return this;
    }
    SrcAstCompDecl build()
    {
        return new SrcAstCompDecl(
                className,
                qualifiers,
                name,
                comment,
                value,
                modifications,
                arraySubscripts,
                conditionalClause,
                constrainingClause,
                annotations);
    }
}
