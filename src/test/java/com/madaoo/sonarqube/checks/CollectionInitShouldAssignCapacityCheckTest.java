package com.madaoo.sonarqube.checks;

import com.madaoo.sonarqube.checks.set.CollectionInitShouldAssignCapacityCheck;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class CollectionInitShouldAssignCapacityCheckTest {
    @Test
    public void test01() {
        JavaCheckVerifier.verify("src/test/files/set/CollectionDemo.java", new CollectionInitShouldAssignCapacityCheck());
    }
}
