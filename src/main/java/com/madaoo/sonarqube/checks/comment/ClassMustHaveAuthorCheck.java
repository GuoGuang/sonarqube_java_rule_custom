package com.madaoo.sonarqube.checks.comment;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.SyntaxTrivia;

import java.util.regex.Pattern;

/**
 * 所有的类都必须添加创建者信息
 */
@Rule(key = "ClassMustHaveAuthorCheck")
public class ClassMustHaveAuthorCheck extends BaseTreeVisitor implements JavaFileScanner {

	private JavaFileScannerContext context;
	private static final String ISSUE_MSG = "所有的类都必须添加创建者信息";
	private static final Pattern AUTHOR_PATTERN = Pattern.compile(".*@author.*",
			Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

	@Override
	public void scanFile(JavaFileScannerContext context) {
		this.context = context;
		scan(context.getTree());
	}

	@Override
	public void visitClass(ClassTree tree) {
		if (!tree.modifiers().firstToken().trivias().isEmpty()) {
			for (SyntaxTrivia trivia : tree.modifiers().firstToken().trivias()) {
				String comment = trivia.comment();
				if (!AUTHOR_PATTERN.matcher(comment).matches()) {
					context.reportIssue(this, tree, ISSUE_MSG);
				}
			}
		} else {
			context.reportIssue(this, tree, ISSUE_MSG);
		}
		super.visitClass(tree);
	}
}
