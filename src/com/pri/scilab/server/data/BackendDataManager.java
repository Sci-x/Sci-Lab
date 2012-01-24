package com.pri.scilab.server.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Collection;

import com.pri.scilab.shared.dto.PageLayout;


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
 public Collection<PageLayout> getLayouts()
 {
  FileInputStream serFile = null;
  try
  {
   File laySer = new File( workDir, "layouts.ser" );
   
   ObjectInputStream ois = new ObjectInputStream( serFile = new FileInputStream(laySer) );
   
   return (Collection<PageLayout>) ois.readObject();
   
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