package com.pri.labs.client.ui.control;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.pri.labs.client.structure.Action;
import com.pri.labs.client.structure.Dock;
import com.pri.labs.client.structure.Dock.Type;
import com.pri.labs.client.structure.Layout;
import com.pri.labs.client.ui.module.DockPanel;
import com.pri.labs.client.ui.module.EditorViewPort;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.ValueCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class LayoutEditor extends AbstractEditor implements Editor
{
 private enum Actions
 {
  RENAME
 }
 
 private static final String defaultDockNamePrefix = "Dock ";
 
 private String icon="/images/silk/layout.png";

 private Layout layout;
 private EditorViewPort cPane;
 
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
 public void actionPerformed(String action, Editor object)
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
     fireEditorChanged();
    }
   });
  }
  
 }


 public String getNewDockName()
 {
  return defaultDockNamePrefix+(++maxDockPostfx);
 }
 

 @Override
 public void actionPerformed(String action, Collection<Editor> objects)
 {
  // TODO Auto-generated method stub
  
 }

 public void setName(String string)
 {
  layout.setName(string);
 }

 @Override
 public void activate( EditorViewPort pane )
 {
  cPane = pane;

  if( pane.getOwner() == this )
   return;
  
  Canvas root = constructLayoutModel( (LayoutNodeEditor) getSubEditors().get(0) );
  

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
  
  LayoutNodeEditor de = createNodeEditor( rd, new RootContainer() );
  
  addChild(de);
  
  createSubEditors( de );
  
//  subEd = new ArrayList<Editor>(1);
//  subEd.add(de);
  
 }
 
 private void createSubEditors( LayoutNodeEditor pde )
 {
  Dock dk = pde.getDock();

  if( dk.getSubDocks() != null )
  {
   for( Dock sdk : dk.getSubDocks() )
   {
    LayoutNodeEditor sde = createNodeEditor( sdk, (DockContainerEditor)pde );
    
    createSubEditors(sde);
    
    pde.addChild(sde);
   }
  }
 }
 
 private LayoutNodeEditor createNodeEditor( Dock d, DockContainerEditor ce )
 {
  if( d.getType() == Type.HSPLIT )
   return new HSplitEditor(this,ce,d);
  else if( d.getType() == Type.VSPLIT )
   return new VSplitEditor(this, ce,d);
  else
   return new DockEditor(this, ce, d);

 }
 
 private Canvas constructLayoutModel(LayoutNodeEditor pde)
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
    
    if( pde.getSubEditors() != null && pde.getSubEditors().size() > 0 )
    {
     for( Editor se : pde.getSubEditors() )
      vlay.addMember( constructLayoutModel((LayoutNodeEditor)se) );
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
 
 class RootContainer extends DockContainerEditor
 {

  protected RootContainer()
  {
   super(LayoutEditor.this, null, null);
  }

  @Override
  public void splitToColumns(DockEditor de, int rowNum)
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
   createSubEditors(contEdt);
   
   LayoutEditor.this.getSubEditors().clear();
   
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
  public void splitToRows(DockEditor de, int rowNum)
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
   createSubEditors(contEdt);
   
   LayoutEditor.this.getSubEditors().clear();
   
   if( cPane != null )
   {
    Canvas cnv = constructLayoutModel(contEdt);
    
    cPane.clean();
    cPane.getPane().addChild(cnv);
   }
   
   LayoutEditor.this.fireChildRemoved(0, de);
   LayoutEditor.this.fireChildInserted(0, contEdt);  }

  public boolean setHeightOfChildren( int v )
  {
   return true;
  }
  
  public boolean setWidthOfChildren( int v )
  {
   return true;
  }

  
  @Override
  public void activate(EditorViewPort pane)
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
  public void actionPerformed(String action, Editor object)
  {
  }

  @Override
  public void actionPerformed(String action, Collection<Editor> objects)
  {
  }
  
 }
}
