package com.rusd.game.client;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.rusd.game.constants.Constants;
import com.rusd.game.network.HudInfo;

/**
 * Created by shane on 7/20/15.
 */
public class HUD {
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private SpriteBatch batch = new SpriteBatch();
    private OrthographicCamera hudCam = new OrthographicCamera();
    private HudInfo hudInfo = null;
    private BitmapFont font = new BitmapFont();

    public HUD() {
        init();
    }

    private void init() {
        hudCam.setToOrtho(false, Constants.VIEWPORTWIDTH, Constants.VIEWPORTHEIGHT);
    }

    public void render() {

        // draw a box on the bottom of the screen
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(0, 0, Constants.WIDTH, 60);
        shapeRenderer.end();

        batch.begin();
        font.draw(batch, "Name", 0, 40);
        font.draw(batch, "Health", 0, 20);
        font.draw(batch, "Kills", 0, 0);
        batch.end();


    }
}
