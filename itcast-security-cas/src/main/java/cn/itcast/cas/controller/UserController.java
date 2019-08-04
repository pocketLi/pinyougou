package cn.itcast.cas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/getUsername")
    public String getUsername(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        System.out.println("request.getRemoteUser()=" + request.getRemoteUser());

        return username;
    }
}
