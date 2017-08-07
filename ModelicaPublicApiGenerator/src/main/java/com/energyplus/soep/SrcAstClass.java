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

import com.google.gson.Gson;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jmodelica.modelica.compiler.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a Modelica "class" (model, block, etc.)
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
    SrcAstClass(SrcAstClass c)
    {
        name = c.name;
        comment = c.comment;
        qualifiers = App.cloneQualifiers(c.qualifiers);
        superClasses = App.cloneSuperClasses(c.superClasses);
        components = App.cloneComponents(c.components);
        classes = App.cloneClasses(c.classes);
    }
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
    SrcAstClass(ClassDecl cd)
    {
        if (!cd.isProtected())
        {
            name = cd.qualifiedName();
            addComment(cd);
            addQualifiers(cd);
            addSuperClasses(cd);
            addClasses(cd);
            addComponents(cd);
            annotations = (new SrcAstAnnotations(cd)).getAnnotations();
        }
    }
    public SrcAstClass(ClassRedeclare cr)
    {
        name = cr.getName().qualifiedName();
        addQualifiers(cr);
        annotations = (new SrcAstAnnotations(cr)).getAnnotations();
        addValue(cr);
    }
    /**
     * There doesn't appear to be a good way to get the right-hand-side of a ClassRedeclare. As such, we
     * convert to a string, look for an "=" sign, and take the trimmed substring as the value.
     * @param cr, the ClassRedeclare instance to process
     */
    private void addValue(ClassRedeclare cr)
    {
        if (cr == null)
            return;
        String val = cr.toString();
        int idx = val.indexOf("=");
        if ((idx > -1) && ((idx+1) < val.length()))
            redeclareAs = val.substring(idx+1).trim();
    }
    boolean hasSuperClasses()
    {
        return ((superClasses != null) && (!superClasses.isEmpty()));
    }
    String getName()
    {
        return name;
    }
    String getComment()
    {
        return comment;
    }
    List<String> getQualifiers()
    {
        return App.cloneQualifiers(qualifiers);
    }
    List<SrcAstCompDecl> getComponents()
    {
        return App.cloneComponents(components);
    }
    List<SrcAstClass> getClasses()
    {
        return App.cloneClasses(classes);
    }
    Map<String,String> getAnnotations()
    {
        return App.cloneAnnotations(annotations);
    }
    List<SrcAstExtClause> getSuperClasses()
    {
        return App.cloneSuperClasses(superClasses);
    }
    private void addComment(ClassDecl cd)
    {
        if (cd.hasStringComment())
            comment = cd.getStringComment().getComment();
    }
    private void addQualifiers(ClassRedeclare cr)
    {
        ArrayList<String> qs = new ArrayList<>();
        BaseClassDecl bcd = cr.getBaseClassDecl();
        if (bcd != null)
        {
            if (bcd.hasRedeclare())
                qs.add("redeclare");
            if (bcd.hasOuter() || bcd.isOuter())
                qs.add("outer");
            if (bcd.hasInner() || bcd.isInner())
                qs.add("inner");
            if (bcd.hasEncapsulated() || bcd.isEncapsulated())
                qs.add("encapsulated");
            if (bcd.hasPartial() || bcd.isPartial())
                qs.add("partial");
            if (bcd.hasReplaceable())
                qs.add("replaceable");
            if (bcd.isBlock())
                qs.add("block");
            if (bcd.isClass())
                qs.add("class");
            if (bcd.isModel())
                qs.add("model");
            if (bcd.isPackage())
                qs.add("package");
            // don't add "public", we only take public fields so it is redundant
            //if (bcd.isPublic())
            //    qs.add("public");
            if (bcd.isProtected())
                qs.add("protected");
        }
        if (cr.hasFinal())
            qs.add("final");
        if (cr.hasEach())
            qs.add("each");
        if (qs.size() > 0)
            qualifiers = qs;
    }
    private void addQualifiers(ClassDecl cd)
    {
        ArrayList<String> qs = new ArrayList<>();
        if (cd.isFullClassDecl())
        {
            FullClassDecl fcd = (FullClassDecl)cd;
            Restriction r = fcd.getRestriction();
            if (r instanceof Connector)
                qs.add("connector");
        }
        if (cd.hasReplaceable())
            qs.add("replaceable");
        if (cd.isBlock())
            qs.add("block");
        if (cd.isClass())
            qs.add("class");
        if (cd.isEncapsulated())
            qs.add("encapsulated");
        if (cd.isExperiment())
            qs.add("experiment");
        if (cd.isInner())
            qs.add("inner");
        if (cd.isInput())
            qs.add("input");
        if (cd.isModel())
            qs.add("model");
        if (cd.isOuter())
            qs.add("outer");
        if (cd.isOutput())
            qs.add("output");
        if (cd.isPackage())
            qs.add("package");
        if (cd.isPartial())
            qs.add("partial");
        if (cd.isPrimitive())
            qs.add("primitive");
        if (cd.isProtected())
            qs.add("protected");
        if (qs.size() > 0)
            qualifiers = qs;
    }
    private void addSuperClasses(ClassDecl cd)
    {
        ArrayList<SrcAstExtClause> ss = new ArrayList<>();
        for (ExtendsClause ec : cd.superClasses())
        {
            ss.add(new SrcAstExtClause(ec));
        }
        if (ss.size() > 0)
            superClasses = ss;
    }
    private void addClasses(ClassDecl cd)
    {
        ArrayList<SrcAstClass> cs = new ArrayList<>();
        for (ClassDecl _cd : cd.classes())
        {
            cs.add(new SrcAstClass(_cd));
        }
        if (cs.size() > 0)
            classes = cs;
    }
    /**
     * Add components that are non-protected (i.e., public).
     * @param cd - the class declaration
     */
    private void addComponents(ClassDecl cd)
    {
        ArrayList<SrcAstCompDecl> cs = new ArrayList<>();
        for (ComponentDecl c : cd.components())
        {
            if (!c.isProtected())
                cs.add(new SrcAstCompDecl(c));
        }
        if (cs.size() > 0)
            components = cs;
    }
    public FlatAstClass flatten(Map<String,FlatAstClass> superClasses) throws DependencyNotPresentException
    {
        return new FlatAstClass(this, superClasses);
    }
    public String toJson()
    {
        Gson g = new Gson();
        return g.toJson(this);
    }
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(17, 31)
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
    public boolean isPackage()
    {
        return qualifiers != null && qualifiers.contains("package");
    }
}
