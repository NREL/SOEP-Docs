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
