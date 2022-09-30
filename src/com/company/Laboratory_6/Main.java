package com.company.Laboratory_6;

//Шаг 1. Инициализация.
//        Work = Available
//        Для i = 1, …, n, если Allocation [i] != 0 то finish [i] = false иначе finish [i] = true.
// Шаг 2. Находим i, такое, что:
//        Finish [i] = false
//        Request [i] <= Work
//        Если такого i нет, переходим к шагу 4.
// Шаг 3.
//        Work = Work + Allocation [i]
//        Finish [i] = true
//        Переход к шагу 2.
//  Шаг 4.
//        Если Finish[i] = false для некоторого i от 1 до n, то система в состоянии тупика.
//        Более того, если Finish[i] = false,
//        то процесс Pi – в состоянии тупика.


class Bancher {
    // Number of processes
    static int Processes = 4;

    // Number of resources
    static int Resources = 3;

    // Function to find the need of each process
    static void calculateNeed(int need[][], int maxm[][], int allot[][]) {
        // Calculating Need of each P
        for (int i = 0; i < Processes; i++)
            for (int j = 0; j < Resources; j++)
                // Need of instance = maxm instance - allocated instance
                need[i][j] = maxm[i][j] - allot[i][j];

            System.out.println("\nNeed={");
        for (int i = 0; i < Processes; i++) {
            System.out.print("   ");
            for (int j = 0; j < Resources; j++)
                System.out.print(need[i][j] + " ");
            System.out.println();
        }
        System.out.println("  }");
    }

    // Function to find the system is in safe state or not
    //Проверка проходимого пути
    static boolean isSafe(int processes[], int avail[], int maxm[][], int allot[][]) {

        int[][] need = new int[Processes][Resources];

        // Function to calculate need matrix
        calculateNeed(need, maxm, allot);

        // Mark all processes as unfinish
        boolean[] finish = new boolean[Processes];

        // To store safe sequence
        int[] safeSeq = new int[Processes];

        // Make a copy of available resources
        int[] work = new int[Resources];
        for (int i = 0; i < Resources; i++)
            work[i] = avail[i];

        // While all processes are not finished or system is not in safe state.
        int count = 0;
        while (count < Processes) {
            // Find a process which is not finish and whose needs can be satisfied with current work[] resources.
            boolean found = false;
            for (int p = 0; p < Processes; p++) {

                // First check if a process is finished, if no, go for next condition
                if (finish[p] == false) {
                    // Check if for all resources of current Processses need is less than work
                    int j;
                    for (j = 0; j < Resources; j++)
                        if (need[p][j] > work[j])
                            break;

                    // If all needs of p were satisfied.
                    if (j == Resources) {
                        // Add the allocated resources of current P to the available/work resources i.e.free the resources
                        for (int k = 0; k < Resources; k++)
                            work[k] += allot[p][k];

                        // Add this process to safe sequence.
                        safeSeq[count++] = p;

                        // Mark this p as finished
                        finish[p] = true;

                        found = true;
                    }
                }
            }

            // If could not find a next process in safe
            // sequence.
            if (found == false) {
                System.out.print("System is not in safe state");
                return false;
            }
        }

        // If system is in safe state then safe sequence will be as below
        System.out.print("System is in safe state.\nSafe"
                + " sequence is: ");
        for (int i = 0; i < Processes; i++)
            System.out.print(safeSeq[i] + " ");

        return true;
    }


    public static void main(String[] args) {
        int processes[] = {0, 1, 2, 3};

        // Available instances of resources
        //Количество свободных ресурсов
        int avail[] = {3, 3, 4};

        // Maximum R that can be allocated
        // to processes
        //Максимальная потребность каждого процесса в системе
        int maxm[][] = {
                {7, 5, 3},
                {3, 2, 2},
                {9, 0, 2},
                {2, 2, 2},
//                {4, 3, 3}
        };

        // Resources allocated to processes
        //Определяет количество ресурсов каждого типа, выделенных в данный момент для каждого процесса.
//        int allot[][] = {
//                {0, 1, 0},
//                {2, 0, 0},
//                {3, 0, 2},
//                {2, 1, 1},
//                {0, 0, 2}};
        int allot[][] = {
                {0, 1, 0},
                {2, 0, 0},
                {3, 0, 2},
                {2, 1, 1},
//                {0, 3, 2}
        };

        // Check system is in safe state or not

        int n = 4, k = 3;
        System.out.println("\nMaxR={");
        for (int i = 0; i < n; i++) {
            System.out.print("   ");
            for (int j = 0; j < k; j++)
                System.out.print(maxm[i][j] + " ");
            System.out.println();
        }
        System.out.println("  }");

        ////
        System.out.println("\nAllocated={");
        for (int i = 0; i < n; i++) {
            System.out.print("   ");
            for (int j = 0; j < k; j++)
                System.out.print(allot[i][j] + " ");
            System.out.println();
        }
        System.out.println("  }");

        isSafe(processes, avail, maxm, allot);
    }
}