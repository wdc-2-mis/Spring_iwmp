package app.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import app.bean.DolrSupportBean;


public interface DolrSupportService {

	LinkedHashMap<Integer,List<DolrSupportBean>> getsupportdata();

}
