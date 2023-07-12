package com.madaoo.sonarqube.checks.feign;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.*;

import java.util.List;

/**
 * 校验feign 接口
 * 类 和 方法均需要有注释 且注释大于30个字符
 */
@Rule(key = "FeignClientCommentaryCheck")
public class FeignClientCommentaryCheck extends BaseTreeVisitor implements JavaFileScanner{
    private JavaFileScannerContext context;
    private static final String CLASS_ISSUE_MSG = "feignClient需要添加注释，描述feignClient类的关键信息！";
    private static final String METHOD_ISSUE_MSG = "feign接口需要添加注释，描述当前方法的关键信息！";
    private static final String ANNOTATION_NAME ="FeignClient";

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitClass(ClassTree tree) {
        if (hasFeignClient(tree)){
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
        if (hasFeignClient(tree)) {
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


    /**
     * 判断是否有 @FeignClient 注解
     * @param tree
     * @return
     */
    private boolean hasFeignClient(ClassTree tree){
        boolean flag =false;
        for (AnnotationTree annotation : tree.modifiers().annotations()) {
            IdentifierTree identifier = (IdentifierTree) annotation.annotationType();
            if (ANNOTATION_NAME.equals(identifier.name())){
                flag=true;
            }
        }
        return flag;
    }

    /**
     * 判断是否有 @FeignClient 注解
     * @param tree
     * @return
     */
    private boolean hasFeignClient(MethodTree tree){
        ClassTree classTree = (ClassTree) tree.parent();
        boolean flag =false;
        for (AnnotationTree annotation : classTree.modifiers().annotations()) {
            IdentifierTree identifier = (IdentifierTree) annotation.annotationType();
            if (ANNOTATION_NAME.equals(identifier.name())){
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
