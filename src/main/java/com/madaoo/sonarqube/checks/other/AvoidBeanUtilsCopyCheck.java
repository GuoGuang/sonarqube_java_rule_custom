package com.madaoo.sonarqube.checks.other;

import com.google.common.collect.ImmutableList;
import com.madaoo.sonarqube.checks.util.AbstractMethodDetectionImpro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.check.Rule;
import org.sonar.java.matcher.MethodMatcher;
import org.sonar.java.matcher.NameCriteria;
import org.sonar.java.matcher.TypeCriteria;
import org.sonar.plugins.java.api.tree.*;

import java.util.List;

/**
 * 避免用Apache Beanutils进行属性的copy
 */
@Rule(key = "AvoidBeanUtilsCopyCheck")
public class AvoidBeanUtilsCopyCheck extends AbstractMethodDetectionImpro {

    private static final String ISSUE_MSG = "避免用Apache Beanutils进行属性的copy";
    private static final Logger LOGGER = LoggerFactory.getLogger(AvoidBeanUtilsCopyCheck.class);

    @Override
    protected List<MethodMatcher> getMethodInvocationMatchers() {
        return ImmutableList.of(MethodMatcher.create().typeDefinition("org.apache.commons.beanutils.BeanUtils")
                .name(NameCriteria.startsWith("copyProperties")).addParameter(TypeCriteria.anyType())
                .addParameter(TypeCriteria.anyType()));
    }

    @Override
    protected void onMethodInvocationFound(MethodInvocationTree mit) {
        LOGGER.info("%%%%%%%%%%%%%%%%%%%%%%%%%%");
        IdentifierTree identifierTree = methodName(mit);
        LOGGER.info("%%%%%%%%%%%%%%%%%%%%%%%%%%" + identifierTree.name() + "===" + isBeanUtilsOwnerType(mit));
        if ("copyProperties".equals(identifierTree.name()) && isBeanUtilsOwnerType(mit)) {
            context.reportIssue(this, identifierTree, ISSUE_MSG);
        }
        super.onMethodInvocationFound(mit);
    }

    private static IdentifierTree methodName(MethodInvocationTree mit) {
        ExpressionTree methodSelect = mit.methodSelect();
        IdentifierTree id;
        if (methodSelect.is(Tree.Kind.IDENTIFIER)) {
            id = (IdentifierTree) methodSelect;
        } else {
            id = ((MemberSelectExpressionTree) methodSelect).identifier();
        }
        return id;
    }

    private static boolean isBeanUtilsOwnerType(MethodInvocationTree mit) {
        return mit.symbol().owner().type().isSubtypeOf("org.apache.commons.beanutils.BeanUtils");
    }
}
