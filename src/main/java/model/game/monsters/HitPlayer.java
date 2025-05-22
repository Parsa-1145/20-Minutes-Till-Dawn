package model.game.monsters;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import model.game.Game;
import model.game.Player;

public class HitPlayer extends Behaviour{
    private float damage;
    @Override
    public void update(float delta, Monster monster) {
        Player player = Game.activeGame.player;

        if(player.getBoundingBox().overlaps(monster.boundingBox)){
            byte[][] playerCollider = player.getCollider();
            byte[][] monsterCollider = monster.getCollider();

            Rectangle playerBox = player.getBoundingBox();
            Rectangle monsterBox = monster.boundingBox;

            float startX = Math.max(playerBox.x, monsterBox.x);
            float startY = Math.max(playerBox.y, monsterBox.y);
            float endX = Math.min(playerBox.x + playerBox.width, monsterBox.x + monsterBox.width);
            float endY = Math.min(playerBox.y + playerBox.height, monsterBox.y + monsterBox.height);

            for (int x = (int) startX; x < endX; x++) {
                for (int y = (int) startY; y < endY; y++) {
                    int playerLocalX = (int)(x - playerBox.x);
                    int playerLocalY = (int)(y - playerBox.y);

                    int monsterLocalX = (int)(x - monsterBox.x);
                    int monsterLocalY = (int)(y - monsterBox.y);

                    playerLocalY = playerCollider[0].length - 1 - playerLocalY;
                    monsterLocalY = monsterCollider[0].length - 1 - monsterLocalY;

                    if (playerLocalX >= 0 && playerLocalX < playerCollider.length &&
                            playerLocalY >= 0 && playerLocalY < playerCollider[0].length &&
                            monsterLocalX >= 0 && monsterLocalX < monsterCollider.length &&
                            monsterLocalY >= 0 && monsterLocalY < monsterCollider[0].length) {

                        if (playerCollider[playerLocalX][playerLocalY] != 0 &&
                                monsterCollider[monsterLocalX][monsterLocalY] != 0) {

                            player.getHit(monster);
                            return;
                        }
                    }
                }
            }
        }
    }
}
