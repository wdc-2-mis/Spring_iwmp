package app.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SessionController {

    @GetMapping("/extendSession")
    public String extendSession(HttpSession session) {
        if (session != null) {
            session.setMaxInactiveInterval(30 * 60); // Extending the session by 30 minutes
            return "Session extended";
        } else {
            return "No active session";
        }
    }
}

