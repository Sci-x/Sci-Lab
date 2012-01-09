package com.pri.labs.client.ui.control;

import java.util.ArrayList;
import java.util.List;

import com.pri.labs.client.structure.HierarchyListener;
import com.pri.labs.client.structure.HierarchyListenerMultiplexor;

public abstract class AbstractEditor implements Editor
{
 private HierarchyListener<Editor> listener;
 private List<Editor> subEditors;

// private Canvas pane;
 
 public AbstractEditor()
 {
 }

// protected void setEditorPane(Canvas cnv)
// {
//  pane=cnv;
// }

// protected Canvas getEditorPane()
// {
//  return pane;
// }
 
 protected void addChild( Editor e )
 {
  if( subEditors == null )
   subEditors=new ArrayList<Editor>(10);
  
  subEditors.add(e);
  
  fireChildInserted(subEditors.size()-1, e);
 }
 
 protected void addChildAt( int ind, Editor e )
 {
  if( subEditors == null )
   subEditors=new ArrayList<Editor>(10);
  
  subEditors.add(ind,e);
  
  fireChildInserted(ind, e);
 }

 
 @Override
 public List<Editor> getSubEditors()
 {
  return subEditors;
 }
 
 protected void fireChildInserted(int idx, Editor chld)
 {
  if( listener != null )
   listener.childInserted(idx, chld);
 }
 
 protected void fireChildRemoved(int idx, Editor chld)
 {
  if( listener != null )
   listener.childRemoved(idx, chld);
 }

 protected void fireEditorChanged()
 {
  if( listener != null )
   listener.nodeChanged();
 }
 
 @Override
 public void addHierarchyListener(HierarchyListener<Editor> editorListener)
 {
  if( listener == null )
  {
   listener=editorListener;
   return;
  }
  
  if( listener instanceof HierarchyListenerMultiplexor<?> )
  {
   ((HierarchyListenerMultiplexor<Editor>)listener).addListener(editorListener);
   return;
  }
  
  HierarchyListenerMultiplexor<Editor> mpx = new HierarchyListenerMultiplexor<Editor>();
  
  mpx.addListener(listener);
  mpx.addListener(editorListener);
  
  listener=mpx;
 }

 @Override
 public void removeHierarchyListener(HierarchyListener<Editor> editorListener)
 {
  if( listener == null )
   return;
  
  if( editorListener == listener )
  {
   listener=null;
   return;
  }
  
  if( listener instanceof HierarchyListenerMultiplexor<?> )
   ((HierarchyListenerMultiplexor<Editor>)listener).removeListener(editorListener);
  
 }

}
