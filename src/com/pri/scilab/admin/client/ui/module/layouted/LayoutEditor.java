package com.pri.scilab.admin.client.ui.module.layouted;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import com.pri.scilab.admin.client.ui.module.activator.Action;
import com.pri.scilab.admin.client.ui.module.activator.Component;
import com.pri.scilab.admin.client.ui.module.activator.ComponentViewPort;
import com.pri.scilab.admin.client.ui.module.activator.HierarchyListener;
import com.pri.scilab.shared.dto.Dock;
import com.pri.scilab.shared.dto.HSplit;
import com.pri.scilab.shared.dto.LayoutComponent;
import com.pri.scilab.shared.dto.PageLayout;
import com.pri.scilab.shared.dto.Split;
import com.pri.scilab.shared.dto.VSplit;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.ValueCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

public class LayoutEditor extends DockContainerComponent implements Component, HierarchyListener<Component>
{
 private static class Int
 {
  int i;
 }
 
 private enum Actions
 {
  RENAME
 }
 
 private static final String defaultDockNamePrefix = "Dock ";
 private static final String defaultVSplitNamePrefix = "VSplit ";
 private static final String defaultHSplitNamePrefix = "HSplit ";
 
 private String icon="/images/silk/layout.png";

 private PageLayout layout;
 private ComponentViewPort cPane;
 
 Map<String, Int> countMap = new TreeMap<String, LayoutEditor.Int>();


// private List<Editor> subEd;
 
 public LayoutEditor( String nm )
 {
  this( new PageLayout( nm ) );
 }

 public LayoutEditor(PageLayout lt)
 {
  super(null);
  
  layout = lt;
 
  createLayoutComponents();
  
//  maxDockPostfx=findMaxDockNameSuffixRec(layout.getRootComponent());
 }

 @Override
 public Action getAction()
 {
  Action res = new Action();

  res.setSubActions( Collections.singletonList(new Action("Rename", Actions.RENAME.name(), "/images/silk/anchor.png", null)) );

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
  return getNewXName(defaultDockNamePrefix);
 }
 
 public String getNewVSplitName()
 {
  return getNewXName(defaultVSplitNamePrefix);
 }

 public String getNewHSplitName()
 {
  return getNewXName(defaultHSplitNamePrefix);
 }

 public String getNewXName( String pfx )
 {
  Int i = countMap.get(pfx);
  
  if( i != null )
   return pfx + (++i.i);
  
  countMap.put(pfx, i=new Int());
  
  i.i = findMaxForPrefix(pfx, this);
  
  return pfx + (++i.i);
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
  
  Canvas root = constructLayoutView( (LayoutNodeComponent) getSubComponents().get(0) );
  
  VLayout myContainer = new VLayout();
  
  myContainer.setWidth100();
  myContainer.setHeight100();
  
  myContainer.addMember(root);

  pane.clean();
  pane.getPane().addChild(myContainer);
  pane.setOwner(this);
  setPanel(myContainer);
 }

 @Override
 public void deactivate()
 {
  cPane = null;
 }
 
 private void createLayoutComponents()
 {
  LayoutComponent rc = layout.getRootComponent();
  
  if( rc == null )
   return;
  
  LayoutNodeComponent de = createNodeComponent( rc, this );
  
  addChild(de);
  
 }
 
 private LayoutNodeComponent createNodeComponent( LayoutComponent lc, DockContainerComponent parent )
 {
  LayoutNodeComponent comp = null;
  
  boolean split = true;
  
  if( lc instanceof Dock )
  {
   split = false;
   comp = new DockComponent(this);
  }
  else if( lc instanceof HSplit)
   comp = new HSplitEditor(this);
  else if( lc instanceof VSplit)
   comp = new VSplitEditor(this);
  else
   return null;
   
  comp.setId(lc.getName());
  comp.setHeight(lc.getHeight());
  comp.setWidth(lc.getWidth());
   
  if( split && ((Split)lc).getComponents() != null )
  {
   for( LayoutComponent sc : ((Split)lc).getComponents() )
    ((DockContainerComponent)comp).addChildComponent( createNodeComponent(sc,(DockContainerComponent)comp) );
   
  }
  return comp;
 }


 
 private Canvas constructLayoutView(LayoutNodeComponent pde)
 {
  if( pde instanceof DockComponent )
  {
   Canvas dp = new DockPanel(LayoutNodeComponent.dim2String(pde.getWidth()),LayoutNodeComponent.dim2String(pde.getHeight()), (DockComponent)pde, pde );
   pde.setPanel(dp);
   
   return dp;
  }
  
  Layout lay=null;
  
  if( pde instanceof VSplitEditor )
   lay = new VLayout(1);
  else if( pde instanceof HSplitEditor)
   lay = new HLayout(1);

  if( lay == null )
   return null;
  
  lay.setWidth(LayoutNodeComponent.dim2String(pde.getWidth()));
  lay.setHeight(LayoutNodeComponent.dim2String(pde.getHeight()));
   
  lay.setPadding(1);
   
  pde.setPanel(lay);
   
  if( pde.getSubComponents() != null && pde.getSubComponents().size() > 0 )
  {
   for( Component se : pde.getSubComponents() )
    lay.addMember( constructLayoutView((LayoutNodeComponent)se) );
  }

  
  return lay;
 }

