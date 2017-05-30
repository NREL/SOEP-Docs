package com.energyplus.soep;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface IModelicaParser
{
    public Map<String,SrcAstClass> parseLibrary(String modelicaPath, String path);
    public Map<String,SrcAstClass> parseLibraryFiltering(
            String modelicaPath, String path, List<String> accepts, List<String> rejects);
    public Map<String,SrcAstClass> parseSingles(String modelicaPath, String path, List<String> singles);
}
