package com.pri.scilab.client.ui.module.layouted;


import com.pri.scilab.client.ui.module.activator.ActionHandler;
import com.pri.scilab.client.ui.module.activator.ActionMenu;
import com.pri.scilab.client.ui.module.activator.Component;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.ShowContextMenuEvent;
import com.smartgwt.client.widgets.events.ShowContextMenuHandler;
import com.smartgwt.client.widgets.events.VisibilityChangedEvent;
import com.smartgwt.client.widgets.events.VisibilityChangedHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;

public class DockPanel extends Label //implements ActionHandler<Void>
{
 private String widthStr, heightStr;
 
// private Canvas container;
// private ActionHandler<Void> actListener;
 private DockComponent dockComponent;
 
 public DockPanel(String wd, String ht, DockComponent dockComp, final ActionHandler<Component> lsnr)
 {
  dockComponent = dockComp;
//  container=cont;
  
  setAlign(Alignment.CENTER);
  setLayoutAlign(VerticalAlignment.CENTER);

  
//  widthStr=Dock.dim2String(dock.getWidth());
//  heightStr=Dock.dim2String(dock.getHeight());
  
  setWidth(wd);
  setHeight(ht);
  
//  setContextMenu(new ActionMenu<Component>( dockComp.getAction(), null, lsnr ) );
  
  addClickHandler( new ClickHandler()
  {
   @Override
   public void onClick(ClickEvent event)
   {
    dockComponent.requestFocus();
   }
  });
  
  addShowContextMenuHandler( new ShowContextMenuHandler()
  {

   @Override
   public void onShowContextMenu(ShowContextMenuEvent event)
   {

    Menu nodeMenu = new ActionMenu<Component>(dockComponent.getAction(), null, lsnr);

    nodeMenu.addVisibilityChangedHandler(new VisibilityChangedHandler()
    {

     @Override
     public void onVisibilityChanged(VisibilityChangedEvent event)
     {
//      ((Menu) event.getSource()).destroy();
     }
    });

//    setContextMenu(nodeMenu);
    
    nodeMenu.showContextMenu();

    // nodeMenu.setTop(event.getY());
    // nodeMenu.setLeft(event.getX());
    // nodeMenu.show();

    dockComponent.requestFocus();

    event.cancel();

   }
  });
  
//  addRightMouseDownHandler(new RightMouseDownHandler()
//  {
//   
//   @Override
//   public void onRightMouseDown(RightMouseDownEvent event)
//   {
////    setContextMenu(new ActionMenu<Component>( dockComponent.getAction(), null, lsnr ));
//    
//    event.cancel();
//    
//    
//    Menu nodeMenu = new ActionMenu<Component>( dockComponent.getAction(), null, lsnr);
//    
//    nodeMenu.addVisibilityChangedHandler(new VisibilityChangedHandler()
//    {
//     
//     @Override
//     public void onVisibilityChanged(VisibilityChangedEvent event)
//     {
//      ((Menu)event.getSource()).destroy();
//     }
//    });
//    
//    setContextMenu( nodeMenu );
//    
////      nodeMenu.setTop(event.getY());
////      nodeMenu.setLeft(event.getX());
////      nodeMenu.show();
//      
//      dockComponent.requestFocus();
//
//   }
//  });
//  widthStr = wd;
//  heightStr = ht;
 }

// private LayoutPanel()
// {
//  setContents("Width: default<br>Width: default");
//  setBorder("1px dashed green");
//  setWidth("*");
//  setHeight("*");
//
////  setContents("Width: "+widthStr+"<br>Height: "+heightStr);
//
//  setContextMenu(new LayoutPanelMenu( this ) );
//
//
// }
 
 public boolean isVertical()
 {
  return getParentElement() instanceof VLayout ;
 }
 
 public void setWidth( String w )
 {
  super.setWidth(w);
 
  widthStr=LayoutNodeComponent.dim2String(dockComponent.getWidth());
 
  updateContent();
 }
 
 public void setHeight( String h )
 {
  super.setHeight(h);

  heightStr=LayoutNodeComponent.dim2String(dockComponent.getHeight());
  
  updateContent();
 }
 
 public void updateContent()
 {
  setContents("Dock: "+dockComponent.getName()+"<br>Width: "+widthStr+"<br>Height: "+heightStr);
 }
 
// private int convertSizeToNumber( String szStr ) throws Exception
// {
//  szStr = szStr.trim();
//  
//  if( szStr.matches("\\d+") )
//  {
//   return Integer.parseInt(szStr);
//  }
//  else if( szStr.matches("\\d+%") )
//  {
//   return - Integer.parseInt(szStr.substring(0,szStr.length()-1));
//  }
//  else if( "*".equals(szStr) )
//   return 0;
//  
//  throw new Exception("Invalid input string");
// }
// 
// private static class MessageCB implements BooleanCallback
// {
//  private static MessageCB instance = new MessageCB();
//
//  private static MessageCB getInstance()
//  {
//   return instance;
//  }
//  
//  @Override
//  public void execute(Boolean value)
//  {
//  }
//  
// }


// private static int findMaxDockNameSuffix( Dock dk )
// {
//  while( dk.getParent() != null )
//   dk=dk.getParent();
//  
//  return findMaxDockNameSuffixRec(dk);
// }
 


}
