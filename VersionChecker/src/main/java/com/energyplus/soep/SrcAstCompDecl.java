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

import java.util.List;
import java.util.Map;

/**
 *
 */
public class SrcAstCompDecl
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
    SrcAstCompDecl(
            String classNameIn,
            List<String> qualifiersIn,
            String nameIn,
            String commentIn,
            String valueIn,
            List<SrcAstMod> modificationsIn,
            String arraySubscriptsIn,
            String conditionalClauseIn,
            String constrainingClauseIn,
            Map<String,String> annotationsIn)
    {
        className = classNameIn;
        qualifiers = qualifiersIn;
        name = nameIn;
        comment = commentIn;
        value = valueIn;
        modifications = modificationsIn;
        arraySubscripts = arraySubscriptsIn;
        conditionalClause = conditionalClauseIn;
        constrainingClause = constrainingClauseIn;
        annotations = annotationsIn;
    }
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder()
                .append(className)
                .append(qualifiers)
                .append(name)
                .append(comment)
                .append(value)
                .append(modifications)
                .append(arraySubscripts)
                .append(conditionalClause)
                .append(constrainingClause)
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
        if (!(other instanceof SrcAstCompDecl))
            return false;
        SrcAstCompDecl rhs = (SrcAstCompDecl)other;
        return new EqualsBuilder()
                .append(className, rhs.className)
                .append(qualifiers, rhs.qualifiers)
                .append(name, rhs.name)
                .append(comment, rhs.comment)
                .append(value, rhs.value)
                .append(modifications, rhs.modifications)
                .append(arraySubscripts, rhs.arraySubscripts)
                .append(conditionalClause, rhs.conditionalClause)
                .append(constrainingClause, rhs.constrainingClause)
                .append(annotations, rhs.annotations)
                .isEquals();
    }
    public String getClassName()
    {
        return className;
    }
    public List<String> getQualifiers()
    {
        return qualifiers;
    }
    public String getName()
    {
        return name;
    }
    public String getComment()
    {
        return comment;
    }
    public String getValue()
    {
        return value;
    }
    public List<SrcAstMod> getModifications()
    {
        return modifications;
    }
    public String getArraySubscripts()
    {
        return arraySubscripts;
    }
    public String getConditionalClause()
    {
        return conditionalClause;
    }
    public String getConstrainingClause()
    {
        return constrainingClause;
    }
    public Map<String,String> getAnnotations()
    {
        return annotations;
    }
    public boolean isParameter()
    {
        if (qualifiers == null)
            return false;
        return qualifiers.contains("parameter");
    }
}
