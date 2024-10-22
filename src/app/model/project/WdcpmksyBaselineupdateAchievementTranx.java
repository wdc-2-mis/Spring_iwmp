package app.model.project;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import app.model.UserReg;

@Entity
@Table(name="wdcpmksy_baselineupdate_achievement_tranx" ,schema="public")
public class WdcpmksyBaselineupdateAchievementTranx implements java.io.Serializable {
	
	
	private int tranxId;
    private WdcpmksyBaselineupdateAchievement wdcpmksyBaselineupdateAchievement;
    private UserReg iwmpUserRegBySentfrom;
    private UserReg iwmpUserRegBySentTo;
    private Date senton;
    private Character action;
    private String remarks;

   public WdcpmksyBaselineupdateAchievementTranx() {
   }

	
   public WdcpmksyBaselineupdateAchievementTranx(int tranxId) {
       this.tranxId = tranxId;
   }
   public WdcpmksyBaselineupdateAchievementTranx(int tranxId, WdcpmksyBaselineupdateAchievement wdcpmksyBaselineupdateAchievement, UserReg iwmpUserRegBySentfrom, UserReg iwmpUserRegBySentTo, Date senton, Character action, String remarks) {
      this.tranxId = tranxId;
      this.wdcpmksyBaselineupdateAchievement = wdcpmksyBaselineupdateAchievement;
      this.iwmpUserRegBySentfrom = iwmpUserRegBySentfrom;
      this.iwmpUserRegBySentTo = iwmpUserRegBySentTo;
      this.senton = senton;
      this.action = action;
      this.remarks = remarks;
   }
  
   @Id 
   @GeneratedValue(strategy=GenerationType.AUTO)
   @Column(name="tranx_id", unique=true, nullable=false)
   public int getTranxId() {
       return this.tranxId;
   }
   
   public void setTranxId(int tranxId) {
       this.tranxId = tranxId;
   }

   
   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="achievement_id")
   public WdcpmksyBaselineupdateAchievement getWdcpmksyBaselineupdateAchievement() {
	return wdcpmksyBaselineupdateAchievement;
   }


   public void setWdcpmksyBaselineupdateAchievement(WdcpmksyBaselineupdateAchievement wdcpmksyBaselineupdateAchievement) {
	this.wdcpmksyBaselineupdateAchievement = wdcpmksyBaselineupdateAchievement;
	}


   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="sent_from")
   public UserReg getIwmpUserRegBySentfrom() {
       return this.iwmpUserRegBySentfrom;
   }
   
   public void setIwmpUserRegBySentfrom(UserReg iwmpUserRegBySentfrom) {
       this.iwmpUserRegBySentfrom = iwmpUserRegBySentfrom;
   }

   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="sent_to")
   public UserReg getIwmpUserRegBySentTo() {
       return this.iwmpUserRegBySentTo;
   }
   
   public void setIwmpUserRegBySentTo(UserReg iwmpUserRegBySentTo) {
       this.iwmpUserRegBySentTo = iwmpUserRegBySentTo;
   }

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name="sent_on", length=35)
   public Date getSenton() {
       return this.senton;
   }
   
   public void setSenton(Date senton) {
       this.senton = senton;
   }

   
   @Column(name="action", length=1)
   public Character getAction() {
       return this.action;
   }
   
   public void setAction(Character action) {
       this.action = action;
   }

   
   @Column(name="remarks", length=5000)
   public String getRemarks() {
       return this.remarks;
   }
   
   public void setRemarks(String remarks) {
       this.remarks = remarks;
   }




}
