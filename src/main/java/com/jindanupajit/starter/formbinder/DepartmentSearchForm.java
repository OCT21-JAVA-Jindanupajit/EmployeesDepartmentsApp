package com.jindanupajit.starter.formbinder;

import com.jindanupajit.starter.util.thymeleaf.ActionMapping;
import com.jindanupajit.starter.util.thymeleaf.ActionType;
import com.jindanupajit.starter.util.thymeleaf.UserInput;
import org.springframework.web.bind.annotation.RequestMethod;

@ActionMapping(Action= ActionType.SEARCH, Method=RequestMethod.GET, Url="/department/search", Label="Search")
public class DepartmentSearchForm {

    @UserInput(Ordinal = 1, Label = "Search Departments")
    private String q;

    public DepartmentSearchForm() {
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    // Does not need conversion methods
}
