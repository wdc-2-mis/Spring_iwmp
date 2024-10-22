package app.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import app.model.IwmpAppRoleMap;
import app.model.IwmpMProject;
import app.bean.IwmpMenu;
import app.bean.IwmpRoleMenuMap;
import app.bean.Login;
import app.bean.MenuMap;
import app.bean.RoleMenuList;
import app.bean.menu.IwmpMMenu;
import app.model.IwmpAppRoleMap;
import app.service.MenuService;

@Controller
public class MenuController {

	HttpSession session;

	@Autowired
	private MenuService menuService;

	private static final Logger logger = Logger.getLogger(MenuController.class);

	public MenuController() {
		// System.out.println("MenuController()");
	}

	// getMenuUserId will return menu user wise
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/fetchMenu", method = RequestMethod.POST)
	@ResponseBody

	public LinkedHashMap<String, List<MenuMap>> getMenuUserId(HttpServletRequest request) {

		session = request.getSession();

		// String userid = session.getAttribute("loginid").toString();

		LinkedHashMap<String, List<MenuMap>> userMenu = new LinkedHashMap<String, List<MenuMap>>();
		if (session != null && session.getAttribute("loginid") != null) {
			String userid = session.getAttribute("loginid").toString();
			userMenu.putAll(menuService.getMenuUserId(userid));
		} else {

		}
		return userMenu;
	}
	// mainMenuPage will return main menu page with data

	@RequestMapping(value = "/mainmenu", method = RequestMethod.GET)
	public ModelAndView mainMenuPage(ModelAndView model, HttpServletRequest request) throws IOException {

		// List<IwmpRoleMenuMap> listMenu = menuService.getAllMenuRole();
		session = request.getSession(true);
		if (session != null && session.getAttribute("loginID") != null) {
			String msg = null;
			if (request.getParameter("msg") != null)
				msg = request.getParameter("msg");

			String role = "";
			model.addObject("role", role);
			LinkedHashMap<String, List<RoleMenuList>> listMenu = new LinkedHashMap<String, List<RoleMenuList>>();
			listMenu.putAll(menuService.getMenuAll());
			model.addObject("listMenu", listMenu);
			List<IwmpAppRoleMap> listRole = menuService.getAllRole();
			model.addObject("listRole", listRole);
			model.addObject("msg", msg);
			model.setViewName("mainmenu");
			System.out.println("Hello");
		} else {
			model = new ModelAndView("login");
			model.addObject("login", new Login());
		}
		return model;
	}

	@RequestMapping(value = "/newMenu", method = RequestMethod.GET)

	// public ModelAndView newContact(ModelAndView model) {
	public String newContact(Locale locale, Model model) {
		System.out.println("newContact()");
		IwmpMenu menu = new IwmpMenu();
		IwmpMMenu parent = new IwmpMMenu();
		menu.setIwmpMMenu(parent);
		menu.setIsParent(1);
		menu.setIsactive(true);
		// menu.setParentMenu(parent);
		model.addAttribute("menu", menu);
		List<IwmpAppRoleMap> listRole = menuService.getAllRole();
		model.addAttribute("listRole", listRole);

		List<IwmpMMenu> listMenu = menuService.getParentMenu();
		// List<IwmpRoleMenuMap> listMenu = menuService.getAllMenuRoleParent();
		model.addAttribute("listMenu", listMenu);
//	        model.setViewName("MenuForm");
//	        return model;
		return "MenuForm";
	}

	@RequestMapping(value = "/saveMenu", method = RequestMethod.POST)
	public String saveMenu(@ModelAttribute("menu") @Valid IwmpMenu menu, BindingResult result, Model model) {
	//	System.out.println("savemenu()");
		if (result.hasErrors()) {
			List<IwmpAppRoleMap> listRole = menuService.getAllRole();
			model.addAttribute("listRole", listRole);
			// List<IwmpRoleMenuMap> listMenu = menuService.getAllMenuRoleParent();
			List<IwmpMMenu> listMenu = menuService.getParentMenu();
			model.addAttribute("listMenu", listMenu);
			return "MenuForm";
		} else {
			System.out.print("menu.getIsactive()==" + menu.getIsactive());
			System.out.print("menu.getTarget()==" + menu.getTarget());
			// System.out.print("menu.getParent()=="+menu.getIwmpMMenu().getMenuId());
			if (menu.getIsParent() == 0 && menu.getTarget().isEmpty() == true) {
				// Child Menu
				List<IwmpAppRoleMap> listRole = menuService.getAllRole();
				model.addAttribute("listRole", listRole);
				List<IwmpMMenu> listMenu = menuService.getParentMenu();
				model.addAttribute("listMenu", listMenu);
				model.addAttribute("error", "Target is mandatory for Child Menu");
				return "MenuForm";
			} else if (menu.getIsParent() == 0 && menu.getIwmpMMenu().getMenuId() == 0) {
				List<IwmpAppRoleMap> listRole = menuService.getAllRole();
				model.addAttribute("listRole", listRole);
				List<IwmpMMenu> listMenu = menuService.getParentMenu();
				model.addAttribute("listMenu", listMenu);
				model.addAttribute("selectchild", "Please Select Parent for Child Menu");
				return "MenuForm";
			} else {

				if (menu.getMenuId() == 0) { // if menu id is 0 then creating the
					// Menu service other updating the menu
					menuService.addMenu(menu);
				} else {
					menuService.updateEmployee(menu);
				}
				return "redirect:/mainmenu";
			}
		}
	}

