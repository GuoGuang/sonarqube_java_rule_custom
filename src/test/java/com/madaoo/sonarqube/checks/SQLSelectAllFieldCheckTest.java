package com.madaoo.sonarqube.checks;

import com.madaoo.sonarqube.checks.comment.SQLSelectAllFieldCheck;
import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;

public class SQLSelectAllFieldCheckTest {

    @Test
    public void test01() {
//        JavaCheckVerifier.verifyNoIssue("src/test/files/mybatis/LogApiMapper.java", new SQLSelectAllFieldCheck());
        JavaCheckVerifier.verifyNoIssue("src/test/files/mybatis/BladexMaterialClassMapper.java", new SQLSelectAllFieldCheck());
//        XmlCheckVerifier.verifyNoIssue("src/test/files/mybatis/BladexMaterialClassMapper.xml", new SQLXMLSelectAllFieldCheck());
    }
}
