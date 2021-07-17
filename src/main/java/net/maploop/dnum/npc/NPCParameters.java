package net.maploop.dnum.npc;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface NPCParameters {
    String idname();

    String name() default "NPC";
    String aboveName() default "";
    int id();
    String signature();
    String texture();

    String world() default "";
    double x() default 0.0;
    double y() default 0.0;
    double z() default 0.0;

    float yaw() default 0;
    float pitch() default 0;

    boolean looking() default false;
}
