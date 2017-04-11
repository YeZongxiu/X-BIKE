package com.ucmed.common.api.logger;

import java.lang.annotation.*;

/**
 * Created by ucmed on 2017/3/15.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Log {
    String[] apiName();

    String describ() default "";
}