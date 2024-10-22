package app.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.model.GetGoiReleaseToStateTreasury;
import app.model.PfmsCgireceiptDetaildata;
import app.model.PfmsTreasuryreceiptDetaildata;
import app.PfmsTreasureBean;
import app.dao.PfmsCgiReportDao;
import app.service.PfmsCgiDataService;

@Service
public class PfmsCgiServiceImpl implements PfmsCgiDataService{
	@Autowired
	private PfmsCgiReportDao Cgidata;
	
	@Override
	public List<PfmsCgireceiptDetaildata> getPfmscgidetaildata() {
		return Cgidata.Pfmscgidetaildata();
	}

	@Override
	public List<PfmsTreasureBean> getPfmstrereciptdata() {
		return Cgidata.Pfmstrereciptdata();
	}

	@Override
	public List<PfmsTreasuryreceiptDetaildata> getexpandtreasure(int statecode) {
		return Cgidata.expandtreasure(statecode);
	}

	@Override
	public List<PfmsTreasureBean> gettotalPfmstrereciptdata(int statecode) {
		return Cgidata.totalPfmstrereciptdata(statecode);
	}

	@Override
	public List<GetGoiReleaseToStateTreasury> getGoiReleaseToStateTreasury(int finyr) {
		return Cgidata.getGoiReleaseToStateTreasury(finyr);
	}
	
}
