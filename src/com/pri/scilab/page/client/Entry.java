package com.pri.scilab.page.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pri.scilab.page.client.ui.Renderer;
import com.pri.scilab.shared.dto.Page;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Entry implements EntryPoint
{
 @Override
 public void onModuleLoad()
 {
  PageServiceAsync.instance.getPage("001", new AsyncCallback<Page>()
  {
   
   @Override
   public void onSuccess(Page pg)
   {
    new Renderer( pg ).draw();
   }
   
   @Override
   public void onFailure(Throwable arg0)
   {
   }
  });
  
 }}
