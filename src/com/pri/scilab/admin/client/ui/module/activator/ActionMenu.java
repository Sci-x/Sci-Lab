package com.pri.scilab.admin.client.ui.module.activator;


import java.util.List;

import com.pri.scilab.shared.action.Action;
import com.pri.scilab.shared.action.ActionHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemSeparator;
import com.smartgwt.client.widgets.menu.events.ItemClickEvent;
import com.smartgwt.client.widgets.menu.events.ItemClickHandler;

public class ActionMenu<T> extends Menu implements ItemClickHandler
{
 private MenuItemSeparator separator;
 private T object;
 private ActionHandler<T> handler;
 
 public ActionMenu( Action act, T obj, ActionHandler<T> hnd )
 {
  super();
  
  object=obj;
  handler=hnd;
  
  addItemClickHandler(this);

  createMenu(this, act.getSubActions() );
 }
 
 private void createMenu( Menu mnu, List<Action> acts )
 {
  MenuItem[] items = new MenuItem[ acts.size() ];
  
  int i=0;
  
  for(Action act : acts)
  {
   if(act.getText() == null)
   {
    if(separator == null)
     separator = new MenuItemSeparator();

    items[i++] = separator;
//    mnu.addItem(separator);
   }
   else
   {
    MenuItem item = new ActionMenuItem(act.getText(), act.getCls(), act.getAction() );

    if(act.getSubActions() != null)
    {
     Menu smnu = new Menu();

     createMenu(smnu, act.getSubActions());

     item.setSubmenu(smnu);

    }
    
    items[i++] = item;

//    mnu.addItem(item);
   }
  }
  
  mnu.setData(items);
 }
 
 private class ActionMenuItem extends MenuItem
 {
  public ActionMenuItem( String title, String icon, String action )
  {
   super(title,icon);
   
   setAttribute("myaction", action);
  }
 }


 @Override
 public void onItemClick(ItemClickEvent event)
 {
  handler.
  actionPerformed(
    event.getItem()
    .getAttribute("myaction"), object);
 }
}
