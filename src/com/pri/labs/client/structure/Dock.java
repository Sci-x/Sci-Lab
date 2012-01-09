package com.pri.labs.client.structure;

import java.io.Serializable;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Dock implements Serializable, IsSerializable
{
 public enum Type
 {
  DOCK, VSPLIT, HSPLIT
 }

 private int id;
 private String name;
 
 private int        width;
 private int        height;
 private Type       type;
 private Dock       parent;
 private List<Dock> subDocks;

 public int getId()
 {
  return id;
 }

 public void setId(int id)
 {
  this.id = id;
 }

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

 public Type getType()
 {
  return type;
 }

 public void setType(Type type)
 {
  this.type = type;
 }

 public List<Dock> getSubDocks()
 {
  return subDocks;
 }

 public void setSubDocks(List<Dock> subDocks)
 {
  this.subDocks = subDocks;
 }

 
 public static String dim2String( int dim )
 {
  if( dim == 0 )
   return "*";
  
  if( dim < 0 )
   return String.valueOf(-dim)+"%";

  return String.valueOf(dim);
 }

 public Dock getParent()
 {
  return parent;
 }

 public void setParent(Dock parent)
 {
  this.parent = parent;
 }
}
