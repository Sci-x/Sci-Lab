package com.pri.scilab.admin.client.ui.module.activator;

import java.util.LinkedList;
import java.util.List;

public class HierarchyListenerMultiplexor<T> implements HierarchyListener<T>
{
 private List<HierarchyListener<T>> listeners = new LinkedList<HierarchyListener<T>>();

 public void addListener( HierarchyListener<T> lsn )
 {
  listeners.add(lsn);
 }

 public void removeListener( HierarchyListener<T> lsn )
 {
  listeners.remove(lsn);
 }
 
 @Override
 public void childInserted(int idx, T chld)
 {
  for( HierarchyListener<T> lsn : listeners )
   lsn.childInserted(idx, chld);
 }

 @Override
 public void childRemoved(T chld)
 {
  for( HierarchyListener<T> lsn : listeners )
   lsn.childRemoved(chld);
 }

 @Override
 public void nodeChanged()
 {
  for( HierarchyListener<T> lsn : listeners )
   lsn.nodeChanged();
 }

 @Override
 public void childReplaced(int idx, T chld)
 {
  for( HierarchyListener<T> lsn : listeners )
   lsn.childReplaced(idx, chld);
 }

 @Override
 public void focusRequested()
 {
  for( HierarchyListener<T> lsn : listeners )
   lsn.focusRequested();
 }

 @Override
 public void childrenSwaped(int idx1, int idx2)
 {
  for( HierarchyListener<T> lsn : listeners )
   lsn.childrenSwaped(idx1, idx2);
 }

}
