package com.pri.labs.client.ui.module;

import java.util.List;

import com.pri.labs.client.structure.ActionAdapter;
import com.pri.labs.client.structure.CommonTreeNode;
import com.pri.labs.client.structure.HierarchyListener;
import com.pri.labs.client.ui.control.Editor;
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

public class EditorTree extends VLayout
{
 private final Tree treeModel = new Tree();
 private final TreeGrid tree = new TreeGrid();
 
 private Editor activeEditor = null;
 
 public EditorTree( final EditorViewPort editorPane, Editor[] editors )
 {
  treeModel.setModelType(TreeModelType.CHILDREN);  
  treeModel.setNameProperty("Name");  

  EditorNode[] nodes = new EditorNode[editors.length];
  
//  nodes[0] = new EditorNode("Layouts", "accept.png", null, null);
  
  for( int i=0; i < editors.length; i++ )
   nodes[i] = createEditorNode(editors[i]);
  
  treeModel.setRoot( new EditorNode("Root", "", null, nodes ) );
       
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
//    System.out.println("Node clocked: "+((EditorNode)event.getNode()).getEditor().getName());
//    ts.clear();
    
    System.out.println(event.getNode().getAttributeAsObject("editor").getClass().getName());

    final Editor ed = (Editor)event.getNode().getAttributeAsObject("editor");

    if( ed == activeEditor )
     return;
    
    
    Canvas[] sbCanvas = ts.getChildren();
    
    if( sbCanvas != null )
    {
     for( Canvas cn : sbCanvas )
     {
      ts.removeChild(cn);
     }
    }
 

    
    ActionStrip.putActions(ts, ed.getAction(), ed, new ActionAdapter<Editor>()
      {
       @Override
       public void actionPerformed(String action, Editor object)
       {
        ed.actionPerformed(action, ed);        
       }
      });

    if( activeEditor != null )
     activeEditor.deactivate();
    
    activeEditor=ed;
    
    ed.activate( editorPane );
   }
  });

  
  tree.addNodeContextClickHandler( new NodeContextClickHandler()
  {
   @Override
   public void onNodeContextClick(final NodeContextClickEvent event)
   {
    event.cancel();
    
    final Editor ed = ((EditorNode)event.getNode()).getEditor();
    
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
 
 private EditorNode createEditorNode( Editor ed )
 {
  EditorNode[] subedNodes= null;
  
  List<Editor> subed = ed.getSubEditors();
  
  if( subed != null )
  {
   subedNodes = new EditorNode[subed.size()];
   
   int i=0;
   for( Editor ied : subed )
    subedNodes[i++] = createEditorNode(ied);
  }
  
  EditorNode en = new EditorNode(ed.getName(),ed.getIcon(), ed, subedNodes);
  
  ed.addHierarchyListener(new EditorListener(en));
  
  return en;
 }
 
 private static class EditorNode extends CommonTreeNode
 {

  public EditorNode(String name, String icon, Editor obj, CommonTreeNode... children)
  {
   super(name, icon, obj, children);
   
   setTitle(name);
   
   setAttribute("editor", obj);
  }

  public Editor getEditor()
  {
   return (Editor)getUserObject();
  }
 
 }
 
 class EditorListener implements HierarchyListener<Editor>
 {
  private CommonTreeNode treeNode;
  
  public EditorListener(CommonTreeNode nd)
  {
   treeNode=nd;
  }
  
  
  @Override
  public void childInserted(int idx, Editor chld)
  {
   CommonTreeNode nNd = createEditorNode(chld);
   
   treeModel.add(nNd, treeNode, idx); //.setAttribute("editor", chld);
   
   for( TreeNode n : treeModel.getChildren(treeNode) )
     System.out.println( n.getClass().getName());
//   chld.addHierarchyListener( new EditorListener(nNd) );
  }

  @Override
  public void childRemoved(int idx, Editor chld)
  {
   TreeNode[] cNodes = treeModel.getChildren(treeNode);
   
   for( int i=0; i < cNodes.length; i++ )
   {
    if( ((CommonTreeNode)cNodes[i]).getUserObject() == chld )
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
   treeNode.setTitle(((EditorNode)treeNode).getEditor().getName());
   treeNode.setIcon(((EditorNode)treeNode).getEditor().getIcon());
   tree.redraw();
  }

 }
}
