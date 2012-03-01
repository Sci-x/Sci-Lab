package com.pri.scilab.shared.action;

import java.util.ArrayList;
import java.util.List;


public class Action
{
 public static final Action separator = new Action(null,null);
 
 private String   text;
 private String   action;
 private String   cls;
 private List<Action> subActions;

 public Action()
 {}

 public Action(String text, String action)
 {
  this(text, action, null, null);
 }
 
 public Action(String text, String action, String cls)
 {
  this(text, action, cls, null);
 }

 public Action(List<Action> subActions)
 {
  this(null, null, null, subActions);
 }

 public Action(String text, String action, String cls, List<Action> subActions)
 {
  super();
  this.text = text;
  this.action = action;
  this.cls = cls;
  this.subActions = subActions;
 }

 public String getText()
 {
  return text;
 }

 public void setText(String text)
 {
  this.text = text;
 }

 public String getAction()
 {
  return action;
 }

 public void setAction(String action)
 {
  this.action = action;
 }

 public String getCls()
 {
  return cls;
 }

 public void setCls(String cls)
 {
  this.cls = cls;
 }

 public List<Action> getSubActions()
 {
  return subActions;
 }

 public void setSubActions(List<Action> subActions)
 {
  this.subActions = subActions;
 }


 public Action copy()
 {
  return copyAction(this);
 }
 
 public static Action copyAction( Action orAc )
 {
  Action newAc = new Action();
  newAc.action=orAc.action;
  newAc.cls=orAc.cls;
  newAc.text=orAc.text;
  
  if( orAc.subActions != null )
  {
   newAc.subActions = new ArrayList<Action>(orAc.subActions.size());
   
   for( int i=0; i < orAc.subActions.size(); i++ )
    newAc.subActions.add(copyAction(orAc.subActions.get(i)));
  }
  
  return newAc;
 }
}