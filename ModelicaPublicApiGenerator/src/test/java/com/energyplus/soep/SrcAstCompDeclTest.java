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