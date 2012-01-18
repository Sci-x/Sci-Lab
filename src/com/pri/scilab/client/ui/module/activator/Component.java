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

 void addHierarchyListener(HierarchyListener<Component> editorListener);

 void removeHierarchyListener(HierarchyListener<Component> editorListener);

 void activate( ComponentViewPort pane );
 void deactivate();
}
