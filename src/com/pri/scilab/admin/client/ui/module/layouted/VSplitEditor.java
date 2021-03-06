package com.pri.scilab.admin.client.ui.module.layouted;

import java.util.Collection;
import java.util.List;

import com.pri.scilab.admin.client.ui.module.activator.Component;
import com.pri.scilab.admin.client.ui.module.activator.ComponentViewPort;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.ValueCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class VSplitEditor extends DockContainerComponent
{


 public VSplitEditor( LayoutEditor led )
 {
  super(led );
 }
 
 public void setPanel(Canvas panel)
 {
  super.setPanel(panel);
  
  if( panel != null )
   panel.setBorder("1px dashed black");
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
  getPanel().setBorder("1px dashed black");
  getLayoutEditor().deactivate();
 }

 @Override
 public String getIcon()
 {
  return "/images/silk/application_split.png";
 }



 @Override
 public void actionPerformed(String action, Component object)
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
  else if( action.equals(Actions.REMOVE.name()) )
   getContainer().removeChild(this);
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
  super.setWidth(wd);
  
  if( getPanel() != null )
   getPanel().setWidth( dim2String(wd) );
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
  
  VLayout panel = (VLayout)getPanel();

  for(int i=0; i < rowNum; i++ )
  {
   DockComponent nde = new DockComponent(getLayoutEditor());
   
   nde.setId(getLayoutEditor().getNewDockName());
   nde.setHeight(0);
   nde.setWidth(-100);

   
   
   addChild(nde);

   if( panel != null )
   {
    DockPanel dkp = new DockPanel(dim2String(nde.getWidth()), dim2String(nde.getHeight()), nde, nde);

    panel.addMember(dkp);
    nde.setPanel(dkp);
   }
  }
  
 }
 
 @Override
 public void splitToColumns( DockComponent de, int rowNum )
 {
  int ind=-1;
  
  for( Component ed : getSubComponents() )
  {
   ind++;
   
   if( ed == de )
    break;
  }
  
  
  HSplitEditor contEdt = new HSplitEditor(getLayoutEditor());
  contEdt.setId( getLayoutEditor().getNewHSplitName() );
  contEdt.setHeight( de.getHeight() );
  contEdt.setWidth( de.getWidth() );

  HLayout hl = new HLayout(1);
  hl.setWidth("100%");
  hl.setPadding(1);
  hl.setHeight(dim2String(de.getHeight()));

  contEdt.setPanel(hl);
  
  for(int i=0; i < rowNum; i++ )
  {
   DockComponent nde = new DockComponent(getLayoutEditor());
   
   nde.setId(getLayoutEditor().getNewDockName());
   nde.setHeight(-100);
   nde.setWidth(0);

   contEdt.addChild(nde);
  }
  
  replaceChild( ind, contEdt );
  
  VLayout panel = (VLayout)getPanel();
  
  if( panel != null )
  {
   panel.removeMember(panel.getMember(ind));
   
   panel.addMember(hl,ind);
   
//   if( ( getSubEditors().size()-1 ) != ind )
//    panel.reorderMember(getSubEditors().size()-1, ind);
   
   for( Component ed : contEdt.getSubComponents())
   {
    DockComponent dked = (DockComponent)ed;
    
    
    DockPanel dkp = new DockPanel(dim2String(dked.getWidth()),dim2String(dked.getHeight()), dked, dked );

    hl.addMember(dkp);
    dked.setPanel(dkp);
   }
  }
  
//  fireChildRemoved(ind, de);
//  fireChildInserted(ind, contEdt);
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
  
  
  
  int newH = de.getHeight()/rowNum;
  
  de.setHeight(newH);
  
  for(int i=1; i < rowNum; i++ )
  {
   DockComponent nde = new DockComponent(getLayoutEditor());
   
   nde.setId(getLayoutEditor().getNewDockName());
   nde.setHeight(newH);
   nde.setWidth(de.getWidth());

   insertChild(ind+i,nde);
  }
  
  VLayout panel = (VLayout)getPanel();
  
  if( panel != null )
  {
   panel.getMember(ind).setWidth(dim2String(de.getWidth()));
   
   
   for( int j=ind+1; j < ind+rowNum; j++)
   {
    DockComponent dked = (DockComponent)getSubComponents().get(j);

    
    DockPanel dkp = new DockPanel(dim2String(dked.getWidth()),dim2String(dked.getHeight()), dked, dked );

    panel.addMember(dkp,j);
    dked.setPanel(dkp);
   }
   
  }
  
 // fireChildRemoved(ind, de);
 // fireChildInserted(ind, contEdt);
  
 }

 @Override
 public boolean canSetChildHeight()
 {
  return getSubComponents().size() > 0;
 }

 @Override
 public boolean canSetChildWidth()
 {
  return getContainer().canSetChildWidth();
 }

}
