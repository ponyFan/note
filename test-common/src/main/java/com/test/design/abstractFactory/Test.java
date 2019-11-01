package com.test.design.abstractFactory;

import com.test.design.abstractFactory.color.Color;
import com.test.design.abstractFactory.shape.Shape;

/**
 * 抽象工厂模式：
 *
 *                                                       |----circle<shape>
 *                   |----shapeFactory<abstractFactory>--|----rectangle<shape>
 *                   |                                   |----square<shape>
 * abstractFactory---
 *                   |                                   |----red<color>
 *                   |----colorFactory<abstractFactory>--|----green<color>
 *                                                       |----blue<color>
 * @author zelei.fan
 * @date 2019/10/30 15:30
 */
public class Test {

    public static void main(String[] args) {
        AbstractFactory shape = FactoryProducer.getFactory("shape");
        Shape square = shape.getShape("square");
        square.draw();
        AbstractFactory color = FactoryProducer.getFactory("color");
        Color blue = color.getColor("blue");
        blue.fill();
    }
}
