package com.pri.scilab.client.ui.module.activator;

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
 public void childRemoved(int idx, T chld)
 {
  for( HierarchyListener<T> lsn : listeners )
   lsn.childRemoved(idx, chld);
 }

 @Override
 public void nodeChanged()
 {
  for( HierarchyListener<T> lsn : listeners )
   lsn.nodeChanged();
 }

}
