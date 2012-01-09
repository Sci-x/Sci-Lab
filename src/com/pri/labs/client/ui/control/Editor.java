package com.pri.labs.client.ui.control;

import java.util.List;

import com.pri.labs.client.structure.Action;
import com.pri.labs.client.structure.ActionHandler;
import com.pri.labs.client.structure.HierarchyListener;
import com.pri.labs.client.ui.module.EditorViewPort;

public interface Editor extends ActionHandler<Editor>
{
 Action getAction();

 String getName();
 String getIcon();

 List<Editor> getSubEditors();

 void addHierarchyListener(HierarchyListener<Editor> editorListener);

 void removeHierarchyListener(HierarchyListener<Editor> editorListener);

 void activate( EditorViewPort pane );
 void deactivate();
}
