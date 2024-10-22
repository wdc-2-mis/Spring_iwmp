package app.daoImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;


import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import app.model.IwmpAppRoleMap;

import app.bean.IwmpMenu;
import app.bean.IwmpRoleMenuMap;
import app.bean.MenuMap;
import app.bean.RoleMenuList;
import app.bean.menu.IwmpMMenu;
import app.dao.MenuDao;

//@Repository
@Repository("MenuDao")
public class MenuDaoImpl implements MenuDao {

	 @Autowired
	SessionFactory sessionFactory;
	@Value("${getMenu}")
	String getMenu;
	@Value("${getAllMenu}")
	String getAllMenu;
	@Value("${getAllMenuRole}")
	String getAllMenuRole;

	@Override
//	@Transactional(rollbackFor = {RuntimeException.class})
	public void addMenu(IwmpMenu menu) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			if (menu.getIsParent() == 1) {
				IwmpMMenu mmenu = new IwmpMMenu();
				mmenu.setMenuName(menu.getSubmenuName());
				mmenu.setHseqNo(menu.getSeqNo());
				mmenu.setIsactive(menu.getIsactive());
				session.saveOrUpdate(mmenu);
			} else {
				menu.setIsactive(true);
				session.saveOrUpdate(menu);
				System.out.print("menu");
				
				int[] temp = menu.getMapRoleId();
				// if (menu.getIwmpMMenu()==null) {
				for (int j = 0; j < temp.length; j++) {
					System.out.print("role");
					IwmpRoleMenuMap mrole = new IwmpRoleMenuMap();
					IwmpAppRoleMap rm = new IwmpAppRoleMap();
					rm.setRoleId(temp[j]);
					mrole.setIwmpAppRoleMap(rm);
					mrole.setIwmpMenu(menu);
					session.saveOrUpdate(mrole);
				}
				// }
			}

