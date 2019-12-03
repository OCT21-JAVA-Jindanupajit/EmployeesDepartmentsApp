package com.jindanupajit.starter.formbinder;

import com.jindanupajit.starter.model.User;
import com.jindanupajit.starter.service.PasswordEncoder;
import com.jindanupajit.starter.util.thymeleaf.ActionMapping;
import com.jindanupajit.starter.util.thymeleaf.ActionType;
import com.jindanupajit.starter.util.thymeleaf.UserInput;
import org.springframework.web.bind.annotation.RequestMethod;

@ActionMapping(Action=ActionType.PERSIST, Method=RequestMethod.POST, Url="/user/add", Label="Register")
public class UserRegistration {

    @UserInput(Ordinal = 1, Label = "Display Name")
    private String displayName;

    @UserInput(Ordinal = 2, Label = "Email")
    private String username;

    @UserInput(Ordinal = 3, Label = "Password")
    private String password;

    @UserInput(Ordinal = 4, Label = "Password Verify")
    private String passwordVerify;

    public UserRegistration() {
    }

    public UserRegistration(String displayName, String username, String password) {
        this.displayName = displayName;
        this.username = username;
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    public String getPasswordVerify() {
        return passwordVerify;
    }

    public void setPasswordVerify(String passwordVerify) {
        this.passwordVerify = passwordVerify;
    }

    public User toUser() {
        User user = new User(this.displayName, this.username, PasswordEncoder.getInstance().encode(this.password), null);
        return user;
    }
}
