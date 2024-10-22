package app.serviceImpl;


import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import app.dao.BlockMasterDao;
import app.model.master.IwmpBlock;
import app.service.BlockMasterService;

@Service("blockMasterService")
public class BlockMasterServiceImpl implements BlockMasterService{

	@Autowired
	BlockMasterDao blockMasterDao;
	
	@Override
	public LinkedHashMap<Integer, String> getBlockByDistCode(Integer stateCode,Integer districtCode) {
		// TODO Auto-generated method stub
		return blockMasterDao.getBlockByDistCode(stateCode,districtCode);
	}

	@Override
	public LinkedHashMap<Integer, String> getBlockByDistCode(Integer districtCode) {
		// TODO Auto-generated method stub
		return blockMasterDao.getBlockByDistCode(districtCode);
	}

	@Override
	public List<IwmpBlock> getInactiveBlockList(int stateCode, int districtCode, int blockCode) {
		// TODO Auto-generated method stub
		return blockMasterDao.getInactiveBlockList(stateCode, districtCode, blockCode);
	}

	@Override
	public void updateBlockList(List<IwmpBlock> blockList) {
		// TODO Auto-generated method stub
		blockMasterDao.updateBlockList(blockList);
		
	}

	@Override
	public List<IwmpBlock> getActiveBlockList(int stateCode, int districtCode, int blockCode) {
		// TODO Auto-generated method stub
		return blockMasterDao.getActiveBlockList(stateCode, districtCode, blockCode);
	}

	@Override
	public IwmpBlock findBlockBcode(int blockCode) {
		// TODO Auto-generated method stub
		return blockMasterDao.findBlockBcode(blockCode);
	}

	@Override
	public Boolean updateBlock(IwmpBlock block) {
		// TODO Auto-generated method stub
		return blockMasterDao.updateBlock(block);
	}

	@Override
	public LinkedHashMap<String,Integer> getBlockWithBlockLgdByDistCode(Integer districtCode) {
		return blockMasterDao.getBlockWithBlockLgdByDistCode(districtCode);
	}

}
