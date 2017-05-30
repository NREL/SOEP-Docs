package com.energyplus.soep;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 *
 */
public class JModelicaParserImplTest
{
    private IModelicaParser mp;
    @Before
    public void setUp()
    {
        mp = new JModelicaParserImpl();
    }
    @Test
    public void testThatWeCanParseAnEntireLibrary() throws Exception
    {
        File mblFile = new File("MBL/Buildings");
        File mslFile = new File("MSL");
        Map<String,SrcAstClass> lib = mp.parseLibrary(mslFile.getAbsolutePath(), mblFile.getAbsolutePath());
        assertNotNull(lib);
        assertTrue(!lib.isEmpty());
        assertTrue(lib.containsKey("Buildings.Fluid.Movers.FlowControlled_m_flow"));
    }
}
