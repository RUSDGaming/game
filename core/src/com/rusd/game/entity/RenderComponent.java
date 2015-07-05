package com.rusd.game.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by shane on 7/4/15.
 */
public class RenderComponent {
    public enum Shape {
        RECTANGLE,
        CIRCLE
    }

    private Shape shape;

    private Entity entity;


    private Integer textureId;

    private Rectangle rectangle;
    private Circle circle;


    public void renderShape(ShapeRenderer sr) {


        switch (shape) {
            case RECTANGLE: {
                sr.rect(entity.getBodyComponent().getPosition().x, entity.getBodyComponent().getPosition().y, rectangle.width, rectangle.height);
                break;
            }
            case CIRCLE: {
                sr.circle(entity.getBodyComponent().getPosition().x, entity.getBodyComponent().getPosition().y, circle.radius);
                break;
            }

        }

    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Integer getTextureId() {
        return textureId;
    }

    public void setTextureId(Integer textureId) {
        this.textureId = textureId;
    }
}
