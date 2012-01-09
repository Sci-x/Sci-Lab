package com.pri.labs.client.structure;

import java.util.Collection;

public interface ActionHandler<T>
{
 void actionPerformed(String action, T object);
 void actionPerformed(String action, Collection<T> objects);
}
