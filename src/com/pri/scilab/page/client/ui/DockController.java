package com.pri.scilab.page.client.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.pri.scilab.page.client.ui.DockDecorationDialog.DialogCallback;
import com.pri.scilab.shared.dto.DockVisualCfg;
import com.pri.scilab.shared.dto.DockVisualCfg.Background;
import com.pri.scilab.shared.dto.DockVisualCfg.Frame;
import com.pri.scilab.shared.dto.Docklet;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.DropEvent;
import com.smartgwt.client.widgets.events.DropHandler;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemSeparator;
import com.smartgwt.client.widgets.menu.events.ItemClickEvent;
import com.smartgwt.client.widgets.menu.events.ItemClickHandler;

public class DockController implements DialogCallback
{
 private enum Actions
 {
  MNGFILTERS,
  FILTER,
  ADDDOCKLET,
  DECOR
 }
 
 private static String objectAttribute = "_object";
 private static String actionAttribute = "_action";
 
 private List<Docklet> docklets = new ArrayList<Docklet>();
 private Layout parent;
 private Layout container;
 private Collection<DockletFilter> filters;
 private Set<DockletFilter> activeFilters;
 
 private MenuItem filtersMenuItem;
 
 private DockVisualCfg config;
 
 public DockController( DockVisualCfg cfg, Layout prnt )
 {
  config = cfg;
  parent = prnt;
  
  
  createContainer();
  
  
  Menu contextMenu = new Menu();
  
  MenuItem newMI = new MenuItem("New Fact","images/icons/page/newfact.png");
  newMI.setAttribute(actionAttribute, Actions.ADDDOCKLET);

  MenuItem decorationMI = new MenuItem("Decoration","images/icons/page/decoration.png");
  decorationMI.setAttribute(actionAttribute, Actions.DECOR);

  filtersMenuItem = new MenuItem("Filters","images/icons/page/filter.png");
  
  filtersMenuItem.setSubmenu(createFiltersSubmenu());
  
  contextMenu.setItems( newMI, decorationMI, filtersMenuItem );
  
  container.setContextMenu(contextMenu);
  
  contextMenu.addItemClickHandler( new ItemClickHandler()
  {
   
   @Override
   public void onItemClick(ItemClickEvent event)
   {
    MenuItem mi = event.getItem();
    
    if( mi.getAttributeAsObject(actionAttribute) ==  Actions.ADDDOCKLET )
     addDocklet();
    else if( mi.getAttributeAsObject(actionAttribute) ==  Actions.DECOR )
     editDecoration();
   }

  });
  
 }

 private void createContainer()
 {
  container = new VStack();
  
  container.setMembersMargin(2);
//  hl.setMargin(2);
  
//  hl.setHPolicy(LayoutPolicy.FILL);
  
  container.setBorder("1px solid black");

  container.setWidth(dim2String(layCont.getWidth()));
  container.setHeight(dim2String(layCont.getHeight()));
  
  container.setAnimateMembers(true);  
  container.setAnimateMemberTime(300);  

  container.setCanAcceptDrop(true);  

  container.setDropLineThickness(4);  

  Canvas dropLineProperties = new Canvas();  
  dropLineProperties.setBackgroundColor("aqua");  
  container.setDropLineProperties(dropLineProperties);  

  container.setShowDragPlaceHolder(true);  

  Canvas placeHolderProperties = new Canvas();  
  placeHolderProperties.setBorder("2px dashed #8289A6");  
  container.setPlaceHolderProperties(placeHolderProperties);  
  
  
  container.addDropHandler( new DropHandler()
  {
   
   @Override
   public void onDrop(DropEvent event)
   {
    System.out.println( container.getDropPosition()+" Source: "+event.getSource().getClass()+" Drop: "+EventHandler.getDragTarget().getClass() );
   
    Layout cont = (Layout)event.getSource(); 
    
    Window dropWin = (Window)EventHandler.getDragTarget();
    
    int padding = cont.getPadding()!=null?cont.getPadding():0;
    
    
    dropWin.setAutoSize( false );
    dropWin.setWidth(((Layout)event.getSource()).getViewportWidth()-padding*2);
   }
  });


 }

