package com.madaoo.sonarqube.checks.comment;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.*;

/**
 * 类、类属性、类方法的注释必须使用javadoc规范
 */
@Rule(key = "ClassMethodVarDocCheck")
public class ClassMethodVarDocCheck extends BaseTreeVisitor implements JavaFileScanner {

    private JavaFileScannerContext context;
    private static final String ISSUE_MSG = "类、类属性、类方法的注释必须使用javadoc规范,使用/**内容*/格式,不得使用//xxx方式和/*xxx*/方式";

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitClass(ClassTree tree) {
        String className = tree.symbol().name();
        if (!className.endsWith("Controller")) {
            String classJavadoc = getJavadoc(tree);
            if (!classJavadoc.trim().startsWith("/**")) {
                context.reportIssue(this, tree, ISSUE_MSG);
            }
        }
        super.visitClass(tree);
    }

    @Override
    public void visitMethod(MethodTree tree) {
        ClassTree parent = (ClassTree) tree.parent();
        String className = parent.symbol().name();
        if (!className.endsWith("Controller")) {
            if (tree.modifiers().firstToken().trivias().isEmpty()) {
                context.reportIssue(this, tree, ISSUE_MSG);
            } else {
                String javadoc = null;
                for (SyntaxTrivia trivia : tree.modifiers().firstToken().trivias()) {
                    String comment = trivia.comment();
                    if (comment != null) {
                        javadoc = comment;
                    }
                }
                if (!javadoc.trim().startsWith("/**")) {
                    context.reportIssue(this, tree, ISSUE_MSG);
                }
            }

        }
        super.visitMethod(tree);
    }

    @Override
    public void visitVariable(VariableTree tree) {
        if (tree.symbol().isPublic() || tree.symbol().isProtected() || tree.symbol().isPrivate()) {
            if (tree.modifiers().firstToken().trivias().isEmpty()) {
                super.visitVariable(tree);
            } else {
                String javadoc = null;
                for (SyntaxTrivia trivia : tree.modifiers().firstToken().trivias()) {
                    String comment = trivia.comment();
                    if (comment != null) {
                        javadoc = comment;
                    }
                }
                if (!javadoc.trim().startsWith("/**")) {
                    context.reportIssue(this, tree, ISSUE_MSG);
                }
            }
        }
        super.visitVariable(tree);
    }

    /**
     * 获取类注释 注意:不考虑没有注释的情况 详情可见 ClassMustHaveAuthorCheck 这里要求必须要有注释
     *
     * @param tree
     * @return
     */
    private String getJavadoc(ClassTree tree) {
        String javadoc = null;
        if (!tree.modifiers().firstToken().trivias().isEmpty()) {
            for (SyntaxTrivia trivia : tree.modifiers().firstToken().trivias()) {
                javadoc = trivia.comment();
            }
        }
        return javadoc;
    }
}
