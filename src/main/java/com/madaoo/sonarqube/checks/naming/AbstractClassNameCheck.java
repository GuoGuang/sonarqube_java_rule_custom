package com.madaoo.sonarqube.checks.naming;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;

import java.util.regex.Pattern;

/**
 * @author chenzhou
 * <p>
 * 抽象类命名使用 Abstract 或 Base 开头
 */
@Rule(key = "AbstractClassNameCheck")
public class AbstractClassNameCheck extends BaseTreeVisitor implements JavaFileScanner {
    private static final String ISSUE_MSG = "抽象类命名使用Abstract或Base开头";
    private static final Pattern PATTERN = Pattern.compile("^(Abstract|Base).*");
    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitClass(ClassTree tree) {
        String className = tree.simpleName().name();
        if (tree.symbol().isAbstract() && !PATTERN.matcher(className).matches()) {
            context.reportIssue(this, tree, ISSUE_MSG);
        }
        super.visitClass(tree);
    }
}
