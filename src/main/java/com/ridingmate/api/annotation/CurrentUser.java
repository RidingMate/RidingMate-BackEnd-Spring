package com.ridingmate.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Target({
        ElementType.TYPE,
        ElementType.PARAMETER
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
//유저 서비스에서 리턴한 객체를 바로 파라미터로 사용 가능
public @interface CurrentUser {
}
