package com.madaoo.sonarqube;

import com.google.common.collect.ImmutableList;
import com.madaoo.sonarqube.checks.code.CodeBlockMaxLineCheck;
import com.madaoo.sonarqube.checks.comment.AbsAndInterMethodMustDocCheck;
import com.madaoo.sonarqube.checks.comment.ClassMethodVarDocCheck;
import com.madaoo.sonarqube.checks.comment.ClassMustHaveAuthorCheck;
import com.madaoo.sonarqube.checks.comment.EnumConstantsMustHaveCommentCheck;
import com.madaoo.sonarqube.checks.concurrent.AvoidExecutorsCreatePoolCheck;
import com.madaoo.sonarqube.checks.concurrent.CreateThreadShouldPoolCheck;
import com.madaoo.sonarqube.checks.constant.AvoidUseMagicNumberCheck;
import com.madaoo.sonarqube.checks.exception.AvoidReturnInFinallyCheck;
import com.madaoo.sonarqube.checks.exception.MethodReturnWrapperCheck;
import com.madaoo.sonarqube.checks.flowcontrol.*;
import com.madaoo.sonarqube.checks.naming.*;
import com.madaoo.sonarqube.checks.oop.*;
import com.madaoo.sonarqube.checks.other.AvoidBeanUtilsCopyCheck;
import com.madaoo.sonarqube.checks.set.CollectionInitShouldAssignCapacityCheck;
import com.madaoo.sonarqube.checks.set.SubListNotCastArrayListCheck;
import org.sonar.plugins.java.api.JavaCheck;

import java.util.List;

/**
 * 用于规则扩展
 *
 * @author chenzhou
 */
public final class RulesList {

    private RulesList() {
    }

    public static List<Class> getChecks() {
        return ImmutableList.<Class>builder().addAll(getJavaChecks()).addAll(getJavaTestChecks()).build();
    }

    public static List<Class<? extends JavaCheck>> getJavaChecks() {
        return ImmutableList.<Class<? extends JavaCheck>>builder().add(AbstractClassNameCheck.class)
                .add(ArrayNameCheck.class).add(BooleanPropertyNameCheck.class)
                .add(ExceptionClassNameCheck.class).add(LowerCameCaseVariableNameCheck.class)
                .add(PackageNameCheck.class).add(TestClassNameCheck.class)
                .add(UpperCameCaseClassNameCheck.class).add(AviodUseDeprecatedCheck.class)
                .add(OverrideAnnotationAddCheck.class).add(AvoidUseMagicNumberCheck.class)
                .add(ClassMustHaveAuthorCheck.class).add(ClassMethodVarDocCheck.class)
                .add(EnumConstantsMustHaveCommentCheck.class).add(AvoidNewDateGetTimeCheck.class)
                .add(AvoidBeanUtilsCopyCheck.class)
                .add(RandomNotToIntCheck.class).add(AvoidExecutorsCreatePoolCheck.class)
                .add(SubListNotCastArrayListCheck.class).add(CollectionInitShouldAssignCapacityCheck.class)
                .add(CollectionToArrayCheck.class).add(AvoidReturnInFinallyCheck.class)
                .add(AvoidComplexConditionCheck.class).add(WrapperCompareEqualsCheck.class)
                .add(MethodReturnWrapperCheck.class).add(MultilineBlocksCurlyCheck.class)
                .add(AbsAndInterMethodMustDocCheck.class).add(RegexPatternCompileCheck.class)
                .add(CreateThreadShouldPoolCheck.class).add(ConstantNameCheck.class)
                .add(AvoidInstanceForStaticMethodCheck.class).add(UnderlineDollarNameCheck.class)
                .add(StringOrNotNullInsideEquailsCheck.class).add(SwitchHaveDefaultCheck.class)
                .add(SwitchCaseEndWithBreakCheck.class).add(SwitchDefaultMustLastCheck.class)
                .add(CodeBlockMaxLineCheck.class)
                .build();
    }

    public static List<Class<? extends JavaCheck>> getJavaTestChecks() {
        return ImmutableList.<Class<? extends JavaCheck>>builder().build();
    }
}
