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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 */
public class ResourceHelper
{
    private static File rscDir;
    private static Path tempDir;
    public ResourceHelper()
    {
        this(true);
    }
    public ResourceHelper(boolean makeTempDir)
    {
        resolveResourceDirectory();
        if (makeTempDir)
            makeTempDirectory();
    }
    public static void deleteDirectoryRecursively(File dir)
    {
        if (dir.exists())
        {
            File[] files = dir.listFiles();
            if (files != null)
            {
                for (File f : files)
                {
                    if (f.isDirectory())
                    {
                        deleteDirectoryRecursively(f);
                    }
                    else
                    {
                        f.delete();
                    }
                }
            }
        }
    }
    private void resolveResourceDirectory()
    {
        if (rscDir == null)
        {
            rscDir = new File("src/test/resources");
        }
    }
    private void makeTempDirectory()
    {
        try
        {
            tempDir = Files.createTempDirectory("mbl-version-checker");
        }
        catch (Exception e)
        {
            tempDir = null;
        }
    }
    public File getResourceDir()
    {
        return rscDir;
    }
    public File getTempDir()
    {
        return tempDir.toFile();
    }
    public File getResourceFile(String relativePath)
    {
        return new File(rscDir, relativePath);
    }
    public String readFile(String path) throws IOException
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
    public void cleanUp()
    {
        if (tempDir == null)
            return;
        deleteDirectoryRecursively(tempDir.toFile());
    }
}
