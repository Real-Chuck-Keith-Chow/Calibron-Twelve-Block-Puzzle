import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Visualizer visualizer = new Visualizer();
        List<Dimension> allPieces = Arrays.asList(
            new Dimension(28, 14), new Dimension(21, 18), new Dimension(21, 18),
            new Dimension(21, 14), new Dimension(21, 14), new Dimension(23, 11),
            new Dimension(23, 10), new Dimension(28, 7), new Dimension(28, 6),
            new Dimension(17, 14), new Dimension(14, 4), new Dimension(18, 7)
        );
        Board board = new Board();
        List<PiecePosition> positions = new ArrayList<>();
        System.out.println(getSolution(board, allPieces, positions, visualizer));
    }

    public static List<PiecePosition> getSolution(Board board, List<Dimension> remaining, 
            List<PiecePosition> positions, Visualizer visualizer) {
        visualizer.visualizeList(positions);
        
        if (board.getSpace() == 0) {
            return positions;
        }
        
        for (Dimension piece : remaining) {
            for (boolean isRotated : Arrays.asList(false, true)) {
                Dimension rotatedPiece = isRotated ? new Dimension(piece.height, piece.width) : piece;
                if (board.doesFit(rotatedPiece)) {
                    Board newBoard = board.copy();
                    List<Dimension> newRemaining = new ArrayList<>(remaining);
                    Point position = newBoard.insert(rotatedPiece);
                    newRemaining.remove(piece);
                    positions.add(new PiecePosition(rotatedPiece, position));
                    
                    List<PiecePosition> solution = getSolution(newBoard, newRemaining, positions, visualizer);
                    if (solution != null) {
                        return solution;
                    }
                    positions.remove(positions.size() - 1);
                }
            }
        }
        return null;
    }
}