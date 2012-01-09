package com.pri.labs.client.ui.control;

import com.pri.labs.client.structure.Dock;


public abstract class DockContainerEditor extends LayoutNodeEditor
{

 protected DockContainerEditor(LayoutEditor led, DockContainerEditor cn, Dock d)
 {
  super(led, cn, d);
 }

 public abstract void splitToColumns( DockEditor de, int rowNum);

 public abstract void splitToRows(DockEditor de, int rowNum);

 public abstract boolean setHeightOfChildren( int v );
 public abstract boolean setWidthOfChildren( int v );
}
