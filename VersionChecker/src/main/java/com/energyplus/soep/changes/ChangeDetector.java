package com.energyplus.soep.changes;

import com.energyplus.soep.SrcAstClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The purpose of this class is to detect changes between two versions of the public APIs for exposed portions of
 * the Modelica Buildings Library (in particular) and any Modelica Library in general.
 */
public class ChangeDetector
{
    private Map<String,SrcAstClass> oldVer;
    private Map<String,SrcAstClass> newVer;
    public ChangeDetector(
            Map<String,SrcAstClass> oldVerIn,
            Map<String,SrcAstClass> newVerIn)
    {
        oldVer = oldVerIn;
        newVer = newVerIn;
    }
    public List<String>
    getChanges()
    {
        List<String> changes = new ArrayList<>();
        DetectClassChanges dcc = new DetectClassChanges();
        dcc.getBackwardIncompatibleChanges(changes, oldVer, newVer);
        return changes;
    }
}
