package com.pri.scilab.client;

import java.util.Collection;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pri.scilab.shared.dto.PageLayout;

public interface BackendServiceAsync
{

 void getLayouts(AsyncCallback<Collection<PageLayout>> callback);

}
