// Copyright 2017 Big Ladder Software LLC, All rights reserved.
// 
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
// 
// (1) Redistributions of source code must retain the above copyright notice,
// this list of conditions and the following disclaimer.
// 
// (2) Redistributions in binary form must reproduce the above copyright notice,
// this list of conditions and the following disclaimer in the documentation
// and/or other materials provided with the distribution.
// 
// (3) Neither the name of the copyright holder nor the names of its
// contributors may be used to endorse or promote products derived from this
// software without specific prior written permission. 
// 
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
// LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
// SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
// POSSIBILITY OF SUCH DAMAGE.
package com.energyplus.soep;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Command line arguments
 */
class Args
{
    @Parameter(names = { "--lib", "-L" }, description = "Name of a library to parse in its entirety")
    private String lib;
    @Parameter(names = { "--modelicapath", "-mp" }, description = "Modelica Path: multiple entries are OK")
    private List<String> modelicaPaths = new ArrayList<>();
    @Parameter(names = { "--output", "-o" }, description = "Output file name to write; defaults to 'out.json'")
    private String output = "out.json";
    @Parameter(names = { "--help", "-h" }, help = true, description = "Show usage and help message")
    private boolean help;
    @Parameter(names = { "--version", "-v" }, description = "Show version information")
    private boolean version;
    @Parameter(names = { "--quiet", "-q" }, description = "Suppress command line printing")
    private boolean quiet = false;
    @Parameter(names = { "--only", "-1" }, description = "Process only single files (more than one can be specified)")
    private List<String> singles;
    @Parameter(names = { "--reject", "-R" }, description = "Reject dotted paths containing the given text")
    private List<String> rejects;
    @Parameter(names = { "--accept", "-A" }, description = "Accept only dotted paths containing the given text")
    private List<String> accepts;
    public String getLib()
    {
        return lib;
    }
    public boolean getVerbose()
    {
        return !quiet;
    }
    public boolean getVersion()
    {
        return version;
    }
    public static void printHeader()
    {
        System.out.println("Modelica Public API Generator");
        System.out.println("VERSION: " + App.getVersion());
    }
    public static void printHelp()
    {
        printHeader();
        StringBuilder sb = new StringBuilder();
        sb.append("\nGiven modelica paths and a library root, create the public facing API for the");
        sb.append("\nthat library. For example,\n");
        sb.append("\n   java -jar ModelicaPublicApiGenerator-" + App.getVersion() + ".jar -mp MSL -L MBL\n");
        System.out.println(sb.toString());
        JCommander.newBuilder()
                .addObject(new Args())
                .build()
                .usage();
    }
    public List<String> getModelicaPaths()
    {
        return modelicaPaths;
    }
    public String getModelicaPathsAsString()
    {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (String mPath : modelicaPaths)
        {
            if (!isFirst)
            {
                sb.append(App.getModelicaPathSep());
            }
            else
            {
                isFirst = false;
            }
            sb.append(mPath);
        }
        return sb.toString();
    }
    public String getOutputPath()
    {
        return output;
    }
    public boolean getHelp()
    {
        return help;
    }
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("LIBRARY PATH:\n");
        sb.append(lib);
        sb.append("\nMODELICAPATH = " + getModelicaPathsAsString());
        sb.append("\nOUTPUT_FILE  = " + output);
        return sb.toString();
    }
    public boolean hasSingles()
    {
        if (singles == null)
            return false;
        return singles.size() > 0;
    }
    public List<String> getSingles()
    {
        return singles;
    }
    public List<String> getRejects()
    {
        if (rejects == null)
            return new ArrayList<>();
        return rejects;
    }
    public boolean hasRejects()
    {
        return rejects != null && rejects.size() > 0;
    }
    public List<String> getAccepts()
    {
        if (accepts == null)
            return new ArrayList<>();
        return accepts;
    }
    public boolean hasAccepts()
    {
        return accepts != null && accepts.size() > 0;
    }
}
