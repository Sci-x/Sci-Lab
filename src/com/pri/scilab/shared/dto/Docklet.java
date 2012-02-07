package com.pri.scilab.shared.dto;

import java.io.Serializable;
import java.util.List;

public class Docklet implements Serializable
{
 private String title;
 private String  contents;

 private boolean hasFrame;
 private boolean hasHeader;
 
 private String source;
 private List<Tag> tags;

 public String getContents()
 {
  return contents;
 }

 public void setContents(String contents)
 {
  this.contents = contents;
 }

 public boolean hasFrame()
 {
  return hasFrame;
 }

 public void setHasFrame(boolean hasFrame)
 {
  this.hasFrame = hasFrame;
 }

 public boolean hasHeader()
 {
  return hasHeader;
 }

 public void setHasHeader(boolean hasHeader)
 {
  this.hasHeader = hasHeader;
 }

 public String getTarget()
 {
  return source;
 }

 public void setTarget(String source)
 {
  this.source = source;
 }

 public List<Tag> getTags()
 {
  return tags;
 }

 public void setTags(List<Tag> tags)
 {
  this.tags = tags;
 }

 public String getTitle()
 {
  return title;
 }

 public void setTitle(String title)
 {
  this.title = title;
 }
}
