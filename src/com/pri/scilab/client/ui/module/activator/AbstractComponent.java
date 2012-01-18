package com.pri.scilab.client.ui.module.activator;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractComponent implements Component
{
 private HierarchyListener<Component> listener;
 private List<Component> subComponents;
 private Component parent;
 private String id;

// private Canvas pane;
 
 public AbstractComponent()
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
 
 public String getId()
 {
  return id;
 }

 public void setId(String id)
 {
  this.id = id;
 }
 
 public void addChild( Component e )
 {
  if( subComponents == null )
   subComponents=new ArrayList<Component>(10);
  
  subComponents.add(e);
  
  fireChildInserted(subComponents.size()-1, e);
 }
 
 protected void addChildAt( int ind, Component e )
 {
  if( subComponents == null )
   subComponents=new ArrayList<Component>(10);
  
  subComponents.add(ind,e);
  
  fireChildInserted(ind, e);
 }

 
 @Override
 public List<Component> getSubComponents()
 {
  return subComponents;
 }
 
 protected void fireChildInserted(int idx, Component chld)
 {
  if( listener != null )
   listener.childInserted(idx, chld);
 }
 
 protected void fireChildRemoved(int idx, Component chld)
 {
  if( listener != null )
   listener.childRemoved(idx, chld);
 }

 protected void fireComponentChanged()
 {
  if( listener != null )
   listener.nodeChanged();
 }
 
 @Override
 public void addHierarchyListener(HierarchyListener<Component> editorListener)
 {
  if( listener == null )
  {
   listener=editorListener;
   return;
  }
  
  if( listener instanceof HierarchyListenerMultiplexor<?> )
  {
   ((HierarchyListenerMultiplexor<Component>)listener).addListener(editorListener);
   return;
  }
  
  HierarchyListenerMultiplexor<Component> mpx = new HierarchyListenerMultiplexor<Component>();
  
  mpx.addListener(listener);
  mpx.addListener(editorListener);
  
  listener=mpx;
 }

 @Override
 public void removeHierarchyListener(HierarchyListener<Component> editorListener)
 {
  if( listener == null )
   return;
  
  if( editorListener == listener )
  {
   listener=null;
   return;
  }
  
  if( listener instanceof HierarchyListenerMultiplexor<?> )
   ((HierarchyListenerMultiplexor<Component>)listener).removeListener(editorListener);
  
 }

 @Override
 public Component getParentComponent()
 {
  return parent;
 }

 public void setParentComponent(Component parent)
 {
  this.parent = parent;
 }

}
