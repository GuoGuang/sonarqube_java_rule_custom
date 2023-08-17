package com.madaoo.sonarqube.checks.oop;

import com.madaoo.sonarqube.checks.PrinterVisitor;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.*;

@Rule(key = "AvoidInstanceForStaticMethodCheck")
public class AvoidInstanceForStaticMethodCheck extends BaseTreeVisitor implements JavaFileScanner {
    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
        System.out.println(PrinterVisitor.print(context.getTree()));
    }

    @Override
    public void visitMemberSelectExpression(MemberSelectExpressionTree memberSelect) {
        if (memberSelect.identifier().symbol().isStatic()) {
            ExpressionTree memberSelectExpression = memberSelect.expression();
            if (memberSelectExpression.is(Tree.Kind.MEMBER_SELECT)) {
                memberSelectExpression = ((MemberSelectExpressionTree) memberSelectExpression).identifier();
            }
            if (!memberSelectExpression.is(Tree.Kind.IDENTIFIER)
                    || ((IdentifierTree) memberSelectExpression).symbol().isVariableSymbol()) {
                context.reportIssue(this, memberSelect, "将此实例引用更改为静态引用,避免使用实例引用静态变量或方法");
            }
        }
        super.visitMemberSelectExpression(memberSelect);
    }
}
