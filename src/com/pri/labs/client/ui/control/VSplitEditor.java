package com.pri.labs.client.ui.control;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.pri.labs.client.structure.Action;
import com.pri.labs.client.structure.Dock;
import com.pri.labs.client.ui.module.DockPanel;
import com.pri.labs.client.ui.module.EditorViewPort;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.ValueCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class VSplitEditor extends DockContainerEditor
{
 public enum Actions
 {
  ADD_ROWS,
  SET_W,
  SET_H,
  REMOVE
 }

 
 private static Action  actins = new Action(null,null,null, new Action[]{
   new Action("Add rows",Actions.ADD_ROWS.name(),"/images/silk/application_vsplit_add.png",null),
   new Action("Set width",Actions.SET_W.name(),"/images/silk/hsize.png",null),
   new Action("Set height",Actions.SET_H.name(),"/images/silk/vsize.png",null),
   new Action("Remove",Actions.REMOVE.name(),"/images/silk/cross.png",null),
   
 });
 
 
 public VSplitEditor( LayoutEditor led, DockContainerEditor cn, Dock d )
 {
  super(led, cn, d);
 }
 
 public void setPanel(Canvas panel)
 {
  super.setPanel(panel);
  
  if( panel != null )
   panel.setBorder("1px dashed green");
 }
 
 @Override
 public void activate(EditorViewPort pane)
 {
  getLayoutEditor().activate(pane);
  getPanel().setBorder("2px dashed green");
 }


 @Override
 public void deactivate()
 {
  getPanel().setBorder("1px dashed green");
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
  return "/images/silk/application_split.png";
 }



 @Override
 public void actionPerformed(String action, Editor object)
 {

  if( Actions.ADD_ROWS.name().equals(action) )
  {
   askAndAddRows();
  }
  else if( action.equals(Actions.SET_H.name()) )
  {
   askForDimension("Enter new dock height", new CallBack()
   {
    @Override
    public void execute(int value)
    {
     getContainer().setHeightOfChildren(value);
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
     setWidthOfChildren(value);
    }
   });
  }
  
 }

 @Override
 public void actionPerformed(String action, Collection<Editor> objects)
 {
  // TODO Auto-generated method stub

 }

 
 public boolean setHeightOfChildren( int v )
 {
  return false;
 }
 
 public boolean setWidthOfChildren( int value )
 {
  if( ! getContainer().setWidthOfChildren(value) )
   setWidth(value);
  
  return true;
 }
 
 public void setWidth( int wd )
 {
  getDock().setWidth(wd);
  
  if( getPanel() != null )
   getPanel().setWidth( Dock.dim2String(wd) );
 }
 
 private void askAndAddRows()
 {
  SC.askforValue("Enter number of rows", new ValueCallback()
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
    
    addRows( rowNum );
   }
  }
  );
 }
 
 private void addRows( int rowNum )
 {
  List<Dock> subDks = getDock().getSubDocks();
  
  VLayout panel = (VLayout)getPanel();

  for(int i=0; i < rowNum; i++ )
  {
   Dock nd = new Dock();
   
   nd.setName(getLayoutEditor().getNewDockName());
   nd.setHeight(0);
   nd.setWidth(-100);
   nd.setType(Dock.Type.DOCK);

   subDks.add( nd );
   
   DockEditor nde = new DockEditor(getLayoutEditor(), this, nd);
   
   addChild(nde);

   if( panel != null )
   {
    DockPanel dkp = new DockPanel(Dock.dim2String(nd.getWidth()), Dock.dim2String(nd.getHeight()), nd, nde);

    panel.addMember(dkp);
    nde.setPanel(dkp);
   }
  }
  
 }
 
 @Override
 public void splitToColumns( DockEditor de, int rowNum )
 {
  int ind=-1;
  
  for( Editor ed : getSubEditors() )
  {
   ind++;
   
   if( ed == de )
    break;
  }
  
  
  Dock rd = de.getDock();
  
  rd.setName( rd.getName());
  
  rd.setType(Dock.Type.HSPLIT);
  HSplitEditor contEdt = new HSplitEditor(getLayoutEditor(), this, rd);

  List<Dock> subDks = new ArrayList<Dock>(rowNum);

  HLayout hl = new HLayout(1);
  hl.setWidth("100%");
  hl.setPadding(1);
  hl.setHeight(Dock.dim2String(rd.getHeight()));

  contEdt.setPanel(hl);
  
  for(int i=0; i < rowNum; i++ )
  {
   Dock nd = new Dock();
   
   nd.setName(getLayoutEditor().getNewDockName());
   nd.setHeight(-100);
   nd.setWidth(0);
   nd.setType(Dock.Type.DOCK);

   subDks.add( nd );
   
   DockEditor nde = new DockEditor(getLayoutEditor(), contEdt, nd);
   
   contEdt.addChild(nde);
  }
  
  rd.setSubDocks(subDks);
  
  getSubEditors().set(ind, contEdt);
  
  VLayout panel = (VLayout)getPanel();
  
  if( panel != null )
  {
   panel.removeMember(panel.getMember(ind));
   
   panel.addMember(hl,ind);
   
//   if( ( getSubEditors().size()-1 ) != ind )
//    panel.reorderMember(getSubEditors().size()-1, ind);
   
   for( Editor ed : contEdt.getSubEditors())
   {
    DockEditor dked = (DockEditor)ed;
    
    Dock d = dked.getDock();
    
    DockPanel dkp = new DockPanel(Dock.dim2String(d.getWidth()),Dock.dim2String(d.getHeight()), d, dked );

    hl.addMember(dkp);
    dked.setPanel(dkp);
   }
  }
  
  fireChildRemoved(ind, de);
  fireChildInserted(ind, contEdt);
 }

 @Override
 public void splitToRows( DockEditor de, int rowNum )
 {
  int ind=-1;
  
  for( Editor ed : getSubEditors() )
  {
   ind++;
   
   if( ed == de )
    break;
  }
  
  
  Dock rd = de.getDock();
  
  int newH = rd.getHeight()/rowNum;
  
  rd.setName( rd.getName() );
  rd.setHeight(newH);

  List<Dock> subDks = getDock().getSubDocks();
  
  for(int i=1; i < rowNum; i++ )
  {
   Dock nd = new Dock();
   
   nd.setName(getLayoutEditor().getNewDockName());
   nd.setHeight(newH);
   nd.setWidth(rd.getWidth());
   nd.setType(Dock.Type.DOCK);

   subDks.add(ind+i, nd );
   
   DockEditor nde = new DockEditor(getLayoutEditor(), this, nd);
   
   addChildAt(ind+i,nde);
  }
  
  VLayout panel = (VLayout)getPanel();
  
  if( panel != null )
  {
   panel.getMember(ind).setWidth(Dock.dim2String(rd.getWidth()));
   
   
   for( int j=ind+1; j < ind+rowNum; j++)
   {
    DockEditor dked = (DockEditor)getSubEditors().get(j);

    Dock d = dked.getDock();
    
    DockPanel dkp = new DockPanel(Dock.dim2String(d.getWidth()),Dock.dim2String(d.getHeight()), d, dked );

    panel.addMember(dkp,j);
    dked.setPanel(dkp);
   }
   
  }
  
 // fireChildRemoved(ind, de);
 // fireChildInserted(ind, contEdt);
  
 }

}
