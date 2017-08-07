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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jmodelica.modelica.compiler.*;
import org.jmodelica.util.OptionRegistry;
import com.beust.jcommander.JCommander;

import java.io.*;
import java.util.*;
import java.util.List;

public class App
{
    private static String VERSION = "1.0.0";
    private static String sep;

    private static boolean bothNull(Object a, Object b)
    {
        if (a == null)
        {
            if (b != null)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        return false;
    }
    private static boolean eitherNull(Object a, Object b)
    {
        return ((a == null) || (b == null));
    }
    static boolean mapStrStrEquals(Map<String,String> a, Map<String,String> b)
    {
        if (bothNull(a,b))
            return true;
        if (eitherNull(a,b))
            return false;
        if (a.size() != b.size())
            return false;
        Set<String> keys = a.keySet();
        Set<String> otherKeys = a.keySet();
        if (!keys.equals(otherKeys))
            return false;
        for (String k : keys)
        {
            if (!a.get(k).equals(b.get(k)))
                return false;
        }
        return true;
    }
    static boolean listOfSrcAstExtClausesEquals(List<SrcAstExtClause> as, List<SrcAstExtClause> bs)
    {
        if (bothNull(as,bs))
            return true;
        if (eitherNull(as,bs))
            return false;
        if (as.size()!= bs.size())
            return false;
        for (int i=0; i < as.size(); i++)
        {
            if (!as.get(i).equals(bs.get(i)))
                return false;
        }
        return true;
    }
    static boolean listOfSrcAstClassEquals(List<SrcAstClass> as, List<SrcAstClass> bs)
    {
        if (bothNull(as,bs))
            return true;
        if (eitherNull(as,bs))
            return false;
        if (as.size()!= bs.size())
            return false;
        for (int i=0; i < as.size(); i++)
        {
            if (!as.get(i).equals(bs.get(i)))
                return false;
        }
        return true;
    }
    static boolean listOfSrcAstCompDeclEquals(List<SrcAstCompDecl> as, List<SrcAstCompDecl> bs)
    {
        if (bothNull(as,bs))
            return true;
        if (eitherNull(as,bs))
            return false;
        if (as.size()!= bs.size())
            return false;
        for (int i=0; i < as.size(); i++)
        {
            if (!as.get(i).equals(bs.get(i)))
                return false;
        }
        return true;
    }
    static boolean listOfSrcAstModEquals(List<SrcAstMod> as, List<SrcAstMod> bs)
    {
        if (bothNull(as,bs))
            return true;
        if (eitherNull(as,bs))
            return false;
        if (as.size()!= bs.size())
            return false;
        for (int i=0; i < as.size(); i++)
        {
            if (!as.get(i).equals(bs.get(i)))
                return false;
        }
        return true;
    }
    static boolean stringEquals(String a, String b)
    {
        if (bothNull(a,b))
            return true;
        if (eitherNull(a,b))
            return false;
        return a.equals(b);
    }
    static boolean listOfStringEquals(List<String> as, List<String> bs)
    {
        if (bothNull(as,bs))
            return true;
        if (eitherNull(as,bs))
            return false;
        if (as.size() != bs.size())
            return false;
        for (int i=0; i < as.size(); i++)
        {
            if (!as.get(i).equals(bs.get(i)))
                return false;
        }
        return true;
    }
    static List<String> cloneQualifiers(List<String> quals)
    {
        if (quals == null)
            return null;
        List<String> qs = new ArrayList<>();
        qs.addAll(quals);
        return qs;
    }
    static List<SrcAstMod> cloneModifications(List<SrcAstMod> mods)
    {
        if (mods == null)
            return null;
        List<SrcAstMod> ms = new ArrayList<>();
        for (SrcAstMod m : mods)
        {
            ms.add(new SrcAstMod(m));
        }
        return ms;
    }
    static List<SrcAstExtClause> cloneSuperClasses(List<SrcAstExtClause> superClasses)
    {
        if (superClasses == null)
            return null;
        List<SrcAstExtClause> scs = new ArrayList<>();
        for (SrcAstExtClause sc : superClasses)
        {
            scs.add(new SrcAstExtClause(sc));
        }
        return scs;
    }
    static List<SrcAstCompDecl> cloneComponents(List<SrcAstCompDecl> comps)
    {
        if (comps == null)
            return null;
        List<SrcAstCompDecl> cs = new ArrayList<>();
        for (SrcAstCompDecl c : comps)
        {
            cs.add(new SrcAstCompDecl(c));
        }
        return cs;
    }
    static Map<String,String> cloneAnnotations(Map<String,String> anns)
    {
        if (anns == null)
            return null;
        Map<String,String> as = new HashMap<>();
        for (String k : anns.keySet())
        {
            as.put(k, anns.get(k));
        }
        return as;
    }
    static List<SrcAstClass> cloneClasses(List<SrcAstClass> classes)
    {
        if (classes == null)
            return null;
        List<SrcAstClass> cs = new ArrayList<>();
        for (SrcAstClass c : classes)
        {
            cs.add(new SrcAstClass(c));
        }
        return cs;
    }
    protected static String getVersion()
    {
        return VERSION;
    }
    private static boolean isOs(String osPattern)
    {
        String osName = System.getProperty("os.name");
        return osName.toLowerCase().contains(osPattern.toLowerCase());
    }
    static String getModelicaPathSep()
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
    static boolean isWindows()
    {
        return isOs("win");
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
            System.exit(1);
        }
        catch (beaver.Parser.Exception e)
        {
            System.out.println("Beaver Parser exception at path: " + path);
            System.out.println("Exception: " + e);
            System.exit(1);
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
    private static SrcAstClass processOne(String mp, String path, String id)
    {
        ModelicaCompiler c = createCompiler(mp);
        SourceRoot sr = parseModel(c, path);
        ClassDecl cd = nodeForPath(sr, id);
        return new SrcAstClass(cd);
    }
    private static void writeOne(String outputPath, String outputText)
    {
        try
        {
            PrintWriter output = new PrintWriter(outputPath);
            output.write(outputText);
            output.close();
        }
        catch (java.io.FileNotFoundException e)
        {
            System.out.println("Couldn't write to " + outputPath);
            System.exit(1);
        }
    }
    private static String convertMapToJson(Map<String,SrcAstClass> m)
    {
        Gson g = new Gson();
        return g.toJson(m);
    }
    private static void displayAdvice()
    {
        System.out.println("Call with the --help flag for help on usage.");
    }
    /*
    private static String readFile(File path)
    {
        StringBuilder sb = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new FileReader(path)))
        {
            String line = br.readLine();
            while (line != null)
            {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Warning! File not found: " + path.getAbsolutePath());
            System.exit(ErrorCodes.FILE_DOESNT_EXIST);
        }
        catch (IOException e)
        {
            System.out.println("Warning! IO Exception on: " + path.getAbsolutePath());
            System.exit(ErrorCodes.IO_ISSUE);
        }
        return sb.toString();
    }
    */
    public static Map<String,SrcAstClass> loadOutput(String filePath)
    {
        Gson g = new Gson();
        Map<String,SrcAstClass> out;
        try
        {
            out =  g.fromJson(new FileReader(filePath), new TypeToken<HashMap<String,SrcAstClass>>(){}.getType());
            return out;
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error! File not found: " + filePath);
        }
        System.exit(ErrorCodes.FILE_DOESNT_EXIST);
        return null;
    }
    public static void main(String[] rawArgs)
    {
        Args args = new Args();
        JCommander.newBuilder()
                .addObject(args)
                .build()
                .parse(rawArgs);
        IModelicaParser modPar = new JModelicaParserImpl();
        if (args.getHelp())
        {
            Args.printHelp();
            return;
        }
        if (args.getVersion())
        {
            Args.printHeader();
            return;
        }
        if (args.getModelicaPaths().size() == 0)
        {
            System.out.println("One or more modelica paths must be specified");
            displayAdvice();
            return;
        }
        if (args.getLib() == null || args.getLib().isEmpty())
        {
            System.out.println("Must specify exactly one library path");
            displayAdvice();
            return;
        }
        if (args.getVerbose())
        {
            System.out.println("Running with the following arguments:\n" + args.toString());
            System.out.println("------");
        }
        Map<String,SrcAstClass> publicAst;
        String modelicaPath = args.getModelicaPathsAsString();
        if (args.hasSingles())
        {
            publicAst = modPar.parseSingles(modelicaPath, args.getLib(), args.getSingles());
        }
        else
        {
            if (args.hasAccepts() || args.hasRejects())
            {
                publicAst = modPar.parseLibraryFiltering(
                        modelicaPath, args.getLib(), args.getAccepts(), args.getRejects());
            }
            else
            {
                publicAst = modPar.parseLibrary(modelicaPath, args.getLib());
            }
        }
        String outputPath = args.getOutputPath();
        writeOne(outputPath, convertMapToJson(publicAst));
        if (args.getVerbose())
        {
            System.out.println("Done!");
        }
    }
}
