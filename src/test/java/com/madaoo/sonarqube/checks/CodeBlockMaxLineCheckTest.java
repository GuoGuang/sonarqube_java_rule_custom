package com.madaoo.sonarqube.checks;

import com.madaoo.sonarqube.checks.code.CodeBlockMaxLineCheck;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class CodeBlockMaxLineCheckTest {

    @Test
    public void codeBlockMaxLineCheckTest() {
        JavaCheckVerifier.verify("src/test/files/author/CodeBlockMaxLineCheck.java", new CodeBlockMaxLineCheck());
    }
}
