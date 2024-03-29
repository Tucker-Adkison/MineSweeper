import java.util.TimerTask;

class TimeCount extends TimerTask {
   private static boolean enabled;

   TimeCount() {
      enabled = true;
   }

   @Override
   public void run() {
      System.out.println(enabled);
      if(enabled) {
         MineSweeper.getBoard().paintImmediately(Board.checkChanges());
      }
   }

   public static void disable() {
      enabled = false;
   }
}