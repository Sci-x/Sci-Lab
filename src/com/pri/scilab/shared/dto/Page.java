package com.pri.scilab.shared.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Page implements Serializable
{
 private PageLayout    layout;
 private List<Docklet> docklets;
 private Map<String,DockVisualCfg> visConfigs;

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

 public Map<String, DockVisualCfg> getVisualConfigs()
 {
  return visConfigs;
 }

 public DockVisualCfg getVisualConfig( String id )
 {
  return visConfigs.get(id);
 }

 
 public void setVisualConfigs(Map<String, DockVisualCfg> visConfigs)
 {
  this.visConfigs = visConfigs;
 }
}
