package com.madaoo.sonarqube.checks.util;

import org.sonar.plugins.java.api.tree.*;

/**
 * @author chenzhou
 */
public class JenFireRuleUtil {

	public static IdentifierTree methodName(MethodInvocationTree mit) {
		ExpressionTree methodSelect = mit.methodSelect();
		IdentifierTree identifierTree;
		if (methodSelect.is(Tree.Kind.IDENTIFIER)) {
			identifierTree = (IdentifierTree) methodSelect;
		} else {
			identifierTree = ((MemberSelectExpressionTree) methodSelect).identifier();
		}
		return identifierTree;
	}

	public static boolean isAbstractAndInterface(ClassTree tree) {
		return !tree.symbol().isAbstract() && !tree.symbol().isInterface();
	}
}
