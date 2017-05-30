package com.energyplus.soep;

import com.beust.jcommander.JCommander;
import com.energyplus.soep.changes.ChangeDetector;
import com.google.gson.Gson;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Detect VersionChanges between multiple versions of a public JSON API descriptor of a project.
 *
 */
public class App 
{

    private static String readFile(String path) throws IOException
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
        return sb.toString();
    }
    public static void main(String[] argsIn)
    {
        Args args = new Args();
        JCommander.newBuilder()
                .addObject(args)
                .build()
                .parse(argsIn);
        JsonReader jr = new JsonReader();
        Gson g = new Gson();
        try
        {
            PrintWriter pw = new PrintWriter(args.getOutputPath());
            String oldContents = readFile(args.getOldVersionApiFile());
            Map<String,SrcAstClass> v1 = jr.readMap_String_SrcAstClass(oldContents);
            String newContents = readFile(args.getNewVersionApiFile());
            Map<String,SrcAstClass> v2 = jr.readMap_String_SrcAstClass(newContents);
            ChangeDetector cd = new ChangeDetector(v1, v2);
            List<String> changes = cd.getChanges();
            pw.write(g.toJson(changes));
            pw.close();
        }
        catch (java.io.FileNotFoundException e)
        {
            System.out.println("File not found:\n" + e.getMessage());
            System.exit(1);
        }
        catch (IOException e)
        {
            System.out.println("IO Exception:\n" + e.getMessage());
            System.exit(1);
        }
    }
}
