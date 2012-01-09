package com.pri.labs.server.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Collection;

import com.pri.labs.client.structure.Layout;

public class BackendDataManager
{
 private static BackendDataManager instance = new BackendDataManager();
 
 public static BackendDataManager getInstance()
 {
  return instance;
 }
 
// private Collection<Layout> layouts;
 
 static final File workDir = new File("/tmp/Labs");
 
 
 private BackendDataManager()
 {
 }
 

 @SuppressWarnings("unchecked")
 public Collection<Layout> getLayouts()
 {
  FileInputStream serFile = null;
  try
  {
   File laySer = new File( workDir, "layouts.ser" );
   
   ObjectInputStream ois = new ObjectInputStream( serFile = new FileInputStream(laySer) );
   
   return (Collection<Layout>) ois.readObject();
   
  }
  catch (Exception e)
  {
   if( serFile != null )
   {
    try
    {
     serFile.close();
    }
    catch(Exception e2)
    {
    }
   }
  }
  
  return null;
 }
 
}