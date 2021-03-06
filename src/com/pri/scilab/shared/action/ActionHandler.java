package com.pri.scilab.shared.action;

import java.util.Collection;

public interface ActionHandler<T>
{
 void actionPerformed(String action, T object);
 void actionPerformed(String action, Collection<T> objects);
}
