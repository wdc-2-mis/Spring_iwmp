package app.serviceImpl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.ConvergenceStatusBean;
import app.bean.ConvergenceWorksBean;
import app.dao.ConvergenceWorksDao;
import app.model.ConvergenceWorkDetail;
import app.service.ConvergenceWorksService;

@Service("ConvergenceWorksService")
public class ConvergenceWorksServiceImpl implements ConvergenceWorksService{
	
	@Autowired
	ConvergenceWorksDao convergenceWorksDao;
	
	@Override
	public LinkedHashMap<Integer, String> getAllSchemes() {
		return convergenceWorksDao.getAllSchemes();
	}
	
	@Override
	public List<ConvergenceWorksBean> getConvergenceWorks(Integer stcd, Integer dcode, Integer projid) {
		return convergenceWorksDao.getConvergenceWorks(stcd, dcode, projid);
	}

	@Override
	public List<ConvergenceWorksBean> getListConvergenceWorkd(Integer projid) {
		return convergenceWorksDao.getListConvergenceWorkd(projid);
	}

	@Override
	public Map<Integer, String> getConvergenceSchemeDetails() {
		return convergenceWorksDao.getConvergenceSchemeDetails();
	}

	@Override
	public String saveasdraftcnvrgdtl(List<String> workcode, List<String> cnvrgstatus, List<String> cvrgschmeid,
			List<String> expndwdc, List<String> expndcnvrgschm) {
		return convergenceWorksDao.saveasdraftcnvrgdtl(workcode, cnvrgstatus, cvrgschmeid, expndwdc, expndcnvrgschm);
	}

	@Override
	public List<ConvergenceWorkDetail> getConvergenceWorkDetail() {
		return convergenceWorksDao.getConvergenceWorkDetail();
	}

	@Override
	public String deleteCnvrgnceWorkCode(Integer workcode) {
		return convergenceWorksDao.deleteCnvrgnceWorkCode(workcode);
	}

	@Override
	public String completeConvergenceWorkCode(String[] cnvrgnceId) {
		return convergenceWorksDao.completeConvergenceWorkCode(cnvrgnceId);
	}

	@Override
	public List<ConvergenceWorksBean> getConvergedWorks() {
		return convergenceWorksDao.getConvergedWorks();
	}
	
	@Override
	public List<ConvergenceWorksBean> getConvergedDistWorks(Integer stcd) {
		return convergenceWorksDao.getConvergedDistWorks(stcd);
	}

	@Override
	public List<ConvergenceWorksBean> getConvergedProjWorks(Integer dcode) {
		return convergenceWorksDao.getConvergedProjWorks(dcode);
	}
	
	@Override
	public List<ConvergenceWorksBean> getConvergedProjDetailWorks(Integer projid) {
		return convergenceWorksDao.getConvergedProjDetailWorks(projid);
	}

	@Override
	public List<ConvergenceWorksBean> getConvergedExpndtr(Integer stcd, Integer dcode, Integer projid , Integer finyr, Integer sid) {
		return convergenceWorksDao.getConvergedExpndtr(stcd, dcode, projid, finyr, sid);
	}

	@Override
	public List<ConvergenceWorksBean> getEnteredConWorks() {
		return convergenceWorksDao.getEnteredConWorks();
	}

	@Override
	public List<ConvergenceWorksBean> getConvergedStateExpndtr() {
		return convergenceWorksDao.getConvergedStateExpndtr();
	}

	@Override
	public List<ConvergenceWorksBean> getStatusConWorks(Integer stcd) {
		return convergenceWorksDao.getStatusConWorks(stcd);
	}

	@Override
	public List<ConvergenceWorksBean> getRemainingConWorks(Integer dcode) {
		return convergenceWorksDao.getRemainingConWorks(dcode);
	}

	@Override
	public List<ConvergenceStatusBean> getConvergenceStatus(Integer projId, Integer head, Integer activity) {
		// TODO Auto-generated method stub
		return convergenceWorksDao.getConvergenceStatus(projId, head, activity);
	}

	@Override
	public String updateConvergenceStatus(List<String> workcode, List<String> cnvrgstatus) {
		// TODO Auto-generated method stub
		return convergenceWorksDao.updateConvergenceStatus(workcode, cnvrgstatus);
	}

}
