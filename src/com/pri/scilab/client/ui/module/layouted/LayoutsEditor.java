package com.pri.scilab.client.ui.module.layouted;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.pri.scilab.client.DataManager;
import com.pri.scilab.client.ui.module.activator.AbstractComponent;
import com.pri.scilab.client.ui.module.activator.Action;
import com.pri.scilab.client.ui.module.activator.Component;
import com.pri.scilab.client.ui.module.activator.ComponentViewPort;

public class LayoutsEditor extends AbstractComponent implements Component
{
 private enum Actions
 {
  NEW
 }
 
 private List<Component> layouts = new ArrayList<Component>();
 
 public LayoutsEditor()
 {
  super();
  
  Collection<Layout> lts = DataManager.getInstance().getLayouts();
  
  if( lts != null )
  {
   for( Layout lt : lts )
   {
    LayoutEditor lay = new LayoutEditor(lt);

    layouts.add(lay);
    
    fireChildInserted(layouts.size()-1, lay);
   }
  }
 }

 @Override
 public Action getAction()
 {
  Action res = new Action();

  res.setSubActions(

  new Action[] 
   { 
    new Action("New Layout", Actions.NEW.name(), "/images/silk/add.png", null),
    new Action("New Layout", Actions.NEW.name(), "/images/silk/anchor.png", null),
    new Action(null, null),
    new Action("New Layout", Actions.NEW.name(), "/images/silk/application.png", null),
    new Action("Sub", Actions.NEW.name(), "/images/silk/add.png",   new Action[] 
                                                                               { 
     new Action("New Layout", Actions.NEW.name(), "/images/silk/add.png", null),
     new Action("New Layout", Actions.NEW.name(), "/images/silk/anchor.png", null),
     new Action(null, null),
     new Action("New Layout", Actions.NEW.name(), "/images/silk/application.png", null),
     new Action("Sub", Actions.NEW.name(), "/images/silk/add.png", null ),
    }
 ),
   }

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

 @Override
 public List<Component> getSubComponents()
 {
  return layouts;
 }

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
   
   layouts.add(lay);
   
   fireChildInserted(layouts.size()-1, lay);
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