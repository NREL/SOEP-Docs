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

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 *
 */
public class SrcAstExtClauseBuilderTest
{
    private SrcAstExtClauseBuilder b;
    @Before
    public void setUp()
    {
        b = new SrcAstExtClauseBuilder();
    }
    @Test
    public void setNameOfExtendedClass() throws Exception
    {
        SrcAstExtClause actual = b.setNameOfExtendedClass("test").build();
        SrcAstExtClause expected = new SrcAstExtClause(
                "test",
                null,
                null);
        assertTrue(expected.equals(actual));
    }
    @Test
    public void addModification() throws Exception
    {
        SrcAstMod m = new SrcAstModBuilder()
                .setName("test")
                .build();
        SrcAstExtClause actual = b
                .addModification(m)
                .build();
        SrcAstMod mExpect = new SrcAstMod(
                "test",
                null,
                null,
                null,
                null,
                null);
        List<SrcAstMod> mods = new ArrayList<>();
        mods.add(mExpect);
        SrcAstExtClause expected = new SrcAstExtClause(
                null,
                mods,
                null);
        assertTrue(expected.equals(actual));
    }
    @Test
    public void addAnnotation() throws Exception
    {
        SrcAstExtClause actual = b
                .addAnnotation("test", "test it!")
                .build();
        Map<String,String> annExp = new HashMap<>();
        annExp.put("test", "test it!");
        SrcAstExtClause expected = new SrcAstExtClause(
                null,
                null,
                annExp);
        assertTrue(expected.equals(actual));
    }
}
