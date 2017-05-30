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
