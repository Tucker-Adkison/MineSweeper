import java.util.TimerTask;

class TimeCount extends TimerTask {
   private static boolean enabled;

   TimeCount() {
      enabled = true;
   }

   @Override
   public void run() {
      if(enabled) {
         MineSweeper.getBoard().paintImmediately(0,0,Board.getFrameWidth(),Board.getFrameBorder());
      }
   }

   public static void disable() {
      enabled = false;
   }
}