package com.pri.scilab.client.ui.module.layouted;



public abstract class DockContainerComponent extends LayoutNodeComponent
{
// public enum Type
// {
//  VERTICAL,
//  HORIZONTAL
// }

 protected DockContainerComponent(LayoutEditor led, DockContainerComponent cn)
 {
  super(led, cn);
 }

 public abstract void splitToColumns( DockComponent de, int rowNum);

 public abstract void splitToRows(DockComponent de, int rowNum);

 public abstract boolean setHeightOfChildren( int v );
 public abstract boolean setWidthOfChildren( int v );
}
