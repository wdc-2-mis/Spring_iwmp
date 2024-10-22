package app.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.OOMFAchvDetailsBean;
import app.dao.OOMFAchvDetailsDao;
import app.service.OOMFAchvDetailsService;

@Service("OOMFAchvDetailsService")
public class OOMFAchvDetailsServiceImpl implements OOMFAchvDetailsService{
	
	@Autowired
	OOMFAchvDetailsDao oomfAchvDao;

	@Override
	public List<OOMFAchvDetailsBean> getOOMFAchvDetails(Integer finyr) {
		return oomfAchvDao.getOOMFAchvDetails(finyr);
	}
	
	
}
