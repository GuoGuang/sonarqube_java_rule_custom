package com.madaoo.sonarqube.checks;

import com.madaoo.sonarqube.checks.set.SubListNotCastArrayListCheck;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class SubListNotCastArrayListCheckTest {
    @Test
    public void detected() {
        JavaCheckVerifier.verify("src/test/files/SubListDemo.java", new SubListNotCastArrayListCheck());
    }
}
