package com.madaoo.sonarqube.checks.code;

import com.google.common.collect.ImmutableList;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * 代码最大行数限制
 *
 * @author GuoGuang
 * @created by guoguang0536@gmail.com / 1831682775@qq.com
 * @date 2021/06/29/ 15:16:00
 */
@Rule(key = "CodeBlockMaxLineCheck")
public class CodeBlockMaxLineCheck extends IssuableSubscriptionVisitor {
    private static final String ISSUE_MSG = "建议函数内代码行数不要超过50行";
    private static final int DEFAULT_MAX_LINES = 50;

    private final Deque<Integer> count = new LinkedList<>();
    private final Deque<Integer> level = new LinkedList<>();

    @Override
    public void scanFile(JavaFileScannerContext context) {
        count.clear();
        level.clear();
        level.push(0);
        count.push(0);
        super.scanFile(context);
    }

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return ImmutableList.<Tree.Kind>builder().add(Tree.Kind.METHOD).build();
    }

    @Override
    public void visitNode(Tree tree) {
        int methodLine = (tree.lastToken().line() - tree.firstToken().line()) - 1;
        if (methodLine > DEFAULT_MAX_LINES) {
            context.reportIssue(this, tree, ISSUE_MSG);
        }
    }

}