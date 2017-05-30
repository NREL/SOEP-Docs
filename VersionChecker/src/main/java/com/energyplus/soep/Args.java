package com.energyplus.soep;

import com.beust.jcommander.Parameter;

import java.util.List;

/**
 * Command-line argument parsing
 */
public class Args
{
    @Parameter(names = { "--output", "-o" }, description = "Path to file where output should be written")
    private String outputPath = "out.json";
    @Parameter(arity = 2, required = true, description = "API files: old first, new second")
    private List<String> apiFiles;
    String getOutputPath()
    {
        return outputPath;
    }
    String getOldVersionApiFile()
    {
        return apiFiles.get(0);
    }
    String getNewVersionApiFile()
    {
        return apiFiles.get(1);
    }
}
