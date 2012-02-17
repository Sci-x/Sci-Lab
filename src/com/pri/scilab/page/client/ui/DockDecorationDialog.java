package com.pri.scilab.page.client.ui;

import java.util.LinkedHashMap;

import com.pri.scilab.shared.dto.Dock;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.ColorPickerItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.SpinnerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

public class DockDecorationDialog extends Window
{
 private static DockDecorationDialog instance;

 
 private enum Frames
 {
  NONE,
  FRAME,
  BORDER
 }
 
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
  
  CheckboxItem tbCb = new CheckboxItem();
  tbCb.setTitle("Show toolbar");
  
  RadioGroupItem frameSelector = new RadioGroupItem();  
  frameSelector.setTitle("Framing");  
  
  LinkedHashMap<String, String> optMap = new LinkedHashMap<String, String>();
  
  optMap.put(Frames.NONE.name(), "None");
  optMap.put(Frames.FRAME.name(), "Frame");
  optMap.put(Frames.BORDER.name(), "Border");
  
  frameSelector.setValueMap(optMap);
  frameSelector.setValue(Frames.NONE.name());
  
 
  decorForm.setItems(tbCb, frameSelector);

  DynamicForm borderForm = new DynamicForm();
  borderForm.setGroupTitle("Border properties");
  borderForm.setIsGroup(true);
//  borderForm.setWidth(280);

  final SpinnerItem borderThickness = new SpinnerItem();  
  borderThickness.setTitle("Thickness");  
  borderThickness.setDefaultValue(1);  
  borderThickness.setMin(1);  
  borderThickness.setMax(4);  
  borderThickness.setStep(1);  
  borderThickness.setDisabled(true);

  final SelectItem borderStyle = new SelectItem();
  borderStyle.setTitle("Style");
  borderStyle.setValueMap("solid","dotted","dashed","double","ridge","groove","outset","inset");
  borderStyle.setDisabled(true);
  borderStyle.setValue("solid");
  
  final ColorPickerItem colorPicker = new ColorPickerItem();  
  colorPicker.setTitle("Color");  
  colorPicker.setWidth(85);  
  colorPicker.setDisabled(true);
  colorPicker.setValue("black");

  frameSelector.addChangedHandler(new ChangedHandler()
  {
   
   @Override
   public void onChanged(ChangedEvent event)
   {
    boolean border = event.getValue().equals(Frames.BORDER.name());

    borderThickness.setDisabled(! border);
    borderStyle.setDisabled(! border);
    colorPicker.setDisabled(! border);
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

  DynamicForm bgForm = new DynamicForm();
  
  ChangedHandler chgHnd = new ChangedHandler()
  {
   
   @Override
   public void onChanged(ChangedEvent event)
   {
    // TODO Auto-generated method stub
    throw new dev.NotImplementedYetException();
    //
   }
  };
  
  RadioGroupItem noneBg = new RadioGroupItem();
  noneBg.setShowTitle(false);
  noneBg.setValueMap("None");
  noneBg.setValue("None");
  
  noneBg.addChangedHandler(chgHnd);
 
  RadioGroupItem colorBg = new RadioGroupItem();
  colorBg.setShowTitle(false);
  colorBg.setValueMap("Color");
 
  final ColorPickerItem bgColorPicker = new ColorPickerItem();  
  bgColorPicker.setTitle("Color");  
  bgColorPicker.setWidth(85);  
  bgColorPicker.setDisabled(true);
  bgColorPicker.setValue("#FFE3C8");

  
  RadioGroupItem imgBg = new RadioGroupItem();
  imgBg.setShowTitle(false);
  imgBg.setValueMap("Image");
  
  DynamicForm imgForm = new DynamicForm();
  imgForm.setIsGroup(true);
  imgForm.setGroupTitle("Image properties");
  imgForm.setDisabled(true);
  
  CheckboxItem rptX = new CheckboxItem();
  rptX.setTitle("Repeat horizontally");

  CheckboxItem rptY = new CheckboxItem();
  rptY.setTitle("Repeat vertically");

  imgForm.setItems(rptX,rptY);
  
  bgForm.setItems(new SpacerItem(),noneBg,new SpacerItem(),colorBg,bgColorPicker);
  
  bgLay.setMembers(bgForm,imgForm);
  
  SectionStackSection margSec = new SectionStackSection("Margin / padding");
  VLayout mrgLay = new VLayout();
  mrgLay.setMembersMargin(4);
  mrgLay.setMargin(4);
  margSec.setItems(mrgLay);
  margSec.setExpanded(true);
 
  dcklAttrs.addSection(margSec);

  
  HLayout btnPanel = new HLayout();
  btnPanel.setLayoutAlign(Alignment.CENTER);
  btnPanel.setWidth("1%");
  btnPanel.setMargin(5);
  btnPanel.setMembersMargin(30);
  btnPanel.setBorder("1px dotted black");
  
  IButton okBt = new IButton("OK");
  
  IButton cancelBt = new IButton("Cancel");
  
  btnPanel.setMembers(okBt,cancelBt);
  
  lay0.setMembers(dcklAttrs,btnPanel);
  
  addItem(lay0);
  
  setWidth(300);
  setHeight("80%");
  
  setAutoCenter(true);
  setIsModal(true);
  setShowModalMask(true);
  
  setTitle("Dock decoration");
 }
 
 public static void edit( Dock d )
 {
  if( instance == null )
   instance = new DockDecorationDialog();
  
  instance.show();
 }
}
