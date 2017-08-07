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

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 *
 */
public class JsonReaderTest
{
    private static ResourceHelper rh;
    @BeforeClass
    public static void setUp()
    {
        rh = new ResourceHelper(false);
    }
    @AfterClass
    public static void tearDown()
    {
        rh.cleanUp();
    }
    @Test
    public void testReadingFromJsonFile() throws Exception
    {
        File path1 = rh.getResourceFile("a_v1.json");
        Map<String,SrcAstClass> actual = new JsonReader().readMap_String_SrcAstClass(rh.readFile(path1.getAbsolutePath()));
        Map<String,SrcAstClass> expected = new HashMap<>();
        SrcAstCompDecl c = new SrcAstCompDeclBuilder()
                .setClassName("Boolean")
                .addQualifier("parameter")
                .setName("allowFlowReversal")
                .setValue("true")
                .build();
        SrcAstClass a = new SrcAstClassBuilder()
                .setName("Buildings.Example.A")
                .addQualifier("model")
                .addComponent(c)
                .build();
        expected.put("Buildings.Example.A", a);
        assertTrue(expected.equals(actual));
    }
}
