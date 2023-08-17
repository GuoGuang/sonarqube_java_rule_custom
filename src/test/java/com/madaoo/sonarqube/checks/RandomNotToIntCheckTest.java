package com.madaoo.sonarqube.checks;

import com.madaoo.sonarqube.checks.oop.RandomNotToIntCheck;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class RandomNotToIntCheckTest {
    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/RandomFloatToIntCheck.java", new RandomNotToIntCheck());
    }

}
