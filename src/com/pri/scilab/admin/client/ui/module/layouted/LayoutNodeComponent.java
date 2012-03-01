package com.pri.scilab.admin.client.ui.module.layouted;

import com.pri.scilab.admin.client.ui.module.activator.AbstractComponent;
import com.pri.scilab.admin.client.ui.module.activator.Component;
import com.pri.scilab.shared.action.Action;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.ValueCallback;
import com.smartgwt.client.widgets.Canvas;

public abstract class LayoutNodeComponent extends AbstractComponent
{
 public enum Actions
 {
  ADD_COLS,
  ADD_ROWS,
  SPLIT2COL,
  SPLIT2ROW,
  SET_W,
  SET_H,
  REMOVE,
  UP,
  DOWN,
  LEFT,
  RIGHT
 }
 
 protected static Action splt2ColAct = new Action("Split to columns",Actions.SPLIT2COL.name(),"/images/silk/application_hsplit_add.png",null);
 protected static Action splt2RowAct = new Action("Split to rows",Actions.SPLIT2ROW.name(),"/images/silk/application_vsplit_add.png",null);
 protected static Action setWidthAct = new Action("Set width",Actions.SET_W.name(),"/images/silk/hsize.png",null);
 protected static Action setHeightAct = new Action("Set height",Actions.SET_H.name(),"/images/silk/vsize.png",null);
 protected static Action remAct = new Action("Remove",Actions.REMOVE.name(),"/images/silk/cross.png",null);

 protected static Action leftAct = new Action("Left",Actions.LEFT.name(),"/images/silk/arrow_left.png",null);
 protected static Action rightAct = new Action("Right",Actions.RIGHT.name(),"/images/silk/arrow_right.png",null);
 protected static Action upAct = new Action("Up",Actions.LEFT.name(),"/images/silk/arrow_up.png",null);
 protected static Action downAct = new Action("Down",Actions.RIGHT.name(),"/images/silk/arrow_down.png",null);

 protected static Action addColAct = new Action("Add columns",Actions.ADD_COLS.name(),"/images/silk/application_hsplit_add.png",null);
 protected static Action addRowAct = new Action("Add rows",Actions.ADD_ROWS.name(),"/images/silk/application_vsplit_add.png",null);

 
// private Dock dock;
 private LayoutEditor layEd;
 private Canvas panel;
 private int width;
 private int height;
// private DockContainerComponent container;
 
 protected LayoutNodeComponent( LayoutEditor led )
 {
  layEd=led;
  
  addHierarchyListener(led);
 }
 
// public Dock getDock()
// {
//  return dock;
// }
//
// public void setDock(Dock dock)
// {
//  this.dock = dock;
// }
// 
// public Dock.Type getType()
// {
//  return dock.getType();
// }

 public LayoutEditor getLayoutEditor()
 {
  return layEd;
 }
 
 public Canvas getPanel()
 {
  return panel;
 }

 public void setPanel(Canvas panel)
 {
  this.panel = panel;
 }
 
 @Override
 public String getName()
 {
  return getId();
 }
 
 protected interface CallBack
 {
  void execute(int value);
 }
 
 protected void askForDimension( String prompt , final CallBack cb )
 {
  SC.askforValue(prompt, new ValueCallback()
  {
   public void execute(String value)
   {
    value = value.trim();
    
    int multip = 0; 
    
    int val=0;
   
    if( "*".equals(value) )
     val=0;
    else if( value.charAt(value.length()-1) == '%' )
    {
     value = value.substring(0,value.length()-1);
     multip=-1;
    }
    else
     multip=1;
    
    if( multip != 0 )
    {
     try
     {
      val=Integer.parseInt(value);
      val*=multip;
     }
     catch(Exception e)
     {
     }
     
     if( val == 0 || val < -100 )
     {
      SC.say("Entered value is invalid");
      return;
     }
    }

    cb.execute(val);
   }
  });
  
 }

 public DockContainerComponent getContainer()
 {
  Component parent = getParentComponent();
  
  if( parent == null || ! (parent instanceof DockContainerComponent) )
   return null;
  
  return (DockContainerComponent)parent;
 }

 public int getWidth()
 {
  return width;
 }

 public void setWidth(int width)
 {
  this.width = width;
 }

 public int getHeight()
 {
  return height;
 }

 public void setHeight(int height)
 {
  this.height = height;
 }
 
 public static String dim2String( int dim )
 {
  if( dim == 0 )
   return "*";
  
  if( dim < 0 )
   return String.valueOf(-dim)+"%";

  return String.valueOf(dim);
 }
}
