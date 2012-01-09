package com.pri.scilab.client.ui.module.activator;

import com.smartgwt.client.widgets.Canvas;

public class ComponentViewPort
{
 private Component owner;
 private Canvas pane;
 
 public ComponentViewPort( Canvas pane )
 {
  this.pane=pane;
 }
 
 public Canvas getPane()
 {
  return pane;
 }

 public Component getOwner()
 {
  return owner;
 }

 public void setOwner(Component owner)
 {
  this.owner = owner;
 }
 
 public void clean()
 {
  Canvas[] chld = pane.getChildren();
  
  if( chld == null )
   return;
  
  for(Canvas cn : chld)
   pane.removeChild(cn);
 }
 
}
