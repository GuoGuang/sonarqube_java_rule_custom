package com.madaoo.sonarqube.checks.set;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.*;

import java.util.Deque;
import java.util.LinkedList;

/**
 * ArrayList的subList结果不可强转成ArrayList，否则会抛出ClassCastException异常
 */
@Rule(key = "SubListNotCastArrayListCheck")
public class SubListNotCastArrayListCheck extends BaseTreeVisitor implements JavaFileScanner {
    private JavaFileScannerContext context;
    private static final String ISSUE_MSG = "ArrayList的subList结果不可强转成ArrayList，否则会抛出ClassCastException异常";
    private final Deque<Symbol.TypeSymbol> enclosingClass = new LinkedList<>();

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitClass(ClassTree tree) {
        Symbol.TypeSymbol enclosingSymbol = tree.symbol();
        enclosingClass.push(enclosingSymbol);
        super.visitClass(tree);
        enclosingClass.pop();
    }

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {
        super.visitMethodInvocation(tree);
        if (tree.parent().is(Tree.Kind.TYPE_CAST)) {
            TypeCastTree tpcTree = (TypeCastTree) tree.parent();
            if (tree.methodSelect().is(Tree.Kind.MEMBER_SELECT)) {
                MemberSelectExpressionTree memberSelectExpressionTree = (MemberSelectExpressionTree) tree
                        .methodSelect();
                IdentifierTree identifierTree = memberSelectExpressionTree.identifier();
                if (!enclosingClassExtendsDate() && "subList".equals(identifierTree.name())
                        && calledOnTypeInheritedFromDate(memberSelectExpressionTree.expression())
                        && !tree.arguments().isEmpty() && tpcTree.symbolType().name().equals("ArrayList")) {
                    context.reportIssue(this, identifierTree, ISSUE_MSG);
                }
            }
        }

    }

    private boolean enclosingClassExtendsDate() {
        return enclosingClass.peek() != null && enclosingClass.peek().type().isSubtypeOf("java.util.List");
    }

    private static boolean calledOnTypeInheritedFromDate(ExpressionTree tree) {
        return tree.symbolType().isSubtypeOf("java.util.List");
    }
}
