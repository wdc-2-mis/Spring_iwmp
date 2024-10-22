package app.dao;

import java.util.LinkedHashMap;
import java.util.List;

import app.model.master.IwmpBlock;

public interface BlockMasterDao {
	LinkedHashMap<Integer, String> getBlockByDistCode(Integer stateCode,Integer districtCode);
	LinkedHashMap<Integer, String> getBlockByDistCode(Integer districtCode);
	LinkedHashMap<String,Integer> getBlockWithBlockLgdByDistCode(Integer districtCode);
	List<IwmpBlock> getInactiveBlockList(int stateCode,int districtCode,int blockCode);
	List<IwmpBlock> getActiveBlockList(int stateCode,int districtCode,int blockCode);
	void updateBlockList(List<IwmpBlock> blockList);
	Boolean updateBlock(IwmpBlock blockList);
	IwmpBlock findBlockBcode(int blockCode);
	
	List<IwmpBlock> getActiveBlockList();
}
