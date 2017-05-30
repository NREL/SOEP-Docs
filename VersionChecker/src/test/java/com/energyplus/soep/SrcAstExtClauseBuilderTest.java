package com.energyplus.soep;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 *
 */
public class SrcAstExtClauseBuilderTest
{
    private SrcAstExtClauseBuilder b;
    @Before
    public void setUp()
    {
        b = new SrcAstExtClauseBuilder();
    }
    @Test
    public void setNameOfExtendedClass() throws Exception
    {
        SrcAstExtClause actual = b.setNameOfExtendedClass("test").build();
        SrcAstExtClause expected = new SrcAstExtClause(
                "test",
                null,
                null);
        assertTrue(expected.equals(actual));
    }
    @Test
    public void addModification() throws Exception
    {
        SrcAstMod m = new SrcAstModBuilder()
                .setName("test")
                .build();
        SrcAstExtClause actual = b
                .addModification(m)
                .build();
        SrcAstMod mExpect = new SrcAstMod(
                "test",
                null,
                null,
                null,
                null,
                null);
        List<SrcAstMod> mods = new ArrayList<>();
        mods.add(mExpect);
        SrcAstExtClause expected = new SrcAstExtClause(
                null,
                mods,
                null);
        assertTrue(expected.equals(actual));
    }
    @Test
    public void addAnnotation() throws Exception
    {
        SrcAstExtClause actual = b
                .addAnnotation("test", "test it!")
                .build();
        Map<String,String> annExp = new HashMap<>();
        annExp.put("test", "test it!");
        SrcAstExtClause expected = new SrcAstExtClause(
                null,
                null,
                annExp);
        assertTrue(expected.equals(actual));
    }
}