public class HiloSimple {
    public static void main(String[] args) {
        Thread worker = new Thread(new Task());
        worker.start();
    }
}

class Task implements Runnable {
    public void run() {
        System.out.println("I am a thread.");
    }
}
