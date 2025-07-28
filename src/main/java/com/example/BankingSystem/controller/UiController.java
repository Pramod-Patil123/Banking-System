package com.example.BankingSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Controller
public class UiController {

    @GetMapping("/")
    public String homeRedirect() {
        return "redirect:/accounts";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String processLoginForm(
            @RequestParam("contactNo") String contactNo,
            @RequestParam("pin") String pin,
            RedirectAttributes redirectAttributes) {

        if ("9130667095".equals(contactNo) && "1234".equals(pin)) {
            redirectAttributes.addFlashAttribute("message", "Login successful! Welcome.");
            // FIX IS HERE: Changed from addFlashFlashAttribute to addFlashAttribute
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/accounts";
        } else {
            redirectAttributes.addFlashAttribute("message", "Login failed: Invalid contact number or PIN.");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "You have been logged out.");
        redirectAttributes.addFlashAttribute("messageType", "success");
        return "redirect:/accounts";
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam("name") String name,
            @RequestParam("pin") String pin,
            @RequestParam("email") String email,
            @RequestParam("dob") String dobString,
            @RequestParam("contactNo") String contactNo,
            RedirectAttributes redirectAttributes) {

        if (name.isEmpty() || pin.isEmpty() || email.isEmpty() || dobString.isEmpty() || contactNo.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Registration failed: All fields are required.");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/register";
        }

        LocalDate dob = null;
        try {
            dob = LocalDate.parse(dobString);
        } catch (DateTimeParseException e) {
            redirectAttributes.addFlashAttribute("message", "Registration failed: Invalid Date of Birth format.");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/register";
        }

        System.out.println("New User Registration Details:");
        System.out.println("Name: " + name);
        System.out.println("PIN: " + pin);
        System.out.println("Email: " + email);
        System.out.println("DOB: " + dob);
        System.out.println("Contact No: " + contactNo);
        System.out.println("--- End Registration Details ---");

        redirectAttributes.addFlashAttribute("message", "Registration successful! Please log in.");
        redirectAttributes.addFlashAttribute("messageType", "success");

        return "redirect:/login";
    }
}