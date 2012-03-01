package com.pri.scilab.admin.client.ui.module.layouted;

import java.util.ArrayList;
import java.util.Collection;

import com.pri.scilab.admin.client.DataManager;
import com.pri.scilab.admin.client.ui.module.activator.AbstractComponent;
import com.pri.scilab.admin.client.ui.module.activator.Component;
import com.pri.scilab.admin.client.ui.module.activator.ComponentViewPort;
import com.pri.scilab.shared.action.Action;
import com.pri.scilab.shared.dto.PageLayout;

public class LayoutsEditor extends AbstractComponent implements Component
{
 private enum Actions
 {
  NEW
 }
 
// private List<Component> layouts = new ArrayList<Component>();
 
 public LayoutsEditor()
 {
  super();
  
  Collection<PageLayout> lts = DataManager.getInstance().getLayouts();
  
  if( lts != null )
  {
   for( PageLayout lt : lts )
   {
    LayoutEditor lay = new LayoutEditor(lt);

    addChild(lay);
   }
  }
 }

 @Override
 public Action getAction()
 {
  Action res = new Action();

  res.setSubActions(

   new ArrayList<Action>() 
  {{
    add(new Action("New Layout", Actions.NEW.name(), "/images/silk/add.png", null));
    add(new Action("New Layout", Actions.NEW.name(), "/images/silk/anchor.png", null));
    add(new Action(null, null));
    add(new Action("New Layout", Actions.NEW.name(), "/images/silk/application.png", null));
    add(new Action("Sub", Actions.NEW.name(), "/images/silk/add.png", new ArrayList<Action>()
    {{
      add(new Action("New Layout", Actions.NEW.name(), "/images/silk/add.png", null));
      add(new Action("New Layout", Actions.NEW.name(), "/images/silk/anchor.png", null));
      add(new Action(null, null));
      add(new Action("New Layout", Actions.NEW.name(), "/images/silk/application.png", null));
      add(new Action("Sub", Actions.NEW.name(), "/images/silk/add.png", null));
    }}
     ));
   }}

  );

  return res;
 }

 @Override
 public String getIcon()
 {
  return "/images/silk/accept.png";
 }

 @Override
 public String getName()
 {
  return "Layouts";
 }

// @Override
// public List<Component> getSubComponents()
// {
//  return layouts;
// }

 @Override
 public void actionPerformed(String action, Component object)
 {
  System.out.println("Layouts action: "+action);
  
  if( Actions.NEW.name().equals(action) )
  {
   
   int max = 0;
   String newNamePrefix="New Layout ";
   if( getSubComponents() != null )
   {
    for( Component e : getSubComponents() )
    {
     String eName = e.getName();
     
     if( eName.startsWith(newNamePrefix) )
     {
      try
      {
       int nmnum = Integer.parseInt(eName.substring(newNamePrefix.length()));
       
       if( nmnum > max )
        max=nmnum;
      }
      catch(Exception e2)
      {
      }
     }
    }
   }
   
   max++;
   LayoutEditor lay = new LayoutEditor(newNamePrefix+max);
//   lay.setName(newNamePrefix+max);

   addChild(lay);
   
//   layouts.add(lay);
//   
//   fireChildInserted(layouts.size()-1, lay);
  }
 }

 @Override
 public void actionPerformed(String action, Collection<Component> objects)
 {
 }

 @Override
 public void activate( ComponentViewPort pane )
 {
  pane.clean();
  pane.setOwner(this);
 }

 @Override
 public void deactivate()
 {
  // TODO Auto-generated method stub
  
 }

 @Override
 public String getId()
 {
  return "layouts";
 }

 

}
