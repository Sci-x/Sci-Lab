package com.pri.scilab.page.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.pri.scilab.shared.dto.Page;

@RemoteServiceRelativePath("Service")
public interface PageService extends RemoteService
{
 Page getPage( String pgId );

// public static final String SERVICE_URI = "Service";
//
// public static class Util
// {
//
//  public static PageServiceAsync getInstance()
//  {
//
//   PageServiceAsync instance = (PageServiceAsync) GWT.create(PageService.class);
//   ServiceDefTarget target = (ServiceDefTarget) instance;
//   target.setServiceEntryPoint(GWT.getModuleBaseURL() + SERVICE_URI);
//   return instance;
//  }
// }

}
