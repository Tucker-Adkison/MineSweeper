import java.awt.*;
import javax.swing.*;
import java.util.Timer;

class MineSweeper{
   private static Board board = new Board();
   private static JFrame frame = new JFrame("MineSweeper");
   private static int height;
   private static int width;

   public static void main(String[] args) {
      width = 500;
      height = 500;
      board.addMouseListener(new Events());
      frame.addComponentListener(board);
      Timer timer = new Timer();
      timer.schedule(new TimeCount(), 0, 1000);
      frame.add(board, BorderLayout.CENTER);
      frame.pack();
      frame.setSize(width, height);
      frame.setVisible(true);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }

   public static Board getBoard() {
      return board;
   }

   public static JFrame getFrame() {
      return frame;
   }

   public static int getHeight() {
      return height;
   }
   public static int getWidth() {
      return width;
   }
}