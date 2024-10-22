package app.serviceImpl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.model.IwmpAppRoleMap;
import app.bean.IwmpMenu;
import app.bean.IwmpRoleMenuMap;
import app.bean.MenuMap;
import app.bean.RoleMenuList;
import app.bean.menu.IwmpMMenu;
import app.dao.MenuDao;
import app.service.MenuService;

@Service
//@Transactional
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDao menuDAO;

	@Override
	// @Transactional(rollbackFor = {RuntimeException.class})
	public void addMenu(IwmpMenu menu) {
		// TODO Auto-generated method stub
		menuDAO.addMenu(menu);
	}

	@Override
	public List<IwmpMenu> getAllMenu() {
		// TODO Auto-generated method stub
		return menuDAO.getAllMenu();
	}

	@Override
	// @Transactional(rollbackFor = {RuntimeException.class})
	public IwmpMenu updateEmployee(IwmpMenu menu) {
		// TODO Auto-generated method stub
		return menuDAO.updateMenu(menu);
	}

	@Override
	// @Transactional(rollbackFor = {RuntimeException.class})
	public String deleteMenu(int menuId) {
		// TODO Auto-generated method stub
		return menuDAO.deleteMenu(menuId);
	}

	@Override
	// @Transactional(rollbackFor = {RuntimeException.class})
	public String deleteSubMenu(int menuId) {
		// TODO Auto-generated method stub
		return menuDAO.deleteSubMenu(menuId);
	}

	@Override
	public IwmpMenu getMenu(int menuId) {
		// TODO Auto-generated method stub
		return menuDAO.getMenu(menuId);
	}

	@Override
	public IwmpMMenu getParentMenu(int menuId) {
		// TODO Auto-generated method stub
		return menuDAO.getParentMenu(menuId);
	}

	@Override
	public List<IwmpAppRoleMap> getAllRole() {
		// TODO Auto-generated method stub
		return menuDAO.getAllRole();
	}

	@Override
	public int[] mapRoleMenu(int menuId) {
		// TODO Auto-generated method stub
		return menuDAO.getMapRoleMenu(menuId);
	}

	@Override
	public List<IwmpMMenu> getParentMenu() {
		// TODO Auto-generated method stub
		return menuDAO.getParentMenu();
	}

	@Override
	public List<IwmpRoleMenuMap> getAllMenuRole() {
		// TODO Auto-generated method stub
		return menuDAO.getAllMenuWithRole();
	}

	@Override
	public LinkedHashMap<String, List<MenuMap>> getMenuUserId(String userid) {
		// TODO Auto-generated method stub
		return menuDAO.getMenuUserId(userid);
	}

	@Override
	public List<IwmpRoleMenuMap> getAllMenuRoleParent() {
		// TODO Auto-generated method stub
		return menuDAO.getAllMenuRoleParent();
	}

	@Override
	public LinkedHashMap<String, List<RoleMenuList>> getMenuAll() {
		// TODO Auto-generated method stub
		return menuDAO.getMenuAll();
	}

	@Override
	public LinkedHashMap<String, List<RoleMenuList>> getMenuAllRole(String roleId) {
		// TODO Auto-generated method stub
		return menuDAO.getMenuAllRole(roleId);
	}

	@Override
	public List<IwmpRoleMenuMap> getAllMenuWithRole(String roleId) {
		// TODO Auto-generated method stub
		return menuDAO.getAllMenuRoleInfo(roleId);
	}

	@Override
	public List<IwmpRoleMenuMap> getAllMenuWithRole() {
		// TODO Auto-generated method stub
		return menuDAO.getAllMenuRoleInfo();
	}

}
