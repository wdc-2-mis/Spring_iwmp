package app.bean;

public class AddVillGrmPnchytBlkBean {
	String villagename;
	String blockname;
	int blocklgdcode;
	String grampanchayatname;
	int grampanchayatlgdcode;
	String districtname;
	int districtlgdcode;
	String statename;
	int statelgdcode;
	boolean status;
	
	
	public AddVillGrmPnchytBlkBean() {
	}

	public AddVillGrmPnchytBlkBean(String villagename, String blockname,int blocklgdcode, String grampanchayatname,int grampanchayatlgdcode, String districtname,
			int districtlgdcode, String statename, int statelgdcode, boolean status) {
		super();
		this.villagename = villagename;
		this.blockname = blockname;
		this.blocklgdcode = blocklgdcode;
		this.grampanchayatname = grampanchayatname;
		this.grampanchayatlgdcode= grampanchayatlgdcode;
		this.districtname = districtname;
		this.districtlgdcode = districtlgdcode;
		this.statename = statename;
		this.statelgdcode = statelgdcode;
		this.status = status;
	}

	public String getVillagename() {
		return villagename;
	}

	public void setVillagename(String villagename) {
		this.villagename = villagename;
	}

	public String getBlockname() {
		return blockname;
	}

	public void setBlockname(String blockname) {
		this.blockname = blockname;
	}

	public int getBlocklgdcode() {
		return blocklgdcode;
	}

	public void setBlocklgdcode(int blocklgdcode) {
		this.blocklgdcode = blocklgdcode;
	}

	public String getGrampanchayatname() {
		return grampanchayatname;
	}

	public void setGrampanchayatname(String grampanchayatname) {
		this.grampanchayatname = grampanchayatname;
	}

	public int getGrampanchayatlgdcode() {
		return grampanchayatlgdcode;
	}

	public void setGrampanchayatlgdcode(int grampanchayatlgdcode) {
		this.grampanchayatlgdcode = grampanchayatlgdcode;
	}

	public String getDistrictname() {
		return districtname;
	}

	public void setDistrictname(String districtname) {
		this.districtname = districtname;
	}

	public int getDistrictlgdcode() {
		return districtlgdcode;
	}

	public void setDistrictlgdcode(int districtlgdcode) {
		this.districtlgdcode = districtlgdcode;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public int getStatelgdcode() {
		return statelgdcode;
	}

	public void setStatelgdcode(int statelgdcode) {
		this.statelgdcode = statelgdcode;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
}
