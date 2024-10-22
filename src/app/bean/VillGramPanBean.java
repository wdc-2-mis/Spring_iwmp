package app.bean;

import java.math.BigDecimal;

public class VillGramPanBean {
	
	private int vcode;
	private String villagename;
	private String grampanchayatname;
	private int bls_out_main_id_pk;
	private String plot_no;
	private BigDecimal area;
	private String description;
	private BigDecimal micro_irrigation;
	private Character micro_status;
	public int getVcode() {
		return vcode;
	}
	public void setVcode(int vcode) {
		this.vcode = vcode;
	}
	public String getVillagename() {
		return villagename;
	}
	public void setVillagename(String villagename) {
		this.villagename = villagename;
	}
	public String getGrampanchayatname() {
		return grampanchayatname;
	}
	public void setGrampanchayatname(String grampanchayatname) {
		this.grampanchayatname = grampanchayatname;
	}
	
	
	public int getBls_out_main_id_pk() {
		return bls_out_main_id_pk;
	}
	public void setBls_out_main_id_pk(int bls_out_main_id_pk) {
		this.bls_out_main_id_pk = bls_out_main_id_pk;
	}
	public String getPlot_no() {
		return plot_no;
	}
	public void setPlot_no(String plot_no) {
		this.plot_no = plot_no;
	}
	
	
	public BigDecimal getArea() {
		return area;
	}
	public void setArea(BigDecimal area) {
		this.area = area;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "VillGramPanBean [vcode=" + vcode + ",micro_status=" + micro_status + ", villagename=" + villagename + ", grampanchayatname="
				+ grampanchayatname + "]";
	}
	
	public BigDecimal getMicro_irrigation() {
		return micro_irrigation;
	}
	public void setMicro_irrigation(BigDecimal micro_irrigation) {
		this.micro_irrigation = micro_irrigation;
	}
	public Character getMicro_status() {
		return micro_status;
	}
	public void setMicro_status(Character micro_status) {
		this.micro_status = micro_status;
	}
	

}
