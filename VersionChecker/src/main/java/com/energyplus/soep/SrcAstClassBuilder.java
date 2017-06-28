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
