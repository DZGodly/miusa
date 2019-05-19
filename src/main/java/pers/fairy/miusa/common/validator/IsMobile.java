package pers.fairy.miusa.common.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author ：DZGodly
 * @date ：Created in 2019/4/5 14:24
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {IsMobileValidator.class}
)
public @interface IsMobile {
    // required 和 message 是自定义参数，groups 和 payload 是两个必须包含的参数。
    boolean required() default true;

    String message() default "手机号码格式错误！"; // 校验失败时的错误信息

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
