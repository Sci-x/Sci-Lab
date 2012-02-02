package com.pri.scilab.client.ui.module.layouted;

import java.util.ArrayList;
import java.util.List;

import com.pri.scilab.client.ui.module.activator.Action;
import com.pri.scilab.client.ui.module.activator.Component;
import com.smartgwt.client.widgets.layout.Layout;



public abstract class DockContainerComponent extends LayoutNodeComponent
{
 
// private List<LayoutNodeComponent> children = new ArrayList<LayoutNodeComponent>();

 protected DockContainerComponent(LayoutEditor led)
 {
  super(led);
 }

// public abstract void splitToColumns( DockComponent de, int rowNum);
//
// public abstract void splitToRows(DockComponent de, int rowNum);
//
// public abstract boolean setHeightOfChildren( int v );
// public abstract boolean setWidthOfChildren( int v );

 public void addChildComponent(LayoutNodeComponent nc)
 {
  addChild(nc);
 }

 public abstract void splitToColumns(DockComponent dockComponent, int rowNum);
 public abstract void splitToRows(DockComponent dockComponent, int rowNum);

 public abstract boolean setHeightOfChildren(int value);
 public abstract boolean setWidthOfChildren(int value);

 public abstract boolean canSetChildWidth();
 public abstract boolean canSetChildHeight();

 public int removeChild(LayoutNodeComponent comp)
 {
  if( getSubComponents().size() < 2 )
   return getContainer().removeChild( this );

  
  int ind=-1;
  boolean found = false;
  
  for( Component ed : getSubComponents() )
  {
   ind++;
   
   if( ed == comp )
   {
    found = true;
    break;
   }
  }
  
  if( ! found )
   return -1;

  ((Layout)getPanel()).removeMember(comp.getPanel());
  
  if( getSubComponents().size() == 2 )
  {
   int myInd=-1;
   
   for( Component cp : getContainer().getSubComponents() )
   {
    myInd++;
    
    if( cp == this)
     break;
   }

   LayoutNodeComponent othComp = (LayoutNodeComponent)getSubComponents().get(ind==0?1:0);
   
   othComp.setWidth(getWidth());
   othComp.setHeight(getHeight());
   
   getParentComponent().replaceChild(myInd, othComp);
   
//   
//   
//   getContainer().addChildAt( myInd, othComp );
   
   ((Layout)getPanel()).removeMember(othComp.getPanel());
   ((Layout)getContainer().getPanel()).removeMember(getPanel());
   ((Layout)getContainer().getPanel()).addMember(othComp.getPanel(), myInd);
  }
  else  
   super.removeChild(comp);
  
  return ind;
 }

 public void swapChildren(int idx1, int idx2)
 {
  super.swapChildren(idx1, idx2);
  
  if( getPanel() != null )
   ((Layout)getPanel()).reorderMember(idx2, idx1);
 }
 
 @Override
 public Action getAction()
 {
  List<Action> subAc = new ArrayList<Action>(5);
  
  if( this instanceof HSplitEditor )
   subAc.add(addColAct);
  else
   subAc.add(addRowAct);
  
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
  subAc.add(remAct);
 
  return new Action(null,null,null,subAc);
 }
}
