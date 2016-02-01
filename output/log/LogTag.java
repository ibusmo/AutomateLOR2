package log;

public class LogTag {
	public enum logexestatus{
		INIT,
		TEAR,
		PASS,
		FAIL,
		START,
		FINISH,
		None
	}
	public enum logoperation{
		WebDriver,
		OpenBrowser,
		Certificate,
		Login,
		Logout,
		Register,
		WorkBox,
		RegScanning,
		NCB,
		ConsComment,
		BasiInfo,
		None, 
		RegisScan, 
		Assigment, 
		LoanApp, 
		CMS, 
		CSMColl, 
		Config, 
		SendWork, 
		RequireDoc, 
		EndWork, 
		Wait, 
		SearchWorkBOx, 
		CA, 
		SBROAssign, 
		SBROSECAssign, 
		CMDEPTAssign, 
		Comment, 
		GotoApp, VerifyCommitment, VerifyAppState, RCOM, Condition, ConditionVerify, CMGRAssign, MakeContract, ContractVerify, PrepareAccept, SetBudget, DataDrive
	}
	public enum logtab{
		WorkBox,
		RegCustomer,
		LoanFormCOM,
		Policy,
		RequireDoc,
		AttachFiles,
		RegCollate,
		ExcSummary,
		None, 
		RegColl, 
		Assignment, 
		Edit, 
		GetCMS, 
		CollInfo, 
		CMSSendWork, 
		CMSGotoApp, 
		Collateral, 
		NCB, 
		ListofCMS, 
		Land, 
		LandBuilding, 
		Building, 
		SendWork, 
		EndWork, 
		Wait, 
		Search, 
		SearchWorkBOx, 
		RegLegalCustomer, 
		LoanFormDDA, 
		CA, VerifyAppState, SelectCommittee, Committment, CustomerNotify, Condition, ConditionVerify, ContractAdditionalNew, ContractAdditional, ContractLoanReport, ContractCollReport, ContractSignDate, ContractVerify, PrepareAccept, DataDrive 
	} 
	public enum logsubtab{
		WorkBox,
		PersonalBox,
		Add,
		
		CIFInfo,
		CIFOtherInfo,
		CIFSalary,
		CIFCareer,
		CIFExpense,
		CIFNonNCB,
		
		LongTermLoan,
		
		NCB,
		None, 
		Warrantee, 
		Mortgage, 
		GeneralInfo, 
		LandInfo, 
		BuildingInfo, 
		SupportInfo, 
		EvaMethod, 
		Value, 
		PartPledge, 
		AddLand, 
		AddLandBuilding, 
		AddWarranterNormal, 
		Evaluate, 
		AddBuilding, 
		AddAccounting, 
		AddWarranterLegal, 
		info, Option, PromissoryNote, OD, NormalCustomer, LegalCustomer, BookGarantee, AddLottery, DataDrive
	} 
	public enum logelement{
		None
	}
	public enum logaction{
		Start,
		Stop,
		Fill_login,
		Fill_again,
		Comfirm,
		Click,
		Radio,
		Dropdown,
		Text,
		Save,
		Get,
		Date,
		Popup,
		Check,
		Attach,
		SendWork,
		None, 
		Checkbox, 
		LoadData, 
		DropdownNoText, 
		Draft, 
		OK, 
		DropdownRobot, 
		Wait, 
		Verify, SaveDraft, GotoApp, JSExe, Documents
	}
}
