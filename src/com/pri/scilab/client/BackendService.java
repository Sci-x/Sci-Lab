package com.pri.scilab.client;

import java.util.Collection;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.pri.scilab.client.ui.module.layouted.Layout;

public interface BackendService extends RemoteService
{
 
 Collection<Layout> getLayouts();
 
 
 public static final String SERVICE_URI = "Service";

 public static class Util
 {

  public static BackendServiceAsync getInstance()
  {

   BackendServiceAsync instance = (BackendServiceAsync) GWT.create(BackendService.class);
   ServiceDefTarget target = (ServiceDefTarget) instance;
   target.setServiceEntryPoint(GWT.getModuleBaseURL() + SERVICE_URI);
   return instance;
  }
 }

}
