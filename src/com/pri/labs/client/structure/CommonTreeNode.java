package com.pri.labs.client.structure;

import com.smartgwt.client.widgets.tree.TreeNode;

public class CommonTreeNode extends TreeNode
{
// private CommonTreeNode[] children;
 
 public CommonTreeNode(String name, String icon)
 {
  this(name, icon, new CommonTreeNode[] {});
 }

 public CommonTreeNode(String name, CommonTreeNode... children)
 {
  this(name, null, children);
 }

 public CommonTreeNode(String name, String icon, Object obj, CommonTreeNode... children)
 {
//  this.children=children;
  
  setAttribute("Name", name);
  setAttribute("children", children);
  setAttribute("myobject", obj);

  if(icon != null)
   setAttribute("icon", icon);
 }
 
 public Object getUserObject()
 {
  return getAttributeAsObject("myobject");
 }
 
 public void setIcon( String ic )
 {
  setAttribute("icon", ic);
 }
// public void setChildren(CommonTreeNode[] chld)
// {
//  this.children=chld;
//  super.setChildren(chld);
// }
// 
// public CommonTreeNode[] getChildren()
// {
//  return children;
// }
}
