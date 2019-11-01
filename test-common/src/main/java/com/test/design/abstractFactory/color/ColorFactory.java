package com.test.design.abstractFactory.color;

import com.test.design.abstractFactory.AbstractFactory;
import com.test.design.abstractFactory.shape.Shape;

/**
 * @author zelei.fan
 * @date 2019/10/30 17:42
 */
public class ColorFactory extends AbstractFactory {
    @Override
    public Color getColor(String colorName) {
        switch (colorName){
            case "red":
                return new Red();
            case "green":
                return new Green();
            case "blue":
                return new Blue();
            default:
                return null;
        }
    }

    @Override
    public Shape getShape(String shapeName) {
        return null;
    }
}