			session.getTransaction().commit();

		} catch (Exception ex) {
			System.out.print("Err" + ex.getMessage());
			session.getTransaction().rollback();
			ex.printStackTrace();
		} finally {
			// session.getTransaction().commit();

		}

	}

	@SuppressWarnings("unchecked")
	public List<IwmpMenu> getAllMenu() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();

		session.beginTransaction();

		List<IwmpMenu> temp = session.createQuery("from IwmpMenu order by parentId,menuName").list();
		session.getTransaction().commit();

		return temp;
	}

	@SuppressWarnings("unchecked")
	public List<IwmpRoleMenuMap> getAllMenuWithRole() {
		// TODO Auto-generated method stub
		// Transaction transaction = null;
		Session session = sessionFactory.getCurrentSession();

		session.beginTransaction();

		Criteria criteria = session.createCriteria(IwmpRoleMenuMap.class);
		criteria.createAlias("iwmpAppRoleMap", "a");
		criteria.createAlias("iwmpMenu", "b");
		criteria.addOrder(Order.asc("a.roleName"));
		criteria.addOrder(Order.asc("b.menuId"));
		criteria.addOrder(Order.asc("b.parentId"));
		criteria.addOrder(Order.asc("b.seqNo"));
		List<IwmpRoleMenuMap> temp = (List<IwmpRoleMenuMap>) criteria.list();
		session.getTransaction().commit();

		return temp;
	}

	@Override
	public String deleteMenu(int menuId) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			IwmpMMenu menu = (IwmpMMenu) session.load(IwmpMMenu.class, menuId);
			

			if (null != menu) {
				session.delete(menu);
			}
			session.getTransaction().commit();
			return "success";

		} catch (Exception ex) {

			return "fail";
		}

	}
	
	@Override
	public String deleteSubMenu(int menuId) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			session.beginTransaction();
			IwmpMenu menu = (IwmpMenu) session.load(IwmpMenu.class, menuId);

			if (null != menu) {
				session.delete(menu);
			}
			session.getTransaction().commit();
			return "success";

		} catch (Exception ex) {

			return "fail";
		}

	}

	@Override
	public IwmpMenu updateMenu(IwmpMenu menu) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			
			
			if (menu.getIsParent() == 1) {
				IwmpMMenu mmenu = (IwmpMMenu) session.load(IwmpMMenu.class, menu.getMenuId());
				mmenu.setMenuName(menu.getSubmenuName());
				mmenu.setMenuHindiName(menu.getSubmenuHindiName());
				mmenu.setHseqNo(menu.getSeqNo());
				mmenu.setIsactive(menu.getIsactive());
				session.update(mmenu);
			} else {
				menu.setIsactive(menu.getIsactive());
				IwmpMenu p = (IwmpMenu) session.load(IwmpMenu.class, menu.getMenuId());
				p.getIwmpRoleMenuMaps().clear();
				p.setSubmenuName(menu.getSubmenuName());
				IwmpMMenu head = new IwmpMMenu();
				head.setMenuId(menu.getIwmpMMenu().getMenuId());
				p.setIwmpMMenu(head);
				p.setTarget(menu.getTarget());
				p.setSubmenuHindiName(menu.getSubmenuHindiName());
				p.setSeqNo(menu.getSeqNo());
				p.setMapRoleId(menu.getMapRoleId());
				p.setIsactive(menu.getIsactive());
				if (p.getIwmpMMenu().getMenuId() > 0) {
					int[] temp = menu.getMapRoleId();
					for (int j = 0; j < temp.length; j++) {
						System.out.print("role");
						IwmpRoleMenuMap mrole = new IwmpRoleMenuMap();
						IwmpAppRoleMap rm = new IwmpAppRoleMap();
						rm.setRoleId(temp[j]);
						mrole.setIwmpAppRoleMap(rm);
						mrole.setIwmpMenu(menu);
						session.save(mrole);
					}
				}
				session.saveOrUpdate(p);
				// }
			}

			
			

			session.getTransaction().commit();
		} catch (Exception ex) {
			session.getTransaction().rollback();
			System.out.print(ex.getMessage());
		}

		return menu;
	}

	@Override
	public IwmpMenu getMenu(int menuId) {
		// TODO Auto-generated method stub
		IwmpMenu temp = null;
		Session session = sessionFactory.getCurrentSession();
		try {
			if (session.getTransaction().isActive())
				session.getTransaction().commit();
			session.beginTransaction();
			temp = (IwmpMenu) session.get(IwmpMenu.class, menuId);
			session.getTransaction().commit();
		} catch (Exception ex) {
			session.getTransaction().rollback();
			System.out.println("get getParentMenu by user id error : " + ex.getMessage());
		} finally {
			// session.getTransaction().commit();
		}
		return temp;
	}
	
	@Override
	public IwmpMMenu getParentMenu(int menuId) {
		// TODO Auto-generated method stub
		IwmpMMenu temp = null;
		Session session = sessionFactory.getCurrentSession();
		try {
			if (session.getTransaction().isActive())
				session.getTransaction().commit();
			session.beginTransaction();
			temp = (IwmpMMenu) session.get(IwmpMMenu.class, menuId);
			session.getTransaction().commit();
		} catch (Exception ex) {
			session.getTransaction().rollback();
			System.out.println("get getParentMenu by user id error : " + ex.getMessage());
		} finally {
			// session.getTransaction().commit();
		}
		return temp;
	}

	@Override
	public List<IwmpAppRoleMap> getAllRole() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		List<IwmpAppRoleMap> map = session.createQuery("from IwmpAppRoleMap").list();

		session.getTransaction().commit();

		return map;
	}

	@Override
	public int[] getMapRoleMenu(int menuId) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		int[] map = null;
		Session session = sessionFactory.getCurrentSession();
		try {
			if (session.getTransaction().isActive())
				session.getTransaction().commit();
			session.beginTransaction();

			Query q = session.createQuery(
					"select p.iwmpAppRoleMap.roleId from IwmpRoleMenuMap as p where p.iwmpMenu.menuId=:menuId");
			q.setParameter("menuId", menuId);
			List results = q.list();
			if (results != null) {
				Iterator qu = results.iterator();
				map = new int[results.size()];
				int i = 0;
				while (qu.hasNext()) {
					map[i] = (int) qu.next();
					i = i + 1;
				}
			}
			session.getTransaction().commit();

		} catch (Exception ex) {
			session.getTransaction().rollback();
			System.out.println("get getParentMenu by user id error : " + ex.getMessage());
		} finally {
			// session.getTransaction().commit();
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IwmpMMenu> getParentMenu() {
		// TODO Auto-generated method stub
		List<IwmpMMenu> temp = null;
		Session session = sessionFactory.getCurrentSession();
		try {
			if (session.getTransaction().isActive())
				session.getTransaction().commit();
			session.beginTransaction();

			temp = session.createQuery("from IwmpMMenu  order by menuName").list();
			session.getTransaction().commit();
		} catch (Exception ex) {
			session.getTransaction().rollback();
			System.out.println("get getParentMenu by user id error : " + ex.getMessage());
		} finally {
			// session.getTransaction().commit();
		}
		return temp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap<String, List<MenuMap>> getMenuUserId(String userid) {

		Session session = sessionFactory.getCurrentSession();
		LinkedHashMap<String, List<MenuMap>> userMenu = new LinkedHashMap<String, List<MenuMap>>();
		String sql = getMenu;
		try {
			if (session.getTransaction().isActive())
				session.getTransaction().commit();
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(sql);
			query.setString("userid", userid);

			query.setResultTransformer(Transformers.aliasToBean(MenuMap.class));
			List<MenuMap> list = query.list();
			List<MenuMap> sublist = new ArrayList<MenuMap>();
			if ((list != null) && (list.size() > 0)) {
				for (MenuMap row : list) {
					if (!userMenu.containsKey(row.getParentname())) {
						sublist = new ArrayList<MenuMap>();
						sublist.add(row);
						userMenu.put(row.getParentname(), sublist);
					} else {
						sublist.add(row);
						userMenu.put(row.getParentname(), sublist);
					}
				}
			}
			session.getTransaction().commit();
		} catch (Exception ex) {
			session.getTransaction().rollback();
			System.out.println("get Menu by user id error : " + ex.getMessage());
		} finally {
			// session.getTransaction().commit();
		}
		return userMenu;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IwmpRoleMenuMap> getAllMenuRoleParent() {
		// TODO Auto-generated method stub
		// Transaction transaction = null;
		Session session = this.sessionFactory.getCurrentSession();

		session.beginTransaction();

		Criteria criteria = session.createCriteria(IwmpRoleMenuMap.class);
		criteria.createAlias("iwmpAppRoleMap", "a");
		criteria.createAlias("iwmpMenu", "b");
		criteria.addOrder(Order.asc("a.roleName"));
		criteria.addOrder(Order.asc("b.submenuName"));
		criteria.add(Restrictions.eq("b.iwmpMMenu.menuId", 0));

		List<IwmpRoleMenuMap> temp = (List<IwmpRoleMenuMap>) criteria.list();
		session.getTransaction().commit();

		return temp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap<String, List<RoleMenuList>> getMenuAll() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		LinkedHashMap<String, List<RoleMenuList>> userMenu = new LinkedHashMap<String, List<RoleMenuList>>();
		String sql = getAllMenu;
		try {
		//	session.beginTransaction();
			SQLQuery query = session.createSQLQuery(sql);
	//		Query query = session.createQuery(sql);

			query.setResultTransformer(Transformers.aliasToBean(RoleMenuList.class));
			List<RoleMenuList> list = query.list();
			List<RoleMenuList> sublist = new ArrayList<RoleMenuList>();
			if ((list != null) && (list.size() > 0)) {
				for (RoleMenuList row : list) {
					if (!userMenu
							.containsKey(row.getRolename() + "," + row.getParentname() + ":::" + row.getParentid()+ "$" + row.getHseqno()+"@" + row.getPactive())) {
						sublist = new ArrayList<RoleMenuList>();
						sublist.add(row);
						userMenu.put(row.getRolename() + "," + row.getParentname() + ":::" + row.getParentid() + "$" + row.getHseqno()+"@" + row.getPactive(),
								sublist);
					} else {
						sublist.add(row);
						userMenu.put(row.getRolename() + "," + row.getParentname() + ":::" + row.getParentid() + "$" + row.getHseqno()+"@" + row.getPactive(),
								sublist);
					}
				}
			}
		} catch (Exception ex) {
			session.getTransaction().rollback();
			System.out.println("errmsg:"+ex.getMessage());
		} finally {
			session.getTransaction().commit();
		}
		return userMenu;
	}

	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap<String, List<RoleMenuList>> getMenuAllRole(String roleId) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		LinkedHashMap<String, List<RoleMenuList>> userMenu = new LinkedHashMap<String, List<RoleMenuList>>();
		String sql = getAllMenuRole;
		try {
			session.beginTransaction();
			SQLQuery query = session.createSQLQuery(sql);
			query.setInteger("roleid", Integer.parseInt(roleId));

//			query.setResultTransformer(Transformers.aliasToBean(RoleMenuList.class));
//			List<RoleMenuList> list = query.list();
//			List<RoleMenuList> sublist = new ArrayList<RoleMenuList>();
//			if ((list != null) && (list.size() > 0)) {
//				for (RoleMenuList row : list) {
//					if (!userMenu
//							.containsKey(row.getRolename() + "," + row.getParentname() + ":::" + row.getParentid()+ "$" + row.getHseqno()+"@" + row.getPactive())) {
//						sublist = new ArrayList<RoleMenuList>();
//						sublist.add(row);
//						userMenu.put(row.getRolename() + "," + row.getParentname() + ":::" + row.getParentid() + "$" + row.getHseqno()+"@" + row.getPactive(),
//								sublist);
//					} else {
//						sublist.add(row);
//						userMenu.put(row.getRolename() + "," + row.getParentname() + ":::" + row.getParentid() + "$" + row.getHseqno()+"@" + row.getPactive(),
//								sublist);
//					}
//				}
//			}
			
			query.setResultTransformer(Transformers.aliasToBean(RoleMenuList.class));
			List<RoleMenuList> list = query.list();
			List<RoleMenuList> sublist = new ArrayList<RoleMenuList>();
			if ((list != null) && (list.size() > 0)) {
				for (RoleMenuList row : list) {
					if (!userMenu
							.containsKey(row.getRolename() + "," + row.getParentname() + ":::" + row.getParentid()+ "$" + row.getHseqno()+"@" + row.getPactive())) {
						sublist = new ArrayList<RoleMenuList>();
						sublist.add(row);
						userMenu.put(row.getRolename() + "," + row.getParentname() + ":::" + row.getParentid() + "$" + row.getHseqno()+"@" + row.getPactive(),
								sublist);
					} else {
						sublist.add(row);
						userMenu.put(row.getRolename() + "," + row.getParentname() + ":::" + row.getParentid() + "$" + row.getHseqno()+"@" + row.getPactive(),
								sublist);
					}
				}
			}
		} catch (Exception ex) {
			session.getTransaction().rollback();
			System.out.println(ex.getMessage());
		} finally {
			session.getTransaction().commit();
		}
		return userMenu;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IwmpRoleMenuMap> getAllMenuRoleInfo(String roleId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		List<IwmpRoleMenuMap> map = (List<IwmpRoleMenuMap>) session.createQuery(
				"from IwmpRoleMenuMap where iwmpAppRoleMap.roleId=:role order by iwmpAppRoleMap.roleName,iwmpMenu.menuName")
				.setParameter("role", roleId).uniqueResult();
		session.getTransaction().commit();
		return map;
	}

	public List<IwmpRoleMenuMap> getAllMenuRoleInfo() {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		List<IwmpRoleMenuMap> map = session.createQuery("from IwmpRoleMenuMap").list();

		session.getTransaction().commit();
		return map;
	}

}
