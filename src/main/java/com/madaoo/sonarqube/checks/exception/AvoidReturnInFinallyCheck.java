package com.madaoo.sonarqube.checks.exception;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.*;

import java.util.List;

/**
 * 不能在finally块中使用return
 */
@Rule(key = "AvoidReturnInFinallyCheck")
public class AvoidReturnInFinallyCheck extends BaseTreeVisitor implements JavaFileScanner {
	private static final String ISSUE_MSG = "不能在finally块中使用return,finally块中的return返回后方法结束执行,不会再执行try块中的return语句";
	private JavaFileScannerContext context;

	@Override
	public void scanFile(JavaFileScannerContext context) {
		this.context = context;
		scan(context.getTree());
	}

	@Override
	public void visitTryStatement(TryStatementTree tree) {
		if (tree.finallyBlock() != null) {
			hasReturnStatement(tree);
		}
		super.visitTryStatement(tree);
	}

	private void hasReturnStatement(TryStatementTree tree) {
		BlockTree finallyBlock = tree.finallyBlock();
		List<StatementTree> state = finallyBlock.body();
		for (StatementTree statementTree : state) {
			if (statementTree.is(Tree.Kind.RETURN_STATEMENT)) {
				context.reportIssue(this, statementTree, ISSUE_MSG);
			}
		}
	}
}
