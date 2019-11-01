package com.test.design.abstractFactory;

import com.test.design.abstractFactory.color.Color;
import com.test.design.abstractFactory.shape.Shape;

/**
 * @author zelei.fan
 * @date 2019/10/30 17:36
 */
public abstract class AbstractFactory {
    public abstract Color getColor(String colorName);
    public abstract Shape getShape(String shapeName);
}