 private Menu createFiltersSubmenu()
 {
  final Menu filtersMenu = new Menu();

  MenuItem mngMI = new MenuItem("Manage filters","images/icons/page/editfilters.png");
  
  mngMI.setAttribute(actionAttribute, Actions.MNGFILTERS);

  MenuItem items[];
  
  if( filters != null && filters.size() > 0 )
  {
   items = new MenuItem[ filters.size()+2];
   
   items[0] = mngMI;
   items[1] = new MenuItemSeparator();
   
   int i=1;
   
   for( DockletFilter df : filters )
   {
    i++;
    
    items[i] = new MenuItem( df.getName() );
    
    if( activeFilters != null && activeFilters.contains(df) )
     items[i].setChecked(true);
    
    items[i].setAttribute(objectAttribute, df);
    items[i].setAttribute(actionAttribute, Actions.FILTER);
   }
   
  }
  else
   items = new MenuItem[] { mngMI };
  
  filtersMenu.setItems(items);
  
  filtersMenu.addItemClickHandler( new ItemClickHandler( )
  {
   
   @Override
   public void onItemClick(ItemClickEvent event)
   {
    MenuItem mi = event.getItem();
    
    if( mi.getAttributeAsObject(actionAttribute) ==  Actions.FILTER )
    {
     boolean checked = ! mi.getChecked();
     
     mi.setChecked( checked );
     
     if( checked )
      activeFilters.add((DockletFilter)mi.getAttributeAsObject(objectAttribute));
     else
      activeFilters.remove((DockletFilter)mi.getAttributeAsObject(objectAttribute));
     
//     ArrayList<DockletFilter> flt = new ArrayList<DockletFilter>();
//     
//     for( MenuItem fmi : filtersMenu.getItems() )
//     {
//      if( fmi.getAttributeAsObject(actionAttribute) ==  Actions.FILTER && fmi.getChecked() )
//       flt.add((DockletFilter)mi.getAttributeAsObject(objectAttribute));
//     }
     
     applyFilter();
    }
    else if( mi.getAttributeAsObject(actionAttribute) ==  Actions.MNGFILTERS )
    {
     manageFilters();
    }
    
    
   }



  });
  
  return filtersMenu;
 }
 
 private void manageFilters()
 {
 }
 
 private void applyFilter()
 {
  //
 }
 

 private void editDecoration()
 {
  DockDecorationDialog.edit( new DockVisualCfg(), this );
 }

 private void addDocklet()
 {
  DockletEditorDialog.edit( new Docklet() );
 }


 @Override
 public void dialogClosed(DockVisualCfg d)
 {
  if( d == null )
   return;
  
  VStack nStack = new VStack();
  
  
  if( d.getBackgroundStyle() == Background.COLOR || d.getBackgroundStyle() == Background.IMGNFILL )
   nStack.setBackgroundColor(d.getBackgroundColor());
  
  if( d.getFrameStyle() == Frame.NONE )
   nStack.setBorder("none");
  if( d.getFrameStyle() == Frame.FRAME )
   nStack.setShowEdges(true);
  else if( d.getFrameStyle() == Frame.BORDER )
   nStack.setBorder(d.getBorderThicknes()+"px "+d.getBorderStyle()+" "+d.getBorderColor());
  

  Canvas[] chld = container.getMembers();
 
  for( Canvas c : chld )
  {
   c.removeFromParent();
   
   if( c instanceof Window )
   {
    ((Window)c).setAutoSize(false);
   }
  }
  
  container.removeMembers(chld);
  
  nStack.setMargin( d.getMargin() );
  nStack.setPadding( d.getPadding() );
  
  
  for( Canvas c : chld )
  {
   nStack.addMember(c);
  }


  int n = parent.getMemberNumber(container);
  
  parent.removeMember(container);
  
  parent.addMember(nStack, n);
  
  container = nStack;
  
  for( Canvas c : chld )
  {
   if( c instanceof Window )
   {
    ((Window)c).setAutoSize(true);
   }
   else
    c.setWidth100();
  }
  
//  parent.reflow();
//  parent.redraw();
//  container.reflow();
//  container.redraw();


//  container.setMembers(chld);
  
//  for( Canvas c : container.getMembers() )
//  {
//   if( c instanceof Window )
//   {
//    ((Window)c).setAutoSize(true);
//   }
//  }
 }
}
