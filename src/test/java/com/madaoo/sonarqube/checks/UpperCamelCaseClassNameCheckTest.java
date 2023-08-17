package com.madaoo.sonarqube.checks;

import com.madaoo.sonarqube.checks.naming.UpperCameCaseClassNameCheck;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class UpperCamelCaseClassNameCheckTest {
    @Test
    public void test1() {
        JavaCheckVerifier.verify("src/test/files/upperCameCase/macroPolo.java", new UpperCameCaseClassNameCheck());
    }


}
