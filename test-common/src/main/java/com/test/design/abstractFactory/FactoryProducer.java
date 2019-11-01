package com.test.design.abstractFactory;

import com.test.design.abstractFactory.color.ColorFactory;
import com.test.design.abstractFactory.shape.ShapeFactory;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zelei.fan
 * @date 2019/10/31 17:34
 */
public class FactoryProducer {

    public static AbstractFactory getFactory(String name){
        if (StringUtils.equals(name, "shape")){
            return new ShapeFactory();
        }else if (StringUtils.equals(name, "color")){
            return new ColorFactory();
        }else {
            return null;
        }
    }
}
