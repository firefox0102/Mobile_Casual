package gingerbeardmen.com.sortswipe.SortSwipe;

/**
 * Created by Peter on 9/27/2014.
 */
public class Card {
    public static final int TYPE_1 = 0;
    public static final int TYPE_2 = 1;
    public static final int TYPE_3 = 2;
    public static final int TYPE_4 = 3;
    public static final int TYPE_5 = 4;

    public int x, y;
    public int type;
    public boolean hasBeenFlung;

    public Card(int type) {
        this.x = 110;
        this.y = 190;
        this.type = type;
        hasBeenFlung = false;
    }

    public void resetPosition() {
        x = 110;
        y = 190;
    }
}
