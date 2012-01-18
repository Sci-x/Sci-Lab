package com.pri.scilab.client.ui.module.layouted;


import com.pri.scilab.client.ui.module.activator.ActionHandler;
import com.pri.scilab.client.ui.module.activator.ActionMenu;
import com.pri.scilab.client.ui.module.activator.Component;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

public class DockPanel extends Label //implements ActionHandler<Void>
{
 private String widthStr, heightStr;
 
// private Canvas container;
// private ActionHandler<Void> actListener;
 private DockComponent dockComponent;
 
 public DockPanel(String wd, String ht, DockComponent dockComp, ActionHandler<Component> lsnr)
 {
  dockComponent = dockComp;
//  container=cont;
  
  setAlign(Alignment.CENTER);
  setLayoutAlign(VerticalAlignment.CENTER);

  
//  widthStr=Dock.dim2String(dock.getWidth());
//  heightStr=Dock.dim2String(dock.getHeight());
  
  setWidth(wd);
  setHeight(ht);
  
  setContextMenu(new ActionMenu<Component>( DockComponent.getDockEditorAction(), null, lsnr ) );
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
 
  widthStr=Dock.dim2String(dockComponent.getWidth());
 
  updateContent();
 }
 
 public void setHeight( String h )
 {
  super.setHeight(h);

  heightStr=Dock.dim2String(dockComponent.getHeight());
  
  updateContent();
 }
 
 public void updateContent()
 {
  setContents("Dock: "+dockComponent.getName()+"<br>Width: "+widthStr+"<br>Height: "+heightStr);
 }
 
 private int convertSizeToNumber( String szStr ) throws Exception
 {
  szStr = szStr.trim();
  
  if( szStr.matches("\\d+") )
  {
   return Integer.parseInt(szStr);
  }
  else if( szStr.matches("\\d+%") )
  {
   return - Integer.parseInt(szStr.substring(0,szStr.length()-1));
  }
  else if( "*".equals(szStr) )
   return 0;
  
  throw new Exception("Invalid input string");
 }
 
 private static class MessageCB implements BooleanCallback
 {
  private static MessageCB instance = new MessageCB();

  private static MessageCB getInstance()
  {
   return instance;
  }
  
  @Override
  public void execute(Boolean value)
  {
  }
  
 }


// private static int findMaxDockNameSuffix( Dock dk )
// {
//  while( dk.getParent() != null )
//   dk=dk.getParent();
//  
//  return findMaxDockNameSuffixRec(dk);
// }
 


}
