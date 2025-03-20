package com.test.wepapp.controller;

import com.test.wepapp.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @Autowired
    private SessionService sessionService;

    @GetMapping(path = "/")
    public String home() {
        return "home";
    }

    @GetMapping(path = "/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("user", sessionService.getCurrentUser());
        return "dashboard";
    }


}
