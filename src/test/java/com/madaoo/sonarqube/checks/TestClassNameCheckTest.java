package com.madaoo.sonarqube.checks;

import com.madaoo.sonarqube.checks.naming.TestClassNameCheck;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class TestClassNameCheckTest {
    @Test
    public void test1() {
        JavaCheckVerifier.verify("src/test/files/naming/Demo.java", new TestClassNameCheck());
    }
}
