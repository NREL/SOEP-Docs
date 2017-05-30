package com.energyplus.soep;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 *
 */
public class SrcAstModTest
{
    @Test
    public void testThatHashCodeAndEqualsWork() throws Exception
    {
        SrcAstMod c1 = new SrcAstMod(
                "name",
                "comment",
                new ArrayList<String>(){{
                    add("qualifier");
                }},
                "value",
                new ArrayList<>(),
                new HashMap<String,String>(){{
                    put("annotation", "annotationValue");
                }});
        SrcAstMod c2 = new SrcAstMod(
                "name",
                "comment",
                new ArrayList<String>(){{
                    add("qualifier");
                }},
                "value",
                new ArrayList<>(),
                new HashMap<String,String>(){{
                    put("annotation", "annotationValue");
                }});
        SrcAstMod c3 = new SrcAstMod(
                "name2",
                "comment",
                new ArrayList<String>(){{
                    add("qualifier");
                }},
                "value",
                new ArrayList<>(),
                new HashMap<String,String>(){{
                    put("annotation", "annotationValue");
                }});
        assertTrue("equal objects should be equal", c1.equals(c2));
        assertFalse("unequal objects should not equal", c1.equals(c3));
        assertTrue("hash codes of equal objects should be equal", c1.equals(c2));
    }
}
