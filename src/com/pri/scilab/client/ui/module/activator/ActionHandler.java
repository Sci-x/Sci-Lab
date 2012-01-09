package com.pri.scilab.client.ui.module.activator;

import java.util.Collection;

public interface ActionHandler<T>
{
 void actionPerformed(String action, T object);
 void actionPerformed(String action, Collection<T> objects);
}
