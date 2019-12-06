package com.jindanupajit.starter.formbinder;

import com.jindanupajit.plugins.org.springframework.boot.binder.Binder;
import com.jindanupajit.starter.util.thymeleaf.ActionMapping;
import com.jindanupajit.starter.util.thymeleaf.ActionType;
import com.jindanupajit.starter.util.thymeleaf.UserInput;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
@Component
@ActionMapping(Action= ActionType.LOGIN, Method=RequestMethod.POST, Url="/login", Label="Login")
public class LoginForm implements Binder {

    @UserInput(Ordinal = 1, Label = "Email")
    private String username;

    @UserInput(Ordinal = 2, Label = "Password", Secret = true)
    private String password;

    public LoginForm() {
    }

    public LoginForm(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // No converter, Spring Security does this for us!

}
