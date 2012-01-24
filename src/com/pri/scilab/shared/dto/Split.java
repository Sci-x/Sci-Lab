package com.pri.scilab.shared.dto;

import java.util.List;

public class Split implements LayoutComponent
{
 private String name;
 private int width;
 private int height;
 
 private List<LayoutComponent> content;
 
 @Override
 public String getName()
 {
  return name;
 }

 public List<LayoutComponent> getComponents()
 {
  return content;
 }

 public int getWidth()
 {
  return width;
 }

 public void setWidth(int width)
 {
  this.width = width;
 }

 public int getHeight()
 {
  return height;
 }

 public void setHeight(int height)
 {
  this.height = height;
 }

}
