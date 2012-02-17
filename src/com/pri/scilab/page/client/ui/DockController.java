package com.pri.scilab.page.client.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.pri.scilab.shared.dto.Dock;
import com.pri.scilab.shared.dto.Docklet;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemSeparator;
import com.smartgwt.client.widgets.menu.events.ItemClickEvent;
import com.smartgwt.client.widgets.menu.events.ItemClickHandler;

public class DockController
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
 private Layout container;
 private Collection<DockletFilter> filters;
 private Set<DockletFilter> activeFilters;
 
 private MenuItem filtersMenuItem;
 
 
 public DockController( Layout ly )
 {
  container = ly;
  
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
  DockDecorationDialog.edit( new Dock() );
 }

 private void addDocklet()
 {
  DockletEditorDialog.edit( new Docklet() );
 }
}
