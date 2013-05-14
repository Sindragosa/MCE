package sedridor.mce.core;

import java.io.DataInputStream;
import java.io.File;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ClassOverrider
{
    public static byte[] overrideBytes(String name, byte[] bytes, String newName, File location)
    {
        try
        {
            ZipFile zip = new ZipFile(location);
            ZipEntry entry = zip.getEntry(name.replace('.', '/') + ".class");
            if (entry == null)
            {
                System.out.println(name + " not found in " + location.getName());
            }
            else
            {
                DataInputStream zin = new DataInputStream(zip.getInputStream(entry));
                bytes = new byte[(int)entry.getSize()];
                zin.readFully(bytes);
                zin.close();
                System.out.println(name + " was overridden from " + location.getName());
            }
            zip.close();
        }
        catch(Exception e)
        {
            throw new RuntimeException("Error overriding " + name + " from " + location.getName(), e);
        }
        return bytes;
    }
    
    public static byte[] getClassBytes(String name, File location)
    {
        byte[] bytes = null;
        try
        {
            ZipFile zip = new ZipFile(location);
            ZipEntry entry = zip.getEntry(name.replace('.', '/')+".class");
            if (entry == null)
            {
                System.out.println(name + " not found in " + location.getName());
            }
            else
            {
                DataInputStream zin = new DataInputStream(zip.getInputStream(entry));
                bytes = new byte[(int)entry.getSize()];
                zin.readFully(bytes);
                zin.close();
                System.out.println("Bytes for " + name + " were attained from " + location.getName());
            }
            zip.close();
        }
        catch(Exception e)
        {
            throw new RuntimeException("Error loading " + name + " from " + location.getName(), e);
        }
        return bytes;
    }
}
