package com.madaoo.sonarqube.checks;

import com.madaoo.sonarqube.checks.concurrent.AvoidExecutorsCreatePoolCheck;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class AvoidExecutorsCreatePoolCheckTest {
    @Test
    public void detected() {
        JavaCheckVerifier.verify("src/test/files/SimpleThreadPool.java", new AvoidExecutorsCreatePoolCheck());
    }
}
