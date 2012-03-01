package com.pri.scilab.admin.client.ui.module.activator;

import java.util.ArrayList;
import java.util.List;

import com.pri.scilab.shared.action.ActionAdapter;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.VisibilityChangedEvent;
import com.smartgwt.client.widgets.events.VisibilityChangedHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.client.widgets.tree.events.NodeClickEvent;
import com.smartgwt.client.widgets.tree.events.NodeClickHandler;
import com.smartgwt.client.widgets.tree.events.NodeContextClickEvent;
import com.smartgwt.client.widgets.tree.events.NodeContextClickHandler;

public class ActivatorTree extends VLayout
{
 static final String NAME_PROP="name";
// static final String CHILDREN_PROP="children";
 static final String CONTROLLER_PROP="_controller";
 static final String ICON_PROP="icon";
 
 private final Tree treeModel = new Tree();
 private final TreeGrid tree = new TreeGrid();
 
 private Component activeComponent = null;
 private ComponentViewPort viewPort;
 private ToolStrip toolStrip;
 
 public ActivatorTree( final ComponentViewPort editorPane, Component[] editors )
 {
  viewPort=editorPane;
  
  treeModel.setModelType(TreeModelType.CHILDREN);  
  treeModel.setNameProperty(NAME_PROP);

  tree.setSelectionType( SelectionStyle.SINGLE );
  
  TreeNode rootNode = new TreeNode();
  
  treeModel.setRoot( rootNode );

  for( int i=0; i < editors.length; i++ )
  {
   TreeNode cn = new TreeNode();
   
   treeModel.add(cn, rootNode);

   attachComponent(cn, editors[i]);
  }

  
  tree.setShowHeader(false);
  tree.setData(treeModel);
  tree.setShowConnectors(true);
  tree.setShowRoot(false);

  treeModel.openAll();
  
  toolStrip = new ToolStrip();
  toolStrip.setWidth100();
  toolStrip.setMembersMargin(3);

  tree.addNodeClickHandler( new NodeClickHandler()
  {
   @Override
   public void onNodeClick(NodeClickEvent event)
   {
    activateComponent( getNodeComponent(event.getNode()) );
   }
  });

  

  
  tree.addNodeContextClickHandler( new NodeContextClickHandler()
  {
   @Override
   public void onNodeContextClick(final NodeContextClickEvent event)
   {
    event.cancel();
    
    final Component ed = getNodeComponent(event.getNode());
    
    Menu nodeMenu = new ActionMenu<Void>( ed.getAction(), null, new ActionAdapter<Void>()
      {
       @Override
       public void actionPerformed(String action, Void object)
       {
        ed.actionPerformed(action, ed);        
       }
      });
      
    nodeMenu.addVisibilityChangedHandler(new VisibilityChangedHandler()
    {
     
     @Override
     public void onVisibilityChanged(VisibilityChangedEvent event)
     {
      ((Menu)event.getSource()).destroy();
     }
    });
    
      nodeMenu.setTop(event.getY());
      nodeMenu.setLeft(event.getX());
      nodeMenu.show();

   }
  }
  );
  
  
  addMember(toolStrip);
  
  tree.setWidth100();
  tree.setHeight100();
  
  addMember(tree);
 }
 
 
 private void activateComponent( final Component comp )
 {
  if( comp == activeComponent )
   return;
  
  
  Canvas[] sbCanvas = toolStrip.getMembers();
  
  toolStrip.removeMembers(sbCanvas);

  
  ActionStrip.putActions(toolStrip, comp.getAction(), comp, new ActionAdapter<Component>()
    {
     @Override
     public void actionPerformed(String action, Component object)
     {
      comp.actionPerformed(action, comp);        
     }
    });

  if( activeComponent != null )
   activeComponent.deactivate();
  
  activeComponent=comp;
  
  comp.activate( viewPort );

 }
// private TreeNode createComponentNode( Component ed )
// {
//  TreeNode[] subedNodes= null;
//  
//  List<Component> subed = ed.getSubComponents();
//  
//  if( subed != null )
//  {
//   subedNodes = new TreeNode[subed.size()];
//   
//   int i=0;
//   for( Component ied : subed )
//    subedNodes[i++] = createComponentNode(ied);
//  }
//  
//  TreeNode myNode = new TreeNode();
//     
//  myNode.setTitle(ed.getName());
//  myNode.setAttribute(NAME_PROP, ed.getName());
//  myNode.setAttribute(CHILDREN_PROP, subedNodes);
//  myNode.setAttribute(COMPONENT_PROP, ed);
//  myNode.setAttribute(ICON_PROP, ed.getIcon());
//  
//  ed.addHierarchyListener(new ComponentListener(myNode));
//  
//  return myNode;
// }
 
