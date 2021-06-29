package com.madaoo.sonarqube.checks;

import com.madaoo.sonarqube.checks.naming.LowerCameCaseVariableNameCheck;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class LowerCameCaseVariableNameCheckTest {
	@Test
	public void test() {
		JavaCheckVerifier.verify(
				"src/test/files/lowerCameCase/LowerCameCaseMapper.java",
				new LowerCameCaseVariableNameCheck());

	}
}
