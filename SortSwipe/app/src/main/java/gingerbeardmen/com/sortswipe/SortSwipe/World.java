package gingerbeardmen.com.sortswipe.SortSwipe;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

/**
 * Created by Peter on 9/21/2014.
 */

public class World {
    static final int WORLD_WIDTH = 10;
    static final int WORLD_HEIGHT = 13;
    static final int SCORE_INCREMENT = 10;
    static final float TICK_INITIAL = 0.5f;
    static final float TICK_DECREMENT = 0.05f;

    public List<Card> cardList = new ArrayList<Card>();
    public boolean gameOver = false;
    public int score = 0;

    boolean fields[][] = new boolean[WORLD_WIDTH][WORLD_HEIGHT];
    Random random = new Random();
    float tickTime = 0;
    static float tick = TICK_INITIAL;

    public World() {
        placeCards(1);
    }

    public void placeCards(int stage) {
        //TODO:: switch statement for stage
        switch (stage) {
            case 1:
                for(int i = 0; i < 6; i++) {
                    cardList.add(new Card(random.nextInt(2)));
                }
                break;
            case 2:
                for(int i = 0; i < 10; i++) {
                    cardList.add(new Card(random.nextInt(4)));
                }
                break;
            case 3:
                int x = 0;
                for(int i = 0; i < 15; i++) {
                    Card c = new Card(random.nextInt(5));
                    if(c.type == Card.TYPE_5)
                        x++;
                    if(x < 2)
                        cardList.add(new Card(random.nextInt(5)));
                    else
                        cardList.add(new Card(random.nextInt(4)));
                }
                break;
            case 4:
                int y = 0;
                for(int i = 0; i < 17; i++) {
                    Card c = new Card(random.nextInt(5));
                    if(c.type == Card.TYPE_5)
                        y++;
                    if(y < 4)
                        cardList.add(new Card(random.nextInt(5)));
                    else
                        cardList.add(new Card(random.nextInt(4)));
                }
                break;
        }
    }

    public void update(float deltaTime) {
        if (gameOver)
            return;

        tickTime += deltaTime;
        while (tickTime > tick) {
            tickTime -= tick; //Increment the counter
        }
    }
}
