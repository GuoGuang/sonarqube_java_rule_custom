package com.madaoo.sonarqube.checks;

import com.madaoo.sonarqube.checks.oop.RegexPatternCompileCheck;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class RegexPatternCompileCheckTest {
	@Test
	public void test() {
		JavaCheckVerifier.verify("src/test/files/regex/RegexPatternsNeedlesslyCheck.java", new RegexPatternCompileCheck());
	}
}
