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

import com.beust.jcommander.JCommander;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.nio.file.Path;

import static org.junit.Assert.*;

/**
 *
 */
public class ArgsTest
{
    private static Args args;
    private static ResourceHelper rh;
    private static File out;
    private static File path1;
    private static File path2;
    private void parseArgs(String[] rawArgs)
    {
        JCommander.newBuilder()
                .addObject(args)
                .build()
                .parse(rawArgs);
    }
    @BeforeClass
    public static void setUp()
    {
        rh = new ResourceHelper();
        out = new File(rh.getTempDir(), "test_that_changes_are_detected.json");
        path1 = rh.getResourceFile("a_v1.json");
        path2 = rh.getResourceFile("a_v2.json");
        args = new Args();
    }
    @AfterClass
    public static void tearDown()
    {
        rh.cleanUp();
    }
    @Test
    public void getOutputPath() throws Exception
    {
        String[] rawArgs = new String[]{"-o", out.getAbsolutePath(), path1.getAbsolutePath(), path2.getAbsolutePath()};
        parseArgs(rawArgs);
        assertTrue(args.getOutputPath().equals(out.getAbsolutePath()));
    }
    @Test
    public void getOldVersionApiFile() throws Exception
    {
        String[] rawArgs = new String[]{"-o", out.getAbsolutePath(), path1.getAbsolutePath(), path2.getAbsolutePath()};
        parseArgs(rawArgs);
        assertTrue(args.getOldVersionApiFile().equals(path1.getAbsolutePath()));
    }
    @Test
    public void getNewVersionApiFile() throws Exception
    {
        String[] rawArgs = new String[]{"-o", out.getAbsolutePath(), path1.getAbsolutePath(), path2.getAbsolutePath()};
        parseArgs(rawArgs);
        assertTrue(args.getNewVersionApiFile().equals(path2.getAbsolutePath()));
    }
    @Test
    public void testUseOfLongNameForFileOutput() throws Exception
    {
        String[] rawArgs = new String[]{
                "--output", out.getAbsolutePath(), path1.getAbsolutePath(), path2.getAbsolutePath()};
        parseArgs(rawArgs);
        assertEquals(out.getAbsolutePath(), args.getOutputPath());
    }
}
