package com.pri.scilab.client.ui.module.activator;

public interface HierarchyListener<T>
{
 void childInserted(int idx, T chld);
 void childReplaced(int idx, T chld);
 void childRemoved(T chld);
 
 void focusRequested();
 void childrenSwaped(int idx1, int idx2);
 void nodeChanged();
}
