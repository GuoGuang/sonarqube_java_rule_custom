package com.madaoo.sonarqube.checks;

import com.madaoo.sonarqube.checks.exception.AvoidReturnInFinallyCheck;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class AvoidReturnInFinallyCheckTest {
    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/exception/FinallyReturnDemo.java", new AvoidReturnInFinallyCheck());
    }

}
