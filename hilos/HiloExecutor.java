import java.util.concurrent.*;

// Clase Summation que implementa Callable para poder ser ejecutada por un ExecutorService.
// Devuelve un Integer como resultado de su operación.
class Summation implements Callable<Integer> {
    private int upper;

    public Summation(int upper) {
        this.upper = upper;
    }

    // Método call que será ejecutado en un nuevo hilo cuando sea enviado a un ExecutorService.
    // Calcula la suma de todos los números desde 1 hasta 'upper'.
    public Integer call() {
        int sum = 0;
        for (int i = 1; i <= upper; i++)
            sum += i;
        return sum;
    }
}

public class HiloExecutor {
    public static void main(String[] args) {
        int upper = 5; // Límite superior para la suma.
        // Crea un ExecutorService con un solo hilo para ejecutar tareas.
        ExecutorService pool = Executors.newSingleThreadExecutor();
        // Envía la tarea de sumar números hasta 'upper' al ExecutorService y guarda el Future resultante.
        Future<Integer> result = pool.submit(new Summation(upper));

        try {
            // Espera y obtiene el resultado de la tarea cuando esté disponible.
            System.out.println("sum = " + result.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            // Cierra el ExecutorService de forma ordenada una vez que todas las tareas han terminado.
            pool.shutdown();
        }       
    }
}