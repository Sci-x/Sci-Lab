package com.pri.labs.client;

import java.util.Collection;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.pri.labs.client.structure.Layout;

public interface BackendServiceAsync
{

 void getLayouts(AsyncCallback<Collection<Layout>> callback);

}
