package com.madaoo.sonarqube.checks;

import org.apache.commons.lang.StringUtils;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.Tree;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 用于打印java语法树
 */
public class PrinterVisitor extends BaseTreeVisitor {

    private static final int INDENT_SPACES = 2;
    private final StringBuilder sb;
    private int indentLevel;

    public PrinterVisitor() {
        sb = new StringBuilder();
        indentLevel = 0;
    }

    public static String print(Tree tree) {
        PrinterVisitor pv = new PrinterVisitor();
        pv.scan(tree);
        return pv.sb.toString();
    }

    private StringBuilder indent() {
        return sb.append(StringUtils.leftPad("", INDENT_SPACES * indentLevel));
    }

    @Override
    protected void scan(List<? extends Tree> trees) {
        if (!trees.isEmpty()) {
            sb.deleteCharAt(sb.length() - 1);
            sb.append(" : [\n");
            super.scan(trees);
            indent().append("]\n");
        }
    }

    @Override
    protected void scan(@Nullable Tree tree) {
        if (tree != null && tree.getClass().getInterfaces() != null && tree.getClass().getInterfaces().length > 0) {
            String nodeName = tree.getClass().getInterfaces()[0].getSimpleName();
            indent().append(nodeName).append("\n");
        }
        indentLevel++;
        super.scan(tree);
        indentLevel--;
    }
}
