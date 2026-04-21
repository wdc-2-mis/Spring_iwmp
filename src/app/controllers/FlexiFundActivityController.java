package app.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.bean.Login;
import app.service.FlexiFundActivityService;

@Controller("flexiFundActivityController")
public class FlexiFundActivityController {

    HttpSession session;
    
    @Autowired(required = true)
    FlexiFundActivityService flexiFundActivityService;
    
    @RequestMapping(value = "/showactivityform", method = RequestMethod.GET)
    public ModelAndView showActivityForm(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav;
        session = request.getSession(true);
        if(session != null && session.getAttribute("loginID") != null) {
            mav = new ModelAndView("reports/flexiFundMActivity");
            mav.addObject("activitydata", flexiFundActivityService.getActivityData());
            
            String message = (String) session.getAttribute("message");
            if(message != null && !message.isEmpty()) {
                mav.addObject("message", message);
                session.removeAttribute("message");
            }
        } else {
            mav = new ModelAndView("login");
            mav.addObject("login", new Login());
        }
        return mav;
    }
    
    @RequestMapping(value = "/saveactivityData", method = RequestMethod.POST)
    public ModelAndView saveActivityData(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("actname") String actname,
            RedirectAttributes redirectAttributes) {
        
        Boolean flag = false;
        String message = "";
        
        try {
            session = request.getSession(true);
            if(session != null && session.getAttribute("loginID") != null) {
                flag = flexiFundActivityService.saveActivity(actname, session.getAttribute("loginID").toString());
                if(flag) {
                    message = "Activity has been saved successfully";
                } else {
                    message = "Data cannot be saved because Activity Name already exists";
                }
            } else {
                message = "Unable to save data";
            }
        } catch(Exception ex) {
            ex.printStackTrace();
            message = "Error: " + ex.getMessage();
        }
        
        session.setAttribute("message", message);
        
        return new ModelAndView("redirect:/showactivityform");
    }
    
    @RequestMapping(value = "/updateactivityData", method = RequestMethod.POST)
    public ModelAndView updateActivityData(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("id") int id, 
            @RequestParam("actname") String actname) {
        
        Boolean flag = false;
        String message = "";
        
        try {
            session = request.getSession(true);
            if(session != null && session.getAttribute("loginID") != null) {
                flag = flexiFundActivityService.updateActivityData(id, actname, session.getAttribute("loginID").toString());
                if(flag) {
                    message = "Activity ID: " + id + " has been updated successfully";
                } else {
                    message = "Could not update record because this Activity is already used in transaction table";
                }
            } else {
                message = "Problem on updating data";
            }
        } catch(Exception ex) {
            ex.printStackTrace();
            message = "Error: " + ex.getMessage();
        }
        
        session.setAttribute("message", message);
        
        return new ModelAndView("redirect:/showactivityform");
    }
    
    @RequestMapping(value = "/deleteactivityData", method = RequestMethod.POST)
    public ModelAndView deleteActivityData(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("id") int id) {
        
        Boolean flag = false;
        String message = "";
        
        try {
            session = request.getSession(true);
            if(session != null && session.getAttribute("loginID") != null) {
                flag = flexiFundActivityService.deleteActivityData(id);
                if(flag) {
                    message = "Activity ID: " + id + " has been removed successfully";
                } else {
                    message = "Could not delete record because this record exists in transaction table";
                }
            } else {
                message = "Unable to remove data";
            }
        } catch(Exception ex) {
            ex.printStackTrace();
            message = "Error: " + ex.getMessage();
        }
        
        session.setAttribute("message", message);
        
        return new ModelAndView("redirect:/showactivityform");
    }
}