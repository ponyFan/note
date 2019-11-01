package com.test.design.abstractFactory.shape;

import com.test.design.abstractFactory.AbstractFactory;
import com.test.design.abstractFactory.color.Color;

/**
 * @author zelei.fan
 * @date 2019/10/30 17:50
 */
public class ShapeFactory extends AbstractFactory {
    @Override
    public Color getColor(String colorName) {
        return null;
    }

    @Override
    public Shape getShape(String shapeName) {
        switch (shapeName){
            case "circle":
                return new Circle();
            case "rectangle":
                return new Rectangle();
            case "square":
                return new Square();
            default:
                return null;
        }
    }
}
