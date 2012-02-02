package com.pri.scilab.admin.client.ui.module.activator;

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
 
 @Override
 public String getId()
 {
  return id;
 }

 @Override
 public void setId(String id)
 {
  this.id = id;
 }
 
 @Override
 public void addChild( Component e )
 {
  if( subComponents == null )
   subComponents=new ArrayList<Component>(10);
  
  subComponents.add(e);

  e.setParentComponent(this);
  
  fireChildInserted(subComponents.size()-1, e);
 }
 
 @Override
 public void insertChild( int ind, Component e )
 {
  if( subComponents == null )
   subComponents=new ArrayList<Component>(10);
  
  subComponents.add(ind,e);
  
  e.setParentComponent(this);
  
  fireChildInserted(ind, e);
 }

 @Override
 public void replaceChild(int index, Component e )
 {
  subComponents.set(index,e);
  
  e.setParentComponent(this);

  fireChildReplaced(index,e);
 }
 
 @Override
 public void removeChild( Component e )
 {
  int i = subComponents.indexOf(e);
  
  if( i < 0 )
   return;
  
  subComponents.remove(i);
  
  fireChildRemoved(e);
 }
 
 @Override
 public void swapChildren(int idx1, int idx2)
 {
  Component cmp1 = getSubComponents().get(idx1);
  Component cmp2 = getSubComponents().get(idx2);
  
  getSubComponents().set(idx1, cmp2);
  getSubComponents().set(idx2, cmp1);
  
  fireChildrenSwaped(idx1, idx2);
 }
 
 private void fireChildrenSwaped(int idx1, int idx2)
 {
  if( listener != null )
   listener.childrenSwaped(idx1, idx2);
 }

 @Override
 public List<Component> getSubComponents()
 {
  return subComponents;
 }
 
 protected void fireChildReplaced(int idx, Component chld)
 {
  if( listener != null )
   listener.childReplaced(idx, chld);
 }

 
 protected void fireChildInserted(int idx, Component chld)
 {
  if( listener != null )
   listener.childInserted(idx, chld);
 }
 
 protected void fireChildRemoved(Component chld)
 {
  if( listener != null )
   listener.childRemoved(chld);
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

 @Override
 public void setParentComponent(Component parent)
 {
  this.parent = parent;
 }

 @Override
 public void requestFocus()
 {
  listener.focusRequested();
 }
}
