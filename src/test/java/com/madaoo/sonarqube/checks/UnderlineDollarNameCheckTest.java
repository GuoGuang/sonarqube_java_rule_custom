package com.madaoo.sonarqube.checks;

import com.madaoo.sonarqube.checks.naming.UnderlineDollarNameCheck;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class UnderlineDollarNameCheckTest {
    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/under/UnderlineDollarRuleMapper.java", new UnderlineDollarNameCheck());
    }
}
