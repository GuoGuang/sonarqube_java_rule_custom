package com.madaoo.sonarqube.checks;

import com.madaoo.sonarqube.checks.oop.AvoidInstanceForStaticMethodCheck;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class AvoidInstanceForStaticMethodCheckTest {
    @Test
    public void detected() {
        JavaCheckVerifier.verify("src/test/files/staticdemo/StaticMethodInstanceDemo.java",
                new AvoidInstanceForStaticMethodCheck());
    }
}
