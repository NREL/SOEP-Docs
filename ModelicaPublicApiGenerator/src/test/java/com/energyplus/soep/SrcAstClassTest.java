package com.energyplus.soep;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 *
 */
public class SrcAstClassTest
{
    private SrcAstClass ex1;
    private FlatAstClass ex1expected;
    private SrcAstClass ex2;
    private FlatAstClass ex2expected;
    private Map<String,FlatAstClass> superClassMap;
    private void makeEx1()
    {
        List<String> qs = new ArrayList<>();
        qs.add("model");
        qs.add("partial");
        List<String> qs2 = new ArrayList<>();
        qs2.add("parameter");
        List<SrcAstCompDecl> comps = new ArrayList<>();
        comps.add(new SrcAstCompDecl(
                "Boolean",
                qs2,
                "allowFlowReversal",
                "true"));
        ex1 = new SrcAstClass(
                "Buildings.Fluid.Interfaces.PartialTwoPort",
                "Partial component with two ports",
                qs,
                null,
                comps,
                null,
                null,
                null);
    }
    private void makeEx1Expected()
    {
        List<String> qs = new ArrayList<>();
        qs.add("model");
        qs.add("partial");
        List<String> qs2 = new ArrayList<>();
        qs2.add("parameter");
        List<SrcAstCompDecl> comps = new ArrayList<>();
        comps.add(new SrcAstCompDecl(
                "Boolean",
                qs2,
                "allowFlowReversal",
                "true"));
        ex1expected = new FlatAstClass(
                "Buildings.Fluid.Interfaces.PartialTwoPort",
                "Partial component with two ports",
                qs,
                comps,
                null
        );
    }
    private void makeEx2()
    {
        makeEx1Expected();
        List<String> qs = new ArrayList<>();
        List<SrcAstExtClause> scs = new ArrayList<>();
        List<SrcAstMod> mods = new ArrayList<>();
        mods.add(new SrcAstMod(
                "allowFlowReversal",
                null,
                null,
                "false",
                null,
                null));
        scs.add(new SrcAstExtClause(
                "Buildings.Fluid.Interfaces.PartialTwoPort",
                mods,
                null));
        qs.add("model");
        qs.add("partial");
        ex2 = new SrcAstClass(
                "Buildings.Fluid.Interfaces.PartialTwoPortInterface",
                "Partial component with two ports",
                qs,
                scs,
                null,
                null,
                null,
                null);
        superClassMap = new HashMap<>();
        superClassMap.put("Buildings.Fluid.Interfaces.PartialTwoPort", ex1expected);
    }
    private void makeEx2Expected()
    {
        List<String> qs = new ArrayList<>();
        qs.add("model");
        qs.add("partial");
        List<String> qs2 = new ArrayList<>();
        qs2.add("parameter");
        List<SrcAstCompDecl> comps = new ArrayList<>();
        comps.add(new SrcAstCompDecl(
                "Boolean",
                qs2,
                "allowFlowReversal",
                "false"));
        ex2expected = new FlatAstClass(
                "Buildings.Fluid.Interfaces.PartialTwoPortInterface",
                "Partial component with two ports",
                qs,
                comps,
                null
        );
    }
    @Test
    public void flatteningAClassWithNoSupersYieldsFlatModel() throws Exception
    {
        makeEx1();
        makeEx1Expected();
        FlatAstClass ex1actual;
        try
        {
            ex1actual = ex1.flatten(new HashMap<String,FlatAstClass>());
        }
        catch (DependencyNotPresentException e)
        {
            ex1actual = null;
        }
        assertTrue(ex1expected.equals(ex1actual));
    }
    @Test
    public void flatteningAClassWithSupersExtendsApi() throws Exception
    {
        makeEx2();
        makeEx2Expected();
        FlatAstClass ex2actual;
        try
        {
            ex2actual = ex2.flatten(superClassMap);
        }
        catch (DependencyNotPresentException e)
        {
            ex2actual = null;
        }
        assertTrue(ex2expected.equals(ex2actual));
    }
    @Test
    public void testThatEqualsWorksAsExpected()
    {
        SrcAstClass c1 = new SrcAstClass(
                "name",
                "comment",
                new ArrayList<String>() {{
                    add("qualifier1");
                }},
                new ArrayList<SrcAstExtClause>(),
                new ArrayList<SrcAstCompDecl>(),
                new ArrayList<SrcAstClass>(),
                new HashMap<String,String>(){{
                    put("annotationKey","annotationValue");
                }},
                "redeclareAs");
        SrcAstClass c2 = new SrcAstClass(
                "name",
                "comment",
                new ArrayList<String>() {{
                    add("qualifier1");
                }},
                new ArrayList<SrcAstExtClause>(),
                new ArrayList<SrcAstCompDecl>(),
                new ArrayList<SrcAstClass>(),
                new HashMap<String,String>(){{
                    put("annotationKey","annotationValue");
                }},
                "redeclareAs");
        SrcAstClass c3 = new SrcAstClass(
                "name",
                "comment",
                new ArrayList<String>() {{
                    add("qualifier1");
                }},
                new ArrayList<SrcAstExtClause>(),
                new ArrayList<SrcAstCompDecl>(),
                new ArrayList<SrcAstClass>(){{
                    add(c1);
                    add(c2);
                }},
                new HashMap<String,String>(){{
                    put("annotationKey","annotationValue");
                }},
                "redeclareAs");
        assertTrue("same objects should equal", c1.equals(c2));
        assertFalse("different objects should not equal", c1.equals(c3));
        assertTrue("same objects should have same hash value", c1.hashCode() == c2.hashCode());
    }
    @Test
    public void testIfIsPackageWorks() throws Exception
    {
        SrcAstClass c1 = new SrcAstClass(
                "name",
                "comment",
                new ArrayList<String>(){{add("package");}},
                null,
                null,
                null,
                null,
                null);
        SrcAstClass c2 = new SrcAstClass(
                "name",
                "comment",
                new ArrayList<String>(){{add("model");}},
                null,
                null,
                null,
                null,
                null);
        assertTrue(c1.isPackage());
        assertFalse(c2.isPackage());
    }
}