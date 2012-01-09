package com.pri.scilab.client.ui.module.activator;

public interface HierarchyListener<T>
{
 // void childAppended(T chld);
 void childRemoved(int idx, T chld);
 void childInserted(int idx, T chld);
 void nodeChanged();
}