 private void attachComponent( TreeNode myNode, Component comp )
 {
  TreeNode[] subedNodes= null;
  
  subedNodes = treeModel.getChildren(myNode);
  
  if( subedNodes != null && subedNodes.length > 0 )
   treeModel.removeList(subedNodes);
  
//  treeModel.re
  
  List<Component> subed = comp.getSubComponents();
  
  if( subed != null )
  {
   subedNodes = new TreeNode[subed.size()];
   
//   treeModel.addList(subedNodes, myNode);

   int i=0;
   for( Component ied : subed )
   {
    subedNodes[i] = new TreeNode();
    
    treeModel.add(subedNodes[i], myNode);
    
    attachComponent(subedNodes[i], ied);
    
    i++;
   }
  }
  
  NodeController nc = new NodeController(comp, myNode);
  
  myNode.setTitle(comp.getName());
  myNode.setAttribute(NAME_PROP, comp.getName());
  myNode.setAttribute(CONTROLLER_PROP, nc);
  myNode.setAttribute(ICON_PROP, comp.getIcon());
  
  comp.addHierarchyListener( nc );

 }

 private static NodeController getNodeController( Record tn )
 {
  return (NodeController)tn.getAttributeAsObject(CONTROLLER_PROP);
 }
 
 private static Component getNodeComponent( Record tn )
 {
  return getNodeController(tn).getComponent();
 }

 class NodeController implements HierarchyListener<Component>
 {
  private TreeNode treeNode;
  private Component component;
  
  public NodeController(Component c, TreeNode nd)
  {
   component = c;
   treeNode=nd;
  }
  
  
  @Override
  public void childInserted(int idx, Component chld)
  {
   TreeNode nNd = new TreeNode();
   
   treeModel.add(nNd, treeNode, idx);
   
   attachComponent(nNd, chld);
   
//   for( TreeNode n : treeModel.getChildren(treeNode) )
//     System.out.println( n.getClass().getName());
//   chld.addHierarchyListener( new EditorListener(nNd) );
  }

  @Override
  public void childRemoved(Component chld)
  {
   TreeNode[] cNodes = treeModel.getChildren(treeNode);
 
   ListGridRecord rec = tree.getSelectedRecord();

   boolean found = false;
   
   NodeController chNodeCont = null;
   
   for( int i=0; i < cNodes.length; i++ )
   {
    chNodeCont = getNodeController(cNodes[i]);
    
    if( chNodeCont.getComponent() == chld )
    {
     treeModel.remove(cNodes[i]);
     found = true;
     break;
    }
   }
   
   if(! found )
    return;
   
   if( rec != null )
   {
    Component cmp = getNodeComponent(rec);
    
    if( cmp == chld )
    {
     cmp.deactivate();
    
     toolStrip.removeMembers(toolStrip.getMembers());
    }
   }

   
   chld.removeHierarchyListener( chNodeCont );
  }


  @Override
  public void nodeChanged()
  {
   treeNode.setTitle(component.getName());
   treeNode.setIcon(component.getIcon());
   
   tree.redraw();
  }


  @Override
  public void childReplaced(int idx, Component chld)
  {
   attachComponent(treeModel.getChildren(treeNode)[idx], chld);

   tree.redraw();
   
   ListGridRecord rec = tree.getSelectedRecord();
   
   if( rec != null)
   {
    Component cmp = getNodeComponent(rec);
    
    activateComponent( cmp );
   }

  }


  @Override
  public void focusRequested()
  {
   selectComponent(component);
  }


  @Override
  public void childrenSwaped(int idx1, int idx2)
  {
   TreeNode[] chld = treeModel.getChildren(treeNode);
   
   NodeController nc1 = getNodeController(chld[idx1]);
   NodeController nc2 = getNodeController(chld[idx2]);
   
   nc1.getComponent().removeHierarchyListener( nc1 );
   nc2.getComponent().removeHierarchyListener( nc2 );
   
   attachComponent(chld[idx1], nc2.getComponent());
   attachComponent(chld[idx2], nc1.getComponent());

   tree.redraw();
   
   ListGridRecord rec = tree.getSelectedRecord();
   
   if( rec != null)
   {
    Component cmp = getNodeComponent(rec);
    
    activateComponent( cmp );
   }

  }


