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
public class SrcAstClassBuilderTest
{
    private SrcAstClassBuilder b;
    @Before
    public void setUp()
    {
        b = new SrcAstClassBuilder();
    }
    @Test
    public void setName() throws Exception
    {
        SrcAstClass actual = b.setName("test").build();
        SrcAstClass expected = new SrcAstClass(
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
        SrcAstClass actual = b.setComment("test").build();
        SrcAstClass expected = new SrcAstClass(
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
    public void addQualifier() throws Exception
    {
        SrcAstClass actual = b.addQualifier("test").build();
        List<String> qs = new ArrayList<>();
        qs.add("test");
        SrcAstClass expected = new SrcAstClass(
                null,
                null,
                qs,
                null,
                null,
                null,
                null,
                null);
        assertTrue(expected.equals(actual));
    }
    @Test
    public void addSuperClass() throws Exception
    {
        SrcAstExtClause sc = new SrcAstExtClauseBuilder()
                .setNameOfExtendedClass("test")
                .build();
        SrcAstClass actual = b
                .addSuperClass(sc)
                .build();
        SrcAstExtClause scExp = new SrcAstExtClause(
                "test",
                null,
                null);
        List<SrcAstExtClause> scs = new ArrayList<>();
        scs.add(scExp);
        SrcAstClass expected= new SrcAstClass(
                null,
                null,
                null,
                scs,
                null,
                null,
                null,
                null);
        assertTrue(expected.equals(actual));
    }
    @Test
    public void addComponent() throws Exception
    {
        SrcAstCompDecl c = new SrcAstCompDeclBuilder()
                .setName("test")
                .build();
        SrcAstClass actual = b
                .addComponent(c)
                .build();
        SrcAstCompDecl cExp = new SrcAstCompDecl(
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
        List<SrcAstCompDecl> cs = new ArrayList<>();
        cs.add(cExp);
        SrcAstClass expected = new SrcAstClass(
                null,
                null,
                null,
                null,
                cs,
                null,
                null,
                null);
        assertTrue(expected.equals(actual));
    }
    @Test
    public void addClass() throws Exception
    {
        SrcAstClass c = new SrcAstClassBuilder()
                .setName("test")
                .build();
        SrcAstClass actual = b.addClass(c).build();
        SrcAstClass cExp = new SrcAstClass(
                "test",
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        List<SrcAstClass> cs = new ArrayList<>();
        cs.add(cExp);
        SrcAstClass expected= new SrcAstClass(
                null,
                null,
                null,
                null,
                null,
                cs,
                null,
                null);
        assertTrue(expected.equals(actual));
    }
    @Test
    public void addAnnotation() throws Exception
    {
        SrcAstClass actual = b
                .addAnnotation("test", "test it")
                .build();
        Map<String,String> m = new HashMap<>();
        m.put("test", "test it");
        SrcAstClass expected = new SrcAstClass(
                null,
                null,
                null,
                null,
                null,
                null,
                m,
                null);
        assertTrue(expected.equals(actual));
    }
    @Test
    public void setRedeclareAs() throws Exception
    {
        SrcAstClass actual = b
                .setRedeclareAs("test")
                .build();
        SrcAstClass expected = new SrcAstClass(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                "test");
        assertTrue(expected.equals(actual));
    }
}