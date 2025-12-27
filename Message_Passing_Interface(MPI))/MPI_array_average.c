#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main(int argc, char** argv) {
    MPI_Init(&argc, &argv);

    int world_size, world_rank;
    MPI_Comm_size(MPI_COMM_WORLD, &world_size);
    MPI_Comm_rank(MPI_COMM_WORLD, &world_rank);

    int total_elements = 1000;
    int chunk_size = total_elements / world_size;
    float *X = NULL;
    float *sub_X = (float *)malloc(sizeof(float) * chunk_size);

    // rank 0 generates the random array 
    if (world_rank == 0) {
        X = (float *)malloc(sizeof(float) * total_elements);
        srand(time(NULL));
        for (int i = 0; i < total_elements; i++) {
            X[i] = (float)rand() / (float)(RAND_MAX / 100);
        }
    }

    double start_time = MPI_Wtime();

    // scatter elements 
    MPI_Scatter(X, chunk_size, MPI_FLOAT, sub_X, chunk_size, MPI_FLOAT, 0, MPI_COMM_WORLD);

    // Print received elements (first 5) 
    printf("Rank %d received %d elements. First few: %.2f, %.2f...\n", 
            world_rank, chunk_size, sub_X[0], sub_X[1]);


    // Calculate local sum and local average  
    float local_sum = 0;
    for (int i = 0; i < chunk_size; i++) {
        local_sum += sub_X[i];
    }
    float local_avg = local_sum / chunk_size;
    printf("Rank %d local average: %.2f\n", world_rank, local_avg);

    // Calculate global average using local sums 
    float global_sum;
    MPI_Reduce(&local_sum, &global_sum, 1, MPI_FLOAT, MPI_SUM, 0, MPI_COMM_WORLD);

    if (world_rank == 0) {
        float global_avg = global_sum / total_elements;
        double end_time = MPI_Wtime();
        printf("\n--- Results ---\n");
        printf("Global Average: %.4f\n", global_avg);
        printf("Execution Time with %d processes: %f seconds\n", world_size, end_time - start_time);
        free(X);
    }

    free(sub_X);
    MPI_Finalize();
    return 0;
}