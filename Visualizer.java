import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import javax.swing.Timer;

public class Visualizer {
    public static final Color YELLOW_BG = new Color(255, 255, 200);
    public static final Color RED = new Color(220, 60, 60);
    public static final int SCALE = 15;
    private JFrame frame;
    private JPanel panel;
    private JLabel timerLabel;
    private Instant startTime;
    private Timer swingTimer;

    public Visualizer() {
        frame = new JFrame("Calibron Puzzle Solver");
        frame.setLayout(new BorderLayout());

        // Timer panel at top
        JPanel timerPanel = new JPanel();
        timerLabel = new JLabel("00:00:00", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Monospaced", Font.BOLD, 18));
        timerLabel.setForeground(Color.BLUE);
        timerPanel.setBackground(new Color(240, 240, 240));
        timerPanel.add(timerLabel);
        frame.add(timerPanel, BorderLayout.NORTH);

        // Main puzzle panel
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                visualizePieces(g);
            }
        };
        frame.add(panel, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(56 * SCALE + 100, 56 * SCALE + 130);
    }

    private void visualizePieces(Graphics g) {
        if (g != null) {
            g.setColor(YELLOW_BG);
            g.fillRect(0, 0, 56 * SCALE, 56 * SCALE);
        }
    }

    public void startTimer() {
        startTime = Instant.now();
        swingTimer = new Timer(1000, e -> {
            Duration duration = Duration.between(startTime, Instant.now());
            timerLabel.setText(String.format("%02d:%02d:%02d",
                duration.toHours(),
                duration.toMinutesPart(),
                duration.toSecondsPart()));
        });
        swingTimer.start();
        frame.setVisible(true);
    }

    public void stopTimer() {
        if (swingTimer != null) {
            swingTimer.stop();
            System.out.println("Solving completed in: " + timerLabel.getText());
        }
    }

    public String getTimerText() {
        return timerLabel.getText();
    }

    public void visualizeList(List<PiecePosition> positions) {
        Graphics g = panel.getGraphics();
        if (g != null) {
            visualizePieces(g);
            
            for (PiecePosition p : positions) {
                Dimension scaledPiece = new Dimension(
                    p.piece.width * SCALE,
                    p.piece.height * SCALE);
                Point scaledPos = new Point(
                    p.position.x * SCALE,
                    p.position.y * SCALE);
                
                g.setColor(RED);
                g.fillRect(scaledPos.x, scaledPos.y, scaledPiece.width, scaledPiece.height);
                
                g.setColor(YELLOW_BG);
                int border = Math.max(2, SCALE/4);
                g.fillRect(
                    scaledPos.x + border,
                    scaledPos.y + border,
                    scaledPiece.width - 2*border,
                    scaledPiece.height - 2*border);
            }
            panel.repaint();
        }
    }
}
