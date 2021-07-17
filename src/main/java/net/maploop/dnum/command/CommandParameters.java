package net.maploop.dnum.command;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandParameters {
    String permission() default "";
    String[] aliases() default {};
    String description() default "";
    String usage() default "/<command>";
    boolean inGameOnly() default false;
}
