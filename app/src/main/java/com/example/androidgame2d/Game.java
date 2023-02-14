package com.example.androidgame2d;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.androidgame2d.graphics.SpriteSheet;
import com.example.androidgame2d.object.Circle;
import com.example.androidgame2d.object.Enemy;
import com.example.androidgame2d.object.Joystick;
import com.example.androidgame2d.object.Player;
import com.example.androidgame2d.object.Spell;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Game manages all objects in the game and is responsible for updating and rendering
 * all objects on the screen
 */
public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Joystick joystick;
    private GameLoop gameLoop;
    private Player player;
    private List<Enemy> enemyList;
    private List<Spell> spellList;
    public Game(Context context) {
        super(context);

        // Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        SpriteSheet spriteSheet = new SpriteSheet(getContext());

        // Initialize game objects
        joystick = new Joystick(350, 1100, 90, 60);
        player = new Player(getContext(), 2*500, 500, 30, joystick, spriteSheet.getPlayerSprite());
        gameLoop = new GameLoop(this, surfaceHolder);
        enemyList = new ArrayList<>();
        spellList = new ArrayList<>();

        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Handle touch event actions
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (joystick.isPressed((double) event.getX(), (double) event.getY())) {
                    joystick.setIsPressed(true);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if(joystick.getPressed()) {
                    joystick.setActuator((double) event.getX(), (double) event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
                joystick.setIsPressed(false);
                joystick.resetActuator();
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawUPS(canvas);
        drawFPS(canvas);

        joystick.draw(canvas);
        player.draw(canvas);

        for (Enemy enemy : enemyList) {
            enemy.draw(canvas);
        }
    }

    public void drawUPS(Canvas canvas) {
        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS " + averageUPS, 100, 100, paint);
    }

    public void drawFPS(Canvas canvas) {
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS " + averageFPS, 100, 200, paint);
    }

    public void update() {
        joystick.update();

        player.update();

        // Spawn enemy
        if (Enemy.readyToSpawn()) {
            enemyList.add(new Enemy(getContext(), player));
        }

        // Update state of each enemy
        for (Enemy enemy : enemyList) {
            enemy.update();
        }
        
        // Check for collision
        Iterator<Enemy> enemyIterator = enemyList.iterator();
        while (enemyIterator.hasNext()) {
            if (Circle.isColliding(enemyIterator.next(), player)) {
                //remove enemy
                enemyIterator.remove();
            }
        }
    }
}
