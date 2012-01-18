package com.pri.scilab.client.ui.module.layouted;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.pri.scilab.client.ui.module.activator.AbstractComponent;
import com.pri.scilab.client.ui.module.activator.Action;
import com.pri.scilab.client.ui.module.activator.Component;
import com.pri.scilab.client.ui.module.activator.ComponentViewPort;
import com.pri.scilab.client.ui.module.layouted.Dock.Type;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.ValueCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class LayoutEditor extends AbstractComponent implements Component
{
 private enum Actions
 {
  RENAME
 }
 
 private static final String defaultDockNamePrefix = "Dock ";
 
 private String icon="/images/silk/layout.png";

 private Layout layout;
 private ComponentViewPort cPane;
 
 private int maxDockPostfx; 
// private List<Editor> subEd;
 
 public LayoutEditor( String nm )
 {
  this( new Layout( nm ) );
 }

 public LayoutEditor(Layout lt)
 {
  super();
  
  layout = lt;
 
  setDockEditors();
  
  maxDockPostfx=findMaxDockNameSuffixRec(layout.getRootDock());
 }

 @Override
 public Action getAction()
 {
  Action res = new Action();

  res.setSubActions(

  new Action[] 
   { 
    new Action("Rename", Actions.RENAME.name(), "/images/silk/anchor.png", null),
   }

  );

  return res;
 }

 @Override
 public String getIcon()
 {
  return icon;
 }
 
 public void setIcon( String ic )
 {
  icon=ic;
 }

 @Override
 public String getName()
 {
  return layout.getName();
 }

// @Override
// public List<Editor> getSubEditors()
// {
//  return subEd;
// }


 @Override
 public void actionPerformed(String action, Component object)
 {
  if( Actions.RENAME.name().equals(action) )
  {
   SC.askforValue("Enter new name", new ValueCallback()
   {
    @Override
    public void execute(String value)
    {
     setName(value);
     setIcon(icon);
     fireComponentChanged();
    }
   });
  }
  
 }


 public String getNewDockName()
 {
  return defaultDockNamePrefix+(++maxDockPostfx);
 }
 

 @Override
 public void actionPerformed(String action, Collection<Component> objects)
 {
  // TODO Auto-generated method stub
  
 }

 public void setName(String string)
 {
  layout.setName(string);
 }

 @Override
 public void activate( ComponentViewPort pane )
 {
  cPane = pane;

  if( pane.getOwner() == this )
   return;
  
  Canvas root = constructLayoutModel( (LayoutNodeComponent) getSubComponents().get(0) );
  

  pane.clean();
  pane.getPane().addChild(root);
  pane.setOwner(this);
  
 }

 @Override
 public void deactivate()
 {
  cPane = null;
 }
 
 private void setDockEditors()
 {
  Dock rd = layout.getRootDock();
  
  if( rd == null )
   return;
  
  LayoutNodeComponent de = createNodeComponent( rd, new RootContainer() );
  
  addChild(de);
  
  createSubComponents( de );
  
//  subEd = new ArrayList<Editor>(1);
//  subEd.add(de);
  
 }
 
 private void createSubComponents( LayoutNodeComponent pde )
 {
  Dock dk = pde.getDock();

  if( dk.getSubDocks() != null )
  {
   for( Dock sdk : dk.getSubDocks() )
   {
    LayoutNodeComponent sde = createNodeComponent( sdk, (DockContainerComponent)pde );
    
    createSubComponents(sde);
    
    pde.addChild(sde);
   }
  }
 }
 
 private LayoutNodeComponent createNodeComponent( Dock d, DockContainerComponent ce )
 {
  if( d.getType() == Type.HSPLIT )
   return new HSplitEditor(this,ce);
  else if( d.getType() == Type.VSPLIT )
   return new VSplitEditor(this, ce);
  else
   return new DockComponent(this, ce);
 }
 
 private Canvas constructLayoutModel(LayoutNodeComponent pde)
 {
  Dock dock = pde.getDock();
  
  switch( dock.getType() )
  {
   case HSPLIT:
   case VSPLIT:
    com.smartgwt.client.widgets.layout.Layout vlay = dock.getType()==Type.HSPLIT?new HLayout(1):new VLayout(1);
    vlay.setWidth(Dock.dim2String(dock.getWidth()));
    vlay.setHeight(Dock.dim2String(dock.getHeight()));
    
    vlay.setPadding(1);
    
    pde.setPanel(vlay);
    
    if( pde.getSubComponents() != null && pde.getSubComponents().size() > 0 )
    {
     for( Component se : pde.getSubComponents() )
      vlay.addMember( constructLayoutModel((LayoutNodeComponent)se) );
    }
    else
    {
     vlay.addMember( new DockPanel(Dock.dim2String(dock.getWidth()),Dock.dim2String(dock.getHeight()), dock, pde ));
    }
    
    return vlay;
    
   case DOCK:
    
    Canvas dp = new DockPanel(Dock.dim2String(dock.getWidth()),Dock.dim2String(dock.getHeight()), dock, pde );
    pde.setPanel(dp);
    
    return dp;
   
  }
  
  return null;
 }

 private static int findMaxDockNameSuffixRec( Dock dk )
 {
  int max=0;
  
  String name = dk.getName();
  
  if( name.startsWith(defaultDockNamePrefix)  )
  {
   try
   {
    int nm = Integer.parseInt(name.substring(defaultDockNamePrefix.length()));
    
    if( nm > max )
     max=nm;
   }
   catch(Exception e)
   {
   }
  }
  
  if( dk.getSubDocks() != null )
  {
   for( Dock sdk : dk.getSubDocks() )
   {
    int nm = findMaxDockNameSuffixRec(sdk);

    if( nm > max )
     max=nm;
   }
  }
  
  return max;
 }
 
 class RootContainer extends DockContainerComponent
 {

  protected RootContainer()
  {
   super(LayoutEditor.this, null, null);
  }

  @Override
  public void splitToColumns(DockComponent de, int rowNum)
  {
   Dock rd = layout.getRootDock();
   
   rd.setType(Dock.Type.HSPLIT);

   List<Dock> subDks = new ArrayList<Dock>(rowNum);
   
   for(int i=0; i < rowNum; i++ )
   {
    Dock nd = new Dock();
    
    nd.setName(getNewDockName());
    nd.setHeight(-100);
    nd.setWidth(0);
    nd.setType(Dock.Type.DOCK);

    subDks.add( nd );
   }
   
   rd.setSubDocks(subDks);
   
   HSplitEditor contEdt = new HSplitEditor(LayoutEditor.this, this, rd);
   createSubComponents(contEdt);
   
   LayoutEditor.this.getSubComponents().clear();
   
   if( cPane != null )
   {
    Canvas cnv = constructLayoutModel(contEdt);
    
    cPane.clean();
    cPane.getPane().addChild(cnv);
   }
   
   LayoutEditor.this.fireChildRemoved(0, de);
   LayoutEditor.this.fireChildInserted(0, contEdt);
  }

  @Override
  public void splitToRows(DockComponent de, int rowNum)
  {
   Dock rd = layout.getRootDock();
   
   rd.setType(Dock.Type.VSPLIT);

   List<Dock> subDks = new ArrayList<Dock>(rowNum);
   
   for(int i=0; i < rowNum; i++ )
   {
    Dock nd = new Dock();
    
    nd.setName(defaultDockNamePrefix+(++maxDockPostfx));
    nd.setHeight(0);
    nd.setWidth(-100);
    nd.setType(Dock.Type.DOCK);

    subDks.add( nd );
   }
   
   rd.setSubDocks(subDks);
   
   VSplitEditor contEdt = new VSplitEditor(LayoutEditor.this, this, rd);
   createSubComponents(contEdt);
   
   LayoutEditor.this.getSubComponents().clear();
   
   if( cPane != null )
   {
    Canvas cnv = constructLayoutModel(contEdt);
    
    cPane.clean();
    cPane.getPane().addChild(cnv);
   }
   
   LayoutEditor.this.fireChildRemoved(0, de);
   LayoutEditor.this.fireChildInserted(0, contEdt);  
  }

  public boolean setHeightOfChildren( int v )
  {
   return true;
  }
  
  public boolean setWidthOfChildren( int v )
  {
   return true;
  }

  
  @Override
  public void activate(ComponentViewPort pane)
  {
  }

  @Override
  public void deactivate()
  {
  }

  @Override
  public Action getAction()
  {
   return null;
  }

  @Override
  public String getIcon()
  {
   return null;
  }

  @Override
  public void actionPerformed(String action, Component object)
  {
  }

  @Override
  public void actionPerformed(String action, Collection<Component> objects)
  {
  }
  
 }

 @Override
 public String getId()
 {
  return layout.getName();
 }
}
