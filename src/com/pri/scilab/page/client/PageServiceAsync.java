package com.pri.scilab.page.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pri.scilab.shared.dto.Page;

public interface PageServiceAsync
{
 static PageServiceAsync instance = (PageServiceAsync) GWT.create(PageService.class);



 void getPage(String pgId, AsyncCallback<Page> callback);

}
