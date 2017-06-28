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

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 *
 */
public class AppTest
{
    private static ResourceHelper rh;
    @BeforeClass
    public static void setUp()
    {
        rh = new ResourceHelper();
    }
    @AfterClass
    public static void tearDown()
    {
        rh.cleanUp();
    }
    @Test
    public void testThatMainCreatesAnOutputFile() throws Exception
    {
        File out = new File(rh.getTempDir(), "test_that_main_creates_an_output_file.json");
        File path1 = rh.getResourceFile("a_v1.json");
        File path2 = rh.getResourceFile("a_v2.json");
        App.main(new String[]{"-o", out.getAbsolutePath(), path1.getAbsolutePath(), path2.getAbsolutePath()});
        assertTrue(out.exists());
    }
    @Test
    public void testThatChangesAreDetected() throws Exception
    {
        File out = new File(rh.getTempDir(), "test_that_changes_are_detected.json");
        File path1 = rh.getResourceFile("a_v1.json");
        File path2 = rh.getResourceFile("a_v2.json");
        App.main(new String[]{"-o", out.getAbsolutePath(), path1.getAbsolutePath(), path2.getAbsolutePath()});
        Gson g = new Gson();
        String jsonContent = rh.readFile(out.getPath());
        try
        {
            List<String> differences = g.fromJson(jsonContent, new TypeToken<List<String>>(){}.getType());
            assertEquals(1, differences.size());
        }
        catch (JsonSyntaxException e)
        {
            fail("json syntax issue. Actual content:\n" + jsonContent);
        }
    }
    @Test
    public void testOutputFileContents() throws Exception
    {
        File out = new File(rh.getTempDir(), "test_output_file_contents.json");
        File path1 = rh.getResourceFile("a_v1.json");
        File path2 = rh.getResourceFile("a_v2.json");
        App.main(new String[]{"-o", out.getAbsolutePath(), path1.getAbsolutePath(), path2.getAbsolutePath()});
        Gson g = new Gson();
        String jsonContent = rh.readFile(out.getPath());
        try
        {
            List<String> differences = g.fromJson(jsonContent, new TypeToken<List<String>>(){}.getType());
            assertEquals(1, differences.size());
            assertEquals("ComponentDefaultValueRemoved(class_name = \"Buildings.Example.A\", " +
                    "component_name = \"allowFlowReversal\", old_default_value = \"true\")", differences.get(0));
        }
        catch (JsonSyntaxException e)
        {
            fail("json syntax issue. Actual content:\n" + jsonContent);
        }
    }
    @Test
    public void testFailingCase() throws Exception
    {
        File out = new File("extra/version_diffs.json");
        File old_path = new File("extra/old_api.json");
        File new_path = new File("extra/new_api.json");
        App.main(new String[]{"-o", out.getAbsolutePath(), old_path.getAbsolutePath(), new_path.getAbsolutePath()});
        Gson g = new Gson();
        try
        {
            List<String> differences = g.fromJson(
                    new FileReader(out),
                    new TypeToken<List<String>>(){}.getType());
            assertTrue(differences.size() > 0);
        }
        catch (FileNotFoundException e)
        {
            fail("file not found");
        }
        catch (JsonSyntaxException e)
        {
            fail("JSON syntax error");
        }
    }
}
