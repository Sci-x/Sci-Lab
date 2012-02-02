package com.pri.scilab.admin.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pri.scilab.admin.client.ui.AdminPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Entry implements EntryPoint
{
 @Override
 public void onModuleLoad()
 {
  DataManager.getInstance().init( new AsyncCallback<Void>()
  {
   
   @Override
   public void onSuccess(Void arg0)
   {
    new AdminPanel().draw();
   }
   
   @Override
   public void onFailure(Throwable arg0)
   {
    // TODO Auto-generated method stub
    
   }
  });
  
 }}
