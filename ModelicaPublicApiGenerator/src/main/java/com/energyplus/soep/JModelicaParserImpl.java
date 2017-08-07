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
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER, THE UNITED STATES
// GOVERNMENT, OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
// SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
// PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
// OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
// WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
// OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
// ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
package com.energyplus.soep;

import org.jmodelica.modelica.compiler.*;
import org.jmodelica.util.OptionRegistry;

import java.util.*;
import java.util.List;

/**
 *
 */
public class JModelicaParserImpl implements IModelicaParser
{
    private String sep;
    public JModelicaParserImpl() {
    }
    private static boolean isOs(String osPattern)
    {
        String osName = System.getProperty("os.name");
        return osName.toLowerCase().contains(osPattern.toLowerCase());
    }
    private String getModelicaPathSep()
    {
        if (sep != null)
        {
            return sep;
        }
        if (isWindows())
        {
            sep = ";";
            return sep;
        }
        sep = ":";
        return sep;
    }
    private static boolean isWindows()
    {
        return isOs("win");
    }
    private String modelicaPathsToString(List<String> paths)
    {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (String path : paths)
        {
            if (isFirst)
            {
                isFirst = false;
                sb.append(getModelicaPathSep());
            }
            sb.append(path);
        }
        return sb.toString();
    }
    private static ModelicaCompiler createCompiler(String modelicaPath)
    {
        OptionRegistry opts = ModelicaCompiler.createOptions();
        opts.setStringOption("MODELICAPATH", modelicaPath);
        return new ModelicaCompiler(opts);
    }
    private static SourceRoot parseModel(ModelicaCompiler comp, String path)
    {
        String[] paths = {path};
        try
        {
            return comp.parseModel(paths);
        }
        catch (java.io.IOException e)
        {
            System.out.println("IO Exception when trying to parse file at: " + path);
            System.out.println("Exception: " + e);
            System.exit(ErrorCodes.IO_ISSUE);
        }
        catch (beaver.Parser.Exception e)
        {
            System.out.println("Beaver Parser exception at path: " + path);
            System.out.println("Exception: " + e);
            System.exit(ErrorCodes.BEAVER_ISSUE);
        }
        return null;
    }
    private static ClassDecl nodeForPath(SourceRoot sr, String dottedPath)
    {
        Program p = sr.getProgram();
        ClassDecl cd = p.simpleLookupClassDotted(dottedPath);
        if (cd.isFullClassDecl())
            return cd;
        return ((LibNode)(p.simpleLookupClassDotted(dottedPath))).classDecl();
    }
    private boolean[] calculateSkip(String qualifiedName, List<String> accepts, List<String> rejects)
    {
        String lowerCaseName = qualifiedName.toLowerCase();
        boolean initSkip = accepts != null && accepts.size() > 0;
        boolean skip = initSkip;
        boolean skipDueToReject = false;
        for (String a : accepts)
        {
            if (lowerCaseName.contains(a))
            {
                skip = false;
                break;
            }
        }
        if (!skip)
        {
            for (String r : rejects)
            {
                if (lowerCaseName.contains(r))
                {
                    skip = true;
                    skipDueToReject = true;
                    break;
                }
            }
        }
        return new boolean[]{skip, skipDueToReject};
    }
    private void
    addToLib(Map<String,SrcAstClass> lib, ClassDecl c)
    {
        if (c instanceof FullClassDecl)
        {
            String qualifiedName = c.qualifiedName();
            SrcAstClass sac = new SrcAstClass(c);
            if (!sac.isPackage())
                lib.put(qualifiedName, sac);
        }
        else if (c instanceof LibNode)
        {
            ClassDecl cd = ((LibNode) c).classDecl();
            if (cd != null)
            {
                String qualifiedName = cd.qualifiedName();
                SrcAstClass sac = new SrcAstClass(cd);
                if (!sac.isPackage())
                    lib.put(qualifiedName, sac);
            }
        }
    }
    private void
    walkClassesFiltering(
            Map<String,SrcAstClass> lib, Iterable<ClassDecl> classes, List<String> accepts, List<String> rejects)
    {
        boolean[] skipFlags;
        for (ClassDecl c : classes)
        {
            skipFlags = calculateSkip(c.qualifiedName(), accepts, rejects);
            if (!skipFlags[0])
            {
                addToLib(lib, c);
            }
            if (!skipFlags[1])
                walkClassesFiltering(lib, c.classes(), accepts, rejects);
        }
    }
    private void
    walkClasses(Map<String,SrcAstClass> lib, Iterable<ClassDecl> classes)
    {
        for (ClassDecl c : classes)
        {
            addToLib(lib, c);
            walkClasses(lib, c.classes());
        }
    }
    private Map<String, SrcAstClass>
    walkAllNodesFiltering(SourceRoot sr, List<String> accepts, List<String> rejects)
    {
        Map<String,SrcAstClass> lib = new HashMap<>();
        Program p = sr.getProgram();
        LibNode ln = p.getLibNode(0);
        walkClassesFiltering(lib, ln.classes(), accepts, rejects);
        return lib;
    }
    private Map<String, SrcAstClass> walkAllNodes(SourceRoot sr)
    {
        Map<String,SrcAstClass> lib = new HashMap<>();
        Program p = sr.getProgram();
        LibNode ln = p.getLibNode(0);
        walkClasses(lib, ln.classes());
        return lib;
    }
    @Override
    public Map<String,SrcAstClass> parseLibrary(String modelicaPath, String path)
    {
        ModelicaCompiler c = createCompiler(modelicaPath);
        SourceRoot sr = parseModel(c, path);
        return walkAllNodes(sr);
    }
    @Override
    public Map<String,SrcAstClass> parseSingles(String modelicaPath, String path, List<String> singles)
    {
        ModelicaCompiler c = createCompiler(modelicaPath);
        SourceRoot sr = parseModel(c, path);
        Map<String,SrcAstClass> out = new HashMap<>();
        for (String dottedPath : singles)
        {
            SrcAstClass sac = new SrcAstClass(nodeForPath(sr, dottedPath));
            out.put(dottedPath, sac);
        }
        return out;
    }
    @Override
    public Map<String,SrcAstClass>
    parseLibraryFiltering(String modelicaPath, String path, List<String> accepts, List<String> rejects)
    {
        ModelicaCompiler c = createCompiler(modelicaPath);
        SourceRoot sr = parseModel(c, path);
        List<String> lowerCaseAccepts = new ArrayList<>();
        for (String a : accepts)
        {
            lowerCaseAccepts.add(a.toLowerCase());
        }
        List<String> lowerCaseRejects = new ArrayList<>();
        for (String r : rejects)
        {
            lowerCaseRejects.add(r.toLowerCase());
        }
        return walkAllNodesFiltering(sr, lowerCaseAccepts, lowerCaseRejects);

    }
}
