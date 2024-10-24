package com.fren_gor.visualFixer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;

import org.jetbrains.annotations.Nullable;

/**
 * Reflection utility.
 */
public final class ReflectionUtil {

    /**
     * Build a new class getting the proper constructor from parameters
     *
     * @param clazz The class of the object of witch you want the instance
     * @param parameters The contructors parameters
     * @return The new instance
     */
    public static Object newInstance(Class<?> clazz, Object... parameters) {
        Class<?>[] classes = new Class<?>[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            classes[i] = parameters[i].getClass();
        }

        try {
            Constructor<?> c = clazz.getDeclaredConstructor(classes);
            c.setAccessible(true);
            return c.newInstance(parameters);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Invoke method in c class
     *
     * @param object The object where the method is invoked
     * @param method The name of the method
     * @param parameters The object uses as parameters
     * @return What the method return. If the method is void, return null
     */
    @Nullable
    public static Object invoke(Object object, String method, Object... parameters) {
        Method m = null;
        Class<?>[] classes = new Class<?>[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            classes[i] = parameters[i].getClass();
        }

        try {
            m = object.getClass().getDeclaredMethod(method, classes);
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            return null;
        }
        Object o1;
        try {
            boolean b = m.isAccessible();
            m.setAccessible(true);
            o1 = m.invoke(object, parameters);
            m.setAccessible(b);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
        return o1;
    }

    /**
     * Set a field
     *
     * @param object The object where the field is set
     * @param field The name of the field
     * @param newValue The new value of the field
     * @return The object
     */
    @Nullable
    public static Object setField(Object object, String field, Object newValue) {
        Field f;
        try {
            f = object.getClass().getDeclaredField(field);
        } catch (NoSuchFieldException | SecurityException e) {
            throw new ReflectionException(e);
        }

        try {
            boolean b = f.isAccessible();
            f.setAccessible(true);
            f.set(object, newValue);
            f.setAccessible(b);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new ReflectionException(e);
        }

        return object;
    }

    /**
     * Get a field value
     *
     * @param object The object from which the represented field's value is to be
     *         extracted
     * @param field The field name
     * @return The field value
     */
    @Nullable
    public static Object getField(Object object, String field) {
        Field f;
        try {
            f = object.getClass().getDeclaredField(field);
        } catch (NoSuchFieldException | SecurityException e) {
            throw new ReflectionException(e);
        }
        Object o;
        try {
            boolean b = f.isAccessible();
            f.setAccessible(true);
            o = f.get(object);
            f.setAccessible(b);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new ReflectionException(e);
        }

        return o;
    }

    /**
     * Cast a object to a class
     *
     * @param object The object to cast
     * @param clazz The class
     * @return The casted object
     */
    @Nullable
    public static Object cast(Object object, Class<?> clazz) {
        try {
            return clazz.cast(object);
        } catch (Exception e) {
            throw new ReflectionException(e);
        }
    }

    /**
     * @param name The class name
     * @return The NMS class
     * @author michel_0
     */
    public static Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + getCompleteVersion() + "." + name);
        } catch (ClassNotFoundException e) {
            throw new ReflectionException("Can't find NMS Class! (" + "net.minecraft.server." + getCompleteVersion() + "." + name + ")");
        }
    }

    /**
     * @param name The class name
     * @return The CraftBukkit class
     * @author michel_0
     */
    public static Class<?> getCBClass(String name) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + getCompleteVersion() + "." + name);
        } catch (ClassNotFoundException e) {
            throw new ReflectionException("Can't find CB Class! (" + "org.bukkit.craftbukkit." + getCompleteVersion() + "." + name + ")");
        }
    }

    /**
     * Get the server version
     *
     * @return The server version
     */
    public static String getCompleteVersion() {
        return Bukkit.getServer().getClass().getName().split("\\.")[3];
    }

    /**
     * Check if the server is in 1.7
     *
     * @return If the server is in 1.7
     */
    public static boolean versionIs1_7() {
        return getCompleteVersion().startsWith("v1_7");
    }

}