 protected String generateNewName( String pfx )
 {
  return pfx+(findMaxForPrefix(pfx, this)+1);
 }
 
 private int findMaxForPrefix( String pfx, Component comp )
 {
  int max = 0;
  
  if( comp.getName().startsWith(pfx) )
  {
   try
   {
    int nm = Integer.parseInt(comp.getName().substring(pfx.length()));

    if( nm > max )
     max=nm;
   }
   catch(Exception e)
   {
   }
  }
  
  if( comp instanceof DockContainerComponent && ((DockContainerComponent)comp).getSubComponents() != null )
  {
   for( Component sdk : ((DockContainerComponent)comp).getSubComponents() )
   {
    int nm = findMaxForPrefix(pfx, sdk);

    if( nm > max )
     max=nm;
   }
  }

  return max;
 }
 
// private static int findMaxDockNameSuffixRec( LayoutComponent dk )
// {
//  int max=0;
//  
//  String name = dk.getName();
//  
//  if( name.startsWith(defaultDockNamePrefix)  )
//  {
//   try
//   {
//    int nm = Integer.parseInt(name.substring(defaultDockNamePrefix.length()));
//    
//    if( nm > max )
//     max=nm;
//   }
//   catch(Exception e)
//   {
//   }
//  }
//  
//  if( dk instanceof Split && ((Split)dk).getComponents() != null )
//  {
//   for( LayoutComponent sdk : ((Split)dk).getComponents() )
//   {
//    int nm = findMaxDockNameSuffixRec(sdk);
//
//    if( nm > max )
//     max=nm;
//   }
//  }
//  
//  return max;
// }
 
/* 
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
    Canvas cnv = constructLayoutView(contEdt);
    
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
    Canvas cnv = constructLayoutView(contEdt);
    
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

 */
 
 @Override
 public String getId()
 {
  return layout.getName();
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
  contEdt.setId( getNewHSplitName() );

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
  

  VSplitEditor contEdt = new VSplitEditor(getLayoutEditor());
  contEdt.setId( getNewVSplitName() );

  VLayout vl = new VLayout(1);
  vl.setHeight("100%");
  vl.setPadding(1);
  vl.setWidth(dim2String(de.getWidth()));

  contEdt.setPanel(vl);
  
  for(int i=0; i < rowNum; i++ )
  {
   DockComponent nde = new DockComponent(getLayoutEditor());
   
   nde.setId(getLayoutEditor().getNewDockName());
   nde.setHeight(0);
   nde.setWidth(-100);

   contEdt.addChild(nde);
  }
  
  replaceChild( ind, contEdt );
  
//  getSubComponents().set(ind, contEdt);
  
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
    
    DockPanel dkp = new DockPanel(dim2String(dked.getWidth()),dim2String(dked.getHeight()), dked, dked );

    vl.addMember(dkp);
    dked.setPanel(dkp);
   }
  }
  
//  fireChildRemoved(ind, de);
//  fireChildInserted(ind, contEdt);
 }

 @Override
 public int removeChild( LayoutNodeComponent comp )
 {
  return -1;
 }
 
 @Override
 public boolean setHeightOfChildren(int value)
 {
  return false;
 }

 @Override
 public boolean setWidthOfChildren(int value)
 {
  return false;
 }

 @Override
 public boolean canSetChildWidth()
 {
  return false;
 }

 @Override
 public boolean canSetChildHeight()
 {
  return false;
 }

 @Override
 public void childInserted(int idx, Component chld)
 {
 }

 @Override
 public void childReplaced(int idx, Component chld)
 {
  countMap.clear();
 }

 @Override
 public void childRemoved(Component chld)
 {
  countMap.clear();
 }

 @Override
 public void nodeChanged()
 {
  countMap.clear();
 }

 @Override
 public void focusRequested()
 {
 }

 @Override
 public void childrenSwaped(int idx1, int idx2)
 {
  // TODO Auto-generated method stub
  
 }
}
