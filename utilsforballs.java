package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

public class utilsforballs {
   public static float ppm = 1000.0f; public void pos(Button button, CustomViewport vp, int a, int b){
        vp.apply();
        button.setPosition(a/ppm,b/ppm);
    }
    public static Body createobject(World world, int x, int y, BodyDef.BodyType type, boolean circle, int a, int b,CustomViewport vp){
        vp.apply();
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = type;
        bodyDef.position.set(new Vector2(x/ppm, y/ppm));
        Body body = world.createBody(bodyDef);
        if(circle){
            CircleShape shape = new CircleShape();
            shape.setRadius(40/ppm);
            body.createFixture(shape,0.5f);
        }
        else {
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(a/ppm,b/ppm);
            body.createFixture(shape,0.5f);

        }

        return body;

    }
}
