package com.pri.scilab.client.ui.module.layouted;

import java.util.ArrayList;
import java.util.List;

import com.pri.scilab.client.ui.module.activator.Action;



public abstract class DockContainerComponent extends LayoutNodeComponent
{
 public enum Actions
 {
  ADD_COLS,
  ADD_ROWS,
  SET_W,
  SET_H,
  REMOVE
 }
 
 protected static Action addColAct = new Action("Add columns",Actions.ADD_COLS.name(),"/images/silk/application_hsplit_add.png",null);
 protected static Action addRowAct = new Action("Add rows",Actions.ADD_ROWS.name(),"/images/silk/application_vsplit_add.png",null);
 protected static Action setWidthAct = new Action("Set width",Actions.SET_W.name(),"/images/silk/hsize.png",null);
 protected static Action setHeightAct = new Action("Set height",Actions.SET_H.name(),"/images/silk/vsize.png",null);
 protected static Action remAct = new Action("Remove",Actions.REMOVE.name(),"/images/silk/cross.png",null);

 
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

 public abstract void splitToColumns(DockComponent dockComponent, int rowNum);
 public abstract void splitToRows(DockComponent dockComponent, int rowNum);

 public abstract boolean setHeightOfChildren(int value);
 public abstract boolean setWidthOfChildren(int value);

 public abstract boolean canSetChildWidth();
 public abstract boolean canSetChildHeight();

}
