package com.madaoo.sonarqube.checks;

import com.madaoo.sonarqube.checks.oop.CollectionToArrayCheck;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class CollectionToArrayCheckTest {
    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/staticdemo/CollectionToArrayDemo.java",
                new CollectionToArrayCheck());
    }
}
