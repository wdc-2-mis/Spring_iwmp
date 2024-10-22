package app.service.reports;

import java.util.List;

import app.bean.reports.IncrmntOfFrmrIncmBean;

public interface IncrmntOfFrmrIncmService {

	public List<IncrmntOfFrmrIncmBean> getStWiseFrmrIncmDetail(int stcode, int finyrcd);
	public List<IncrmntOfFrmrIncmBean> getStWiseAreaPercentage();
	public List<IncrmntOfFrmrIncmBean> getTarIncmAreaDetails(int finyrcd);
	public List<IncrmntOfFrmrIncmBean> getDistWiseFrmrIncmDetail(int stcode, int finyrcd);
	public List<IncrmntOfFrmrIncmBean> getProjWiseFrmrIncmDetail(int dcode, int finyrcd);
}
