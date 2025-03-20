package com.test.wepapp.controller;

import com.test.wepapp.dto.ChangeRoleDto;
import com.test.wepapp.service.client.MsPatientFeignClient;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {
    @Autowired
    private MsPatientFeignClient msPatientFeignClient;

    @GetMapping(path = "/admin")
    public String admin(Model model) {
        model.addAttribute("users", msPatientFeignClient.getAllUsers());
        return "admin";
    }

    @PostMapping("/admin/change-role")
    public String changeRole(@RequestParam("username") String username, @RequestParam("role") String role, Model model, RedirectAttributes redirectAttributes) {
        try {
            ResponseEntity<?> response = msPatientFeignClient.changeRole(new ChangeRoleDto(username, role));
            redirectAttributes.addFlashAttribute("successMessage", "Role changed successfully");
            return "redirect:/admin";
        } catch (FeignException.Conflict ex) {
            model.addAttribute("errorMessage", "User not found");
            return "admin";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred");
            return "admin";
        }
    }
}
