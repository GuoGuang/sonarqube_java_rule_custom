package com.madaoo.sonarqube.checks.comment;

import org.apache.commons.lang.StringUtils;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.SyntaxTrivia;

import java.util.List;

/**
 * 校验feign 接口
 * 类 和 方法均需要有注释 且注释大于30个字符
 */
@Rule(key = "ServiceCommentaryCheck")
public class ServiceCommentaryCheck extends BaseTreeVisitor implements JavaFileScanner{
    private JavaFileScannerContext context;
    private static final String CLASS_ISSUE_MSG = "Service接口需要添加注释，描述Service类的关键信息！";
    private static final String METHOD_ISSUE_MSG = "Service接口需要添加注释，描述当前方法的关键信息！";


    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitClass(ClassTree tree) {
        if (isService(tree)){
            //判断类注释是否大于30个字符串
            List<SyntaxTrivia> trivias = tree.modifiers().firstToken().trivias();
            if (null==trivias || trivias.size()==0){
                context.reportIssue(this, tree, METHOD_ISSUE_MSG);
            }
            // 验证第一条注释
            SyntaxTrivia firstTrivia =trivias.get(0);
            if (checkValueLength(firstTrivia.comment())){
                context.reportIssue(this, tree, METHOD_ISSUE_MSG);
            }
        }
        super.visitClass(tree);
    }


    @Override
    public void visitMethod(MethodTree tree) {
        if (isService(tree)) {
            //判断类注释是否大于30个字符串
            List<SyntaxTrivia> trivias = tree.modifiers().firstToken().trivias();
            if (null==trivias || trivias.size()==0){
                context.reportIssue(this, tree, CLASS_ISSUE_MSG);
            }
            // 验证第一条注释
            SyntaxTrivia firstTrivia =trivias.get(0);
            if (checkValueLength(firstTrivia.comment())){
                context.reportIssue(this, tree, CLASS_ISSUE_MSG);
            }
        }
        super.visitMethod(tree);
    }




    private boolean isService(ClassTree tree){
        boolean flag =false;
        if ("INTERFACE".equals(tree.kind().name())){
            if (StringUtils.contains(tree.simpleName().name(),"Service")){
                flag=true;
            }
        }
        return flag;
    }


    private boolean isService(MethodTree tree){
        boolean flag =false;
        if ("INTERFACE".equals(tree.kind().name())){
            if (StringUtils.contains(tree.simpleName().name(),"Service")){
                flag=true;
            }
        }
        return flag;
    }



    /**
     * 判断注释 是否大于 30个字符串
     * @param value
     * @return true 不符合规则
     */
    private boolean checkValueLength(String value){
        if (30>=value.length()){
            return true;
        }else {
            return false;
        }
    }
}
