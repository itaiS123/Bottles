import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class BottleAnimation extends JPanel {
    private static final int NUM_BOTTLES = 5;
    private static final int BOTTLE_WIDTH = 100;
    private static final int BOTTLE_HEIGHT = 200;
    private static final int SLOT_HEIGHT = BOTTLE_HEIGHT / 5;
    private int selectedBottle = -1;

    private Bottle[] bottles;
    private static final Color[] AVAILABLE_COLORS = {Color.RED, Color.ORANGE, Color.GREEN, Color.BLUE, Color.MAGENTA};

    public BottleAnimation() {
        bottles = new Bottle[NUM_BOTTLES];
        for (int i = 0; i < NUM_BOTTLES; i++) {
            bottles[i] = new Bottle();
        }
        distributeRandomColors();
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                handleMouseClick(e.getX(), e.getY());
            }
        });
    }

    private void distributeRandomColors() {
        ArrayList<Color> colorPool = new ArrayList<>();
        for (Color color : AVAILABLE_COLORS) {
            for (int i = 0; i < NUM_BOTTLES - 1; i++) {
                colorPool.add(color);
            }
        }
        Collections.shuffle(colorPool);
        for (Color color : colorPool) {
            while (true) {
                int randomIndex = (int) (Math.random() * NUM_BOTTLES);
                Bottle bottle = bottles[randomIndex];
                if (bottle.getColors().size() < Bottle.max_size) {
                    bottle.add_color(color);
                    break;
                }
            }
        }
    }

    private void handleMouseClick(int x, int y) {
        int bottleIndex = (x - 50) / (BOTTLE_WIDTH + 20);
        if (bottleIndex >= 0 && bottleIndex < NUM_BOTTLES) {
            if (selectedBottle == -1) {
                selectedBottle = bottleIndex;
            } else {
                try {
                    Color colorToMove = bottles[selectedBottle].remove_color();
                    bottles[bottleIndex].add_color(colorToMove);
                    if (checkWinCondition()) {
                        JOptionPane.showMessageDialog(this, "Congratulations! You've won the game!", "Game Won", JOptionPane.INFORMATION_MESSAGE);
                        disableInteraction();
                    }
                } catch (IllegalStateException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Action Error", JOptionPane.ERROR_MESSAGE);
                }
                selectedBottle = -1;
            }
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < bottles.length; i++) {
            int x = 50 + i * (BOTTLE_WIDTH + 20);
            int y = 50;
            if (i == selectedBottle) {
                g.setColor(Color.YELLOW);
                g.fillRect(x - 2, y - 2, BOTTLE_WIDTH + 4, BOTTLE_HEIGHT + 4);
            }
            g.setColor(Color.BLACK);
            g.drawRect(x, y, BOTTLE_WIDTH, BOTTLE_HEIGHT);
            Stack<Color> colors = bottles[i].getColors();
            int currentY = y + BOTTLE_HEIGHT - SLOT_HEIGHT;
            for (Color color : colors) {
                g.setColor(color);
                g.fillRect(x, currentY, BOTTLE_WIDTH, SLOT_HEIGHT);
                g.setColor(Color.BLACK);
                g.drawRect(x, currentY, BOTTLE_WIDTH, SLOT_HEIGHT);
                currentY -= SLOT_HEIGHT;
            }
            g.setColor(Color.BLACK);
            g.drawString("Bottle " + (i + 1), x + BOTTLE_WIDTH / 3, y + BOTTLE_HEIGHT + 20);
        }
    }

    public boolean checkWinCondition() {
        for (Bottle bottle : bottles) {
            Stack<Color> colors = bottle.getColors();
            if (colors.size() > 0) {
                Color firstColor = colors.peek();
                for (Color color : colors) {
                    if (!color.equals(firstColor)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void disableInteraction() {
        for (var listener : this.getMouseListeners()) {
            this.removeMouseListener(listener);
        }
    }
}