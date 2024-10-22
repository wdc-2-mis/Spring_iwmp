package app.service;

import java.util.List;

import app.PfmsTreasureBean;
import app.model.GetGoiReleaseToStateTreasury;
import app.model.PfmsCgireceiptDetaildata;
import app.model.PfmsTreasuryreceiptDetaildata;


public interface PfmsCgiDataService {
   public List<PfmsCgireceiptDetaildata> getPfmscgidetaildata();
   public List<PfmsTreasureBean> getPfmstrereciptdata();
   public List<PfmsTreasuryreceiptDetaildata> getexpandtreasure(int statecode);
   public List<PfmsTreasureBean> gettotalPfmstrereciptdata(int statecode);
   public List<GetGoiReleaseToStateTreasury> getGoiReleaseToStateTreasury(int finyr);
  
}
