package com.madaoo.sonarqube.checks.comment;

import com.google.common.base.Joiner;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.*;

import java.util.ArrayList;
import java.util.List;

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
            if (arguments.size() > 0) {
                for (ExpressionTree argument : arguments) {
                    if (argument instanceof BinaryExpressionTree) {
                        BinaryExpressionTree binaryExpressionTree = (BinaryExpressionTree) argument;
                        String sql =Joiner.on("").join(extractAllValues(binaryExpressionTree));
                        if (sql.contains("*")) {
                            return true;
                        }
                    } else if (argument instanceof LiteralTree) {
                        LiteralTree literalTree = (LiteralTree) argument;
                        if (literalTree.value().contains("*")) {
                            return true;
                        }
                    }
                }
            }
        }
        context.reportIssue(this, tree, "4");
        return false;
    }

    // 方法用于从 BinaryExpressionTree 中提取所有子集值
    public List<String> extractAllValues(BinaryExpressionTree binaryExpressionTree) {
        List<String> values = new ArrayList<>();
        // 提取左操作数的值
        extractValuesFromExpression(binaryExpressionTree.leftOperand(), values);
        // 提取右操作数的值
        extractValuesFromExpression(binaryExpressionTree.rightOperand(), values);

        return values;
    }

    // 递归提取表达式树中的值
    private void extractValuesFromExpression(ExpressionTree expressionTree, List<String> values) {
        if (expressionTree instanceof BinaryExpressionTree) {
            BinaryExpressionTree binaryExpression = (BinaryExpressionTree) expressionTree;
            // 递归提取左操作数的值
            extractValuesFromExpression(binaryExpression.leftOperand(), values);
            // 递归提取右操作数的值
            extractValuesFromExpression(binaryExpression.rightOperand(), values);
        } else if (expressionTree instanceof LiteralTree) {
            // 提取文字值并添加到列表中
            values.add(((LiteralTree) expressionTree).value().toString());
        } else {
            // 处理其他类型的表达式，例如方法调用等
            values.add(expressionTree.toString());
        }
    }
}
