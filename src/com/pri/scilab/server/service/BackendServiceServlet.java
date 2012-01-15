package com.pri.scilab.server.service;

import java.util.Collection;

import javax.servlet.http.HttpServlet;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.pri.scilab.client.BackendService;
import com.pri.scilab.client.ui.module.layouted.Layout;
import com.pri.scilab.server.data.BackendDataManager;


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
