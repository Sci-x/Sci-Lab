package com.pri.scilab.client.ui.module.activator;

import java.util.List;



public interface Component extends ActionHandler<Component>
{
 Action getAction();

 String getName();
 String getIcon();
 String getId();

 List<Component> getSubComponents();
 
 Component getParentComponent();
 void setParentComponent( Component p );

 void addHierarchyListener(HierarchyListener<Component> editorListener);

 void removeHierarchyListener(HierarchyListener<Component> editorListener);

 void activate( ComponentViewPort pane );
 void deactivate();

 void setId(String id);

 void addChild(Component e);

 void insertChild(int ind, Component e);

 void replaceChild(int index, Component e);

 void removeChild(Component e);

 void requestFocus();

 void swapChildren(int idx1, int idx2);
}
