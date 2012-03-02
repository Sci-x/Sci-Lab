package com.pri.scilab.page.client.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pri.scilab.shared.dto.Dock;
import com.pri.scilab.shared.dto.DockVisualCfg;
import com.pri.scilab.shared.dto.Docklet;
import com.pri.scilab.shared.dto.HSplit;
import com.pri.scilab.shared.dto.LayoutComponent;
import com.pri.scilab.shared.dto.Page;
import com.pri.scilab.shared.dto.PageLayout;
import com.pri.scilab.shared.dto.Split;
import com.pri.scilab.shared.dto.VSplit;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

public class Renderer extends VLayout
{
 private Map<String, DockView> dockMap = new HashMap<String, DockView>();
 private List<Docklet> orphans = new ArrayList<Docklet>();
 private Page page;

 
 
 public Renderer(Page pg)
 {
  setWidth100();
  setHeight100();
  
  page = pg;
  
  buildLayout(pg.getLayout());
  
  for( Docklet dkl : pg.getDocklets() )
  {
   DockView lay = dockMap.get( dkl.getTarget() );
   
   if( lay == null )
    orphans.add(dkl);
   else
    lay.addDocklet( new DockletView( dkl, lay ) );
  }
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
   
   hl.setWidth(Util.dim2String(layCont.getWidth()));
   hl.setHeight(Util.dim2String(layCont.getHeight()));
   
   
   if( ((Split) layCont).getComponents() != null )
    for( LayoutComponent lc : ((Split) layCont).getComponents() )
     processContainer(lc, hl);
  }
  else if( layCont instanceof Dock )
  {
   DockVisualCfg cfg = page.getVisualConfig(layCont.getName());
   
   if( cfg == null )
    cfg = new DockVisualCfg();
   
   cfg.setWidth( layCont.getWidth() );
   cfg.setHeight( layCont.getHeight() );
   
   DockView dc = new DockView( page.getVisualConfig(layCont.getName()), htmlCont );
   
   htmlCont.addMember( dc.getCanvas() );
   
   dockMap.put(layCont.getName(), dc);
  }

 }

 


}
