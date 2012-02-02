package com.pri.scilab.admin.server.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.pri.scilab.shared.dto.Dock;
import com.pri.scilab.shared.dto.HSplit;
import com.pri.scilab.shared.dto.PageLayout;
import com.pri.scilab.shared.dto.VSplit;


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
  File laySer = new File( workDir, "layouts.ser" );
  
  if( ! laySer.exists() )
  {
   final PageLayout pl = new PageLayout("Test Layout");
   
   VSplit vs = new VSplit();
   vs.setName("VSplit 1");
   vs.setHeight(-100);
   vs.setWidth(-100);
   
   HSplit hp = new HSplit();
   hp.setName("Body split");
   hp.setHeight(0);
   hp.setWidth(-100);
   
   Dock dk = new Dock();
   dk.setName("Header");
   dk.setHeight(100);
   dk.setWidth(-100);
   
   vs.addChildComponent(dk);
   vs.addChildComponent(hp);
   
   dk = new Dock();
   dk.setName("Left Strip");
   dk.setWidth(100);
   dk.setHeight(-100);
   
   hp.addChildComponent(dk);
   
   dk = new Dock();
   dk.setName("Body Strip");
   dk.setHeight(-100);
   
   hp.addChildComponent(dk);

   dk = new Dock();
   dk.setName("Right Strip");
   dk.setWidth(100);
   dk.setHeight(-100);
   
   hp.addChildComponent(dk);
   
   pl.setRootComponent(vs);
   
   
   List<PageLayout> lts = new ArrayList<PageLayout>();
   lts.add(pl);
   return lts;
  }
  
  
  try
  {
   
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