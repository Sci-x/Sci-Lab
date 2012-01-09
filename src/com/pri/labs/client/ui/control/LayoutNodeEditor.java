package com.pri.labs.client.ui.control;

import com.pri.labs.client.structure.Dock;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.ValueCallback;
import com.smartgwt.client.widgets.Canvas;

public abstract class LayoutNodeEditor extends AbstractEditor
{
 private Dock dock;
 private LayoutEditor layEd;
 private Canvas panel;
 private DockContainerEditor container;
 
 protected LayoutNodeEditor( LayoutEditor led, DockContainerEditor cn,  Dock d )
 {
  layEd=led;
  dock=d;
  container=cn;
 }
 
 public Dock getDock()
 {
  return dock;
 }

 public void setDock(Dock dock)
 {
  this.dock = dock;
 }
 
 public Dock.Type getType()
 {
  return dock.getType();
 }

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
  return dock.getName();
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

 public DockContainerEditor getContainer()
 {
  return container;
 }


}
