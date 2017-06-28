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
