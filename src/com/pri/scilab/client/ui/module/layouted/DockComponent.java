package com.pri.scilab.client.ui.module.layouted;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.pri.scilab.client.ui.module.activator.Action;
import com.pri.scilab.client.ui.module.activator.Component;
import com.pri.scilab.client.ui.module.activator.ComponentViewPort;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.ValueCallback;
import com.smartgwt.client.widgets.Canvas;

public class DockComponent extends LayoutNodeComponent
{
 public enum Actions
 {
  SPLIT2COL,
  SPLIT2ROW,
  SET_W,
  SET_H,
  REMOVE
 }
 private static Action splt2ColAct = new Action("Split to columns",Actions.SPLIT2COL.name(),"/images/silk/application_hsplit_add.png",null);
 private static Action splt2RowAct = new Action("Split to rows",Actions.SPLIT2ROW.name(),"/images/silk/application_vsplit_add.png",null);
 private static Action setWidthAct = new Action("Set width",Actions.SET_W.name(),"/images/silk/hsize.png",null);
 private static Action setHeightAct = new Action("Set height",Actions.SET_H.name(),"/images/silk/vsize.png",null);
 private static Action remAct = new Action("Remove",Actions.REMOVE.name(),"/images/silk/cross.png",null);

 
// private static Action  actins = new Action(null,null,null, new Action[]{
//   new Action("Split to columns",Actions.SPLIT2COL.name(),"/images/silk/application_hsplit_add.png",null),
//   new Action("Split to rows",Actions.SPLIT2ROW.name(),"/images/silk/application_vsplit_add.png",null),
//   new Action("Set width",Actions.SET_W.name(),"/images/silk/hsize.png",null),
//   new Action("Set height",Actions.SET_H.name(),"/images/silk/vsize.png",null),
//   new Action("Remove",Actions.REMOVE.name(),"/images/silk/cross.png",null),
//   
// });
 
 private String id; 

 
 public DockComponent( LayoutEditor led, DockContainerComponent cont )
 {
  super(led,cont);
 }
 
 public void setPanel( Canvas panel)
 {
  super.setPanel(panel);
  panel.setBorder("1px dotted black");
 }
 
 @Override
 public void activate(ComponentViewPort pane)
 {
  getLayoutEditor().activate(pane);
  getPanel().setBorder("2px dotted black");
 }


 @Override
 public void deactivate()
 {
  getPanel().setBorder("1px dotted black");

  getLayoutEditor().deactivate();
 }

// public static Action getDockEditorAction()
// {
//  return actins;
// }

 
 @Override
 public Action getAction()
 {
  List<Action> subAc = new ArrayList<Action>(5);
  
  subAc.add(splt2ColAct);
  subAc.add(splt2RowAct);
  
  if( getContainer().canSetChildWidth() )
   subAc.add(setWidthAct);

  if( getContainer().canSetChildHeight() )
   subAc.add(setHeightAct);
  
  subAc.add(remAct);
 
  return new Action(null,null,null,subAc);
 }

 @Override
 public String getIcon()
 {
  return "/images/silk/anchor.png";
 }



 @Override
 public void actionPerformed(String action, Component object)
 {
  final boolean colSplit = action.equals(Actions.SPLIT2COL.name());
  
  if( colSplit || action.equals(Actions.SPLIT2ROW.name()) )
  {
   SC.askforValue("Enter number of "+(colSplit?"columns":"rows"), new ValueCallback()
   {
    
    @Override
    public void execute(String value)
    {
     int rowNum = 0;

     try
     {
      rowNum = Integer.parseInt(value);
     }
     catch(Exception e)
     {
      return;
     }

     if(rowNum <= 0 || rowNum > 10)
      return;
     
     if( colSplit )
      getContainer().splitToColumns(DockComponent.this, rowNum);
     else
      getContainer().splitToRows(DockComponent.this, rowNum);
     
    }
   });

  }
  else if( action.equals(Actions.SET_H.name()) )
  {
   askForDimension("Enter new dock height", new CallBack()
   {
    @Override
    public void execute(int value)
    {
     if( ! getContainer().setHeightOfChildren(value) )
      setHeight(value);
    }
   });
  }
  else if( action.equals(Actions.SET_W.name()) )
  {
   askForDimension("Enter new dock width", new CallBack()
   {
    @Override
    public void execute(int value)
    {
     if( ! getContainer().setWidthOfChildren(value) )
      setWidth(value);
    }
   });
  }

 }
 
 @Override
 public void actionPerformed(String action, Collection<Component> objects)
 {
 }

 public void setHeight( int ht )
 {
  super.setHeight(ht);

  if( getPanel() != null )
   getPanel().setHeight(dim2String(ht));
  
 }
 
 public void setWidth( int ht )
 {
  super.setWidth(ht);

  if( getPanel() != null )
   getPanel().setWidth(dim2String(ht));
 }

 public String getId()
 {
  return id;
 }

 public void setId(String id)
 {
  this.id = id;
 }


}
