package threadtest;

public class tt extends Thread {
    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("\r\naa*9*9*9*9***********************d" + getName());
                sleep(100);
            } catch (InterruptedException e) {
                System.out.println("IN Exception");
                return;
            }
            catch (Exception e) {
                System.out.println("Exception");
            }
        }
    }
}
