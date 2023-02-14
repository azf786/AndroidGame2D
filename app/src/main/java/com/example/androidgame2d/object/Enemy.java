package com.example.androidgame2d.object;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.androidgame2d.Game;
import com.example.androidgame2d.GameLoop;
import com.example.androidgame2d.R;

/**
 * Enemy is an object that follows the player
 */
public class Enemy extends Circle{
    private static final double SPEED_PIXELS_PER_SECOND = Player.SPEED_PIXELS_PER_SECOND * 0.6;
    private static final double MAX_SPEED = SPEED_PIXELS_PER_SECOND/ GameLoop.MAX_UPS;
    private static final int SPAWN_PER_MINUTE = 20;
    private static final double SPAWN_PER_SECOND = SPAWN_PER_MINUTE/60.0;
    private static final double UPDATES_PER_SPAWN = GameLoop.MAX_UPS/SPAWN_PER_SECOND;
    private static double updatesUntilNextSpawn = UPDATES_PER_SPAWN;
    private Player player;

    public Enemy(Context context, Player player, double positionX, double positionY, double radius) {
        super(context, ContextCompat.getColor(context, R.color.enemy), positionX, positionY, radius);

        this.player = player;
    }

    public Enemy(Context context, Player player) {
        this(context, player, Math.random()*1000, Math.random()*1000, 30);
    }

    /**
     * readyToSpawn if its time to spawn a new enemy based on a pre determined number of enemy per
     * minute
     * @return
     */
    public static boolean readyToSpawn() {
        if (updatesUntilNextSpawn <= 0) {
            updatesUntilNextSpawn += UPDATES_PER_SPAWN;
            return true;
        } else {
            updatesUntilNextSpawn--;
            return false;
        }
    }

    @Override
    public void update() {
        // ==========
        // Update velocity of the enemy so that the velocity is in the direction of the player
        // ==========

        // Calculate vector from enemy to player (in x any)
        double distanceToPlayerX = player.getPositionX() - positionX;
        double distanceToPlayerY = player.getPositionY() - positionY;

        // Calculate (absolute) distance between enemy (this) and player
        double distanceToPlayer = getDistanceBetweenObjects(this, player);

        // Calculate direction from enemy to player
        double directionX = distanceToPlayerX/distanceToPlayer;
        double directionY = distanceToPlayerY/distanceToPlayer;

        // Set velocity in the direction to the player
        if (distanceToPlayer > 0) {
            velocityX = directionX*MAX_SPEED;
            velocityY = directionY*MAX_SPEED;
        } else {
            velocityX = 0;
            velocityY = 0;
        }

        //Update the position of the enemy
        positionX += velocityX;
        positionY += velocityY;
    }
}
