package base;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.UnreachableBrowserException;

import controller.Controller;
import log.LogCat;
import log.LogTag.logaction;
import log.LogTag.logelement;
import log.LogTag.logexestatus;
import log.LogTag.logoperation;
import log.LogTag.logsubtab;
import log.LogTag.logtab;
import pools.ReadExcelController;
import pools.ReadExcelXLS;
import testdata.LoadElement;
import testdata.CellTag.col;
import testdata.CellTag.fieldType;
import testdata.DataElementObj;

public abstract class KeywordsCOM{

	protected Controller ctrl;
	protected LogCat logCat;
	
	protected logoperation logoperation;
	protected logtab logtab;
	protected logsubtab logsubtab;

	protected String workBookPath;
	protected String workSheetPath;
	protected int sizeOfData;
	protected int offsetRow;
	
	protected LoadElement regData;
	protected List<DataElementObj> dataElementObjList;
	
	// TO BE OVERRIDE
	protected boolean preCondition(DataElementObj obj){return true;};
	protected boolean posCondition(DataElementObj obj){return true;};
	protected boolean preExecute(){return true;};
	protected boolean posExecute(){return true;};
	
	protected boolean initKeywords(){
		this.logCat = ctrl.logCat;
		this.workBookPath = ctrl.pathVariable.getRelativeExcelPath();
		return true;
	}
	
	protected boolean loadData(){
		offsetRow = 2;
		sizeOfData = getSize();
		if(sizeOfData==-1){ return false; }
		regData = new LoadElement(workBookPath, workSheetPath, sizeOfData, offsetRow);
		if(regData.loadData()){
			dataElementObjList = regData.getObject();
			sendToLogCustom(logexestatus.PASS, logaction.LoadData, null);
		}else{
			sendToLogCustom(logexestatus.FAIL, logaction.LoadData, null);		
			return false;	
		}
		return true;
	}

	public boolean execute() {
		
		if(initKeywords()==false)					return false;
		if(loadData()==false) 						return false;
		
		sendToLogStart();
		if(preExecute()==false) 					{	takeCapture();	return false;	}
		
		for(DataElementObj obj : dataElementObjList){
			preCondition(obj);
			if(obj.run==false) continue;
			
			String objDebug = obj.name + "\t#" + obj.data + "\t#" + obj.type + "\t#" + obj.fieldType + "\t#" + obj.fieldName + "\t#" + obj.fieldValue;
			
			 try{
				switch(obj.type){
					case openbrowser:
						if(caseOpenBrowser(obj)==false) return false;
						break;
					case button:
						if(caseButton(obj)==false) 		return false;
						break;
					case dropdownx:
					case dropdown:
						if(caseDropdown(obj)==false)	return false;
						break;
					case text:
						if(caseText(obj)==false) 		return false;
						break;
					case radio:
						if(caseRadio(obj)==false)		return false;
						break;
					case checkbox:
						if(caseCheckbox(obj)==false) 	return false;
						break;
					case date:
						if(caseDate(obj)==false) 		return false;
						break;
					case popup:
						if(casePopup(obj)==false) 		return false;
						break;
					case alert:
						if(caseAlert(obj)==false) 		return false;
						break;
						
					case save:
					case savedraft:
						if(caseSave(obj)==false) 		return false;
						break;
					case verify:
						if(caseVerify(obj)==false) 		return false;
						break;
						
					case jsexe:
						if(caseJsExe(obj)==false) 		return false;
						break;				
				}
				
				sendToLogCustom(logexestatus.PASS, logaction.None, objDebug);
				
			}catch(NoSuchElementException e){
				sendToLogCustom(logexestatus.FAIL, logaction.None, objDebug + " -NoSuchElementException");
				takeCapture();
				return false;
			}catch(UnreachableBrowserException e){
				sendToLogCustom(logexestatus.FAIL, logaction.None, objDebug + " -UnreachableBrowserException");
				takeCapture();
				return false;
			}catch(InvalidElementStateException e){
				sendToLogCustom(logexestatus.FAIL, logaction.None, objDebug + " -InvalidElementStateException");
				takeCapture();
				return false;
			}catch(NoAlertPresentException e){
				sendToLogCustom(logexestatus.FAIL, logaction.None, objDebug + " -NoAlertPresentException");
				takeCapture();
//				return false;
			}
			catch(TimeoutException e){
				sendToLogCustom(logexestatus.FAIL, logaction.None, objDebug + " -TimeoutException");
				takeCapture();
				return false;
			}
			catch(UnhandledAlertException e){
				sendToLogCustom(logexestatus.FAIL, logaction.None, objDebug + " -UnhandledAlertException");
				takeCapture();
//				return false;
			}catch(WebDriverException e){
				sendToLogCustom(logexestatus.FAIL, logaction.None, objDebug + " -WebDriverException");
				takeCapture();
				return false;
			}catch(NullPointerException e){
				sendToLogCustom(logexestatus.FAIL, logaction.None, objDebug + " -NullPointerException");
				takeCapture();
				return false;
			}catch(Exception e){
				sendToLogCustom(logexestatus.FAIL, logaction.None, objDebug + " -Exception");	
				takeCapture();			
			}
			
			posCondition(obj);
		}

		if(posExecute()==false){
			takeCapture();
			return false;
		}
		sendToLogFinish();
		return true;
	}
	
	private boolean caseOpenBrowser(DataElementObj obj) {
		ctrl.driver.get(obj.fieldValue);
		ctrl.driver.manage().window().maximize();
		return true;
	}
	
