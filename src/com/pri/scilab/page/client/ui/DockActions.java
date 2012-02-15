package com.pri.scilab.page.client.ui;

import java.util.ArrayList;

import com.pri.scilab.admin.client.ui.module.activator.Action;

public class DockActions
{
 enum Actions
 {
  ADDFACT,
  DECOR,
  FILTER
 }
 
 static String iconPath = "/images/icons/page/";
 
 private static Action action;
 
 static
 {
  ArrayList<Action> acts = new ArrayList<Action>();
  
  acts.add( new Action("Add fact", Actions.ADDFACT.name(), iconPath+"addfact.png"  ) );
  acts.add( new Action() );
  acts.add( new Action("Decoration", Actions.DECOR.name(), iconPath+"decoration.png"  ) );
  acts.add( new Action("Filter", Actions.FILTER.name(), iconPath+"filter.png"  ) );
  
  action = new Action( acts );
 }
 
 public static Action getActions()
 {
  return action;
 }
 
}
