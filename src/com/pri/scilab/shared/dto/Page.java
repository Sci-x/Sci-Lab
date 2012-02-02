package com.pri.scilab.shared.dto;

import java.util.List;

public class Page
{
 private PageLayout    layout;
 private List<Docklet> docklets;

 public PageLayout getLayout()
 {
  return layout;
 }

 public void setLayout(PageLayout layout)
 {
  this.layout = layout;
 }

 public List<Docklet> getDocklets()
 {
  return docklets;
 }

 public void setDocklets(List<Docklet> docklets)
 {
  this.docklets = docklets;
 }
}