  public Component getComponent()
  {
   return component;
  }

  
 }

 
 public boolean activateComponent( List<String> path )
 {
  TreeNode pNode = treeModel.getRoot();
 
  for( String pEl : path )
  {
   TreeNode[] chldNodes = treeModel.getChildren(pNode);
   
   TreeNode cNode = null;
   
   for( TreeNode nd : chldNodes )
   {
    Component comp = getNodeComponent(nd);
    
    if( pEl.equals(comp.getId()) )
    {
     cNode = nd;
     break;
    }
   }
   
   if( cNode == null )
    return false;
   
   treeModel.openFolder(cNode);
   pNode=cNode;
  }
 
  tree.selectRecord(pNode);
  
  return true;
 }
 
 private TreeNode findTreeNode( Component c, boolean openPath )
 {
  ArrayList<Component> path = new ArrayList<Component>();
  
  Component comp = c;
  
  while( comp != null )
  {
   path.add(comp);
   
   comp = comp.getParentComponent();
  }
  
  TreeNode cNode = treeModel.getRoot();
  
  for( int i=path.size()-1; i >= 0; i-- )
  {
   boolean found = false;
   
   for( TreeNode tn : treeModel.getChildren(cNode) )
   {
    if( getNodeComponent(tn) == path.get(i) )
    {
     if( openPath )
      treeModel.openFolder(tn);
     
     cNode = tn;
     
     found = true;
     
     break;
    }
   }
   
   if( ! found )
    return null;
  }
  
  return cNode;
 }
 
 public boolean selectComponent( Component c )
 {
  TreeNode cNode = findTreeNode(c, true);

  if( cNode == null )
   return false;
  
  tree.selectSingleRecord(cNode);
  
  activateComponent(c);
  
  return true;
 }

/*
 
 @Override
 public void childInserted(int idx, Component chld)
 {
  TreeNode nNd = new TreeNode();
  
  TreeNode cNode = findTreeNode(chld, false);
  
  treeModel.add(nNd, cNode, idx);
  
  attachComponent(nNd, chld);
  
//  for( TreeNode n : treeModel.getChildren(treeNode) )
//    System.out.println( n.getClass().getName());
//  chld.addHierarchyListener( new EditorListener(nNd) );
 }

 @Override
 public void childRemoved(int idx, Component chld)
 {
  TreeNode cNode = findTreeNode(chld, false);

  TreeNode[] cNodes = treeModel.getChildren(cNode);

  ListGridRecord rec = tree.getSelectedRecord();

  boolean found = false;
  
  for( int i=0; i < cNodes.length; i++ )
  {
   Component cmp = (Component)cNodes[i].getAttributeAsObject(COMPONENT_PROP);
   
   if( cmp == chld )
   {
    treeModel.remove(cNodes[i]);
    found = true;
    break;
   }
  }
  
  if(! found )
   return;
  
  if( rec != null )
  {
   Component cmp = (Component)rec.getAttributeAsObject(COMPONENT_PROP);
   
   if( cmp == chld )
   {
    cmp.deactivate();
   
    toolStrip.removeMembers(toolStrip.getMembers());
   }
  }

  
  chld.removeHierarchyListener( this );
 }


 @Override
 public void nodeChanged( Component c )
 {
  TreeNode cNode = findTreeNode(c, false);
  
  cNode.setTitle(c.getName());
  cNode.setIcon(c.getIcon());
  
  tree.redraw();
 }


 @Override
 public void childReplaced(int idx, Component chld)
 {
  TreeNode cNode = findTreeNode(chld, false);

  attachComponent(treeModel.getChildren(cNode)[idx], chld);

  tree.redraw();
  
  ListGridRecord rec = tree.getSelectedRecord();
  
  if( rec != null)
  {
   Component cmp = (Component)rec.getAttributeAsObject(COMPONENT_PROP);
   
   activateComponent( cmp );
  }

 }


 @Override
 public void focusRequested(Component comp)
 {
  selectComponent(comp);
 }


 @Override
 public void childrenSwaped(int idx1, int idx2)
 {
  TreeNode[] chld = treeModel.getChildren(treeNode);
  
  Component c1 = (Component)chld[idx1].getAttributeAsObject(COMPONENT_PROP);
  Component c2 = (Component)chld[idx2].getAttributeAsObject(COMPONENT_PROP);
  
  attachComponent(chld[idx1], c2);
  attachComponent(chld[idx2], c1);

  tree.redraw();
  
  ListGridRecord rec = tree.getSelectedRecord();
  
  if( rec != null)
  {
   Component cmp = (Component)rec.getAttributeAsObject(COMPONENT_PROP);
   
   activateComponent( cmp );
  }

 }

*/
}
