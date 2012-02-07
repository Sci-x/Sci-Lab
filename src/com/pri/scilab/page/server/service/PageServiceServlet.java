package com.pri.scilab.page.server.service;

import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.pri.scilab.page.client.PageService;
import com.pri.scilab.shared.dto.Dock;
import com.pri.scilab.shared.dto.Docklet;
import com.pri.scilab.shared.dto.HSplit;
import com.pri.scilab.shared.dto.Page;
import com.pri.scilab.shared.dto.PageLayout;
import com.pri.scilab.shared.dto.VSplit;

public class PageServiceServlet extends RemoteServiceServlet implements PageService
{

 @Override
 public Page getPage(String pgId)
 {
  final PageLayout pl = new PageLayout("Test Layout");
  
  VSplit vs = new VSplit();
  vs.setName("VSplit 1");
  vs.setHeight(-100);
  vs.setWidth(-100);
  
  HSplit hp = new HSplit();
  hp.setName("Body split");
  hp.setHeight(0);
  hp.setWidth(-100);
  
  Dock dk = new Dock();
  dk.setName("Header");
  dk.setHeight(100);
  dk.setWidth(-100);
  
  vs.addChildComponent(dk);
  vs.addChildComponent(hp);
  
  dk = new Dock();
  dk.setName("Left Strip");
  dk.setWidth(200);
  dk.setHeight(-100);
  
  hp.addChildComponent(dk);
  
  dk = new Dock();
  dk.setName("Body Strip");
  dk.setHeight(-100);
  
  hp.addChildComponent(dk);

  dk = new Dock();
  dk.setName("Right Strip");
  dk.setWidth(100);
  dk.setHeight(-100);
  
  hp.addChildComponent(dk);
  
  pl.setRootComponent(vs);
 
 
  Page p = new Page();
  
  p.setLayout(pl);
  
  ArrayList<Docklet> dkts = new ArrayList<Docklet>();
  
  p.setDocklets(dkts);
  
  Docklet d = new Docklet();
  d.setHasFrame(false);
  d.setHasHeader(false);
  d.setTarget("Header");
  d.setContents("Header<br/><b>Header</b><br/>Header");
  
  dkts.add(d);
  
  d = new Docklet();
  d.setTitle("The fact");
  d.setHasFrame(true);
  d.setHasHeader(true);
  d.setTarget("Left Strip");
  d.setContents("Left Strip<br/><b>Left Strip</b><br/>Left Strip");
  dkts.add(d);
  
  d = new Docklet();
  d.setHasFrame(true);
  d.setHasHeader(true);
  d.setTarget("Left Strip");
  d.setContents("Left Strip2<br/><b>Left Stri2</b><br/>Left Strip2<br/><b>Left Stri2</b><br/>Left Strip2<br/><b>Left Stri2</b><br/><br/><br/>Left Strip2");
  dkts.add(d);

  d = new Docklet();
  d.setHasFrame(true);
  d.setHasHeader(false);
  d.setTarget("Left Strip");
  d.setContents("Left Strip3<br/><b>Left Stri3</b><br/>Left Strip3");
  dkts.add(d);

  d = new Docklet();
  d.setHasFrame(false);
  d.setHasHeader(false);
  d.setTarget("Left Strip");
  d.setContents("Left Strip4<br/><b>Left Stri4</b><br/>Left Strip4");
  dkts.add(d);

  d = new Docklet();
  d.setHasFrame(true);
  d.setHasHeader(true);
  d.setTarget("Body Strip");
  d.setContents("Body Strip2<br/><b>Body Strip2</b><br/>Body Strip2");
  dkts.add(d);

  return p;
 }
}
