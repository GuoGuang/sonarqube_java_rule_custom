package com.madaoo.sonarqube.checks;

import com.madaoo.sonarqube.checks.other.AvoidBeanUtilsCopyCheck;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class AvoidBeanUtilsCopyCheckTest {
	@Test
	public void test() {
		JavaCheckVerifier.verify("src/test/files/beanutil/BeanUtilDemo.java", new AvoidBeanUtilsCopyCheck());
	}
}
