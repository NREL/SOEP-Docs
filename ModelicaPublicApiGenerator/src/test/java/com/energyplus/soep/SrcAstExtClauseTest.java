package com.energyplus.soep;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 */
public class SrcAstExtClauseTest
{
    @Test
    public void testThatEqualsAndHashWork() throws Exception
    {
        SrcAstExtClause c1 = new SrcAstExtClause("name1",null,null);
        SrcAstExtClause c2 = new SrcAstExtClause("name1",null,null);
        SrcAstExtClause c3 = new SrcAstExtClause("name2",null,null);
        assertTrue("equal value objects should be equal", c1.equals(c2));
        assertFalse("different objects don't equal", c1.equals(c3));
        assertTrue("equal objects have same hash code", c1.hashCode() == c2.hashCode());
    }
}
