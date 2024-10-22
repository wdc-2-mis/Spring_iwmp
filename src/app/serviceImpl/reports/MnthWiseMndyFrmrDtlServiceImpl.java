package app.serviceImpl.reports;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.AddOutcomeParaBean;
import app.dao.reports.MnthWiseMndyFrmrDtlDao;
import app.service.reports.MnthWiseMndyFrmrDtlService;


@Service
public class MnthWiseMndyFrmrDtlServiceImpl implements MnthWiseMndyFrmrDtlService{
	
	@Autowired
	MnthWiseMndyFrmrDtlDao mnthWiseMndyFrmrDtlDao;

	@Override
	public List<AddOutcomeParaBean> getMnthWiseMndyFrmrDtlList(int stcode, int dcode, int projid, int yrcd) {
		// TODO Auto-generated method stub
		return mnthWiseMndyFrmrDtlDao.getMnthWiseMndyFrmrDtlList(stcode, dcode, projid, yrcd);
		
	}

}
