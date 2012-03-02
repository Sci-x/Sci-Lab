package com.pri.scilab.page.client.ui;

import com.pri.scilab.shared.dto.Docklet;
import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.LayoutPolicy;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;

public class DockletView
{
 private Canvas docklet;

 public DockletView(Docklet dkl, DockView lay)
 {
  docklet = createDockletVisual(dkl);
 }

 
 private Canvas createDockletVisual(Docklet dkl)
 {
  if( ! dkl.hasHeader() )
  {
   Label c = new Label();
   
   c.setContents(dkl.getContents());
//   c.setOverflow(Overflow.VISIBLE);
  
   if( dkl.hasFrame() )
    c.setShowEdges(true);
   
   c.setWidth100();
   return c;
  }
  
  final Window dk = new Window() {
   
   public void onDraw()
   {
    setAutoSize( true );
   }
   
  };

  dk.setShowShadow(false);
  dk.setHPolicy(LayoutPolicy.FILL);
//  dk.setWidth100();
  
  
  // enable predefined component animation  
  dk.setAnimateMinimize(true);  

  // Window is draggable with "outline" appearance by default.  
  // "target" is the solid appearance.  
  dk.setDragAppearance(DragAppearance.TARGET);  
  dk.setCanDrop(true);  

  // customize the appearance and order of the controls in the window header  
  dk.setHeaderControls(HeaderControls.MINIMIZE_BUTTON, HeaderControls.HEADER_LABEL, HeaderControls.CLOSE_BUTTON);  

  // show either a shadow, or translucency, when dragging a portlet  
  // (could do both at the same time, but these are not visually compatible effects)  
  // setShowDragShadow(true);  
  dk.setDragOpacity(30);  

  // these settings enable the portlet to autosize its height only to fit its contents  
  // (since width is determined from the containing layout, not the portlet contents)  

  
  dk.setVPolicy(LayoutPolicy.NONE);  
  dk.setOverflow(Overflow.VISIBLE);
  
  dk.setTitle( dkl.getTitle() );
  
  Label lb = new Label( dkl.getContents() );
  lb.setHeight(10);
  lb.setWidth100();
  lb.setMargin(2);
  lb.setCanSelectText(true);
  lb.setOverflow(Overflow.VISIBLE);
 
  dk.addItem( lb );
  
  
  return dk;
 }


 public Canvas getCanvas()
 {
  return docklet;
 }
}
