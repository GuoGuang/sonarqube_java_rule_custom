package com.madaoo.sonarqube.checks;

import com.madaoo.sonarqube.checks.naming.PackageNameCheck;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class PackageNameCheckTest {
    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/package/Package.java", new PackageNameCheck());
    }
}
