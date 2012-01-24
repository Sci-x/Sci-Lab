package com.pri.scilab.shared.dto;

public class PageLayout
{
 private String name;
 private LayoutComponent rootComponent;
 
 
 public PageLayout(String nm)
 {
  name = nm;
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
