package app.service;

import org.springframework.stereotype.Service;

@Service("changePasswordService")
public interface ChangePasswordService {
boolean saveSecondPassword(Integer userRole,Integer stCode,Integer distCode,String project,String password);
}
