package app.serviceImpl.reports;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bean.ShgDetailBean;
import app.bean.reports.StateWiseCurrentStatusBean;
import app.dao.reports.StateWiseCurrentStatusDao;
import app.service.reports.StateWiseCurrentStatusService;

@Service("StateWiseCurrentStatusService")
public class StateWiseCurrentStatusServiceImpl implements StateWiseCurrentStatusService{

	@Autowired(required = true)
	StateWiseCurrentStatusDao dao;
	
	@Override
	public LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>> getStateWiseCurrentStatus() {
		// TODO Auto-generated method stub
		return dao.getStateWiseCurrentStatus();
	}

	@Override
	public LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>> getStateWiseCurrentStatusOther() {
		// TODO Auto-generated method stub
		return dao.getStateWiseCurrentStatusOther();
	}

	@Override
	public LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>> getStateWiseStatusOtherActivity() {
		// TODO Auto-generated method stub
		return dao.getStateWiseStatusOtherActivity();
	}

	@Override
	public LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>> getstateWiseStatusBaselNetTreatArea() {
		// TODO Auto-generated method stub
		return dao.getstateWiseStatusBaselNetTreatArea();
	}

	@Override
	public LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>> getdistWiseStatusBaselNetTreatArea(int id) {
		// TODO Auto-generated method stub
		return dao.getdistWiseStatusBaselNetTreatArea(id);
	}
	@Override
	public LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>> getDistrictWiseCurrentStatusOther(int id) {
		// TODO Auto-generated method stub
		return dao.getDistrictWiseCurrentStatusOther(id);
	}

	@Override
	public LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>> getdistrictWiseStatusBaselNetTreatArea(int id) {
		// TODO Auto-generated method stub
		return dao.getdistrictWiseStatusBaselNetTreatArea(id);
	}

	@Override
	public LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>> getdistrictWiseStatusOtherActivity(int id) {
		// TODO Auto-generated method stub
		return dao.getdistrictWiseStatusOtherActivity(id);
	}

	@Override
	public LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>> getStateWiseCurrentStatusPlanAchieve() {
		// TODO Auto-generated method stub
		return dao.getStateWiseCurrentStatusPlanAchieve();
	}

	@Override
	public LinkedHashMap<Integer, List<StateWiseCurrentStatusBean>> getdistrictWiseCurrentStatusPlanAchieve(int id) {
		// TODO Auto-generated method stub
		return dao.getdistrictWiseCurrentStatusPlanAchieve(id);
	}

	@Override
	public List<StateWiseCurrentStatusBean> projWiseStatusBaselNetTreatArea(int dcode) {
		// TODO Auto-generated method stub
		return dao.projWiseStatusBaselNetTreatArea(dcode);
	}

	@Override
	public List<StateWiseCurrentStatusBean> projWiseCurrentStatusOther(int dcode) {
		// TODO Auto-generated method stub
		return dao.projWiseCurrentStatusOther(dcode);
	}

	@Override
	public List<StateWiseCurrentStatusBean> projWiseStatusBaselArea(int dcode) {
		// TODO Auto-generated method stub
		return dao.projWiseStatusBaselArea(dcode);
	}
	
	public List<StateWiseCurrentStatusBean> projWiseStatusofOtherActivity(int dcode) {
		// TODO Auto-generated method stub
		return dao.projWiseStatusofOtherActivity(dcode);
	}

	@Override
	public List<StateWiseCurrentStatusBean> getprojWiseCurrentStatusPlanAchieve(int dcode) {
		// TODO Auto-generated method stub
		return dao.getprojWiseCurrentStatusPlanAchieve(dcode);
	}
}
