package com.pri.scilab.page.client.ui;

public class Util
{
 public static String dim2String( int dim )
 {
  if( dim == 0 )
   return "*";
  
  if( dim < 0 )
   return String.valueOf(-dim)+"%";
  
  return String.valueOf(dim);
 }
}
