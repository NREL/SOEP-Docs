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
public class SrcAstCompDeclBuilderTest
{
    private SrcAstCompDeclBuilder b;
    @Before
    public void setUp()
    {
        b = new SrcAstCompDeclBuilder();
    }
    @Test
    public void setClassName() throws Exception
    {
        SrcAstCompDecl actual = b
                .setClassName("test")
                .build();
        SrcAstCompDecl expected = new SrcAstCompDecl(
                "test",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        assertTrue(expected.equals(actual));
    }
    @Test
    public void addQualifier() throws Exception
    {
        SrcAstCompDecl actual = b
                .addQualifier("test")
                .build();
        List<String> qs = new ArrayList<>();
        qs.add("test");
        SrcAstCompDecl expected = new SrcAstCompDecl(
                null,
                qs,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        assertTrue(expected.equals(actual));
    }
    @Test
    public void setName() throws Exception
    {
        SrcAstCompDecl actual = b
                .setName("test")
                .build();
        SrcAstCompDecl expected = new SrcAstCompDecl(
                null,
                null,
                "test",
                null,
                null,
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
        SrcAstCompDecl actual = b
                .setComment("test")
                .build();
        SrcAstCompDecl expected = new SrcAstCompDecl(
                null,
                null,
                null,
                "test",
                null,
                null,
                null,
                null,
                null,
                null);
        assertTrue(expected.equals(actual));
    }
    @Test
    public void setValue() throws Exception
    {
        SrcAstCompDecl actual = b
                .setValue("test")
                .build();
        SrcAstCompDecl expected = new SrcAstCompDecl(
                null,
                null,
                null,
                null,
                "test",
                null,
                null,
                null,
                null,
                null);
        assertTrue(expected.equals(actual));
    }
    @Test
    public void addModification() throws Exception
    {
        SrcAstMod m = new SrcAstModBuilder().setName("test").build();
        SrcAstCompDecl actual = new SrcAstCompDeclBuilder()
                .addModification(m)
                .build();
        List<SrcAstMod> mods = new ArrayList<>();
        SrcAstMod mExp = new SrcAstMod(
                "test",
                null,
                null,
                null,
                null,
                null);
        mods.add(mExp);
        SrcAstCompDecl expected = new SrcAstCompDecl(
                null,
                null,
                null,
                null,
                null,
                mods,
                null,
                null,
                null,
                null);
        assertTrue(expected.equals(actual));
    }
    @Test
    public void setArraySubscripts() throws Exception
    {
        SrcAstCompDecl actual = b.setArraySubscripts("test").build();
        SrcAstCompDecl expected = new SrcAstCompDecl(
                null,
                null,
                null,
                null,
                null,
                null,
                "test",
                null,
                null,
                null);
        assertTrue(expected.equals(actual));
    }
    @Test
    public void setConditionalClause() throws Exception
    {
        SrcAstCompDecl actual = b.setConditionalClause("test").build();
        SrcAstCompDecl expected = new SrcAstCompDecl(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                "test",
                null,
                null);
        assertTrue(expected.equals(actual));
    }
    @Test
    public void setConstrainingClause() throws Exception
    {
        SrcAstCompDecl actual = b.setConstrainingClause("test").build();
        SrcAstCompDecl expected = new SrcAstCompDecl(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                "test",
                null);
        assertTrue(expected.equals(actual));
    }
    @Test
    public void addAnnotation() throws Exception
    {
        SrcAstCompDecl actual = b
                .addAnnotation("test","test it!")
                .build();
        Map<String,String> m = new HashMap<>();
        m.put("test", "test it!");
        SrcAstCompDecl expected = new SrcAstCompDecl(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                m);
        assertTrue(expected.equals(actual));
    }
}