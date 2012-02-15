package com.pri.scilab.page.client.ui;

import java.util.LinkedHashMap;

import com.pri.scilab.shared.dto.Docklet;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.ColorPickerItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.SpinnerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

public class DockletEditor extends Window
{
 private static DockletEditor instance;
 
 private enum Frames
 {
  NONE,
  FRAME,
  BORDER
 }

 public DockletEditor()
 {
  VLayout lay0 = new VLayout();
  lay0.setWidth100();
  lay0.setHeight100();
  
  HLayout lay1 = new HLayout();
  lay1.setWidth100();
  lay1.setHeight("*");
  
  VLayout lay2text = new VLayout();
  lay2text.setShowResizeBar(true);
  lay2text.setHeight100();
//  lay2text.setAutoWidth();
  
  SectionStack dcklAttrs = new SectionStack();
  dcklAttrs.setHeight100();
  dcklAttrs.setWidth(280);
  dcklAttrs.setVisibilityMode(VisibilityMode.MULTIPLE);  
  dcklAttrs.setOverflow(Overflow.HIDDEN);  
  dcklAttrs.setShowResizeBar(true);
  dcklAttrs.setAnimateSections(true);
  
  SectionStackSection decorSec = new SectionStackSection("Decoration");
  VLayout decorLay = new VLayout();
  decorLay.setMembersMargin(4);
  decorLay.setMargin(4);
  decorSec.setItems(decorLay);
 
  dcklAttrs.addSection(decorSec);

  
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
  
   
  lay1.setMembers(dcklAttrs,lay2text);
  
  HLayout btnPanel = new HLayout();
  btnPanel.setLayoutAlign(Alignment.CENTER);
  btnPanel.setWidth("1%");
  btnPanel.setHeight("40");
  btnPanel.setMembersMargin(30);

  lay0.setMembers(lay1, btnPanel);
  
  addItem(lay0);
  
  setWidth("80%");
  setHeight("80%");
  
  setAutoCenter(true);
  setIsModal(true);
  setShowModalMask(true);
 }
 
 public static void edit( Docklet d )
 {
  if( instance == null )
   instance = new DockletEditor();
  
  instance.show();
 }
 
}
