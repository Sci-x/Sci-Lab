package com.pri.scilab.client.ui.module.layouted;



public abstract class DockContainerEditor extends LayoutNodeComponent
{

 protected DockContainerEditor(LayoutEditor led, DockContainerEditor cn, Dock d)
 {
  super(led, cn, d);
 }

 public abstract void splitToColumns( DockComponent de, int rowNum);

 public abstract void splitToRows(DockComponent de, int rowNum);

 public abstract boolean setHeightOfChildren( int v );
 public abstract boolean setWidthOfChildren( int v );
}
