package app.service.unfreeze;

import java.util.List;

import org.springframework.stereotype.Service;

import app.bean.ShgDetailBean;

@Service("UnfreezeShgDetailService")
public interface UnfreezeShgDetailService {

	public List<ShgDetailBean> unfreezeListShgDetails(Integer project, String grp, String headType) ;

	public boolean unfreezeShgDetailsData(String[] shg_id, String headType);
		
	}

