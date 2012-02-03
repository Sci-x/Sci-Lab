package com.pri.scilab.page.client.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pri.scilab.shared.dto.Dock;
import com.pri.scilab.shared.dto.Docklet;
import com.pri.scilab.shared.dto.HSplit;
import com.pri.scilab.shared.dto.LayoutComponent;
import com.pri.scilab.shared.dto.Page;
import com.pri.scilab.shared.dto.PageLayout;
import com.pri.scilab.shared.dto.Split;
import com.pri.scilab.shared.dto.VSplit;
import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.LayoutPolicy;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.EdgedCanvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;

public class Renderer extends VLayout
{
 private Map<String, Layout> dockMap = new HashMap<String, Layout>();
 private List<Docklet> orphans = new ArrayList<Docklet>();

 public Renderer(Page pg)
 {
  setWidth100();
  setHeight100();
  
  buildLayout(pg.getLayout());
  
  for( Docklet dkl : pg.getDocklets() )
  {
   Layout lay = dockMap.get( dkl.getTarget() );
   
   if( lay == null )
    orphans.add(dkl);
   else
    lay.addMember( createDockletVisual(dkl) );
  }
 }

 private Canvas createDockletVisual(Docklet dkl)
 {
  if( dkl.isHasHeader() )
  {
   HTMLFlow c = new HTMLFlow();
   
   c.setContents(dkl.getContents());
   
   if( dkl.isHasFrame() )
   {
    EdgedCanvas ec = new EdgedCanvas();
    
    ec.addChild(c);
    
    return ec;
   }
   
   return c;
  }
  
  Window dk = new Window();

  dk.setShowShadow(false);  
  
  // enable predefined component animation  
  dk.setAnimateMinimize(true);  

  // Window is draggable with "outline" appearance by default.  
  // "target" is the solid appearance.  
  dk.setDragAppearance(DragAppearance.OUTLINE);  
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
  
  dk.addItem( new Label( dkl.getContents() ) );
  
  return dk;
 }

 private void buildLayout(PageLayout pgl )
 {
  processContainer( pgl.getRootComponent(), this);
 }
 
 private void processContainer(LayoutComponent layCont, Layout htmlCont )
 {
  boolean hspl = layCont instanceof HSplit;
  
  if( hspl || ( layCont instanceof VSplit ) )
  {
   Layout hl = hspl? new HLayout():new VLayout();
   htmlCont.addMember(hl);
   
   if( ((Split) layCont).getComponents() != null )
    for( LayoutComponent lc : ((Split) layCont).getComponents() )
     processContainer(lc, hl);
  }
  else if( layCont instanceof Dock )
  {
   Layout hl = new VStack();
   htmlCont.addMember(hl);
 
   dockMap.put(layCont.getName(), hl);
  }

 }

}
