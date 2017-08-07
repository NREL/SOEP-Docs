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

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 *
 */
public class SrcAstCompDeclBuilderTest
{
    private SrcAstCompDeclBuilder b;
    @Before
    public void setUp()
    {
        b = new SrcAstCompDeclBuilder();
    }
    @Test
    public void setClassName() throws Exception
    {
        SrcAstCompDecl actual = b
                .setClassName("test")
                .build();
        SrcAstCompDecl expected = new SrcAstCompDecl(
                "test",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        assertTrue(expected.equals(actual));
    }
    @Test
    public void addQualifier() throws Exception
    {
        SrcAstCompDecl actual = b
                .addQualifier("test")
                .build();
        List<String> qs = new ArrayList<>();
        qs.add("test");
        SrcAstCompDecl expected = new SrcAstCompDecl(
                null,
                qs,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        assertTrue(expected.equals(actual));
    }
    @Test
    public void setName() throws Exception
    {
        SrcAstCompDecl actual = b
                .setName("test")
                .build();
        SrcAstCompDecl expected = new SrcAstCompDecl(
                null,
                null,
                "test",
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        assertTrue(expected.equals(actual));
    }
    @Test
    public void setComment() throws Exception
    {
        SrcAstCompDecl actual = b
                .setComment("test")
                .build();
        SrcAstCompDecl expected = new SrcAstCompDecl(
                null,
                null,
                null,
                "test",
                null,
                null,
                null,
                null,
                null,
                null);
        assertTrue(expected.equals(actual));
    }
    @Test
    public void setValue() throws Exception
    {
        SrcAstCompDecl actual = b
                .setValue("test")
                .build();
        SrcAstCompDecl expected = new SrcAstCompDecl(
                null,
                null,
                null,
                null,
                "test",
                null,
                null,
                null,
                null,
                null);
        assertTrue(expected.equals(actual));
    }
    @Test
    public void addModification() throws Exception
    {
        SrcAstMod m = new SrcAstModBuilder().setName("test").build();
        SrcAstCompDecl actual = new SrcAstCompDeclBuilder()
                .addModification(m)
                .build();
        List<SrcAstMod> mods = new ArrayList<>();
        SrcAstMod mExp = new SrcAstMod(
                "test",
                null,
                null,
                null,
                null,
                null);
        mods.add(mExp);
        SrcAstCompDecl expected = new SrcAstCompDecl(
                null,
                null,
                null,
                null,
                null,
                mods,
                null,
                null,
                null,
                null);
        assertTrue(expected.equals(actual));
    }
    @Test
    public void setArraySubscripts() throws Exception
    {
        SrcAstCompDecl actual = b.setArraySubscripts("test").build();
        SrcAstCompDecl expected = new SrcAstCompDecl(
                null,
                null,
                null,
                null,
                null,
                null,
                "test",
                null,
                null,
                null);
        assertTrue(expected.equals(actual));
    }
    @Test
    public void setConditionalClause() throws Exception
    {
        SrcAstCompDecl actual = b.setConditionalClause("test").build();
        SrcAstCompDecl expected = new SrcAstCompDecl(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                "test",
                null,
                null);
        assertTrue(expected.equals(actual));
    }
    @Test
    public void setConstrainingClause() throws Exception
    {
        SrcAstCompDecl actual = b.setConstrainingClause("test").build();
        SrcAstCompDecl expected = new SrcAstCompDecl(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                "test",
                null);
        assertTrue(expected.equals(actual));
    }
    @Test
    public void addAnnotation() throws Exception
    {
        SrcAstCompDecl actual = b
                .addAnnotation("test","test it!")
                .build();
        Map<String,String> m = new HashMap<>();
        m.put("test", "test it!");
        SrcAstCompDecl expected = new SrcAstCompDecl(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                m);
        assertTrue(expected.equals(actual));
    }
}
