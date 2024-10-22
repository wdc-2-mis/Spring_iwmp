package app.service;

import java.util.LinkedHashMap;
import java.util.List;

import app.bean.OutcomeTargetBean;
import app.bean.PhysicalAchievementBean;
import app.model.master.WdcpmksyMOutcome;
import app.model.project.WdcpmksyOutcomeTargetTranx;

public interface OutcomeTargetService {
List<WdcpmksyMOutcome> getActivityDetail(Integer finYr);
String saveAsDraftOutcomeTarget(Integer pCode,Integer finYr,String outcomeid,String outcomedetailid,String target,String createdby);
LinkedHashMap<Integer,String> getUserToForward(Integer regId,String userType);
String forwardOutcomeTargetFromPIA(Integer pCode,Integer finYr,String outcomeid,String outcomedetailid,String target,Integer sentFrom,Integer sentTo,String createdBy);
List<WdcpmksyOutcomeTargetTranx> getForwardedOutcome(Integer pCode,Integer finYr);
List<OutcomeTargetBean> viewMovement(Integer regId);
List<OutcomeTargetBean> checkForAlreadyForwardedOutcome(Integer targetid,Integer regId);
String actionByWCDCSLNA(Integer sentTo,Integer sentFrom,Integer targetid,Character action,String remarks,String userType);
LinkedHashMap<Integer,String> getUserToForward(Integer regId,String userType,Character action,Integer targetid);
List<OutcomeTargetBean> getOutcomeDetails(Integer targetid);
List<OutcomeTargetBean> getOutcomeTargetMovementDetails(Integer targetid);
}
