package com.pri.scilab.shared.dto;

public interface Layout extends Split
{
 public enum Type
 {
  VERTICAL,
  HORIZONTAL
 };
 
 Type getType();
}
