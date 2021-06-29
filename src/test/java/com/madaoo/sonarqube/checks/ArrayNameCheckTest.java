package com.madaoo.sonarqube.checks;

import com.madaoo.sonarqube.checks.naming.ArrayNameCheck;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class ArrayNameCheckTest {
	@Test
	public void test() {
		JavaCheckVerifier.verify(
				"src/test/files/array/ArrayNameCheckMapper.java",
				new ArrayNameCheck());
	}

}
