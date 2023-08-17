package com.madaoo.sonarqube.checks.comment;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.*;

/**
 * 校验Controller 接口
 * 类 和 方法均需要有注释 且注释大于30个字符
 */
@Rule(key = "ControllerCommentaryCheck")
public class ControllerCommentaryCheck extends BaseTreeVisitor implements JavaFileScanner{
    private JavaFileScannerContext context;
    private static final String ANNOTATION_NAME ="RestController";
    private static final String ANNOTATION_NAME_1 ="ApiOperation";
    private static final String ANNOTATION_NAME_2 ="ApiLog";

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }



    @Override
    public void visitMethod(MethodTree tree) {
        if (isController(tree)) {
            if (isRestMethod(tree)){
                if (hasNotApiOperation(tree)){
                    context.reportIssue(this, tree, "api接口需要使用注解@ApiOperation");
                }
                if (hasNotApiLog(tree)){
                    context.reportIssue(this, tree, "api接口需要使用注解@ApiLog");
                }
            }
        }
        super.visitMethod(tree);
    }


    /**
     * 判断是否有 @FeignClient 注解
     * @param tree
     * @return
     */
    private boolean isController(MethodTree tree){
        boolean flag =false;
        ClassTree classTree = (ClassTree) tree.parent();
        for (AnnotationTree annotation : classTree.modifiers().annotations()) {
            IdentifierTree identifier = (IdentifierTree) annotation.annotationType();
            if (ANNOTATION_NAME.equals(identifier.name())){
                flag=true;
            }
        }
        return flag;
    }
    private boolean isRestMethod(MethodTree tree){
        boolean flag =false;
        for (AnnotationTree annotation : tree.modifiers().annotations()) {
            IdentifierTree identifier = (IdentifierTree) annotation.annotationType();
            if ("GetMapping".equals(identifier.name()) || "PostMapping".equals(identifier.name()) || "PutMapping".equals(identifier.name()) || "DeleteMapping".equals(identifier.name())){
                flag=true;
            }
        }
        return flag;
    }



    private boolean hasNotApiOperation(MethodTree tree){
        boolean flag =true;
        for (AnnotationTree annotation : tree.modifiers().annotations()) {
            IdentifierTree identifier = (IdentifierTree) annotation.annotationType();
            if (ANNOTATION_NAME_1.equals(identifier.name())){
                flag=false;
            }
        }
        return flag;
    }

    private boolean hasNotApiLog(MethodTree tree){
        boolean flag =true;
        for (AnnotationTree annotation : tree.modifiers().annotations()) {
            IdentifierTree identifier = (IdentifierTree) annotation.annotationType();
            if (ANNOTATION_NAME_2.equals(identifier.name())){
                flag=false;
            }
        }
        return flag;
    }

}
