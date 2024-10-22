package app.serviceImpl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.DolrSupportBean;
import app.dao.DolrSupportDao;
import app.service.DolrSupportService;

@Service("dolrsupportservice")
public class DolrSupportServiceImpl implements DolrSupportService{

	@Autowired
	DolrSupportDao dolrsupportdao;
	
	@Override
	public LinkedHashMap<Integer, List<DolrSupportBean>> getsupportdata() {
	
		return dolrsupportdao.getsupportdata();
	}

}
