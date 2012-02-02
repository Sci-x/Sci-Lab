package com.pri.scilab.admin.server.service;

import java.util.Collection;

import javax.servlet.http.HttpServlet;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.pri.scilab.admin.client.BackendService;
import com.pri.scilab.admin.server.data.BackendDataManager;
import com.pri.scilab.shared.dto.PageLayout;


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
 public Collection<PageLayout> getLayouts()
 {
  return BackendDataManager.getInstance().getLayouts();
 }

}
