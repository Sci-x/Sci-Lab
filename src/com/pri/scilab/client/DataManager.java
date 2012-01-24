package com.pri.scilab.client;

import java.util.Collection;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pri.scilab.shared.dto.PageLayout;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;

public class DataManager
{
 private static DataManager instance = new DataManager();
 private BackendServiceAsync async;
 
 public static DataManager getInstance()
 {
  return instance;
 }
 
 private Collection<PageLayout> layouts;
 
 
 private DataManager()
 {
  async = BackendService.Util.getInstance();
 }
 
 public void init( final AsyncCallback<Void> cb )
 {
  async.getLayouts(new AsyncCallback<Collection<PageLayout>>()
  {
   
   @Override
   public void onSuccess(Collection<PageLayout> arg0)
   {
    layouts=arg0;
    
    cb.onSuccess(null);
   }
   
   @Override
   public void onFailure( final Throwable arg0)
   {
    SC.warn("Error in communicating with server: "+arg0.getMessage(), new BooleanCallback()
    {
     public void execute(Boolean value)
     {
      cb.onFailure(arg0);
     }
    });
   }
  });
 }
 
 public Collection<PageLayout> getLayouts()
 {
  return layouts;
 }
 
}
