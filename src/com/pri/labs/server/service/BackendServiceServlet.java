package com.pri.labs.server.service;

import java.util.Collection;

import javax.servlet.http.HttpServlet;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.pri.labs.client.BackendService;
import com.pri.labs.client.structure.Layout;
import com.pri.labs.server.data.BackendDataManager;

/**
 * Servlet implementation class BackendServiceServlet
 */
public class BackendServiceServlet extends RemoteServiceServlet implements BackendService
{
 private static final long serialVersionUID = 1L;

 /**
  * @see HttpServlet#HttpServlet()
  */
 public BackendServiceServlet()
 {
  super();
 }

 @Override
 public Collection<Layout> getLayouts()
 {
  return BackendDataManager.getInstance().getLayouts();
 }

}
