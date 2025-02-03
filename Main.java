import java.awt.*;
import java.util.Stack;
import javax.swing.*;

class Bottle {
    private int size;
    private Stack<Color> tube;

    public static final int max_size = 5;

    public Bottle() {
        size = 0;
        tube = new Stack<>();
    }

    public void add_color(Color color) {
        if (size < max_size) {
            tube.push(color);
            size++;
        } else {
            throw new IllegalStateException("Overflow error: Cannot add color. Bottle is full.");
        }
    }

    public Color remove_color() {
        if (size > 0) {
            size--;
            return tube.pop();
        } else {
            throw new IllegalStateException("Underflow error: Cannot remove color. Bottle is empty.");
        }
    }

    public Stack<Color> getColors() {
        return tube;
    }
}

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Bottle Sorting Game");
        BottleAnimation bottlePanel = new BottleAnimation();
        frame.setLayout(new BorderLayout());
        frame.add(bottlePanel, BorderLayout.CENTER);
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}