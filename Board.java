import java.util.Random;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JPanel;

class Board extends JPanel implements ComponentListener {
   private static final long serialVersionUID = 1L;
   private static char[][] values = new char[8][8];
   private static Rectangle[][] board = new Rectangle[8][8];
   private static int mines = 10;
   private static int flags = mines;
   private static int rightFlags = 0;
   private long startTime = 0;
   private static int height = MineSweeper.getHeight();
   private static int width = MineSweeper.getWidth();
   private static final int border = 50;
   private static int squareSize = 0;
   private static int clockX = 0;
   private static int clockY = 0;

   @Override
   public void setSize(Dimension d) {
      super.setSize(d);
   }

   Board() {
      startTime = System.nanoTime();
      Random rand = new Random();
      for (int i = 0; i < mines; i++) {
         int randRow = rand.nextInt(board.length);
         int randCol = rand.nextInt(board[0].length);
         while (values[randRow][randCol] != '*') {
            values[randRow][randCol] = '*';
         }
      }
      this.makeBoard();
   }

   public void paint(Graphics g) {
      this.makeBoard();
      Graphics2D g2 = (Graphics2D) g;
      g.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
      clockX = border + squareSize * board.length + (border/2);
      clockY = height - border - squareSize * 3;
      g.drawString(String.valueOf((System.nanoTime() - startTime) / 1000000000), clockX, clockY);
      for (int i = 0; i < values.length; i++) {
         for (int j = 0; j < values[0].length; j++) {
            g2.setColor(Color.black);
            g2.draw(board[i][j]);
            if (values[i][j] == 'h') {
               g2.setColor(Color.white);
               g2.fill(board[i][j]);
            } else if (values[i][j] == 'f' || values[i][j] == 's') {
               Image img = Toolkit.getDefaultToolkit().getImage("images/flag.png");
               g2.setColor(Color.LIGHT_GRAY);
               g2.fill(board[i][j]);
               g.drawImage(img, (i * squareSize) + border, (j * squareSize) + border, squareSize, squareSize, this);
            } else if (values[i][j] == '*') {
               g2.setColor(Color.blue);
               //g2.setColor(Color.LIGHT_GRAY);
               g2.fill(board[i][j]);
            } else if (Character.getNumericValue(values[i][j]) != -1) {
               Image img = Toolkit.getDefaultToolkit().getImage("images/" + String.valueOf(values[i][j]) + ".png");
               if (Character.getNumericValue(values[i][j]) == 1) {
                  g2.setColor(Color.white);
                  g2.fill(board[i][j]);
                  g.drawImage(img, (i * squareSize) + border, (j * squareSize) + border, squareSize, squareSize, this);
               } else {
                  g2.setColor(Color.white);
                  g2.fill(board[i][j]);
                  g.drawImage(img, (i * squareSize) + border, (j * squareSize) + border, squareSize, squareSize, this);
               }
            } else {
               g2.setColor(Color.LIGHT_GRAY);
               g2.fill(board[i][j]);
            }
         }
      }
   }

   public void makeBoard() {
      if(height < width) {
         squareSize = (height - (2 * border))/board.length;
      } else {
         squareSize = (width - (2 * border))/board.length;
      }
      for (int i = 0; i < board.length; i++) {
         for (int j = 0; j < board[0].length; j++) {
            board[i][j] = new Rectangle((i * squareSize) + border, (j * squareSize) + border, squareSize, squareSize);
         }
      }
      return;
   }

   public static Rectangle checkChanges() {
      return new Rectangle(border + squareSize * board.length, 0, width - (border + squareSize * board.length), height);
   }

   public static char[][] getValuesArray() {
      return values;
   }

   public static Rectangle[][] getRectangles() {
      return board;
   }

   public static void removeRectangle(int x, int y) {
      values[x][y] = 'h';
      MineSweeper.getBoard().repaint();
   }

   public static void putFlag() {
      MineSweeper.getBoard().repaint();
   }

   public static int getFlag() {
      return flags;
   }

   public static void setFlag(int f) {
      flags = f;
   }

   public static int getRightFlags() {
      return rightFlags;
   }

   public static void setRightFlags(int f) {
      rightFlags = f;
   }

   public static int getMines() {
      return mines;
   }

   @Override
   public void componentResized(ComponentEvent e) {
      height = (int) e.getComponent().getSize().getHeight();
      width = (int) e.getComponent().getSize().getWidth();
      MineSweeper.getBoard().repaint();
   }

   @Override
   public void componentMoved(ComponentEvent e) {

   }

   @Override
   public void componentShown(ComponentEvent e) {

   }

   @Override
   public void componentHidden(ComponentEvent e) {

   }
}  