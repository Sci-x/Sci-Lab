package com.pri.scilab.client.ui.module.activator;

import java.util.List;

import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
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
 static final String CHILDREN_PROP="children";
 static final String COMPONENT_PROP="component";
 
 private final Tree treeModel = new Tree();
 private final TreeGrid tree = new TreeGrid();
 
 private Component activeComponent = null;
 
 public ActivatorTree( final ComponentViewPort editorPane, Component[] editors )
 {
  treeModel.setModelType(TreeModelType.CHILDREN);  
  treeModel.setNameProperty(NAME_PROP);
  treeModel.setChildrenProperty(CHILDREN_PROP);

  TreeNode[] nodes = new TreeNode[editors.length];
  
  
  for( int i=0; i < editors.length; i++ )
   nodes[i] = createComponentNode(editors[i]);
  
  TreeNode rootNode = new TreeNode();
  
  rootNode.setAttribute(CHILDREN_PROP, nodes);
  
  treeModel.setRoot( rootNode );
       
  tree.setShowHeader(false);
  tree.setData(treeModel);
  tree.setShowConnectors(true);
  tree.setShowRoot(false);

  treeModel.openAll();
  
  final ToolStrip ts = new ToolStrip();
  ts.setAlign(VerticalAlignment.CENTER);
  ts.setMembersMargin(3);
//  ts.setPadding(2);

  tree.addNodeClickHandler( new NodeClickHandler()
  {
   @Override
   public void onNodeClick(NodeClickEvent event)
   {

    final Component ed = (Component)event.getNode().getAttributeAsObject(COMPONENT_PROP);

    if( ed == activeComponent )
     return;
    
    
    Canvas[] sbCanvas = ts.getChildren();
    
    if( sbCanvas != null )
    {
     for( Canvas cn : sbCanvas )
     {
      ts.removeChild(cn);
     }
    }
 

    
    ActionStrip.putActions(ts, ed.getAction(), ed, new ActionAdapter<Component>()
      {
       @Override
       public void actionPerformed(String action, Component object)
       {
        ed.actionPerformed(action, ed);        
       }
      });

    if( activeComponent != null )
     activeComponent.deactivate();
    
    activeComponent=ed;
    
    ed.activate( editorPane );
   }
  });

  
  tree.addNodeContextClickHandler( new NodeContextClickHandler()
  {
   @Override
   public void onNodeContextClick(final NodeContextClickEvent event)
   {
    event.cancel();
    
    final Component ed = (Component)event.getNode().getAttributeAsObject(COMPONENT_PROP);
    
    Menu nodeMenu = new ActionMenu<Void>( ed.getAction(), null, new ActionAdapter<Void>()
      {
       @Override
       public void actionPerformed(String action, Void object)
       {
        ed.actionPerformed(action, ed);        
       }
      });
      
      nodeMenu.setTop(event.getY());
      nodeMenu.setLeft(event.getX());
      nodeMenu.show();

   }
  }
  );
  
  
//  ts.setHeight(20);
  
  addMember(ts);
  
  tree.setWidth100();
  tree.setHeight100();
  
  addMember(tree);
 }
 
 private TreeNode createComponentNode( Component ed )
 {
  TreeNode[] subedNodes= null;
  
  List<Component> subed = ed.getSubComponents();
  
  if( subed != null )
  {
   subedNodes = new TreeNode[subed.size()];
   
   int i=0;
   for( Component ied : subed )
    subedNodes[i++] = createComponentNode(ied);
  }
  
  TreeNode myNode = new TreeNode();
     
  myNode.setTitle(ed.getName());
  myNode.setAttribute(NAME_PROP, ed.getName());
  myNode.setAttribute(CHILDREN_PROP, subedNodes);
  myNode.setAttribute(COMPONENT_PROP, ed);
  
  ed.addHierarchyListener(new ComponentListener(myNode));
  
  return myNode;
 }
 
 
 class ComponentListener implements HierarchyListener<Component>
 {
  private TreeNode treeNode;
  
  public ComponentListener(TreeNode nd)
  {
   treeNode=nd;
  }
  
  
  @Override
  public void childInserted(int idx, Component chld)
  {
   TreeNode nNd = createComponentNode(chld);
   
   treeModel.add(nNd, treeNode, idx);
   
   for( TreeNode n : treeModel.getChildren(treeNode) )
     System.out.println( n.getClass().getName());
//   chld.addHierarchyListener( new EditorListener(nNd) );
  }

  @Override
  public void childRemoved(int idx, Component chld)
  {
   TreeNode[] cNodes = treeModel.getChildren(treeNode);
   
   for( int i=0; i < cNodes.length; i++ )
   {
    Component cmp = (Component)cNodes[i].getAttributeAsObject(COMPONENT_PROP);
    
    if( cmp == chld )
    {
     treeModel.remove(cNodes[i]);
     return;
    }
   }
   
   chld.removeHierarchyListener( this );
  }


  @Override
  public void nodeChanged()
  {
   Component comp = (Component) treeNode.getAttributeAsObject(COMPONENT_PROP);
   
   treeNode.setTitle(comp.getName());
   treeNode.setIcon(comp.getIcon());
   
   tree.redraw();
  }

 }
}
