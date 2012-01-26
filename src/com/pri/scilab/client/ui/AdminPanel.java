package com.pri.scilab.client.ui;

import java.util.ArrayList;

import com.pri.scilab.client.ui.module.activator.Action;
import com.pri.scilab.client.ui.module.activator.ActivatorTree;
import com.pri.scilab.client.ui.module.activator.Component;
import com.pri.scilab.client.ui.module.activator.ComponentViewPort;
import com.pri.scilab.client.ui.module.layouted.LayoutsEditor;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.HLayout;

public class AdminPanel extends HLayout
{
 public AdminPanel()
 {
  setWidth100();
  setHeight100();


   Canvas editorPane =  new Canvas();
   editorPane.setWidth100();
   editorPane.setHeight100();
 
   ActivatorTree edt = new ActivatorTree( new ComponentViewPort(editorPane), new Component[]{ new LayoutsEditor() } );
   edt.setWidth(300);
   edt.setShowResizeBar(true);

   
   addMember( edt );
   addMember(editorPane);
 }
 
 private Action getLayoutAction()
 {
  Action res = new Action();
  
  res.setSubActions( new ArrayList<Action>() {{ add(new Action("New Layout","new","/images/silk/accept.png",null)); }} );

  
  return res;
 }
}
