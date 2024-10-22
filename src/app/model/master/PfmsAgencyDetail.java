package app.model.master;

import java.math.BigDecimal;
import java.util.Date;

import app.model.IwmpDistrict;
import app.model.IwmpState;

public class PfmsAgencyDetail implements java.io.Serializable {
	private int agencyId;
    private IwmpBlock iwmpBlock;
    private IwmpDistrict iwmpDistrict;
    private IwmpGramPanchayat iwmpGramPanchayat;
    private IwmpState iwmpState;
    private IwmpVillage iwmpVillage;
    private String agencyUniqueCode;
    private String agencyName;
    private Integer districtLgdCode;
    private Integer blockLgdCode;
    private Integer panchayatLgdCode;
    private Integer villageCode;
    private Integer tehsilLgdCode;
    private Integer townLgdCode;
    private Integer wardLgdCode;
    private String cityName;
    private Date date;
    private Integer schemeCode;
    private String componentCode;
    private String componentName;
    private BigDecimal totalAmount;
    private String vouchernumber;
    private String projectname;
    private String apiresponsestatus;
    private Date createdon;

   public PfmsAgencyDetail() {
   }

	
   public PfmsAgencyDetail(int agencyId) {
       this.agencyId = agencyId;
   }
   public PfmsAgencyDetail(int agencyId, IwmpBlock iwmpBlock, IwmpDistrict iwmpDistrict, IwmpGramPanchayat iwmpGramPanchayat, IwmpState iwmpState, IwmpVillage iwmpVillage, String agencyUniqueCode, String agencyName, Integer districtLgdCode, Integer blockLgdCode, Integer panchayatLgdCode, Integer villageCode, Integer tehsilLgdCode, Integer townLgdCode, Integer wardLgdCode, String cityName, Date date, Integer schemeCode, String componentCode, String componentName, BigDecimal totalAmount, String vouchernumber, String projectname, String apiresponsestatus, Date createdon) {
      this.agencyId = agencyId;
      this.iwmpBlock = iwmpBlock;
      this.iwmpDistrict = iwmpDistrict;
      this.iwmpGramPanchayat = iwmpGramPanchayat;
      this.iwmpState = iwmpState;
      this.iwmpVillage = iwmpVillage;
      this.agencyUniqueCode = agencyUniqueCode;
      this.agencyName = agencyName;
      this.districtLgdCode = districtLgdCode;
      this.blockLgdCode = blockLgdCode;
      this.panchayatLgdCode = panchayatLgdCode;
      this.villageCode = villageCode;
      this.tehsilLgdCode = tehsilLgdCode;
      this.townLgdCode = townLgdCode;
      this.wardLgdCode = wardLgdCode;
      this.cityName = cityName;
      this.date = date;
      this.schemeCode = schemeCode;
      this.componentCode = componentCode;
      this.componentName = componentName;
      this.totalAmount = totalAmount;
      this.vouchernumber = vouchernumber;
      this.projectname = projectname;
      this.apiresponsestatus = apiresponsestatus;
      this.createdon = createdon;
   }
  
   public int getAgencyId() {
       return this.agencyId;
   }
   
   public void setAgencyId(int agencyId) {
       this.agencyId = agencyId;
   }
   public IwmpBlock getIwmpBlock() {
       return this.iwmpBlock;
   }
   
   public void setIwmpBlock(IwmpBlock iwmpBlock) {
       this.iwmpBlock = iwmpBlock;
   }
   public IwmpDistrict getIwmpDistrict() {
       return this.iwmpDistrict;
   }
   
   public void setIwmpDistrict(IwmpDistrict iwmpDistrict) {
       this.iwmpDistrict = iwmpDistrict;
   }
   public IwmpGramPanchayat getIwmpGramPanchayat() {
       return this.iwmpGramPanchayat;
   }
   
