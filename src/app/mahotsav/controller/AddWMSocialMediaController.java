package app.mahotsav.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import app.mahotsav.model.WatershedMahotsavRegistration;
import app.mahotsav.service.AddWMSocialMediaService;


@Controller
public class AddWMSocialMediaController {

    @Autowired
    private AddWMSocialMediaService addWMSocialMediaService;



    // Load Add Social Media form
    @GetMapping("/addWMSocialMedia")
    public ModelAndView addWMSocialMedia() {
        return new ModelAndView("mahotsav/addSocialMediaVideo");
    }

    
    @PostMapping("/verifyRegistration")
    @ResponseBody
    public String verifyRegistration(@RequestParam("regNo") String regNo, HttpSession session) {
        try {
            WatershedMahotsavRegistration reg = addWMSocialMediaService.findByUserRegNo(regNo);

            if (reg == null) {
                return "invalidReg";
            }

            // store regNo in session
            session.setAttribute("regNo", regNo);

            // return pipe-separated response
            return String.format("%s|%s|%s|%s",
                    reg.getRegName(),
                    reg.getPhno(),
                    reg.getEmail(),
                    reg.getAddress());
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

}

