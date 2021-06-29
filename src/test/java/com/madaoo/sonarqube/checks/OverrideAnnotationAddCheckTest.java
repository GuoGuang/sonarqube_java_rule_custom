package com.madaoo.sonarqube.checks;

import com.madaoo.sonarqube.checks.oop.OverrideAnnotationAddCheck;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class OverrideAnnotationAddCheckTest {
	@Test
	public void test() {
		JavaCheckVerifier.verify("src/test/files/over/BaseDaoImpl.java", new OverrideAnnotationAddCheck());
	}
}
