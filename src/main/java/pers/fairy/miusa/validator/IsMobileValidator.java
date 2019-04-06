package pers.fairy.miusa.validator;

import org.springframework.util.StringUtils;
import pers.fairy.miusa.utils.ValidationUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author ：DZGodly
 * @date ：Created in 2019/4/5 14:33
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {
    private boolean required = true;
    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(required || !StringUtils.isEmpty(s)) {
            return ValidationUtil.isMobile(s);
        }
        return true;
    }
}