	private boolean caseJsExe(DataElementObj obj) {
		ctrl.jsExecute.runExe(obj);
		sendToLogCustom(logexestatus.PASS, logaction.JSExe, obj.name + ": '" + obj.fieldName + " '");
		return true;
	}
	private boolean caseSave(DataElementObj obj) {
			ctrl.button.save(obj);
		return true;
	}
	
	private boolean caseVerify(DataElementObj obj) {
			if(ctrl.verifyData.runVerify(obj)){
				sendToLogCustom(logexestatus.PASS, logaction.Verify, obj.name + ": " + obj.data 
						+ " - " + obj.fieldType + ": '" + obj.fieldName + "'|'" + obj.fieldValue + "'");
			}
			else{
				sendToLogCustom(logexestatus.FAIL, logaction.Verify, obj.name + ": " + obj.data  
						+ " - " + obj.fieldType + ": '" + obj.fieldName + "'|'" + obj.fieldValue + "'");
				takeCapture();
				return false;
			}
		return true;
	}
	protected boolean caseAlert(DataElementObj obj) {
			alert();
		return true;
	}

	protected boolean casePopup(DataElementObj obj) {
			WebDriver popup = ctrl.popup.RunPopup(obj);
			if (popup != null) {
				sendToLogCustom(logexestatus.PASS, logaction.Popup, obj.name + ": " + obj.data);
			} else {
				sendToLogCustom(logexestatus.FAIL, logaction.Popup, obj.name + ": " + obj.data);
				takeCapture();
				return false;
			}
		return true;
	}

	protected boolean caseDate(DataElementObj obj) {
			ctrl.datePicker.runDatePicker(obj);
		return true;
	}

	protected boolean caseCheckbox(DataElementObj obj) {
			ctrl.checkBox.RunCheckBox(obj);
		return true;
	}

	protected boolean caseRadio(DataElementObj obj) {
			ctrl.radio.RunRadio(obj);
		return true;
	}
	
	protected boolean caseText(DataElementObj obj) {
		//@Override Method for GENERATE NUMBER
		try{
			obj.data = obj.fieldValue!="" && obj.fieldValue!="null" ? getNum((int)Math.round(Double.parseDouble(obj.fieldValue))) : obj.data;
			ctrl.type.RunText(obj);
		}catch(NumberFormatException e){
			ctrl.type.RunText(obj);
		}
		return true;
	}
	
	protected String getNum(int point){
		  Random ran = new Random();
		  int low = (int) Math.pow(10, point-1);
		  int high = (int) Math.pow(10, point)-low;
		  int tmp = ran.nextInt(high) + low;
		  return ""+tmp;
	}
	
	protected boolean caseDropdown(DataElementObj obj) {
			ctrl.dropdown.RunDropdown(obj, false);
		return true;
	}

	protected boolean caseButton(DataElementObj obj) {
		try{
			ctrl.button.RunButton(obj);
		}catch (TimeoutException e){
			if(obj.fieldType == fieldType.linktext)	{
				//Nothing TO DO
			}
			else if(obj.data.toLowerCase().contains("DRAFT")){
				//Nothing TO DO
			}else{
				takeCapture();
				return false;
			}
		}
		return true;
	}	
	
	protected boolean capture(String stepText){
		ctrl.screenCapture.saveShotImage(ctrl.pathVariable.getRelativeLog() + "_" 
				+ (++ctrl.imgId) + "_" 
				+ stepText	+ ".jpg");
		sendToLogCustom(logexestatus.PASS, logaction.Capture, 
				String.format("Capture -index: %s -step: %s", ctrl.imgId-1, stepText));	
		return true;
	}
	
	protected boolean alert(){
		try{
			ctrl.alertHandle.execute();
			sendToLogCustom(logexestatus.PASS, logaction.Comfirm, "Alert!");
		}catch(NoAlertPresentException e){
			sendToLogCustom(logexestatus.PASS, logaction.Comfirm, "No alert!");
		}
		return true;
		
	}
	
	protected int getSize() {
		ReadExcelController rdExl = new ReadExcelXLS(workBookPath, workSheetPath);
		
		int size = -1;
		try{
			size = (int) Math.round(Double.parseDouble(rdExl.getString(col.A, 1)));
			sendToLogCustom(logexestatus.PASS, logaction.LoadData, "size: " + size);
		}catch(NumberFormatException e){
			size = -1;		
			sendToLogCustom(logexestatus.FAIL, logaction.LoadData, "size: " + size + " -NumberFormatException");
		}catch(NullPointerException e){
			size = -1;		
			sendToLogCustom(logexestatus.FAIL, logaction.LoadData, "size: " + size + " -NullPointerException");
		}
		rdExl.closeFile();
		return size;
	}
	
	protected void sendToLogStart() {
		sendToLogCustom(logexestatus.START, logaction.None, null);
	}

	protected void sendToLogFinish() {
		sendToLogCustom(logexestatus.FINISH, logaction.None, null);
	}
	
	protected void sendToLogCustom(logexestatus logexestatus, logaction logaction, String str) {
		logCat.sendToLog(logexestatus, logoperation, logtab, logsubtab, logelement.None,
				logaction, str);
	}
	
	protected void takeCapture(){
		ctrl.screenCapture.saveShotImage(ctrl.pathVariable.getRelativeLog() + ".jpg");
		sendToLogCustom(logexestatus.PASS, logaction.Capture, 
				String.format("Capture -Crash Unexpected Test -index: %s", ctrl.imgId-1));	
	}
}
