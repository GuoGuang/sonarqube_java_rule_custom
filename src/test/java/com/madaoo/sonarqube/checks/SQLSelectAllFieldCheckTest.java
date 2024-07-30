package com.madaoo.sonarqube.checks;

import com.madaoo.sonarqube.checks.comment.SQLXMLSelectAllFieldCheck;
import org.junit.Test;
import org.sonar.java.checks.verifier.XmlCheckVerifier;

public class SQLSelectAllFieldCheckTest {

    @Test
    public void test01() {
//        JavaCheckVerifier.verifyNoIssue("src/test/files/mybatis/BladexMaterialClassMapper.java", new SQLSelectAllFieldCheck());
        XmlCheckVerifier.verifyNoIssue("src/test/files/mybatis/BladexMaterialClassMapper.xml", new SQLXMLSelectAllFieldCheck());
    }
}
