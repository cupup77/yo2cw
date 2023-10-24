package com.mygdx.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.Viewport;

public class CustomViewport extends Viewport {
    private boolean bottom = true;

    public CustomViewport(boolean bottom, float worldWidth, float worldHeight) {
        this(bottom, worldWidth, worldHeight, new OrthographicCamera());
    }

    public CustomViewport (boolean bottom, float worldWidth, float worldHeight, Camera camera) {
        this.bottom = bottom;
        setWorldSize(worldWidth, worldHeight);
        setCamera(camera);
    }

    public void update (int screenWidth, int screenHeight, boolean centerCamera) {
        Vector2 scaled = Scaling.fit.apply(getWorldWidth(), getWorldHeight(), screenWidth / 2f, screenHeight);
        int viewportWidth = Math.round(scaled.x);
        int viewportHeight = Math.round(scaled.y);

        // setScreenBounds(leftSide ? 0 : viewportWidth, (screenHeight - viewportHeight) / 2, viewportWidth, viewportHeight);
        setScreenBounds((screenWidth-viewportWidth)/2, bottom ? 0 : viewportHeight, viewportWidth, viewportHeight);

        apply(centerCamera);
    }
}
