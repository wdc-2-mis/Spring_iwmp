package app.dao;

public interface ChangePasswordDao {
boolean saveSecondPassword(Integer userRole,Integer stCode,Integer distCode,String project,String password);
}
