package com.pri.labs.client.ui.module;

import com.pri.labs.client.ui.control.Editor;
import com.smartgwt.client.widgets.Canvas;

public class EditorViewPort
{
 private Editor owner;
 private Canvas pane;
 
 public EditorViewPort( Canvas pane )
 {
  this.pane=pane;
 }
 
 public Canvas getPane()
 {
  return pane;
 }

 public Editor getOwner()
 {
  return owner;
 }

 public void setOwner(Editor owner)
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
