package firsov.study.securitySpring.controller;

import firsov.study.securitySpring.dto.UserDTO;
import firsov.study.securitySpring.exception.SendEmailException;
import firsov.study.securitySpring.exception.UserAlreadyExistException;
import firsov.study.securitySpring.exception.UserNotFoundException;
import firsov.study.securitySpring.model.Permission;
import firsov.study.securitySpring.model.Role;
import firsov.study.securitySpring.model.User;
import firsov.study.securitySpring.service.UserService;
import firsov.study.securitySpring.util.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class IndexController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/success")
    public String success() {
        return "success";
    }

    @GetMapping("/signup")
    public String registration(Model m) {
        UserDTO userDto = new UserDTO();
        m.addAttribute("user", userDto);
        return "registration";
    }

    @PostMapping("/signup")
    public String processRegistration(@ModelAttribute("user") @Valid UserDTO userDto, BindingResult result, Model model)
    {

        if (result.hasErrors()) {
            return "registration";
        }

        try {
            User registered = userService.registerNewUserAccount(userDto);
        } catch (UserAlreadyExistException uaeEx) {
            model.addAttribute("message", "An account for that username/email already exists.");
            return "registration";
        }
        return "redirect:/success";
    }

    @GetMapping("/forgetPassword")
    public String forgetPassword() {
        return "forgetPassword";
    }

    @PostMapping("/forgetPassword")
    public String processForgetPswd(@NotNull @RequestParam("email") String userEmail,  Model model) {
        try {
            userService.generateToken(userEmail);
        }  catch (UserNotFoundException e) {
            model.addAttribute("message", "An account for that email not exists.");
            return "forgetPassword";
        }
//        catch (SendEmailException e) {
//            model.addAttribute("message", "Try a bit later!");
//        }
        model.addAttribute("message", "Check your email!");
        return "forgetPassword";
    }
}