   public void setIwmpGramPanchayat(IwmpGramPanchayat iwmpGramPanchayat) {
       this.iwmpGramPanchayat = iwmpGramPanchayat;
   }
   public IwmpState getIwmpState() {
       return this.iwmpState;
   }
   
   public void setIwmpState(IwmpState iwmpState) {
       this.iwmpState = iwmpState;
   }
   public IwmpVillage getIwmpVillage() {
       return this.iwmpVillage;
   }
   
   public void setIwmpVillage(IwmpVillage iwmpVillage) {
       this.iwmpVillage = iwmpVillage;
   }
   public String getAgencyUniqueCode() {
       return this.agencyUniqueCode;
   }
   
   public void setAgencyUniqueCode(String agencyUniqueCode) {
       this.agencyUniqueCode = agencyUniqueCode;
   }
   public String getAgencyName() {
       return this.agencyName;
   }
   
   public void setAgencyName(String agencyName) {
       this.agencyName = agencyName;
   }
   public Integer getDistrictLgdCode() {
       return this.districtLgdCode;
   }
   
   public void setDistrictLgdCode(Integer districtLgdCode) {
       this.districtLgdCode = districtLgdCode;
   }
   public Integer getBlockLgdCode() {
       return this.blockLgdCode;
   }
   
   public void setBlockLgdCode(Integer blockLgdCode) {
       this.blockLgdCode = blockLgdCode;
   }
   public Integer getPanchayatLgdCode() {
       return this.panchayatLgdCode;
   }
   
   public void setPanchayatLgdCode(Integer panchayatLgdCode) {
       this.panchayatLgdCode = panchayatLgdCode;
   }
   public Integer getVillageCode() {
       return this.villageCode;
   }
   
   public void setVillageCode(Integer villageCode) {
       this.villageCode = villageCode;
   }
   public Integer getTehsilLgdCode() {
       return this.tehsilLgdCode;
   }
   
   public void setTehsilLgdCode(Integer tehsilLgdCode) {
       this.tehsilLgdCode = tehsilLgdCode;
   }
   public Integer getTownLgdCode() {
       return this.townLgdCode;
   }
   
   public void setTownLgdCode(Integer townLgdCode) {
       this.townLgdCode = townLgdCode;
   }
   public Integer getWardLgdCode() {
       return this.wardLgdCode;
   }
   
   public void setWardLgdCode(Integer wardLgdCode) {
       this.wardLgdCode = wardLgdCode;
   }
   public String getCityName() {
       return this.cityName;
   }
   
   public void setCityName(String cityName) {
       this.cityName = cityName;
   }
   public Date getDate() {
       return this.date;
   }
   
   public void setDate(Date date) {
       this.date = date;
   }
   public Integer getSchemeCode() {
       return this.schemeCode;
   }
   
   public void setSchemeCode(Integer schemeCode) {
       this.schemeCode = schemeCode;
   }
   public String getComponentCode() {
       return this.componentCode;
   }
   
   public void setComponentCode(String componentCode) {
       this.componentCode = componentCode;
   }
   public String getComponentName() {
       return this.componentName;
   }
   
   public void setComponentName(String componentName) {
       this.componentName = componentName;
   }
   public BigDecimal getTotalAmount() {
       return this.totalAmount;
   }
   
   public void setTotalAmount(BigDecimal totalAmount) {
       this.totalAmount = totalAmount;
   }
   public String getVouchernumber() {
       return this.vouchernumber;
   }
   
   public void setVouchernumber(String vouchernumber) {
       this.vouchernumber = vouchernumber;
   }
   public String getProjectname() {
       return this.projectname;
   }
   
   public void setProjectname(String projectname) {
       this.projectname = projectname;
   }
   public String getApiresponsestatus() {
       return this.apiresponsestatus;
   }
   
   public void setApiresponsestatus(String apiresponsestatus) {
       this.apiresponsestatus = apiresponsestatus;
   }
   public Date getCreatedon() {
       return this.createdon;
   }
   
   public void setCreatedon(Date createdon) {
       this.createdon = createdon;
   }




}

