package app.dao;

import java.util.LinkedHashMap;
import java.util.List;

import app.bean.DolrSupportBean;

public interface DolrSupportDao {

	LinkedHashMap<Integer, List<DolrSupportBean>> getsupportdata();

}
