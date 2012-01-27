package com.pri.scilab.shared.dto;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public class LayoutComponent implements Serializable, IsSerializable
{

 private String name;
 private int width;
 private int height;

 public String getName()
 {
  return name;
 }

 public void setName(String name)
 {
  this.name = name;
 }

 public int getWidth()
 {
  return width;
 }

 public int getHeight()
 {
  return height;
 }

 public void setWidth(int width)
 {
  this.width = width;
 }

 public void setHeight(int height)
 {
  this.height = height;
 }

}
