package br.com.six2six.xstreamwriterdsl.util;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;

public class ReflectionUtils {

    @SuppressWarnings("unchecked")
    public static <T> T invokeGetter(Object bean, String attribute) {
        try {
            return (T) getPropertyUtilsBean().getProperty(bean, attribute);
        } catch (Exception e) {
        	throw new IllegalArgumentException("error invoking get method for " + attribute);   
        }
    }
    
    @SuppressWarnings("unchecked")
	public static <T> T invokeRecursiveGetter(Object bean, String path) {
        Object lastValue = null;
        Object lastBean = bean;
        for (String propertyItem : path.split("\\.")) {
            lastValue = invokeGetter(lastBean, propertyItem);
            lastBean = lastValue;
            if (lastValue == null) {
                break;
            }
        }
        return (T) lastValue;
    }
    
    private static PropertyUtilsBean getPropertyUtilsBean() {
    	return BeanUtilsBean.getInstance().getPropertyUtils();
    }
}
