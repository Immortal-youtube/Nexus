package commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ICommand {
    public String commandKey() default "lol";
    public String description() default "no need";
    public boolean containsOption() default true;
}
