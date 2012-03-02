package com.pri.scilab.page.client.ui;

import java.util.LinkedHashMap;

import com.pri.scilab.shared.dto.DockVisualCfg;
import com.pri.scilab.shared.dto.DockVisualCfg.Background;
import com.pri.scilab.shared.dto.DockVisualCfg.BorderStyle;
import com.pri.scilab.shared.dto.DockVisualCfg.Frame;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.ColorPickerItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.SpinnerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.IntegerRangeValidator;
import com.smartgwt.client.widgets.form.validator.Validator;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

public class DockDecorationDialog extends Window
{
 private static DockDecorationDialog instance;

 private CheckboxItem tbCb;
 
 private RadioGroupItem frameSelector;
 private SpinnerItem borderThickness;
 private SelectItem borderStyle;
 private ColorPickerItem colorPicker;
 
 private DynamicForm borderForm;
 
 private CheckboxItem noneBg;
 private CheckboxItem colorBg;
 private CheckboxItem imageBg;
 
 private ColorPickerItem bgColorPicker;
 
 private DynamicForm imgForm;

 private TextItem imgUrl;
 private CheckboxItem rptX;
 private CheckboxItem rptY;
 private TextItem imgPosX;
 private TextItem imgPosY;
 
 private TextItem padL;
// private TextItem padR;
// private TextItem padT;
// private TextItem padB;

 private TextItem mrgL;
// private TextItem mrgR;
// private TextItem mrgT;
// private TextItem mrgB;

 private static DialogCallback callback;
 
