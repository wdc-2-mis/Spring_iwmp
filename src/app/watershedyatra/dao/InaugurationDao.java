package app.watershedyatra.dao;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import app.watershedyatra.bean.InaugurationBean;

public interface InaugurationDao {
	
	public String saveInauguration(InaugurationBean userfileup, HttpSession session);
	
}
