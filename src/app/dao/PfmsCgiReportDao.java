package app.dao;

import java.util.List;

import app.PfmsTreasureBean;
import app.model.GetGoiReleaseToStateTreasury;
import app.model.PfmsCgireceiptDetaildata;
import app.model.PfmsTreasuryreceiptDetaildata;

public interface PfmsCgiReportDao {
public List<PfmsCgireceiptDetaildata> Pfmscgidetaildata();
public List<PfmsTreasureBean> Pfmstrereciptdata();
public List<PfmsTreasuryreceiptDetaildata> expandtreasure(int statecode);
public List<PfmsTreasureBean> totalPfmstrereciptdata(int statecode);
public List<GetGoiReleaseToStateTreasury> getGoiReleaseToStateTreasury(int finyr);

}
