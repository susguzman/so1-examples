import java.util.concurrent.*;

class SumTask extends RecursiveTask<Integer> {
    static final int THRESHOLD = 1000; // Umbral para dividir las tareas
    private int begin; // Índice inicial del segmento a sumar
    private int end; // Índice final del segmento a sumar
    private int[] array; // Array de enteros a sumar

    public SumTask(int begin, int end, int[] array) {
        this.begin = begin;
        this.end = end;
        this.array = array;
    }

    @Override
    protected Integer compute() {
        if (end - begin < THRESHOLD) {
            // Caso base: si el segmento es pequeño, realiza una suma directa
            int sum = 0;
            for (int i = begin; i <= end; i++) {
                sum += array[i];
            }
            return sum;
        } else {
            // Caso recursivo: divide el segmento en dos, crea nuevas tareas y las ejecuta en paralelo
            int mid = (begin + end) / 2;

            SumTask leftTask = new SumTask(begin, mid, array);
            SumTask rightTask = new SumTask(mid + 1, end, array);

            leftTask.fork(); // Ejecuta la tarea izquierda de manera asincrónica
            rightTask.fork(); // Ejecuta la tarea derecha de manera asincrónica
            
            return leftTask.join() + rightTask.join(); // Espera y combina los resultados
        }
    }
}

public class HiloForkJoin {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(); // Crea un pool de hilos para tareas ForkJoin
        int SIZE = 100000; // Tamaño del array
        int[] array = new int[SIZE]; // Crea el array
        
        // Inicializa el array con valores (p.ej., 1 para simplificar la suma)
        for (int i = 0; i < SIZE; i++) {
            array[i] = 1; // Asigna un valor a cada elemento del array
        }

        SumTask task = new SumTask(0, SIZE - 1, array); // Crea la tarea principal
        int sum = pool.invoke(task); // Ejecuta la tarea y espera el resultado

        System.out.println("Sum: " + sum);

        pool.shutdown(); // Cierra el pool de hilos
    }
}
