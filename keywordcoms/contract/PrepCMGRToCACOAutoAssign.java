package contract;

import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import base.KeywordsCOM;
import controller.Controller;
import log.LogTag.logaction;
import log.LogTag.logexestatus;

public class PrepCMGRToCACOAutoAssign extends KeywordsCOM {
	
	public PrepCMGRToCACOAutoAssign(Controller ctrl){
		super.ctrl = ctrl;
		
		super.logoperation 		= log.LogTag.logoperation.CMGRAssign;
		super.logtab 			= log.LogTag.logtab.Assignment;
		super.logsubtab 		= log.LogTag.logsubtab.None;	

	}

	@Override
	public boolean execute() {
		if(initKeywords()==false)					return false;
		sendToLogStart();

		try {

			if(ctrl.verifyData.textContainsByXpath("//*[@id='content']/div/div/div[1]/span[1]", "มอบหมายงาน")==false){
				sendToLogCustom(logexestatus.FAIL, logaction.Verify, "มอบหมายงาน");
				return false;
			}
			sendToLogCustom(logexestatus.PASS, logaction.Verify, "มอบหมายงาน");
			
			ctrl.button.linkText("ส่งงาน");
			sendToLogCustom(logexestatus.PASS, logaction.Click, "Tab ส่งงาน");
			
			alert();
			
			ctrl.dropdown.robotByXpath("//*[@id='btnSendDiv']/table[2]/tbody/tr[1]/td/div[2]/input", 2);
			sendToLogCustom(logexestatus.PASS, logaction.Dropdown, ":กรุณาเลือกทางเลือก =ส่งงานต่อ (Auto Assign)");
			
			ctrl.button.xpath("//*[@id='btnSendDiv']/table[3]/tbody/tr/td/button");
			sendToLogCustom(logexestatus.PASS, logaction.Click, "send ส่งงานต่อ (Auto Assign)");

			alert();
			
			if(ctrl.verifyData.urlContains("inboxAction.do")==false){
				sendToLogCustom(logexestatus.FAIL, logaction.Verify, "Verify Send ส่งงาน");
				return false;
			}
			sendToLogCustom(logexestatus.PASS, logaction.Verify, "Verify Send ส่งงาน");
			
		}catch (TimeoutException e) {
			sendToLogCustom(logexestatus.FAIL, logaction.Comfirm, "ส่งงาน -TimeoutException");
			return false;
		}catch (NoSuchElementException e) {
			sendToLogCustom(logexestatus.FAIL, logaction.Comfirm, "ส่งงาน -NoSuchElementException");
			return false;
		}catch (NullPointerException e) {
			sendToLogCustom(logexestatus.FAIL, logaction.Comfirm, "ส่งงาน -NullPointerException");
			return false;
		}catch(NoAlertPresentException e){
			sendToLogCustom(logexestatus.FAIL, logaction.Comfirm, "ส่งงาน -NoAlertPresentException");
			return false;
		}catch (InvalidElementStateException e){
			sendToLogCustom(logexestatus.FAIL, logaction.Comfirm, "ส่งงาน -InvalidElementStateException");
			return false;			
		}
		
		sendToLogFinish();
		return true;
	}
	
}
