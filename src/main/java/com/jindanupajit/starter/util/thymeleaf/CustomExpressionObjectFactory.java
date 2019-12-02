package com.jindanupajit.starter.util.thymeleaf;

import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.expression.IExpressionObjectFactory;
import com.jindanupajit.starter.util.thymeleaf.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class CustomExpressionObjectFactory implements IExpressionObjectFactory {

    protected static final Set<String> ALL_EXPRESSION_OBJECT_NAMES =
            Collections.unmodifiableSet(
                    new LinkedHashSet<>(
                            Collections.singletonList(Info.expressionObjectName)
                    )
            );

    @Override
    public boolean isCacheable(String expressionObjectName) {
        return true;
    }

    @Override
    public Set<String> getAllExpressionObjectNames() {
        return ALL_EXPRESSION_OBJECT_NAMES;
    }

    @Override
    public Object buildObject(IExpressionContext context, String expressionObjectName) {
        if (Info.expressionObjectName.equals(expressionObjectName)) {
            return new Info();
        }
        return null;
    }

}
