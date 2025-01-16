import java.util.Stack;

public class Bottle { 
    private int size;
    private Stack<Integer> tube;

    private static final int max_size = 5;
    private static final int min_size = 0;

    public Bottle(){
        size = 0;
        tube = new Stack<>();
    }

    public void add_color(Integer color) {
        if (size < max_size) {
            tube.push(color);
            size++;
        } else {
            throw new IllegalStateException("Overflow error: Cannot add color. Bottle is full.");
        }
    }

    public int remove_color() {
        if (size > 0) {
            size--;
            return tube.pop(); 
        } else {
            throw new IllegalStateException("Underflow error: Cannot remove color. Bottle is empty.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Bottle bottle = new Bottle();
        
        // Test the functionality
        try {
            bottle.add_color(1);
            bottle.add_color(2);
            bottle.add_color(3);
            bottle.add_color(4);
            bottle.add_color(5);
            bottle.add_color(6);  // This should throw an overflow error
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println(bottle.remove_color()); // Should return 5
            System.out.println(bottle.remove_color()); // Should return 4
            System.out.println(bottle.remove_color()); // Should return 3
            System.out.println(bottle.remove_color()); // Should return 2
            System.out.println(bottle.remove_color()); // Should return 1
            System.out.println(bottle.remove_color()); // This should throw an underflow error
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }
}
