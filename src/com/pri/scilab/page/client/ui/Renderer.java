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
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.EdgedCanvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.DropEvent;
import com.smartgwt.client.widgets.events.DropHandler;
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
  if( ! dkl.hasHeader() )
  {
   HTMLFlow c = new HTMLFlow();
   
   c.setContents(dkl.getContents());
   
   if( dkl.hasFrame() )
   {
    EdgedCanvas ec = new EdgedCanvas();
    
    ec.addChild(c);
    
    ec.setWidth100();
    
    return ec;
   }
   
   c.setWidth100();
   return c;
  }
  
  Window dk = new Window();

  dk.setShowShadow(false);
  dk.setAutoSize( true );
//  dk.setWidth100();
  
  
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
  
  Label lb = new Label( dkl.getContents() );
  lb.setHeight(10);
  lb.setMargin(2);
  
  dk.addItem( lb );


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
   
   hl.setWidth(dim2String(layCont.getWidth()));
   hl.setHeight(dim2String(layCont.getHeight()));
   
   
   if( ((Split) layCont).getComponents() != null )
    for( LayoutComponent lc : ((Split) layCont).getComponents() )
     processContainer(lc, hl);
  }
  else if( layCont instanceof Dock )
  {
   final Layout hl = new VStack();
  
   hl.setMembersMargin(2);
   hl.setMargin(2);
   
   hl.setBorder("1px solid black");

   hl.setWidth(dim2String(layCont.getWidth()));
   hl.setHeight(dim2String(layCont.getHeight()));
   
   hl.setAnimateMembers(true);  
   hl.setAnimateMemberTime(300);  

   hl.setCanAcceptDrop(true);  

   hl.setDropLineThickness(4);  

   Canvas dropLineProperties = new Canvas();  
   dropLineProperties.setBackgroundColor("aqua");  
   hl.setDropLineProperties(dropLineProperties);  

   hl.setShowDragPlaceHolder(true);  

   Canvas placeHolderProperties = new Canvas();  
   placeHolderProperties.setBorder("2px dashed #8289A6");  
   hl.setPlaceHolderProperties(placeHolderProperties);  
   
   htmlCont.addMember(hl);

   dockMap.put(layCont.getName(), hl);
   
   hl.addDropHandler( new DropHandler()
   {
    
    @Override
    public void onDrop(DropEvent event)
    {
     System.out.println( "Source: "+event.getSource().getClass()+" Drop: "+EventHandler.getDragTarget().getClass() );
    
     ((Window)EventHandler.getDragTarget()).setAutoSize( true );
    }
   });
  }

 }

 public static String dim2String( int dim )
 {
  if( dim == 0 )
   return "*";
  
  if( dim < 0 )
   return String.valueOf(-dim)+"%";
  
  return String.valueOf(dim);
 }

}
