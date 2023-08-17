package com.madaoo.sonarqube.checks.oop;

import com.google.common.collect.ImmutableList;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.List;

@Rule(key = "StringOrNotNullInsideEquailsCheck")
public class StringOrNotNullInsideEquailsCheck extends IssuableSubscriptionVisitor {
    @Override
    public List<Kind> nodesToVisit() {
        return ImmutableList.of(Kind.METHOD_INVOCATION);
    }

    @Override
    public void visitNode(Tree tree) {
        check((MethodInvocationTree) tree);
    }

    private void check(MethodInvocationTree tree) {
        if (isEquals(tree.methodSelect()) && tree.arguments().size() == 1
                && tree.arguments().get(0).is(Kind.STRING_LITERAL)) {
            LiteralTree stringLiteral = (LiteralTree) tree.arguments().get(0);
            reportIssue(stringLiteral, "移动" + stringLiteral.value() + " 字符串，放在比较方法的左边");
        }
    }

    private static boolean isEquals(ExpressionTree tree) {
        if (tree.is(Kind.IDENTIFIER)) {
            return isEquals((IdentifierTree) tree);
        } else if (tree.is(Kind.MEMBER_SELECT)) {
            return isEquals(((MemberSelectExpressionTree) tree).identifier());
        } else {
            return false;
        }
    }

    private static boolean isEquals(IdentifierTree tree) {
        return "equals".equals(tree.name()) || "equalsIgnoreCase".equals(tree.name());
    }
}
