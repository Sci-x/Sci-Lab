package com.pri.scilab.shared.dto;

import java.util.List;

public class Docklet
{
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

 public boolean isHasFrame()
 {
  return hasFrame;
 }

 public void setHasFrame(boolean hasFrame)
 {
  this.hasFrame = hasFrame;
 }

 public boolean isHasHeader()
 {
  return hasHeader;
 }

 public void setHasHeader(boolean hasHeader)
 {
  this.hasHeader = hasHeader;
 }

 public String getSource()
 {
  return source;
 }

 public void setSource(String source)
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
}
