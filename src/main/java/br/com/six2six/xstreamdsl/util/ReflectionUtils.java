package br.com.six2six.xstreamdsl.util;

import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.defaultIfEmpty;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;

public class ReflectionUtils {

    @SuppressWarnings("unchecked")
    public static <T> T invokeGetter(Object bean, String attribute) {
        try {
            return (T) getPropertyUtilsBean().getProperty(bean, attribute);
        } catch (Exception e) {
        	throw new IllegalArgumentException(format("error invoking get method for %s", attribute), e);   
        }
    }
    
    @SuppressWarnings("unchecked")
	public static <T> T invokeRecursiveGetter(Object bean, String path) {
        Object lastValue = null;
        Object lastBean = bean;
        for (String propertyItem : path.split("\\.")) {
            lastValue = invokeGetter(lastBean, propertyItem);
            lastBean = lastValue;
            if (lastValue == null) break;
        }
        return (T) lastValue;
    }
    
    public static <T> void invokeSetter(Object bean, String attribute, Object value) {
        try {
            getPropertyUtilsBean().setProperty(bean, attribute, value);
        } catch (Exception e) {
        	throw new IllegalArgumentException(format("error invoking set method for %s", attribute), e);
        }   
    }
    
    public static void invokeRecursiveSetter(Object bean, String attribute, Object value) {
	    invokeSetter(prepareInvokeRecursiveSetter(bean, attribute), attribute.substring(attribute.lastIndexOf(".") + 1), value);
	}

    public static <T> T newInstance(Class<T> clazz) {
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return clazz.cast(constructor.newInstance());
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
    
    private static Object prepareInvokeRecursiveSetter(Object bean, String attribute) {
        Object targetBean = bean;
        Object lastBean = null;
        
        int lastAttributeIdx = attribute.lastIndexOf(".");
        
        String path  = null;
        if (lastAttributeIdx > 0) {
            path = defaultIfEmpty(attribute.substring(0, lastAttributeIdx), null);
        }
        
        if (path != null) {
            for (String propertyItem : path.split("\\.")) {
                lastBean = targetBean;
                targetBean = invokeGetter(targetBean, propertyItem);
                if (targetBean == null) {
                    targetBean = newInstance(invokeRecursiveType(lastBean, propertyItem));
                    invokeSetter(lastBean, propertyItem, targetBean);                     
                }
            }
        }
        
        return targetBean;
    }
    
    public static Class<?> invokeRecursiveType(Object bean, String attribute) {
    	return invokeRecursiveField(bean, attribute).getType();
    }
    
    private static Field invokeRecursiveField(Object bean, String attribute) {
        Class<?> targetBeanClass = bean.getClass();
        
        for (String propertyItem : attribute.split("\\.")) {
        	Class<?> superClass = targetBeanClass.getSuperclass();
        	
        	do {
                try {
                    Field field = targetBeanClass.getDeclaredField(propertyItem);
                    if (field != null) return field;
                } catch (NoSuchFieldException e) {
                    targetBeanClass = superClass;
                } 
            } while (!Object.class.equals(superClass));
        }
        
        throw new IllegalArgumentException(format("field %s doesn't exists", attribute));
    }
    
    private static PropertyUtilsBean getPropertyUtilsBean() {
    	return BeanUtilsBean.getInstance().getPropertyUtils();
    }
}
