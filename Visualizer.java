import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;  // This is the correct import for List
public class Visualizer {
    public static final Color YELLOW_BG = Color.YELLOW;
    public static final Color RED = Color.RED;
    public static final int SCALE = 15;
    private JFrame frame;
    private JPanel panel;

    public Visualizer() {
        frame = new JFrame("Board Visualizer");
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // visualization logic would go here
            }
        };
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(56 * SCALE, 56 * SCALE);
        frame.setVisible(true);
    }

    public void visualizeList(List<PiecePosition> positions) {
        Graphics g = panel.getGraphics();
        g.setColor(YELLOW_BG);
        g.fillRect(0, 0, 56 * SCALE, 56 * SCALE);
        
        for (PiecePosition p : positions) {
            Dimension piece = p.piece;
            Point position = p.position;
            piece = new Dimension(piece.width * SCALE, piece.height * SCALE);
            position = new Point(position.x * SCALE, position.y * SCALE);
            int border = 5;
            
            g.setColor(RED);
            int[] xPoints = { position.x, position.x + piece.width, position.x + piece.width, position.x };
            int[] yPoints = { position.y, position.y, position.y + piece.height, position.y + piece.height };
            g.fillPolygon(xPoints, yPoints, 4);
            
            g.setColor(YELLOW_BG);
            int[] borderx = { position.x + border, position.x + piece.width - border, 
                             position.x + piece.width - border, position.x + border };
            int[] bordery = { position.y + border, position.y + border, 
                             position.y + piece.height - border, position.y + piece.height - border };
            g.fillPolygon(borderx, bordery, 4);
        }
        panel.repaint();
    }
}