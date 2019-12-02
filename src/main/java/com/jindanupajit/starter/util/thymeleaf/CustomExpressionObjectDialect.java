package com.jindanupajit.starter.util.thymeleaf;

import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

public  class CustomExpressionObjectDialect implements IExpressionObjectDialect {

    private final IExpressionObjectFactory EXPRESSION_OBJECT_FACTORY = new CustomExpressionObjectFactory();

    public CustomExpressionObjectDialect() {
        super();
        System.out.println("CustomExpressionObjectDialect()");
    }

    @Override
    public String getName() {
        return "Info";
    }

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return EXPRESSION_OBJECT_FACTORY;
    }
}
