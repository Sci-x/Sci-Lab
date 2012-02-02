package com.pri.scilab.admin.client.ui.module.layouted;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.pri.scilab.admin.client.ui.module.activator.Action;
import com.pri.scilab.admin.client.ui.module.activator.Component;
import com.pri.scilab.admin.client.ui.module.activator.ComponentViewPort;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.ValueCallback;
import com.smartgwt.client.widgets.Canvas;

public class DockComponent extends LayoutNodeComponent
{

 private String id; 

 
 public DockComponent( LayoutEditor led)
 {
  super(led);
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
  getPanel().setBorder("2px dashed blue");
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
  
  subAc.add( Action.separator );
  
  List<Component> sibls = getContainer().getSubComponents();
  
  if( sibls.get(0) != this )
  {
   if( getContainer() instanceof HSplitEditor )
    subAc.add(leftAct);
   else
    subAc.add(upAct);
  }
  
  if( sibls.get( sibls.size() -1 ) != this )
  {
   if( getContainer() instanceof HSplitEditor )
    subAc.add(rightAct);
   else
    subAc.add(downAct);
  }

  subAc.add( Action.separator );
  
  if( ! ( getContainer() instanceof LayoutEditor ) )
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
  else if( action.equals(Actions.REMOVE.name() ) )
  {
   if( getContainer() != null )
    getContainer().removeChild( this );
  }
  else if( action.equals(Actions.RIGHT.name() ) || action.equals(Actions.DOWN.name() ) )
  {
   if( getContainer() == null )
    return;
   
   List<Component> scList = getContainer().getSubComponents();
   
   int idx = scList.indexOf(this);
   
   if( ( scList.size()-1 ) == idx )
    return;
   
   getContainer().swapChildren( idx, idx+1 );
  }
  else if( action.equals(Actions.LEFT.name() ) || action.equals(Actions.UP.name() ) )
  {
   if( getContainer() == null )
    return;
   
   List<Component> scList = getContainer().getSubComponents();
   
   int idx = scList.indexOf(this);
   
   if( 0 == idx )
    return;
   
   getContainer().swapChildren( idx, idx-1 );
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