 public DockDecorationDialog()
 {
  VLayout lay0 = new VLayout();
  
  SectionStack dcklAttrs = new SectionStack();
  dcklAttrs.setHeight100();
  dcklAttrs.setWidth100();
  dcklAttrs.setVisibilityMode(VisibilityMode.MULTIPLE);  
  dcklAttrs.setOverflow(Overflow.HIDDEN);  
  dcklAttrs.setAnimateSections(true);
  
  SectionStackSection frameSec = new SectionStackSection("Frame");
  VLayout decorLay = new VLayout();
  decorLay.setMembersMargin(4);
  decorLay.setMargin(4);
  frameSec.setItems(decorLay);
  frameSec.setExpanded(true);
 
  dcklAttrs.addSection(frameSec);

  DynamicForm decorForm = new DynamicForm();
  
  tbCb = new CheckboxItem();
  tbCb.setTitle("Show toolbar");
  
  frameSelector = new RadioGroupItem();  
  frameSelector.setTitle("Framing");  
  
  LinkedHashMap<String, String> optMap = new LinkedHashMap<String, String>();
  
  optMap.put(DockVisualCfg.Frame.NONE.name(), "None");
  optMap.put(DockVisualCfg.Frame.FRAME.name(), "Frame");
  optMap.put(DockVisualCfg.Frame.BORDER.name(), "Border");
  
  frameSelector.setValueMap(optMap);
  frameSelector.setValue(DockVisualCfg.Frame.NONE.name());
  
 
  decorForm.setItems(tbCb, frameSelector);

  borderForm = new DynamicForm();
  borderForm.setGroupTitle("Border properties");
  borderForm.setIsGroup(true);
  borderForm.setDisabled(true);
//  borderForm.setWidth(280);

  borderThickness = new SpinnerItem();  
  borderThickness.setTitle("Thickness");  
  borderThickness.setDefaultValue(1);  
  borderThickness.setMin(1);  
  borderThickness.setMax(4);  
  borderThickness.setStep(1);  

  borderStyle = new SelectItem();
  borderStyle.setTitle("Style");
  
  LinkedHashMap<String, String> vMap = new LinkedHashMap<String, String>();
  
  for( DockVisualCfg.BorderStyle bs : DockVisualCfg.BorderStyle.values() )
   vMap.put(bs.name(), bs.getStyle());
  
  borderStyle.setValueMap(vMap);
  borderStyle.setValue("solid");

  colorPicker = new ColorPickerItem();  
  colorPicker.setTitle("Color");  
  colorPicker.setWidth(85);  
  colorPicker.setValue("black");

  frameSelector.addChangedHandler(new ChangedHandler()
  {
   
   @Override
   public void onChanged(ChangedEvent event)
   {
    boolean border = event.getValue().equals(DockVisualCfg.Frame.BORDER.name());

    borderForm.setDisabled(! border );
   }
  });

  
  borderForm.setItems(borderThickness,borderStyle,colorPicker);
  
  decorLay.setMembers(decorForm,borderForm);

  
  SectionStackSection backgroundSec = new SectionStackSection("Background");
  VLayout bgLay = new VLayout();
  bgLay.setMembersMargin(4);
  bgLay.setMargin(4);
  backgroundSec.setItems(bgLay);
  backgroundSec.setExpanded(true);
 
  dcklAttrs.addSection(backgroundSec);

  final DynamicForm bgForm = new DynamicForm();
  imgForm = new DynamicForm();

  
  noneBg = new CheckboxItem(DockVisualCfg.Background.NONE.name(), "None");
  noneBg.setValue(true);
  noneBg.addChangedHandler(new ChangedHandler()
  {
   @Override
   public void onChanged(ChangedEvent event)
   {
    colorBg.setValue(false);
    imageBg.setValue(false);
    
    bgColorPicker.setDisabled(true);
    imgForm.setDisabled(true);
   }
  });
 
  colorBg = new CheckboxItem(DockVisualCfg.Background.COLOR.name(), "Color");
//  colorBg.setShowTitle(false);
  
//  vMap = new LinkedHashMap<String, String>();
//  vMap.put(DockVisualCfg.Background.COLOR.name(), "Color");
//  colorBg.setValueMap(vMap);
  
  colorBg.addChangedHandler(new ChangedHandler()
  {
   @Override
   public void onChanged(ChangedEvent event)
   {
    boolean val = (Boolean)event.getItem().getValue();
    
    if( val )
     noneBg.setValue(false);
    else if( ! imageBg.getValueAsBoolean() )
     noneBg.setValue(true);
    
    bgColorPicker.setDisabled(! val );
   }
  });
  
  
  bgColorPicker = new ColorPickerItem();  
  bgColorPicker.setTitle("Color");  
  bgColorPicker.setWidth(85);  
  bgColorPicker.setDisabled(true);
  bgColorPicker.setValue("#FFE3C8");

  
  imageBg = new CheckboxItem(DockVisualCfg.Background.IMAGE.name(), "Image");
//  imageBg.setShowTitle(false);
  
//  vMap = new LinkedHashMap<String, String>();
//  vMap.put(DockVisualCfg.Background.IMAGE.name(), "Image");
//  imageBg.setValueMap(vMap);
  
  imageBg.addChangedHandler(new ChangedHandler()
  {
   @Override
   public void onChanged(ChangedEvent event)
   {
    boolean val = (Boolean)event.getItem().getValue();
    
    if( val )
     noneBg.setValue(false);
    else if( ! colorBg.getValueAsBoolean() )
     noneBg.setValue(true);
    
    imgForm.setDisabled( ! (Boolean)event.getItem().getValue());
   }
  }); 

  imgForm.setIsGroup(true);
  imgForm.setGroupTitle("Image properties");
  imgForm.setDisabled(true);
  
  imgUrl = new TextItem();
  imgUrl.setTitle("Image");
  
  rptX = new CheckboxItem();
  rptX.setTitle("Repeat horizontally");

  rptY = new CheckboxItem();
  rptY.setTitle("Repeat vertically");

  Validator vld = new CustomValidator()
  {
   @Override
   protected boolean condition(Object value)
   {
    String str = ((String)value).trim();
    
    boolean percent=false;
    
    if( str.charAt(str.length()-1) == '%' )
    {
     percent = true;
     
     str = str.substring(0,str.length()-1).trim();
    }
    
    try
    {
     int val = Integer.valueOf(str);
     
     if( val < 0 )
      return false;
     
     if( percent && val > 100 )
      return false;
    }
    catch(NumberFormatException e)
    {
     return false;
    }
    
    return true;
   }
  };
  
  imgPosX = new TextItem();
  imgPosX.setWidth(100);
  imgPosX.setTitle("Position X");
  imgPosX.setHint("Pixels or percent");
  imgPosX.setValue("0%");
  imgPosX.setValidators(vld);
  imgPosX.setValidateOnChange(true);

  imgPosY = new TextItem();
  imgPosY.setWidth(100);
  imgPosY.setTitle("Position Y");
  imgPosY.setValue("0%");
  imgPosY.setValidateOnChange(true);
  imgPosY.setValidators(vld);

  imgForm.setItems(imgUrl, rptX, rptY,imgPosX,imgPosY);
  
  bgForm.setItems(noneBg,colorBg,bgColorPicker,imageBg);
  
  bgLay.setMembers(bgForm,imgForm);
  
  SectionStackSection margSec = new SectionStackSection("Margin / padding");
  VLayout mrgLay = new VLayout();
  mrgLay.setMembersMargin(4);
  mrgLay.setMargin(4);
  margSec.setItems(mrgLay);
  margSec.setExpanded(true);
 
  DynamicForm padForm = new DynamicForm();
  
  IntegerRangeValidator pdVld = new IntegerRangeValidator();
  pdVld.setMin(0);
  pdVld.setMax(10);
  
  mrgL = new TextItem();
  mrgL.setTitle("Margin");
  mrgL.setHint("px");
  mrgL.setValidators(pdVld);
  mrgL.setValidateOnChange(true);

  padL = new TextItem();
  padL.setTitle("Padding");
  padL.setHint("px");
  padL.setValidators(pdVld);
  padL.setValidateOnChange(true);
  
  padForm.setItems(mrgL,padL);
  
  mrgLay.setMembers(padForm);
  
  dcklAttrs.addSection(margSec);

  
  HLayout btnPanel = new HLayout();
  btnPanel.setLayoutAlign(Alignment.CENTER);
  btnPanel.setWidth("1%");
  btnPanel.setMargin(5);
  btnPanel.setMembersMargin(30);
  
  IButton okBt = new IButton("OK");
  okBt.addClickHandler( new ClickHandler()
  {
   @Override
   public void onClick(ClickEvent event)
   {
    hide();
    callback.dialogClosed( pickConfig() );
   }
  });
  
  IButton cancelBt = new IButton("Cancel");
  cancelBt.addClickHandler( new ClickHandler()
  {
   @Override
   public void onClick(ClickEvent event)
   {
    hide();
    callback.dialogClosed( null );
   }
  });
  
  btnPanel.setMembers(okBt,cancelBt);
  
  lay0.setMembers(dcklAttrs,btnPanel);
  
  addItem(lay0);
  
  setWidth(300);
  setHeight("80%");
  
  setAutoCenter(true);
  setIsModal(true);
  setShowModalMask(true);
  
  setTitle("Dock decoration");
  
  addCloseClickHandler(new CloseClickHandler()
  {
   @Override
   public void onCloseClick(CloseClickEvent event)
   {
    callback.dialogClosed( null );
   }
  });
 }
 

