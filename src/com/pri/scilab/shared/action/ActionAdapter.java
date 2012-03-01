package com.pri.scilab.shared.action;

import java.util.Collection;

public abstract class ActionAdapter<T> implements ActionHandler<T>
{
 @Override
 public void actionPerformed(String action, Collection<T> objects)
 {
 }
}
