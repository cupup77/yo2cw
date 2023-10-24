package com.mygdx.game;

import static com.mygdx.game.utilsforballs.ppm;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;


import dev.lyze.projectTrianglePlatforming.TiledObjectLayerToBox2d;
import dev.lyze.projectTrianglePlatforming.TiledObjectLayerToBox2dOptions;

public class learningvp  extends ApplicationAdapter {
    CustomViewport topview,bottomview; SpriteBatch batch;
    World world;Box2DDebugRenderer dr;Body body;  Stage stage;
    Skin leftskin;
    Skin rightskin;
    Skin jumpskin;
    Button leftbutton;
    Button rightbutton;Texture texture;
    Button jumpbutton; utilsforballs ufb;
    TiledMap map;OrthogonalTiledMapRenderer renderer ;
    int screenwidth,screenheight;

    public void create() {ufb=new utilsforballs();stage = new Stage(new CustomViewport(true,400,400));
        map = new TmxMapLoader().load("lvl1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map,1/ppm);
        world = new World(new Vector2(0,-9.8f),true);
        dr = new Box2DDebugRenderer();
        batch = new SpriteBatch(); texture = new Texture("stone.jpg");
        screenwidth=Gdx.graphics.getWidth();screenheight=Gdx.graphics.getHeight();
        topview = new CustomViewport(false,400/ppm,800/ppm);
        bottomview = new CustomViewport(true,800/ppm,800/ppm);
        body = utilsforballs.createobject(world,50,50, BodyDef.BodyType.DynamicBody,true,0,0,topview);
        var builder = new TiledObjectLayerToBox2d(TiledObjectLayerToBox2dOptions.builder()
                .scale(1f / 1000)
                .throwOnInvalidObject(false)
                .build());
        builder.parseAllLayers(map, world);


        bottomview.apply();
        leftskin  = new Skin(Gdx.files.internal("buttons/left.json"));
        rightskin = new Skin(Gdx.files.internal("buttons/right.json"));
        jumpskin   = new Skin(Gdx.files.internal("buttons/jump.json"));
        leftbutton = new Button(leftskin);
        rightbutton = new Button(rightskin);
        jumpbutton = new Button(jumpskin);
     //  ufb.pos(jumpbutton,bottomview,0,100);
       jumpbutton.sizeBy(100/ppm,100/ppm);
       stage.addActor(leftbutton);
        stage.addActor(jumpbutton);
        stage.addActor(rightbutton);

      leftbutton.setPosition(0,100/ppm);
      rightbutton.setPosition(200/ppm,200/ppm);
      jumpbutton.setPosition(400/ppm,400/ppm);

        Gdx.input.setInputProcessor(new InputMultiplexer(stage));
        topview.update(screenwidth,screenheight);
        bottomview.update(screenwidth,screenheight);
    }


    public void render() {
        ScreenUtils.clear(0.9f, 0.1f, 0.5f, 1);
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            body.applyForceToCenter(new Vector2(2/ppm,0),true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            body.applyForceToCenter(new Vector2(-2/ppm,0),true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            body.applyForceToCenter(new Vector2(0,80/ppm),true);
        }
        topview.apply();
        topview.getCamera().position.set(body.getPosition().x,body.getPosition().y,0);
        renderer.setView((OrthographicCamera) topview.getCamera());
        renderer.render();
        batch.setProjectionMatrix(topview.getCamera().combined);
        world.step(Gdx.graphics.getDeltaTime(),6,2);
        dr.render(world,topview.getCamera().combined);

        topview.update(screenwidth,screenheight);
        bottomview.update(screenwidth,screenheight);

        bottomview.apply();
        batch.setProjectionMatrix(bottomview.getCamera().combined);
        batch.begin();
        batch.draw(texture,0,0,texture.getWidth()/ppm,texture.getHeight()/ppm);
        batch.end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f)); // Update the stage
        stage.draw();
        topview.update(screenwidth,screenheight);
        bottomview.update(screenwidth,screenheight);
    }



}
