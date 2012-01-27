package com.pri.scilab.shared.dto;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PageLayout implements Serializable, IsSerializable
{
 private String name;
 private LayoutComponent rootComponent;
 
 public PageLayout()
 {}
 
 public PageLayout(String nm)
 {
  name = nm;
  rootComponent = new Dock();
  
  rootComponent.setWidth(-100);
  rootComponent.setHeight(-100);
  
  rootComponent.setName("Page Dock");
 }


 public String getName()
 {
  return name;
 }


 public void setName(String name)
 {
  this.name = name;
 }


 public LayoutComponent getRootComponent()
 {
  return rootComponent;
 }


 public void setRootComponent(LayoutComponent rootComponent)
 {
  this.rootComponent = rootComponent;
 }
}
