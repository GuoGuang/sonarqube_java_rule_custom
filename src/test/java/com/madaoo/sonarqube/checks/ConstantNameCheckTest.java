package com.madaoo.sonarqube.checks;

import com.madaoo.sonarqube.checks.naming.ConstantNameCheck;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class ConstantNameCheckTest {
    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/cons/ConstantNameMapper.java", new ConstantNameCheck());
    }
}

