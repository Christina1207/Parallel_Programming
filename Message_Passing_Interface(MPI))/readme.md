# MPI Matrix Average Calculation

A C-based parallel computing project using the **Message Passing Interface (MPI)** to calculate the arithmetic mean of a large random array efficiently across multiple processes.

## Project Overview

The goal of this project is to demonstrate data distribution and collective communication in a parallel environment. The program generates an array of 1,000 random floating-point numbers and distributes them among available processes to compute the global average.

### Task Requirements:
1.  **Generation:** Rank 0 generates an array $X$ of 1,000 random elements.
2.  **Distribution:** Scatter the elements across all participating processes.
3.  **Local Computation:** Each process calculates the sum and average of its received "chunk."
4.  **Global Aggregation:** Calculate the total average using partial sums to maintain mathematical accuracy.

## Parallel Logic & Communication

The program follows a **Scatter-Reduce** pattern to ensure efficiency and accuracy:



* **Scatter Phase:** The master process (Rank 0) uses `MPI_Scatter` to divide the 1,000 elements into $N$ chunks (where $N$ is the number of processes).
* **Computation Phase:** Each process iterates through its local chunk to find the `local_sum`.
* **Reduction Phase:** `MPI_Reduce` is used to collect all `local_sum` values into a single `global_sum` on Rank 0.
* **Final Step:** Rank 0 divides the `global_sum` by 1,000. 
    * *Note: We sum the totals rather than averaging the averages to avoid mathematical errors if chunks are uneven.*

## How to Run

### Prerequisites
* OpenMPI or MPICH installed on your system.
* `gcc` compiler.

### Compilation
Use `mpicc` to compile the source code:

mpicc mpi_average.c -o mpi_average

### Execution
Run the program using mpirun. You can vary the number of processes using the -np flag:

mpirun -np 4 ./mpi_average
