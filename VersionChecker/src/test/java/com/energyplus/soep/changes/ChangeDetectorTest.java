package com.energyplus.soep.changes;

import com.energyplus.soep.JsonReader;
import com.energyplus.soep.ResourceHelper;
import com.energyplus.soep.SrcAstClass;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 *
 */
public class ChangeDetectorTest
{
    private static ResourceHelper rh;
    private JsonReader jr;
    private File pathTo_A_Ver1;
    private File pathTo_A_Ver2;
    private File pathTo_A_Ver3;
    private File pathTo_A_Ver4;
    private File pathTo_B_Ver1;
    private File pathTo_C_Ver1;
    private File pathTo_C_Ver2;
    @BeforeClass
    public static void setUpResources()
    {
        rh = new ResourceHelper(true);
    }
    @AfterClass
    public static void tearDownResources()
    {
        rh.cleanUp();
    }
    @Before
    public void setUp()
    {
        jr = new JsonReader();
        pathTo_A_Ver1 = rh.getResourceFile("a_v1.json");
        pathTo_A_Ver2 = rh.getResourceFile("a_v2.json");
        pathTo_A_Ver3 = rh.getResourceFile("a_v3.json");
        pathTo_A_Ver4 = rh.getResourceFile("a_v4.json");
        pathTo_B_Ver1 = rh.getResourceFile("b_v1.json");
        pathTo_C_Ver1 = rh.getResourceFile("c_v1.json");
        pathTo_C_Ver2 = rh.getResourceFile("c_v2.json");
    }
    private Map<String,SrcAstClass> readFromPath(File path)
    {
        String content;
        Map<String,SrcAstClass> m;
        String absPath = path.getAbsolutePath();
        try
        {
            content = rh.readFile(absPath);
            m = jr.readMap_String_SrcAstClass(content);
        }
        catch (IOException e)
        {
            fail("IOException in loading " + absPath + ": " + e);
            m = new HashMap<>();
        }
        return m;
    }
    @Test
    public void testThatWeDetectChange() throws Exception
    {
        Map<String,SrcAstClass> v1 = readFromPath(pathTo_A_Ver1);
        Map<String,SrcAstClass> v2 = readFromPath(pathTo_A_Ver2);
        ChangeDetector cd = new ChangeDetector(v1, v2);
        List<String> changes = cd.getChanges();
        assertEquals(changes.size(), 1);
        String c0 = changes.get(0);
        assertEquals(c0, "ComponentDefaultValueRemoved(" +
                "class_name = \"Buildings.Example.A\", " +
                "component_name = \"allowFlowReversal\", old_default_value = \"true\")");
    }
    @Test
    public void testThatWeDetectNoChange() throws Exception
    {
        Map<String,SrcAstClass> v1 = readFromPath(pathTo_A_Ver1);
        // Note: we use the path to version 1 twice. Two different objects but same content
        Map<String,SrcAstClass> v2 = readFromPath(pathTo_A_Ver1);
        ChangeDetector cd = new ChangeDetector(v1, v2);
        List<String> changes = cd.getChanges();
        assertEquals(changes.size(), 0);
    }
    @Test
    public void detectClassNotPresent() throws Exception
    {
        Map<String,SrcAstClass> v1 = readFromPath(pathTo_A_Ver1);
        Map<String,SrcAstClass> v2 = readFromPath(pathTo_B_Ver1);
        ChangeDetector cd = new ChangeDetector(v1, v2);
        List<String> changes = cd.getChanges();
        assertEquals(changes.size(), 1);
        assertEquals(changes.get(0), "ClassNoLongerExists(class_name = \"Buildings.Example.A\")");
    }
    @Test
    public void detectChangeInComponent() throws Exception
    {
        Map<String,SrcAstClass> v1 = readFromPath(pathTo_A_Ver1);
        Map<String,SrcAstClass> v2 = readFromPath(pathTo_A_Ver3);
        ChangeDetector cd = new ChangeDetector(v1, v2);
        List<String> changes = cd.getChanges();
        assertEquals(1, changes.size());
        assertEquals("ComponentsRemoved(class_name = \"Buildings.Example.A\", " +
                        "component_names = \"allowFlowReversal\")",
                changes.get(0));
    }
    @Test
    public void detectChangeInComponentName() throws Exception
    {
        Map<String,SrcAstClass> v1 = readFromPath(pathTo_C_Ver1);
        Map<String,SrcAstClass> v2 = readFromPath(pathTo_C_Ver2);
        ChangeDetector cd = new ChangeDetector(v1, v2);
        List<String> changes = cd.getChanges();
        assertEquals(1, changes.size());
        assertEquals("ComponentsRemoved(class_name = \"Buildings.Fluid.Movers.FlowControlled_m_flow\", " +
                        "component_names = \"m_flow_in\")",
                changes.get(0));
    }
    @Test
    public void detectAddingNewParameterWithoutDefaults() throws Exception
    {
        Map<String,SrcAstClass> v1 = readFromPath(pathTo_A_Ver1);
        Map<String,SrcAstClass> v2 = readFromPath(pathTo_A_Ver4);
        ChangeDetector cd = new ChangeDetector(v1, v2);
        List<String> changes = cd.getChanges();
        assertEquals(1, changes.size());
        assertEquals("ComponentsAddedWithoutDefault(class_name = \"Buildings.Example.A\", " +
                        "component_names = \"aNewVariable\")",
                changes.get(0));
    }
}