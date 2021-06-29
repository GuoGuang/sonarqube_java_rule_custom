package com.madaoo.sonarqube.checks.naming;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.*;

import java.util.List;
import java.util.regex.Pattern;

/**
 * 测试类命名以它要测试的类的名称开始，以Test结尾
 */
@Rule(key = "TestClassNameCheck")
public class TestClassNameCheck extends BaseTreeVisitor implements JavaFileScanner {
	private static final String DEFAULT_FORMAT = "^((Test|IT)[a-zA-Z0-9]+|[A-Z][a-zA-Z0-9]*(Test|IT|TestCase|ITCase))$";
	private static final Pattern PATTERN = Pattern.compile(DEFAULT_FORMAT);
	private static final String ISSUE_MSG = "测试类命名以它要测试的类的名称开始，以Test结尾";
	private JavaFileScannerContext context;

	@Override
	public void scanFile(JavaFileScannerContext context) {
		this.context = context;
		scan(context.getTree());
	}

	@Override
	public void visitClass(ClassTree tree) {
		String className = tree.symbol().name();
		if (hasInvalidName(className) && hasTestMethod(tree.members())) {
			context.reportIssue(this, tree, ISSUE_MSG);
		}
		super.visitClass(tree);
	}

	private boolean hasInvalidName(String className) {
		return className != null && !PATTERN.matcher(className).matches();
	}

	private boolean hasTestMethod(List<Tree> members) {
		for (Tree member : members) {
			if (member.is(Tree.Kind.METHOD)) {
				MethodTree tree = (MethodTree) member;
				List<AnnotationTree> annotations = tree.modifiers().annotations();
				for (AnnotationTree annotationTree : annotations) {
					if (annotationTree.annotationType().is(Tree.Kind.IDENTIFIER)) {
						IdentifierTree idf = (IdentifierTree) annotationTree.annotationType();
						if ("Test".equals(idf.name())) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
