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
