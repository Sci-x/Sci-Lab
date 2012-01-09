package com.pri.labs.client.structure;

public class Layout
{
 private int id;
 private Dock rootDock;
 private String name;
 
 public Layout( String nm )
 {
  rootDock = new Dock();
  
  rootDock.setName("Root dock");
  rootDock.setType(Dock.Type.DOCK);
  rootDock.setWidth(-100);
  rootDock.setHeight(-100);
  
  name=nm;
 }
 
 
 public Dock getRootDock()
 {
  return rootDock;
 }


 public String getName()
 {
  return name;
 }


 public void setName(String name)
 {
  this.name = name;
 }
 
}
