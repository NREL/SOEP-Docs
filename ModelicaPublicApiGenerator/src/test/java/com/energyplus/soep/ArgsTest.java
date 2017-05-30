package com.energyplus.soep;

import com.beust.jcommander.JCommander;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 *
 */
public class ArgsTest
{
    private File out;
    private File mbl;
    private File msl;
    private String single1;
    private String[] rawArgs1;
    private String[] rawArgs2;
    private String[] rawArgs3;
    private String[] rawArgs4;
    private Args args;
    @Before
    public void setUp()
    {
        out = new File("junk.json");
        mbl = new File("MBL/Buildings");
        msl = new File("MSL");
        single1 = "Buildings.Fluid.Movers.FlowControlled_m_flow.mo";
        rawArgs1 = new String[]{
                "-mp", msl.getAbsolutePath(),
                "--lib", mbl.getAbsolutePath(),
                "-o", out.getAbsolutePath()};
        rawArgs2 = new String[]{
                "-mp", msl.getAbsolutePath(),
                "--lib", mbl.getAbsolutePath(),
                "-o", out.getAbsolutePath(),
                "--only", single1};
        rawArgs3 = new String[]{
                "-mp", msl.getAbsolutePath(),
                "--lib", mbl.getAbsolutePath(),
                "--reject", "examples",
                "--reject", "validation",
                "--reject", "baseclasses",
                "-o", out.getAbsolutePath()};
        rawArgs4 = new String[]{
                "-mp", msl.getAbsolutePath(),
                "--lib", mbl.getAbsolutePath(),
                "--accept", "Buildings.Airflow.Multizone.Types",
                "-o", out.getAbsolutePath()};
    }
    private void parseArgs(String[] rawArgs)
    {
        args = new Args();
        JCommander.newBuilder()
                .addObject(args)
                .build()
                .parse(rawArgs);
    }
    @Test
    public void testThatArgsParsesCorrectly() throws Exception
    {
        parseArgs(rawArgs1);
        List<String> mps = new ArrayList<>();
        mps.add(msl.getAbsolutePath());
        assertEquals(args.getModelicaPaths(), mps);
        assertEquals(args.getLib(), mbl.getAbsolutePath());
        assertEquals(args.getOutputPath(), out.getAbsolutePath());
        assertFalse("does not have singles", args.hasSingles());
    }
    @Test
    public void testArgsToString() throws Exception
    {
        parseArgs(rawArgs1);
        String actual = args.toString();
        String expected = "LIBRARY PATH:\n" +
                mbl.getAbsolutePath() + "\n" +
                "MODELICAPATH = " + args.getModelicaPathsAsString() + "\n" +
                "OUTPUT_FILE  = " + args.getOutputPath();
        assertEquals(expected, actual);
    }
    @Test
    public void testOnlyFlag() throws Exception
    {
        parseArgs(rawArgs2);
        List<String> onlies = new ArrayList<String>(){{
            add(single1);
        }};
        assertEquals(args.getSingles(), onlies);
        assertTrue("test that reports it has singles", args.hasSingles());
        assertFalse("test that does not have rejects", args.hasRejects());
    }
    @Test
    public void testRejectFlag() throws Exception
    {
        parseArgs(rawArgs3);
        List<String> rejects = args.getRejects();
        assertTrue("rejects contains 'examples'", rejects.contains("examples"));
        assertTrue("rejects contains 'validation'", rejects.contains("validation"));
        assertTrue("rejects contains 'baseclasses'", rejects.contains("baseclasses"));
        assertTrue("test that has rejects", args.hasRejects());
        assertEquals(3, rejects.size());
    }
    @Test
    public void testAcceptFlag() throws Exception
    {
        parseArgs(rawArgs4);
        List<String> accepts = args.getAccepts();
        assertTrue("accepts contains 'Buildings.Airflow.Multizone.Types'",
                accepts.contains("Buildings.Airflow.Multizone.Types"));
        assertFalse("has no rejects", args.hasRejects());
        assertTrue("has accepts", args.hasAccepts());
        assertEquals(1, accepts.size());
    }
}
