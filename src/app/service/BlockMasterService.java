package app.service;


import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import app.model.master.IwmpBlock;
import app.model.master.IwmpGramPanchayat;


@Service("blockMasterService")
public interface BlockMasterService {
	LinkedHashMap<Integer,String> getBlockByDistCode(Integer stateCode,Integer districtCode);
	LinkedHashMap<Integer,String> getBlockByDistCode(Integer distCode);
	LinkedHashMap<String,Integer> getBlockWithBlockLgdByDistCode(Integer districtCode);
	List<IwmpBlock> getInactiveBlockList(int stateCode,int districtCode,int blockCode);
	List<IwmpBlock> getActiveBlockList(int stateCode,int districtCode,int blockCode);
	IwmpBlock findBlockBcode(int blockCode);
	Boolean updateBlock(IwmpBlock blockList);
	void updateBlockList(List<IwmpBlock> blockList);
}
