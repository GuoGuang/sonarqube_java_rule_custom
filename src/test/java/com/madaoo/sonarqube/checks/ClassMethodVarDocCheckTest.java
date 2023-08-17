package com.madaoo.sonarqube.checks;

import com.madaoo.sonarqube.checks.comment.ClassMethodVarDocCheck;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class ClassMethodVarDocCheckTest {

    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/author/aController.java", new ClassMethodVarDocCheck());
    }
}
