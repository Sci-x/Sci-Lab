package com.pri.scilab.shared.dto;

public interface Layout extends Container
{
 public enum Type
 {
  VERTICAL,
  HORIZONTAL
 };
 
 Type getType();
}
