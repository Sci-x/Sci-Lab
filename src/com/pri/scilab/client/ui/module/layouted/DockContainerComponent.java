package com.pri.scilab.client.ui.module.layouted;

import java.util.ArrayList;
import java.util.List;



public abstract class DockContainerComponent extends LayoutNodeComponent
{
 private List<LayoutNodeComponent> children = new ArrayList<LayoutNodeComponent>();

 protected DockContainerComponent(LayoutEditor led, DockContainerComponent cn)
 {
  super(led, cn);
 }

// public abstract void splitToColumns( DockComponent de, int rowNum);
//
// public abstract void splitToRows(DockComponent de, int rowNum);
//
// public abstract boolean setHeightOfChildren( int v );
// public abstract boolean setWidthOfChildren( int v );

 public void addChildComponent(LayoutNodeComponent nc)
 {
  children.add(nc);
 }
}
