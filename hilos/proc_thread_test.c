// Build:   gcc proc_thread_test.c -o proc_thread_test -lpthread
// Run:     ./proc_thread_test

#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <pthread.h>
#include <sys/types.h>
#include <sys/wait.h>

void* thread_function(void *arg) {
    printf("H. Hilo creado en el proceso: %d, ID del hilo: %lu\n", getpid(), pthread_self());
}

int main() {
    pid_t pid;
    pthread_t thread_id;

    printf("P. Proceso inicial PID: %d\n", getpid());

    pid = fork();
    if (pid == 0) { // Proceso hijo
        printf("P. Proceso hijo PID: %d, padre PID: %d\n", getpid(), getppid());

        pid_t child_pid = fork();
        if (child_pid == 0) { // Hijo del hijo
            printf("P. Hijo del hijo PID: %d, padre PID: %d\n", getpid(), getppid());
        }
        // Esto lo ejecuta el proceso hijo y el hijo del hijo
        pthread_create(&thread_id, NULL, thread_function, NULL);
    } else {
        wait(NULL);
    }

    // El proceso padre original, el primer hijo y el hijo del hijo pasan por aca
    pid = fork();
    if (pid == 0) { // Nuevo proceso hijo
        printf("P. Nuevo proceso hijo PID: %d, padre PID: %d\n", getpid(), getppid());
    } else {
        wait(NULL);
    }

    return 0;
}
