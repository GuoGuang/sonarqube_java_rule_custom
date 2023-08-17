package com.madaoo.sonarqube.checks;

import com.madaoo.sonarqube.checks.concurrent.CreateThreadShouldPoolCheck;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class CreateThreadShouldPoolCheckTest {


    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/test/CreateThreadDemo.java", new CreateThreadShouldPoolCheck());
    }
}
