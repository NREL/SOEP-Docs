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