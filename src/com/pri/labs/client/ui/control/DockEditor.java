package com.pri.labs.client.ui.control;

import java.util.Collection;

import com.pri.labs.client.structure.Action;
import com.pri.labs.client.structure.Dock;
import com.pri.labs.client.ui.module.EditorViewPort;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.ValueCallback;
import com.smartgwt.client.widgets.Canvas;

public class DockEditor extends LayoutNodeEditor
{
 public enum Actions
 {
  SPLIT2COL,
  SPLIT2ROW,
  SET_W,
  SET_H,
  REMOVE
 }

 
 private static Action  actins = new Action(null,null,null, new Action[]{
   new Action("Split to columns",Actions.SPLIT2COL.name(),"/images/silk/application_hsplit_add.png",null),
   new Action("Split to rows",Actions.SPLIT2ROW.name(),"/images/silk/application_vsplit_add.png",null),
   new Action("Set width",Actions.SET_W.name(),"/images/silk/hsize.png",null),
   new Action("Set height",Actions.SET_H.name(),"/images/silk/vsize.png",null),
   new Action("Remove",Actions.REMOVE.name(),"/images/silk/cross.png",null),
   
 });
 
 

 
 public DockEditor( LayoutEditor led, DockContainerEditor cont, Dock d )
 {
  super(led,cont,d);
 }
 
 public void setPanel( Canvas panel)
 {
  super.setPanel(panel);
  panel.setBorder("1px dotted black");
 }
 
 @Override
 public void activate(EditorViewPort pane)
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

 public static Action getDockEditorAction()
 {
  return actins;
 }

 
 @Override
 public Action getAction()
 {
  return actins;
 }

 @Override
 public String getIcon()
 {
  return "/images/silk/anchor.png";
 }



 @Override
 public void actionPerformed(String action, Editor object)
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
      getContainer().splitToColumns(DockEditor.this, rowNum);
     else
      getContainer().splitToRows(DockEditor.this, rowNum);
     
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
 public void actionPerformed(String action, Collection<Editor> objects)
 {
 }

 public void setHeight( int ht )
 {
  getDock().setHeight(ht);

  if( getPanel() != null )
   getPanel().setHeight(Dock.dim2String(ht));
  
 }
 
 public void setWidth( int ht )
 {
  getDock().setWidth(ht);

  if( getPanel() != null )
   getPanel().setWidth(Dock.dim2String(ht));
  
 }

}
