package cn.allandeng.blog.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
* @ClassName MyBeanUtils
* @Date: 2020/2/10 13:00
* @Description: 自定义的Bean工具类
* @Author: Allan Deng
* @Version: 1.0
**/
public class MyBeanUtils {


    /**
     * @Title getNullPropertyNames
     * @Author Allan Deng
     * @Description 获取所有的属性值为空属性名数组 
     * @Date 13:22 2020/2/10
     * @Param [source]
     * @return java.lang.String[]
     **/
    public static String[] getNullPropertyNames(Object source) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds =  beanWrapper.getPropertyDescriptors();
        List<String> nullPropertyNames = new ArrayList<>();
        for (PropertyDescriptor pd : pds) {
            String propertyName = pd.getName();
            if (beanWrapper.getPropertyValue(propertyName) == null) {
                nullPropertyNames.add(propertyName);
            }
        }
        return nullPropertyNames.toArray(new String[nullPropertyNames.size()]);
    }

}
