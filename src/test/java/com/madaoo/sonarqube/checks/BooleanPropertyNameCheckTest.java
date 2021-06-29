package com.madaoo.sonarqube.checks;

import com.madaoo.sonarqube.checks.naming.BooleanPropertyNameCheck;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class BooleanPropertyNameCheckTest {
	
	@Test
	public void test01() {
		JavaCheckVerifier.verify("src/test/files/boolean/BooleanPropertyName.java", new BooleanPropertyNameCheck());
	}
}
