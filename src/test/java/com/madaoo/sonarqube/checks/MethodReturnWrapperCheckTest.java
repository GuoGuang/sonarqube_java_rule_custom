package com.madaoo.sonarqube.checks;

import com.madaoo.sonarqube.checks.exception.MethodReturnWrapperCheck;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class MethodReturnWrapperCheckTest {
	@Test
	public void test() {
		JavaCheckVerifier.verify("src/test/files/exception/MethodReturnWrapperDemo.java",
				new MethodReturnWrapperCheck());
	}
}
