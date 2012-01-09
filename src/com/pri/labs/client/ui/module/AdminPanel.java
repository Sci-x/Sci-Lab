package com.pri.labs.client.ui.module;

import com.pri.labs.client.structure.Action;
import com.pri.labs.client.structure.ActionAdapter;
import com.pri.labs.client.structure.CommonTreeNode;
import com.pri.labs.client.ui.control.Editor;
import com.pri.labs.client.ui.control.LayoutsEditor;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.events.NodeContextClickEvent;
import com.smartgwt.client.widgets.tree.events.NodeContextClickHandler;

public class AdminPanel extends HLayout
{
 public AdminPanel()
 {
  setWidth100();
  setHeight100();
  
   final Tree objTree = new Tree();  
   objTree.setModelType(TreeModelType.CHILDREN);  
   objTree.setNameProperty("Name");  
   objTree.setRoot(new CommonTreeNode("Root", "accept.png", null,
     new CommonTreeNode[] {
     new CommonTreeNode("Layouts", "accept.png", new Float(333)),
     new CommonTreeNode("Pages", "accept.png")
   }));
        
   final TreeGrid tree = new TreeGrid();
   tree.setWidth(300);
   
   tree.setData(objTree);
   tree.setAppImgDir("/images/silk/");
   tree.setShowResizeBar(true);
   tree.setShowConnectors(true);
   tree.setShowRoot(false);
   
   tree.addNodeContextClickHandler( new NodeContextClickHandler()
   {
    
    @Override
    public void onNodeContextClick(final NodeContextClickEvent event)
    {
     event.cancel();
     
     System.out.println("Type: "+((CommonTreeNode)event.getNode()).getUserObject().getClass());
     
     Menu layoutMenu = new ActionMenu<Void>( getLayoutAction(), null, new ActionAdapter<Void>()
     {
      @Override
      public void actionPerformed(String action, Void object)
      {
       if( "new".equals(action) )
       {
        objTree.add(new CommonTreeNode("New layout", "/images/silk/accept.png", new Integer(111)), event.getNode());
        
//        CommonTreeNode[] cldr = ((CommonTreeNode)event.getNode()).getChildren();
//        
//        if( cldr == null )
//         
//         event.getNode().setChildren( new CommonTreeNode[] { new CommonTreeNode("New layout", "/images/silk/accept.png") } );
//        else
//        {
//         CommonTreeNode[] newChld = new CommonTreeNode[cldr.length+1];
//         
//         for(int i=0; i< cldr.length; i++)
//         {
//          newChld[i]=cldr[i];
//          i++;
//         }
//         
//         int i=cldr.length;
//         newChld[i]=new CommonTreeNode("New layout "+i, "/images/silk/accept.png");
//         
//         (((CommonTreeNode)event.getNode())).setChildren(newChld);
//        }
//        
//        tree.redraw();
//        //System.out.println(event.getNode().getAttribute("children").getClass().getName());
       }
       
      }
     });
     
     layoutMenu.setTop(event.getY());
     layoutMenu.setLeft(event.getX());
     layoutMenu.show();
     
    }
   });

   Canvas editorPane =  new Canvas();
   editorPane.setWidth100();
   editorPane.setHeight100();
 
   EditorTree edt = new EditorTree( new EditorViewPort(editorPane), new Editor[]{ new LayoutsEditor() } );
   edt.setWidth(300);
   edt.setShowResizeBar(true);

   
   addMember( edt );
   addMember(editorPane);
 }
 
 private Action getLayoutAction()
 {
  Action res = new Action();
  
  res.setSubActions(

    new Action[]
               {
      new Action("New Layout","new","/images/silk/accept.png",null),
               }
  
  );
  
  return res;
 }
}
