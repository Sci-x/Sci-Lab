package com.pri.scilab.shared.dto;

import java.util.ArrayList;
import java.util.List;

public class Split extends LayoutComponent
{
 private List<LayoutComponent> content = new ArrayList<LayoutComponent>();
 

 public List<LayoutComponent> getComponents()
 {
  return content;
 }


 public void addChildComponent(LayoutComponent nc)
 {
  content.add(nc);
 }


}
