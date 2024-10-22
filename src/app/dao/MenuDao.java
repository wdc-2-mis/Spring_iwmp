package app.dao;

import java.util.LinkedHashMap;
import java.util.List;

import app.model.IwmpAppRoleMap;
import app.bean.IwmpMenu;
import app.bean.IwmpRoleMenuMap;
import app.bean.MenuMap;
import app.bean.RoleMenuList;
import app.bean.menu.IwmpMMenu;



public interface  MenuDao {
	    //public void addMenu(IwmpMSubmenu menu);
	    public void addMenu(IwmpMenu menu);
	 
	    public List<IwmpMenu> getAllMenu();
	    
	    public List<IwmpRoleMenuMap> getAllMenuWithRole();
	    
	    public List<IwmpRoleMenuMap> getAllMenuRoleParent();
	    
	    public List<IwmpMMenu> getParentMenu();
	    
	    
	    public String deleteMenu(int menuId);
	    
	    public String deleteSubMenu(int menuId);
	 
	    public IwmpMenu updateMenu(IwmpMenu menu);
	 
	    public IwmpMenu getMenu(int menuId);
	    
	    public IwmpMMenu getParentMenu(int pmenuId);

	    public List<IwmpAppRoleMap> getAllRole();
	    
	    public int [] getMapRoleMenu(int menuId);
	    
	    LinkedHashMap<String,List<MenuMap>> getMenuUserId(String userid);
	    
	    LinkedHashMap<String,List<RoleMenuList>> getMenuAll();
	    
	    LinkedHashMap<String,List<RoleMenuList>> getMenuAllRole(String roleId);
	    
	    public  List<IwmpRoleMenuMap> getAllMenuRoleInfo(String roleId);
	    
	    public  List<IwmpRoleMenuMap> getAllMenuRoleInfo();
}
