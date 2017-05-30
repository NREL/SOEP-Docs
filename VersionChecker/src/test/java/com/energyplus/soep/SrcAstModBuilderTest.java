package com.energyplus.soep;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 *
 */
public class SrcAstModBuilderTest
{
    private SrcAstModBuilder b;
    @Before
    public void setUp()
    {
        b = new SrcAstModBuilder();
    }
    @Test
    public void setName() throws Exception
    {
        SrcAstMod actual = b.setName("test").build();
        SrcAstMod expected = new SrcAstMod(
                "test",
                null,
                null,
                null,
                null,
                null);
        assertTrue(expected.equals(actual));
    }
    @Test
    public void setComment() throws Exception
    {
        SrcAstMod actual = b.setComment("test").build();
        SrcAstMod expected = new SrcAstMod(
                null,
                "test",
                null,
                null,
                null,
                null);
        assertTrue(expected.equals(actual));
    }
    @Test
    public void addQualifier() throws Exception
    {
        SrcAstMod actual = b.addQualifier("test").build();
        List<String> qs = new ArrayList<>();
        qs.add("test");
        SrcAstMod expected = new SrcAstMod(
                null,
                null,
                qs,
                null,
                null,
                null);
        assertTrue(expected.equals(actual));
    }
    @Test
    public void setValue() throws Exception
    {
        SrcAstMod actual = b.setValue("test").build();
        SrcAstMod expected = new SrcAstMod(
                null,
                null,
                null,
                "test",
                null,
                null);
        assertTrue(expected.equals(actual));
    }
    @Test
    public void addModification() throws Exception
    {
        SrcAstMod m = new SrcAstModBuilder().setName("test").build();
        SrcAstMod actual = b.addModification(m).build();
        SrcAstMod modExp = new SrcAstMod(
                "test",
                null,
                null,
                null,
                null,
                null);
        List<SrcAstMod> mods = new ArrayList<>();
        mods.add(modExp);
        SrcAstMod expected = new SrcAstMod(
                null,
                null,
                null,
                null,
                mods,
                null);
        assertTrue(expected.equals(actual));
    }
    @Test
    public void addAnnotation() throws Exception
    {
        SrcAstMod actual = b.addAnnotation("test", "test it!").build();
        Map<String,String> m = new HashMap<>();
        m.put("test", "test it!");
        SrcAstMod expected = new SrcAstMod(
                null,
                null,
                null,
                null,
                null,
                m);
        assertTrue(expected.equals(actual));
    }
}