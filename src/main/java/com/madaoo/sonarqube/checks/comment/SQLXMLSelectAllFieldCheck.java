package com.madaoo.sonarqube.checks.comment;

import org.apache.commons.lang.StringUtils;
import org.sonar.check.Rule;
import org.sonar.java.xml.XmlCheck;
import org.sonar.java.xml.XmlCheckContext;
import org.w3c.dom.Node;

import javax.xml.xpath.XPathExpression;
import java.io.File;

/**
 * 校验mybatis文件的注解和xml中间中的SQL语句不能包含select *
 */
@Rule(key = "SQLXMLSelectAllFieldCheck")
@SuppressWarnings("all")
public class SQLXMLSelectAllFieldCheck implements XmlCheck {

    @Override
    public void scanFile(XmlCheckContext xmlCheckContext) {
        File file = xmlCheckContext.getFile();
        XPathExpression compile = xmlCheckContext.compile("//select");
        Iterable<Node> nodes = xmlCheckContext.evaluateOnDocument(compile);
        for (Node node : nodes) {
            String textContent = node.getTextContent();
            if (StringUtils.isNotBlank(textContent) && textContent.contains("*")) {
                xmlCheckContext.reportIssueOnFile(this, "按照云平台规范，XML中的SQL语句不能包含*");
            }
        }
    }
}
