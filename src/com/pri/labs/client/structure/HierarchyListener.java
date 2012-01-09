package com.pri.labs.client.structure;

public interface HierarchyListener<T>
{
 // void childAppended(T chld);
 void childRemoved(int idx, T chld);
 void childInserted(int idx, T chld);
 void nodeChanged();
}
