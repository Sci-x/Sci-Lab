package com.pri.scilab.client.ui.module.activator;


public class Action
{
 private String   text;
 private String   action;
 private String   cls;
 private Action[] subActions;

 public Action()
 {}

 public Action(String text, String action)
 {
  this(text, action, null, null);
 }
 
 public Action(String text, String action, String cls, Action[] subActions)
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

 public Action[] getSubActions()
 {
  return subActions;
 }

 public void setSubActions(Action[] subActions)
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
   newAc.subActions = new Action[orAc.subActions.length];
   
   for( int i=0; i < orAc.subActions.length; i++ )
    newAc.subActions[i]=copyAction(orAc.subActions[i]);
  }
  
  return newAc;
 }
}