	// fetchallMenu will get all menu
	@RequestMapping(value = "/fetchMenu", method = RequestMethod.GET)
	public ModelAndView fetchallMenu(HttpServletRequest request) {
		String role = request.getParameter("role").toString();
//	    	  System.out.println("fetchMenu()"+roleId);
//		   //     menuService.deleteMenu(menuId);
//		        return new ModelAndView("redirect:/mainmenu");
		// String role="";

		LinkedHashMap<String, List<RoleMenuList>> listMenu = new LinkedHashMap<String, List<RoleMenuList>>();

		if (Integer.parseInt(role) > 0 && Integer.parseInt(role) < 999)
			listMenu.putAll(menuService.getMenuAllRole(role));
		else
			listMenu.putAll(menuService.getMenuAll());

		ModelAndView model = new ModelAndView("mainmenu");
		model.addObject("listMenu", listMenu);
		model.addObject("role", role);
		List<IwmpAppRoleMap> listRole = menuService.getAllRole();
		model.addObject("listRole", listRole);
		model.setViewName("mainmenu");
		return model;
		// return new ModelAndView("redirect:/mainmenu");
	}

	// deleteMenu will delete menu from data base
	@RequestMapping(value = "/deleteMenu", method = RequestMethod.POST)
	@ResponseBody
	public String deleteMenu(HttpServletRequest request) {
		String tmp = request.getParameter("menuId");
		String msg = "";
		if (tmp.contains("+")) {
			int sepPos = tmp.lastIndexOf("+");

			int menuId = Integer.parseInt(tmp.substring(0, sepPos));
			msg = menuService.deleteSubMenu(menuId);

		} else {

			int menuId = Integer.parseInt(tmp);
			msg = menuService.deleteMenu(menuId);
		}
		ModelAndView mv = new ModelAndView("redirect:/mainmenu");
		mv.addObject("msg", msg);
		return msg;
	}

	// editMenu will update the menu
	@RequestMapping(value = "/editMenu", method = RequestMethod.GET)
	public ModelAndView editMenu(HttpServletRequest request) {
		System.out.println("editMenu() pmenuId");
		IwmpMenu menu = new IwmpMenu();
		if (request.getParameter("menuId") != null) {
			int menuId = Integer.parseInt(request.getParameter("menuId"));

			System.out.println("editMenu()" + menuId);
			menu = menuService.getMenu(menuId);

			if (menu.getIwmpMMenu().getMenuId() == 0)
				menu.setIsParent(1);
			else
				menu.setIsParent(0);
			System.out.println("editMenu()" + menu.getSubmenuName());
			menu.setMapRoleId(menuService.mapRoleMenu(menu.getMenuId()));
		} else if (request.getParameter("pmenuId") != null) {
			int menuId = Integer.parseInt(request.getParameter("pmenuId"));
			menu.setIsParent(1);
			IwmpMMenu temp = menuService.getParentMenu(menuId);
			menu.setIwmpMMenu(new IwmpMMenu());
			menu.setSubmenuName(temp.getMenuName());
			menu.setSubmenuHindiName(temp.getMenuHindiName());
			menu.setIsactive(temp.getIsactive());
			menu.setSeqNo(temp.getHseqNo());
			menu.setIsactive(temp.getIsactive());
			menu.setIsParent(1);
			menu.setMenuId(menuId);

		}

		ModelAndView model = new ModelAndView("MenuForm");
		model.addObject("menu", menu);
		System.out.println("role---" + menu.getMapRoleId());
		List<IwmpAppRoleMap> listRole = menuService.getAllRole();
		// List<IwmpAppRoleMap> listRole= menuService.geRoleByParent(menuId);
		model.addObject("listRole", listRole);
		List<IwmpMMenu> listMenu = menuService.getParentMenu();
		// List<IwmpRoleMenuMap> listMenu = menuService.getAllMenuRoleParent();
		model.addObject("listMenu", listMenu);
		return model;

	}

}