 private void setConfig(DockVisualCfg d)
 {
  tbCb.setValue( d.getShowToolbar() );
  frameSelector.setValue(d.getFrameStyle()!=null?d.getFrameStyle().name():DockVisualCfg.Frame.NONE);
  
  borderForm.setDisabled( d.getFrameStyle() != Frame.BORDER );
  
  borderThickness.setValue(d.getBorderThicknes());
  borderStyle.setValue( d.getBorderStyle()!=null?d.getBorderStyle().name():DockVisualCfg.BorderStyle.SOLID.name() );
  colorPicker.setValue(d.getBorderColor()!=null?d.getBorderColor():"black");

  
  Background bgStl = d.getBackgroundStyle() != null?d.getBackgroundStyle() :  Background.NONE;

  
  noneBg.setValue( bgStl == Background.NONE );
  colorBg.setValue( bgStl == Background.COLOR || bgStl == Background.IMGNFILL );
  imageBg.setValue( bgStl == Background.IMAGE || bgStl == Background.IMGNFILL );

  bgColorPicker.setDisabled(bgStl == Background.COLOR || bgStl == Background.IMGNFILL);
  imgForm.setDisabled(bgStl == Background.IMAGE || bgStl == Background.IMGNFILL);

  imgUrl.setValue( d.getBackgroundImage()!=null? d.getBackgroundImage() : "" );
  
  rptX.setValue( d.isBackgroundRepeatX() );
  rptY.setValue( d.isBackgroundRepeatY() );
  
  imgPosX.setValue( d.getBgImageX()!=null?d.getBgImageX():"0%" );
  imgPosY.setValue( d.getBgImageY()!=null?d.getBgImageY():"0%" );
  
  padL.setValue( d.getPadding() );
  mrgL.setValue( d.getMargin() );

 }

 private DockVisualCfg pickConfig()
 {
  DockVisualCfg cfg = new DockVisualCfg();
  
  cfg.setShowToolbar(tbCb.getValueAsBoolean() );
  cfg.setFrameStyle( Frame.valueOf(frameSelector.getValueAsString()) );
  
  cfg.setBorderThicknes( (Integer)borderThickness.getValue() );
  cfg.setBorderStyle(BorderStyle.valueOf(borderStyle.getValueAsString()));
  cfg.setBorderColor(colorPicker.getValueAsString());
  
  cfg.setPadding( Integer.parseInt(padL.getValueAsString()) );
  cfg.setMargin( Integer.parseInt(mrgL.getValueAsString()) );
  
  if( noneBg.getValueAsBoolean() )
   cfg.setBackgroundStyle(Background.NONE);
  else if( colorBg.getValueAsBoolean() )
  {
   if( imageBg.getValueAsBoolean() )
    cfg.setBackgroundStyle(Background.IMGNFILL);
   else
    cfg.setBackgroundStyle(Background.COLOR);
  }
  else if( imageBg.getValueAsBoolean() )
  {
   if( colorBg.getValueAsBoolean() )
    cfg.setBackgroundStyle(Background.IMGNFILL);
   else
    cfg.setBackgroundStyle(Background.IMAGE);
  }
  
  cfg.setBackgroundColor( bgColorPicker.getValueAsString() );
  
  return cfg;
 }
 
 
 public static void edit( DockVisualCfg d, DialogCallback cb )
 {
  if( instance == null )
   instance = new DockDecorationDialog();
  
  instance.setConfig(d);
  
  callback = cb;
  
  instance.show();
 }

 public interface DialogCallback
 {
  void dialogClosed( DockVisualCfg d );
 }
}
