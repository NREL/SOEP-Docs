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
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
// LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
// SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
// POSSIBILITY OF SUCH DAMAGE.
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
