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