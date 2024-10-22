package app.service;

import java.util.LinkedHashMap;
import java.util.List;

import app.model.IwmpAppRoleMap;
import app.bean.IwmpMenu;
import app.bean.IwmpRoleMenuMap;
import app.bean.MenuMap;
import app.bean.RoleMenuList;
import app.bean.menu.IwmpMMenu;

public interface MenuService {

	public void addMenu(IwmpMenu menu);
	 
    public List<IwmpMenu> getAllMenu();
    
    public List<IwmpRoleMenuMap> getAllMenuRole();
    
    public List<IwmpRoleMenuMap> getAllMenuRoleParent();
    
    public List<IwmpMMenu> getParentMenu();
    
    public List<IwmpAppRoleMap> getAllRole();
 
    public String deleteMenu(int menuId);
    
    public String deleteSubMenu(int menuId);
 
    public IwmpMenu getMenu(int menuId);
    
    public IwmpMMenu getParentMenu(int menuId);
    
    LinkedHashMap<String,List<MenuMap>> getMenuUserId(String userid);
    
    LinkedHashMap<String,List<RoleMenuList>> getMenuAll();
 
    LinkedHashMap<String,List<RoleMenuList>> getMenuAllRole(String roleId);
    
    public  List<IwmpRoleMenuMap> getAllMenuWithRole(String roleId);
    
    
    public  List<IwmpRoleMenuMap> getAllMenuWithRole();
    
    public IwmpMenu updateEmployee(IwmpMenu menu);
    
    public int [] mapRoleMenu(int menuId);
}
