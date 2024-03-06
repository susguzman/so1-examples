public class HiloEspera {
    public static void main(String[] args) {
        Runnable task = () -> {
            System.out.println("I am a thread.");
        };

        Thread worker = new Thread(task);
        worker.start();
        
        try {
            worker.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
