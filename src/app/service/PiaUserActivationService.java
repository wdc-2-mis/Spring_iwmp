package app.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import app.model.UserReg;

@Service("piaUserActivationService")
public interface PiaUserActivationService {
	LinkedHashMap<Integer,List<UserReg>> getUserList(UserReg userReg,String stateCode);
}
