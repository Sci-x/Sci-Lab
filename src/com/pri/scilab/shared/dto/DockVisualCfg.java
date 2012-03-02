package com.pri.scilab.shared.dto;


public class DockVisualCfg
{
 public enum Frame
 {
  NONE,
  FRAME,
  BORDER
 }

 public enum Background
 {
  NONE, COLOR, IMAGE, IMGNFILL
 }

 public enum BorderStyle
 {
  SOLID("solid"),
  DOTTED("dotted"),
  DASHED("dashed"),
  DOUBLE("double"),
  RIDGE("ridge"),
  GROOVE("groove"),
  OUTSET("outset"),
  INSET("inset");

  private String brdStyle;

  private BorderStyle(String bsName)
  {
   brdStyle = bsName;
  }

  public String getStyle()
  {
   return brdStyle;
  }
 }

 private Frame       frameStyle;
 private Background  backgroundStyle;

 private int         borderThicknes=1;
 private String      borderColor;
 private BorderStyle borderStyle;

 private String      backgroundColor;
 private String      backgroundImage;

 private String      bgImageX;
 private String      bgImageY;
 
 private boolean     backgroundRepeatX;
 private boolean     backgroundRepeatY;
 private boolean     showToolbar;

 private int width;
 private int height;
 
 private int margin;
 private int padding;
 
 public Frame getFrameStyle()
 {
  return frameStyle;
 }

 public void setFrameStyle(Frame frameStyle)
 {
  this.frameStyle = frameStyle;
 }

 public Background getBackgroundStyle()
 {
  return backgroundStyle;
 }

 public void setBackgroundStyle(Background backgroundStyle)
 {
  this.backgroundStyle = backgroundStyle;
 }

 public int getBorderThicknes()
 {
  return borderThicknes;
 }

 public void setBorderThicknes(int borderThicknes)
 {
  this.borderThicknes = borderThicknes;
 }

 public String getBorderColor()
 {
  return borderColor;
 }

 public void setBorderColor(String borderColor)
 {
  this.borderColor = borderColor;
 }

 public BorderStyle getBorderStyle()
 {
  return borderStyle;
 }

 public void setBorderStyle(BorderStyle borderStyle)
 {
  this.borderStyle = borderStyle;
 }

 public String getBackgroundColor()
 {
  return backgroundColor;
 }

 public void setBackgroundColor(String backgroundColor)
 {
  this.backgroundColor = backgroundColor;
 }

 public String getBackgroundImage()
 {
  return backgroundImage;
 }

 public void setBackgroundImage(String backgroundImage)
 {
  this.backgroundImage = backgroundImage;
 }

 public boolean isBackgroundRepeatX()
 {
  return backgroundRepeatX;
 }

 public void setBackgroundRepeatX(boolean backgroundRepeatX)
 {
  this.backgroundRepeatX = backgroundRepeatX;
 }

 public boolean isBackgroundRepeatY()
 {
  return backgroundRepeatY;
 }

 public void setBackgroundRepeatY(boolean backgroundRepeatY)
 {
  this.backgroundRepeatY = backgroundRepeatY;
 }

 public boolean getShowToolbar()
 {
  return     showToolbar;
 }

 public void setShowToolbar(boolean showToolbar)
 {
  this.showToolbar = showToolbar;
 }

 public String getBgImageX()
 {
  return bgImageX;
 }

 public void setBgImageX(String bgImageX)
 {
  this.bgImageX = bgImageX;
 }

 public String getBgImageY()
 {
  return bgImageY;
 }

 public void setBgImageY(String bgImageY)
 {
  this.bgImageY = bgImageY;
 }

 public int getMargin()
 {
  return margin;
 }

 public void setMargin(int margin)
 {
  this.margin = margin;
 }

 public int getPadding()
 {
  return padding;
 }

 public void setPadding(int padding)
 {
  this.padding = padding;
 }

 public void setWidth(int width)
 {
  this.width=width;
 }

 public void setHeight(int height)
 {
  this.height = height;
 }

 public int getWidth()
 {
  return width;
 }

 public int getHeight()
 {
  return height;
 }

}
