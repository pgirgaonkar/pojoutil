/*
 * MIT LICENSE
 *
 * Copyright <2020> <www.ansatz.in>

 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package in.ansatz.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class PojoUtils {

    public static <T,U> U copyProperties(T source, Class<U> c) throws PojoUtilException {
        U target = null;
        try {
            target = c.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            throw new PojoUtilException("Error while copying properties  of " + source.getClass().getName() + " to " + target.getClass().getName(), e);
        }

    }

    public static <T,U> List<U> copyProperties(List<T> source, Class<U> c) throws PojoUtilException {
        if(null == source || source.isEmpty()) return new LinkedList<>();
        List<U> l = new LinkedList<>();
        for(T s : source){
            l.add(copyProperties(s,c));
        }
        return l;

    }

    public static <T,U> List<U> copyNonNullProperties(List<T> source, Class<U> c) throws PojoUtilException {
        if(null == source || source.isEmpty()) return new LinkedList<>();
        List<U> l = new LinkedList<>();
        for(T s : source){
            l.add(copyNonNullProperties(s,c));
        }
        return l;

    }

    public static <T,U> U copyNonNullProperties(T source, Class<U> c) throws PojoUtilException {
        U target = null;
        try {
            target = c.getDeclaredConstructor().newInstance();
            copyNonNullProperties(source, target);
            return target;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new PojoUtilException("Error while copying properties  of " + source.getClass().getName() + " to " + target.getClass().getName(), e);
        }

    }
    public static <T,U> U copyNonNullProperties(T source, U destination){
        BeanUtils.copyProperties(source, destination,
                getNullPropertyNames(source));
        return destination;
    }

    private static  String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet();
        for(java.beans.PropertyDescriptor pd : pds) {
            //check if value of this property is null then add it to the collection
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        return emptyNames.stream().toArray(String[]::new);
    }
}
