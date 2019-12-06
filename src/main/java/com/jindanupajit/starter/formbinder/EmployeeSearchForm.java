package com.jindanupajit.starter.formbinder;

import com.jindanupajit.plugins.org.springframework.boot.binder.Binder;
import com.jindanupajit.starter.util.thymeleaf.ActionMapping;
import com.jindanupajit.starter.util.thymeleaf.ActionType;
import com.jindanupajit.starter.util.thymeleaf.UserInput;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
@Component
@ActionMapping(Action= ActionType.SEARCH, Method=RequestMethod.GET, Url="/employee/search", Label="Search")
public class EmployeeSearchForm implements Binder {

    @UserInput(Ordinal = 1, Label = "Search Employees")
    private String q;

    public EmployeeSearchForm() {
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    // Does not need conversion methods
}
