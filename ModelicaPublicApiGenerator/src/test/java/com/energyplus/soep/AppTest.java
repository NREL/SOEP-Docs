package com.energyplus.soep;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import static org.junit.Assert.*;

/**
 *
 */
public class AppTest
{
    private File tmpDir;
    private File out;
    private File mbl;
    private File msl;
    private File singleFile;
    private String dottedPath1 = "Buildings.Fluid.Movers.FlowControlled_m_flow";
    private String dottedPath2 = "PressureDrop";
    @Before
    public void setUp()
    {
        try
        {
            tmpDir = Files.createTempDirectory("AppTest").toFile();
        }
        catch (IOException e)
        {
            fail("Unable to create temporary directory");
        }
        out = new File(tmpDir,"junk.json");
        mbl = new File("MBL/Buildings");
        msl = new File("MSL");
        singleFile = new File("src/test/resources/PressureDrop.mo");
    }
    @After
    public void tearDown()
    {
        if (out.exists())
            out.delete();
        if (tmpDir.exists())
            tmpDir.delete();
    }
    @Test
    public void testWeCanGenerateAnApiFileForAnEntireLibrary() throws Exception
    {
        assertFalse(out.exists());
        String[] args = new String[]{
                "--quiet",
                "-mp", msl.getAbsolutePath(),
                "--lib", mbl.getAbsolutePath(),
                "-o", out.getAbsolutePath()};
        App.main(args);
        assertTrue(out.exists());
    }
    @Test
    public void testRunningWithAnOnlyFlag() throws Exception
    {
        assertFalse(out.exists());
        String[] args = new String[]{
                "--quiet",
                "--only", dottedPath1,
                "-mp", msl.getAbsolutePath(),
                "--lib", mbl.getAbsolutePath(),
                "-o", out.getAbsolutePath()
        };
        App.main(args);
        assertTrue(out.exists());
        Map<String, SrcAstClass> data = App.loadOutput(out.getAbsolutePath());
        assertEquals(1, data.size());
        assertTrue("the map contains the expected key", data.containsKey(dottedPath1));
        assertEquals(dottedPath1, data.get(dottedPath1).getName());
    }
    @Test
    public void confirmWeCanRunOnSingleFile() throws Exception
    {
        assertFalse(out.exists());
        String[] args = new String[]{
                "--quiet",
                "--only", dottedPath2,
                "-mp", msl.getAbsolutePath(),
                "-mp", mbl.getAbsolutePath(),
                "--lib", singleFile.getAbsolutePath(),
                "-o", out.getAbsolutePath()
        };
        App.main(args);
        assertTrue(out.exists());
        Map<String, SrcAstClass> data = App.loadOutput(out.getAbsolutePath());
        assertEquals(1, data.size());
        assertTrue("the map contains the expected key", data.containsKey(dottedPath2));
        assertEquals(dottedPath2, data.get(dottedPath2).getName());
    }
    @Test
    public void confirmRejectWorks() throws Exception
    {
        assertFalse(out.exists());
        String[] args = new String[]{
                "--quiet",
                "--reject", "Buildings",
                "-mp", msl.getAbsolutePath(),
                "-L", mbl.getAbsolutePath(),
                "-o", out.getAbsolutePath()
        };
        App.main(args);
        assertTrue(out.exists());
        Map<String, SrcAstClass> data = App.loadOutput(out.getAbsolutePath());
        assertEquals(0, data.size());
    }
    @Test
    public void confirmAcceptWorks() throws Exception
    {
        assertFalse(out.exists());
        String[] args = new String[]{
                "--quiet",
                "--accept", "Buildings.BoundaryConditions.SkyTemperature.Examples",
                "-mp", msl.getAbsolutePath(),
                "-L", mbl.getAbsolutePath(),
                "-o", out.getAbsolutePath()
        };
        App.main(args);
        assertTrue(out.exists());
        Map<String, SrcAstClass> data = App.loadOutput(out.getAbsolutePath());
        assertEquals(1, data.size());
    }
    @Test
    public void confirmAcceptAndRejectWorks() throws Exception
    {
        assertFalse(out.exists());
        String[] args = new String[]{
                "--quiet",
                "--accept", "Buildings.Fluid.MassExchangers",
                "--reject", "Examples",
                "-mp", msl.getAbsolutePath(),
                "-L", mbl.getAbsolutePath(),
                "-o", out.getAbsolutePath()
        };
        App.main(args);
        assertTrue(out.exists());
        Map<String, SrcAstClass> data = App.loadOutput(out.getAbsolutePath());
        assertEquals(2, data.size());
    }
    @Test
    public void validateNestedClassRedeclare() throws Exception
    {
        assertFalse(out.exists());
        String[] args = new String[]{
                "--quiet",
                "--only", "Buildings.Fluid.HeatExchangers.WetCoilDiscretized",
                "-mp", msl.getAbsolutePath(),
                "-L", mbl.getAbsolutePath(),
                "-o", out.getAbsolutePath()
        };
        App.main(args);
        assertTrue(out.exists());
        Map<String, SrcAstClass> data = App.loadOutput(out.getAbsolutePath());
        assertEquals(1, data.size());
        assertTrue(data
                .get("Buildings.Fluid.HeatExchangers.WetCoilDiscretized")
                .getSuperClasses().get(0)
                .getModifications().get(1)
                .getModifications().get(0)
                .getQualifiers().contains("redeclare"));
    }
}
