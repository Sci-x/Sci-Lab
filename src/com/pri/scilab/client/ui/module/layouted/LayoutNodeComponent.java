package com.pri.scilab.client.ui.module.layouted;

import com.pri.scilab.client.ui.module.activator.AbstractComponent;
import com.pri.scilab.client.ui.module.activator.Component;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.ValueCallback;
import com.smartgwt.client.widgets.Canvas;

public abstract class LayoutNodeComponent extends AbstractComponent
{
// private Dock dock;
 private LayoutEditor layEd;
 private Canvas panel;
 private int width;
 private int height;
// private DockContainerComponent container;
 
 protected LayoutNodeComponent( LayoutEditor led, DockContainerComponent cn )
 {
  layEd=led;
//  dock=d;
  setParentComponent(cn);
  
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
