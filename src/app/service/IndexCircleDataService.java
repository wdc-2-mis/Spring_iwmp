package app.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service("indexCircleDataService")
public interface IndexCircleDataService {
	List<String> getCircleData();
}
