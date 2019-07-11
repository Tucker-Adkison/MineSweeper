import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class Events implements MouseListener {
   private int numberOfMines = 0;
   private boolean enable = true;

   @Override
   public void mouseClicked(MouseEvent e) {
   }

   @Override
   public void mousePressed(MouseEvent e) {

   }

   @Override
   public void mouseReleased(MouseEvent e) {
      if(enable) {
         if(Board.getRightFlags() == Board.getMines()) {
            enable = false;
            TimeCount.disable();
            return;
         }
         if(e.getButton() == MouseEvent.BUTTON3 && Board.getFlag() != 0) {
            for (int i = 0; i < Board.getRectangles().length; i++) {
               for (int j = 0; j < Board.getRectangles()[0].length; j++) {
                  if (Board.getRectangles()[i][j].contains(e.getX(), e.getY())) {
                     System.out.print(Board.getValuesArray()[i][j]);
                     switch(Board.getValuesArray()[i][j]) {
                        case '*':
                           Board.getValuesArray()[i][j] = 's';
                           Board.setRightFlags(Board.getRightFlags() + 1);
                           Board.setFlag(Board.getFlag() - 1);
                           Board.putFlag();
                           break;
                        case 'f':
                           Board.getValuesArray()[i][j] = '\u0000';
                           Board.setFlag(Board.getFlag() + 1);
                           break;
                        case 's':
                           Board.getValuesArray()[i][j] = '*';
                           Board.setRightFlags(Board.getRightFlags() - 1);
                           Board.setFlag(Board.getFlag() + 1);
                           break;
                        default:
                           if(Board.getValuesArray()[i][j] != 'h' && !Character.isDigit(Board.getValuesArray()[i][j])) {
                              Board.getValuesArray()[i][j] = 'f';
                              Board.setFlag(Board.getFlag() - 1);
                              Board.putFlag();
                           }
                     }
                     //System.out.print(Board.getValuesArray()[i][j]);
                  }
               }
            }
            MineSweeper.getBoard().repaint();
         } else {
            for (int i = 0; i < Board.getRectangles().length; i++) {
               for (int j = 0; j < Board.getRectangles()[0].length; j++) {
                  if (Board.getRectangles()[i][j].contains(e.getX(), e.getY())) {
                     if (Board.getValuesArray()[i][j] != '*') {
                        calculate(i, j);
                     } else {
                        enable = false;
                        TimeCount.disable();
                     }
                  }
               }
            }
         }
      }
   }

   public void calculate(int x, int y) {
      char[][] G = Board.getValuesArray();
      Queue<int[]> Q = new LinkedList<int[]>(neighbors(G, x, y));
      Q.add(new int[] {x, y});
      if(numberOfMines == 0 && G[x][y] != 'f' && G[x][y] != 's') {
         Board.removeRectangle(x, y);
         while(Q.size() > 0) {
            int[] v = Q.poll();
            ArrayList<int[]> n = neighbors(G, v[0], v[1]);
            for(int i = 0; i < n.size(); i++) {
               if(G[n.get(i)[0]][n.get(i)[1]] == '\u0000') {
                  neighbors(G, n.get(i)[0], n.get(i)[1]);
                  if(numberOfMines == 0) {
                     Board.removeRectangle(n.get(i)[0], n.get(i)[1]);
                     G[n.get(i)[0]][n.get(i)[1]] = 'h';
                     Q.add(n.get(i));
                  } else if (numberOfMines > 0){
                     Board.removeRectangle(n.get(i)[0], n.get(i)[1]);
                     putNumbers(n.get(i)[0], n.get(i)[1]);
                     G[n.get(i)[0]][n.get(i)[1]] = (char) (numberOfMines + '0');
                  }
               }
            }
         }
      } else {
         if(Board.getValuesArray()[x][y] == '\u0000') {
            putNumbers(x, y);
         }
      }
   }

   public void putNumbers(int x, int y) {
      Board.getValuesArray()[x][y] = (char)(numberOfMines + '0');
      MineSweeper.getBoard().repaint();
   }

   public ArrayList<int[]> neighbors(char[][] init, int x, int y) {
      ArrayList<int[]> result = new ArrayList<int[]>();
      numberOfMines = 0;
      for(int i = -1; i <= 1; i++) {
         for(int j = -1; j <= 1; j++) {
            if(j == 0 && i == 0) {
               continue;
            } else {
               if((i == 1 && j == 0) || (i == -1 && j == 0) || (i == 0 && j == 1) || (i == 0 && j == -1)) {
                  if((x+i) < init.length && (x+i) >= 0 && (y+j) < init[0].length && (y+j) >= 0) {
                     if (init[x + i][y + j] != '*'){
                        if(init[x + i][y + j] != 's') {
                           result.add(new int[] {x+i, y+j});
                           continue;
                        }
                     }
                     numberOfMines++;
                  }
               } else {
                  if((x+i) < init.length && (x+i) >= 0 && (y+j) < init[0].length && (y+j) >= 0) {
                     if (init[x + i][y + j] == '*' || init[x + i][y + j] == 's') {
                        numberOfMines++;
                     }
                  }
               }
            }
         }
      }
      return result;
   }

   @Override
   public void mouseEntered(MouseEvent e) {

   }

   @Override
   public void mouseExited(MouseEvent e) {

   }
}