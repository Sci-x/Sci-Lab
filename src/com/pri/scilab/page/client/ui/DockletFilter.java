package com.pri.scilab.page.client.ui;

import com.pri.scilab.shared.dto.Docklet;

public interface DockletFilter
{

 boolean testDocklet( Docklet dl );

 String getName();
 
}
