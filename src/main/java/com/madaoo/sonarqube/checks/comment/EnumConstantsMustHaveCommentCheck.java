package com.madaoo.sonarqube.checks.comment;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.EnumConstantTree;

/**
 * 所有的枚举类型字段必须要有注释，说明每个数据项的用途
 */
@Rule(key = "EnumConstantsMustHaveCommentCheck")
public class EnumConstantsMustHaveCommentCheck extends BaseTreeVisitor implements JavaFileScanner {

    private JavaFileScannerContext context;
    private static final String ISSUE_MSG = "所有的枚举类型字段必须要有注释，说明每个数据项的用途";

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitEnumConstant(EnumConstantTree tree) {
        if (tree.initializer().firstToken().trivias().isEmpty()) {
            // 没有注释，标记错误
            context.reportIssue(this, tree, ISSUE_MSG);
        }
        super.visitEnumConstant(tree);
    }
}
