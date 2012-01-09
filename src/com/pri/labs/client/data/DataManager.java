package com.pri.labs.client.data;

import java.util.Collection;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pri.labs.client.BackendService;
import com.pri.labs.client.BackendServiceAsync;
import com.pri.labs.client.structure.Layout;
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
 
 private Collection<Layout> layouts;
 
 
 private DataManager()
 {
  async = BackendService.Util.getInstance();
 }
 
 public void init( final AsyncCallback<Void> cb )
 {
  async.getLayouts(new AsyncCallback<Collection<Layout>>()
  {
   
   @Override
   public void onSuccess(Collection<Layout> arg0)
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
 
 public Collection<Layout> getLayouts()
 {
  return layouts;
 }
 
}
