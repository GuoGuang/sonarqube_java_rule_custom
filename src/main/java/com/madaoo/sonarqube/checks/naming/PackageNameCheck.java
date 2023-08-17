package com.madaoo.sonarqube.checks.naming;

import org.sonar.check.Rule;
import org.sonar.java.model.PackageUtils;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.CompilationUnitTree;

import java.util.regex.Pattern;

/**
 * 包名统一使用小写，点分隔符之间有且仅有一个自然语义的英语单词
 */
@Rule(key = "PackageNameCheck")
public class PackageNameCheck extends BaseTreeVisitor implements JavaFileScanner {
    private JavaFileScannerContext context;
    private static final String ISSUE_MSG = "包名统一使用小写，点分隔符之间有且仅有一个自然语义的英语单词";
    private static final Pattern PATTERN = Pattern.compile("^[a-z_]+(\\.[a-z_][a-z0-9_]*)*$");

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitCompilationUnit(CompilationUnitTree tree) {
        if (tree.packageDeclaration() != null) {
            String packageName = PackageUtils.packageName(tree.packageDeclaration(), ".");
            if (!PATTERN.matcher(packageName).matches()) {
                context.reportIssue(this, tree, ISSUE_MSG);
            }
        }
        super.visitCompilationUnit(tree);
    }

}
