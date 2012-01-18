package com.pri.scilab.client.ui.module.layouted;

import java.util.Collection;

import com.pri.scilab.client.ui.module.activator.Action;
import com.pri.scilab.client.ui.module.activator.Component;
import com.pri.scilab.client.ui.module.activator.ComponentViewPort;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.ValueCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class HSplitEditor extends DockContainerComponent
{
 public enum Actions
 {
  ADD_COLS,
  SET_W,
  SET_H,
  REMOVE
 }

 
 private static Action  actins = new Action(null,null,null, new Action[]{
   new Action("Add columns",Actions.ADD_COLS.name(),"/images/silk/application_hsplit_add.png",null),
   new Action("Set width",Actions.SET_W.name(),"/images/silk/hsize.png",null),
   new Action("Set height",Actions.SET_H.name(),"/images/silk/vsize.png",null),
   new Action("Remove",Actions.REMOVE.name(),"/images/silk/cross.png",null),
   
 });
 
 
 public HSplitEditor( LayoutEditor led, DockContainerComponent cn)
 {
  super(led, cn);
 }
 
 public void setPanel(Canvas panel)
 {
  super.setPanel(panel);
  panel.setBorder("1px dashed blue");
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
  getPanel().setBorder("1px dashed blue");
  getLayoutEditor().deactivate();
 }

 @Override
 public Action getAction()
 {
  return actins;
 }

 @Override
 public String getIcon()
 {
  return "/images/silk/application_hsplit.png";
 }



 @Override
 public void actionPerformed(String action, Component object)
 {
  if( Actions.ADD_COLS.name().equals(action) )
  {
   askAndAddCols();
  }
  else if( action.equals(Actions.SET_H.name()) )
  {
   askForDimension("Enter new dock height", new CallBack()
   {
    @Override
    public void execute(int value)
    {
     setHeightOfChildren(value);
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
     getContainer().setWidthOfChildren(value);
    }
   });
  }
 }

 private void askAndAddCols()
 {
  SC.askforValue("Enter number of columns", new ValueCallback()
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
    
    addCols( rowNum );
   }
  }
  );
 }
 
 private void addCols( int colNum )
 {
  HLayout panel = (HLayout)getPanel();

  for(int i=0; i < colNum; i++ )
  {
   final DockComponent nde = new DockComponent(getLayoutEditor(), this);

   
   nde.setId(getLayoutEditor().getNewDockName());
   nde.setHeight(-100);
   nde.setWidth(0);
//   nd.setType(Dock.Type.DOCK);

   addChild(nde);
   
   if( panel != null )
   {
    DockPanel dkp = new DockPanel(dim2String(nde.getWidth()), dim2String(nde.getHeight()), nde, nde);
    
    dkp.addClickHandler( new ClickHandler()
    {
     
     @Override
     public void onClick(ClickEvent event)
     {
      System.out.println("Clicked: "+nde.getId());
     }
    });

    panel.addMember(dkp);
    nde.setPanel(dkp);
   }
  }
  
  


 }
 
 public void setHeight( int v )
 {
  super.setHeight(v);
  
  if( getPanel() != null )
   getPanel().setHeight( Dock.dim2String(v) );
 }
 
 public boolean setHeightOfChildren( int value )
 {
  if(!getContainer().setHeightOfChildren(value))
   setHeight(value);
  
  return true;
 }
 
 public boolean setWidthOfChildren( int v )
 {
  return false;
 }
 
 @Override
 public void actionPerformed(String action, Collection<Component> objects)
 {
  // TODO Auto-generated method stub

 }

 @Override
 public void splitToColumns( DockComponent de, int colNum )
 {
  int ind=-1;
  
  for( Component ed : getSubComponents() )
  {
   ind++;
   
   if( ed == de )
    break;
  }
  
  
  int newW = de.getWidth()/colNum;
  
  de.setWidth(newW);

  
  for(int i=1; i < colNum; i++ )
  {
   DockComponent nde = new DockComponent(getLayoutEditor(), this);

   nde.setId(getLayoutEditor().getNewDockName());
   nde.setHeight(de.getHeight());
   nde.setWidth(newW);

   addChildAt(ind+i,nde);
  }
  
  HLayout panel = (HLayout)getPanel();
  
  if( panel != null )
  {
   panel.getMember(ind).setHeight(Dock.dim2String(de.getHeight()));
   
   
   for( int j=ind+1; j < ind+colNum; j++)
   {
    DockComponent dked = (DockComponent)getSubComponents().get(j);

    DockPanel dkp = new DockPanel(dim2String(dked.getWidth()),dim2String(dked.getHeight()), dked, dked );

    panel.addMember(dkp,j);
    dked.setPanel(dkp);
   }
   
  }
 }

 @Override
 public void splitToRows( DockComponent de, int rowNum )
 {
  int ind=-1;
  
  for( Component ed : getSubComponents() )
  {
   ind++;
   
   if( ed == de )
    break;
  }
  
  


  VSplitEditor contEdt = new VSplitEditor(getLayoutEditor(), this);

  VLayout vl = new VLayout(1);
  vl.setHeight("100%");
  vl.setPadding(1);
  vl.setWidth(dim2String(de.getWidth()));

  contEdt.setPanel(vl);
  
  for(int i=0; i < rowNum; i++ )
  {
   Dock nd = new Dock();
   DockComponent nde = new DockComponent(getLayoutEditor(), contEdt);
   
   nde.setId(getLayoutEditor().getNewDockName());
   nde.setHeight(0);
   nde.setWidth(-100);

   contEdt.addChild(nde);
  }
  
  getSubComponents().set(ind, contEdt);
  
  HLayout panel = (HLayout)getPanel();
  
  if( panel != null )
  {
   panel.removeMember(panel.getMember(ind));
   
   panel.addMember(vl,ind);
   
//   if( ( getSubEditors().size()-1 ) != ind )
//    panel.reorderMember(getSubEditors().size()-1, ind);
   
   for( Component ed : contEdt.getSubComponents())
   {
    DockComponent dked = (DockComponent)ed;
    
    DockPanel dkp = new DockPanel(dim2String(dked.getWidth()),Dock.dim2String(dked.getHeight()), dked, dked );

    vl.addMember(dkp);
    dked.setPanel(dkp);
   }
  }
  
  fireChildRemoved(ind, de);
  fireChildInserted(ind, contEdt);
 }

}
