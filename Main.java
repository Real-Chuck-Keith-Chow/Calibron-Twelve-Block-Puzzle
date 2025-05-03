import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Visualizer visualizer = new Visualizer();
        visualizer.startTimer();

        List<Dimension> allPieces = Arrays.asList(
            new Dimension(28, 14), new Dimension(21, 18), new Dimension(21, 18),
            new Dimension(21, 14), new Dimension(21, 14), new Dimension(23, 11),
            new Dimension(23, 10), new Dimension(28, 7), new Dimension(28, 6),
            new Dimension(17, 14), new Dimension(14, 4), new Dimension(18, 7)
        );
        
        Board board = new Board();
        List<PiecePosition> positions = new ArrayList<>();
        
        List<PiecePosition> solution = getSolution(board, allPieces, positions, visualizer);
        visualizer.stopTimer();
        
        if (solution != null) {
            System.out.println("Solution found in " + visualizer.getTimerText());
        } else {
            System.out.println("No solution found after " + visualizer.getTimerText());
        }
    }

    public static List<PiecePosition> getSolution(Board board, List<Dimension> remaining,
            List<PiecePosition> positions, Visualizer visualizer) {
        visualizer.visualizeList(positions);
        
        if (board.getSpace() == 0) {
            return positions;
        }
        
        for (int i = 0; i < remaining.size(); i++) {
            Dimension piece = remaining.get(i);
            for (boolean rotate : new boolean[]{false, true}) {
                Dimension currentPiece = rotate ? 
                    new Dimension(piece.height, piece.width) : piece;
                
                if (board.doesFit(currentPiece)) {
                    Board newBoard = board.copy();
                    Point pos = newBoard.insert(currentPiece);
                    
                    List<Dimension> newRemaining = new ArrayList<>(remaining);
                    newRemaining.remove(i);
                    
                    positions.add(new PiecePosition(currentPiece, pos));
                    List<PiecePosition> solution = getSolution(
                        newBoard, newRemaining, positions, visualizer);
                    
                    if (solution != null) return solution;
                    positions.remove(positions.size() - 1);
                }
            }
        }
        return null;
    }
}
