package app.serviceImpl;


import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.dao.IndexCircleDataDao;
import app.service.IndexCircleDataService;

@Service("indexCircleDataService")
public class IndexCircleDataServiceImpl implements IndexCircleDataService {
	
	@Autowired
	private IndexCircleDataDao dao ;

	@Override
	//@Transactional
	public List<String> getCircleData() {
		return dao.getCircleData();
	}
}
