package app.service.reports;

import java.util.List;

import app.bean.reports.PMKSYBean;

public interface PMKSYService {
	
	public List<PMKSYBean> getBenifitedFarmersNo();
	public List<PMKSYBean> getWtrHrvstngStrctrNo();
	public List<PMKSYBean> getDistWiseBenifitedFarmersNo(int stcode);
	public List<PMKSYBean> getProjWiseBenifitedFarmersNo(int dcode);
	public List<PMKSYBean> getStWiseWtrHrvstngIrriDetail();
	public List<PMKSYBean> getDistWiseWtrHrvstngIrriDetail(int stcode);
	public List<PMKSYBean> getProjWiseWtrHrvstngIrriDetail(int dcode);
}
