package com.pri.scilab.page.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pri.scilab.shared.dto.Page;

public interface PageServiceAsync
{

 void getPage(String pgId, AsyncCallback<Page> callback);

}
