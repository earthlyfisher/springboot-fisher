package com.wyp.boot.earthlyfisher.validator;

import com.wyp.boot.earthlyfisher.domain.UserDomain;
import com.wyp.boot.earthlyfisher.pojo.User;
import com.wyp.boot.earthlyfisher.service.UserService;
import com.wyp.boot.earthlyfisher.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * 用户增加或者修改validator
 * Created by earthlyfisher on 2017/2/21.
 */
@Component
public class UserCUValidator implements Validator {

    private UserService userService;

    @Autowired
    public UserCUValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDomain.class.isAssignableFrom(clazz)
                || User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target instanceof UserDomain) {
            UserDomain domain = (UserDomain) target;
            if (StringUtils.isNullOrEmpty(domain.getUser().getName())
                    || StringUtils.isNullOrEmpty(domain.getUser().getPassword())
                    || StringUtils.isNullOrEmpty(domain.getRePwd())) {
                errors.rejectValue("user.field", "user.field.invalid", "user field is invalid");
                return;
            }

            if (!domain.getUser().getPassword().equals(domain.getRePwd())) {
                errors.rejectValue("user.password.different", "user.password.different", "user password is different");
                return;
            }
        } else if (target instanceof User) {
            ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");
            ValidationUtils.rejectIfEmpty(errors, "password", "password.empty");
        }

    }
}
