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

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 *
 */
public class SrcAstCompDeclTest
{
    @Test
    public void testEquals() throws Exception
    {
        SrcAstCompDecl c1 = new SrcAstCompDecl(
                "className",
                new ArrayList<String>() {{
                    add("qualifier");
                }},
                "name",
                "comment",
                "value",
                new ArrayList<>(),
                "arraySubscripts",
                "conditional clause",
                "constraining clause",
                new HashMap<String,String>() {{
                    put("annotation1", "value1");
                }});
        SrcAstCompDecl c2 = new SrcAstCompDecl(
                "className",
                new ArrayList<String>() {{
                    add("qualifier");
                }},
                "name",
                "comment",
                "value",
                new ArrayList<>(),
                "arraySubscripts",
                "conditional clause",
                "constraining clause",
                new HashMap<String,String>() {{
                    put("annotation1", "value1");
                }});
        SrcAstCompDecl c3 = new SrcAstCompDecl(
                "classNameDIFF",
                new ArrayList<String>() {{
                    add("qualifier");
                }},
                "name",
                "comment",
                "value",
                new ArrayList<>(),
                "arraySubscripts",
                "conditional clause",
                "constraining clause",
                new HashMap<String,String>() {{
                    put("annotation1", "value1");
                }});
        assertTrue("same value objects should equal", c1.equals(c2));
        assertFalse("non-same value objects should not equal", c1.equals(c3));
        assertTrue("hash values for equal objects should be the same",
                c1.hashCode() == c2.hashCode());
    }
}
