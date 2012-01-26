package com.pri.scilab.client.ui.module.activator;

public interface HierarchyListener<T>
{
 void childInserted(int idx, T chld);
 void childReplaced(int idx, Component chld);
 void childRemoved(int idx, T chld);
 
 void nodeChanged();
}
