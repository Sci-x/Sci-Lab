package com.pri.labs.client.ui.control;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.pri.labs.client.data.DataManager;
import com.pri.labs.client.structure.Action;
import com.pri.labs.client.structure.Layout;
import com.pri.labs.client.ui.module.EditorViewPort;

public class LayoutsEditor extends AbstractEditor implements Editor
{
 private enum Actions
 {
  NEW
 }
 
 private List<Editor> layouts = new ArrayList<Editor>();
 
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
 public List<Editor> getSubEditors()
 {
  return layouts;
 }

 @Override
 public void actionPerformed(String action, Editor object)
 {
  System.out.println("Layouts action: "+action);
  
  if( Actions.NEW.name().equals(action) )
  {
   
   int max = 0;
   String newNamePrefix="New Layout ";
   if( getSubEditors() != null )
   {
    for( Editor e : getSubEditors() )
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
 public void actionPerformed(String action, Collection<Editor> objects)
 {
 }

 @Override
 public void activate( EditorViewPort pane )
 {
  pane.clean();
  pane.setOwner(this);
 }

 @Override
 public void deactivate()
 {
  // TODO Auto-generated method stub
  
 }

 

}
