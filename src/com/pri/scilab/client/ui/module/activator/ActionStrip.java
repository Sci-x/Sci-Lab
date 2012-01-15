package com.pri.scilab.client.ui.module.activator;


import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.menu.MenuButton;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripSeparator;

public class ActionStrip<T> extends ToolStrip
{

 
 public static <T> void putActions( ToolStrip ts, Action act, final T obj, final ActionHandler<T> hndl )
 {
  ts.addSpacer(10);
  
  for( final Action a : act.getSubActions() )
  {
   if( a.getText() == null )
    ts.addMember( new ToolStripSeparator() );
   else if( a.getSubActions() == null )
   {
    ImgButton bt = new ImgButton();
    bt.setSrc(a.getCls());
    bt.setWidth( 16 );
    bt.setHeight( 16 );
    bt.setShowDown(false);
    bt.setShowRollOver(false);
    bt.setShowFocused(false);

    bt.setTooltip("<b>"+a.getText()+"</b>");
    
    bt.addClickHandler( new ClickHandler()
    {
     @Override
     public void onClick(ClickEvent event)
     {
      hndl.actionPerformed(a.getAction(), obj);
     }
    });
    
    ts.addMember(bt);
   }
   else
   {
    MenuButton mb = new MenuButton(a.getText(),new ActionMenu<T>(a, obj, hndl));
    mb.setHeight(18);
    mb.setAutoFit( true );
    ts.addMember(mb);
   }
  }
 }
}
