package com.madaoo.sonarqube.checks.comment;

import org.sonar.check.Rule;
import org.sonar.java.model.expression.LiteralTreeImpl;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.*;

/**
 * 校验mybatis类文件的注解中的SQL语句不能包含select *
 */
@Rule(key = "SQLSelectAllFieldCheck")
@SuppressWarnings("all")
public class SQLSelectAllFieldCheck extends BaseTreeVisitor implements JavaFileScanner {
    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }


    @Override
    public void visitMethod(MethodTree tree) {
        if (isMybatisMapper(tree)) {
            if (isSelectAnnotationMethod(tree)) {
                if (isContainsSelectAllAnnotation(tree)) {
                    context.reportIssue(this, tree, "按照云平台规范，@Select注解 中的SQL语句不能包含select *");
                }
            }
        }
        super.visitMethod(tree);
    }

    private boolean isMybatisMapper(MethodTree tree) {
        ClassTree classTree = (ClassTree) tree.parent();
        String className = classTree.simpleName().name();
        return className.endsWith("Mapper");
    }

    private boolean isSelectAnnotationMethod(MethodTree tree) {
        for (AnnotationTree annotation : tree.modifiers().annotations()) {
            IdentifierTree identifier = (IdentifierTree) annotation.annotationType();
            if ("Select".equals(identifier.name())) {
                return true;
            }
        }
        return false;
    }

    /**
     * select 注解中是否包含*的查询语法
     */
    private boolean isContainsSelectAllAnnotation(MethodTree tree) {
        for (AnnotationTree annotation : tree.modifiers().annotations()) {
            Arguments arguments = annotation.arguments();
            if (arguments.size() == 1) {
                ExpressionTree expressionTree = (ExpressionTree) arguments.get(0);
                if (((LiteralTreeImpl) expressionTree).value().contains("*")) {
                    return true;
                }
            }
        }
        return false;
    }
}
