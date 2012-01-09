package com.pri.scilab.client;

import java.util.Collection;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pri.scilab.client.ui.module.layouted.Layout;

public interface BackendServiceAsync
{

 void getLayouts(AsyncCallback<Collection<Layout>> callback);

}
