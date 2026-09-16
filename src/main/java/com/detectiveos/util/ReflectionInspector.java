package com.detectiveos.util;
import java.lang.reflect.Field;
import java.util.Arrays;
public final class ReflectionInspector {
    private ReflectionInspector(){}
    public static String describe(Object object){
        StringBuilder s=new StringBuilder("Reflection: ").append(object.getClass().getSimpleName()).append(" fields => ");
        Arrays.stream(object.getClass().getDeclaredFields()).map(Field::getName).forEach(f->s.append(f).append(", "));
        return s.toString();
    }
}
