package com.pri.scilab.admin.client.ui.module.activator;

import java.util.Collection;

public abstract class ActionAdapter<T> implements ActionHandler<T>
{
 @Override
 public void actionPerformed(String action, Collection<T> objects)
 {
 }
}