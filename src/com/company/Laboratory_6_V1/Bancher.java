package com.company.Laboratory_6_V1;

public class Bancher {
    static int n = 5, k = 4;
    static Integer c[][] = new Integer[n][k]; //starea curentă
    static Integer r[][] = new Integer[n][k]; //resursele necesare
    static Integer e[] = new Integer[k];  //resursele existente
    static Integer p[] = new Integer[k];  //resursele utilizare
    static Integer a[] = new Integer[k];  //resursele disponibile
    static Integer s[] = new Integer[n];  //starea rîndurilor din 'r'


    /* Iniţializarea vectorului stării rîndurilor – rînduri stabile şi nestabile în 'r'*/
    static void defineS() {
        /* inţializarea vectorului stării prin 0 */
        for (int i = 0; i < n; i++)
            s[i] = 0;
    }

    /* inţializarea vectorului cu resurse ocupate  'p'*/
    static void defineP() {
        /* inţializarea vectorului p cu 0 */
        for (int j = 0; j < k; j++) {
            p[j] = 0;
        }

        /*Determinăm vectorul cu resurse ocupate. Conţinutul vectorului 'p' este suma elementelor rândului corespunzător a matricii cu resurse ocupate. */
        for (int j = 0; j < k; j++)
            for (int i = 0; i < n; i++)
                p[j] += c[i][j];
        /* Exstragem P */
        System.out.print("P=( ");
        for (int j = 0; j < k; j++)
            System.out.print(p[j] + " ");
        System.out.println(")");
    }

    /* Aprecierea valorilor vectorului cu resurse disponibile */
    static boolean defineA() {
        for (int j = 0; j < k; j++) {
            a[j] = e[j] - p[j];
            /* controlul vectorului cu resurse existente şi al matricei cu resurse ocupate */
            if (a[j] < 0) {
                System.out.println("Date iniţiale incorecte");
                return false;
            }
        }

        /* Exstragem A */
        System.out.print("A=( ");
        for (int j = 0; j < k; j++)
            System.out.print(a[j] + " ");
        System.out.println(")");

        return true;
    }

    public static void main(String[] args) {
        /* Iniîializarea matricei cu starea curentă С */
        /**/
        c[0][0] = 3;
        c[0][1] = 0;
        c[0][2] = 1;
        c[0][3] = 1;
        c[1][0] = 0;
        c[1][1] = 1;
        c[1][2] = 0;
        c[1][3] = 0;
        c[2][0] = 1;
        c[2][1] = 1;
        c[2][2] = 1;
        c[2][3] = 0;
        c[3][0] = 1;
        c[3][1] = 1;
        c[3][2] = 0;
        c[3][3] = 1;
        c[4][0] = 0;
        c[4][1] = 0;
        c[4][2] = 0;
        c[4][3] = 0;
        c[0][0] = 1;
        c[0][1] = 0;
        c[0][2] = 1;
        c[0][3] = 1;
        c[1][0] = 0;
        c[1][1] = 1;
        c[1][2] = 0;
        c[1][3] = 0;
        c[2][0] = 1;
        c[2][1] = 1;
        c[2][2] = 1;
        c[2][3] = 0;
        c[3][0] = 1;
        c[3][1] = 1;
        c[3][2] = 0;
        c[3][3] = 1;
        c[4][0] = 0;
        c[4][1] = 0;
        c[4][2] = 0;
        c[4][3] = 0;
        /**/
        /*Extragem C */
        System.out.println("C={");
        for (int i = 0; i < n; i++) {
            System.out.print("   ");
            for (int j = 0; j < k; j++)
                System.out.print(c[i][j] + " ");
            System.out.println();
        }
        System.out.println("  }");

        /* Iniţializarea matricei cu resurse necesare R */

        r[0][0] = 1;
        r[0][1] = 1;
        r[0][2] = 0;
        r[0][3] = 0;
        r[1][0] = 0;
        r[1][1] = 1;
        r[1][2] = 1;
        r[1][3] = 2;
        r[2][0] = 3;
        r[2][1] = 1;
        r[2][2] = 0;
        r[2][3] = 0;
        r[3][0] = 0;
        r[3][1] = 3;
        r[3][2] = 1;
        r[3][3] = 0;
        r[4][0] = 2;
        r[4][1] = 1;
        r[4][2] = 1;
        r[4][3] = 0;
        r[0][0] = 0;
        r[0][1] = 1;
        r[0][2] = 0;
        r[0][3] = 0;
        r[1][0] = 0;
        r[1][1] = 0;
        r[1][2] = 1;
        r[1][3] = 1;
        r[2][0] = 1;
        r[2][1] = 1;
        r[2][2] = 0;
        r[2][3] = 0;
        r[3][0] = 0;
        r[3][1] = 3;
        r[3][2] = 0;
        r[3][3] = 0;
        r[4][0] = 1;
        r[4][1] = 0;
        r[4][2] = 1;
        r[4][3] = 0;
        /*Extragem R */
        System.out.println("R={ ");
        for (int i = 0; i < n; i++) {
            System.out.print("   ");
            for (int j = 0; j < k; j++)
                System.out.print(r[i][j] + " ");
            System.out.println();
        }
        System.out.println("  }");

        /* Iniţializarea vectorului cu resurse existente*/
        //e[0]=6;     e[1]=3;     e[2]=4; e[3]=4; // stare nestabilă
        e[0] = 6;
        e[1] = 5;
        e[2] = 5;
        e[3] = 5;  // stare stabilă
        //Extragem E
        System.out.print("E=( ");
        for (int j = 0; j < k; j++)
            System.out.print(e[j] + " ");
        System.out.println(")");
        defineP();
        defineS();
        if (defineA() == true) {
            int quantityOfGoodStrings = 0;
            for (int i = 0; i < n; i++) {
                if (s[i] != 1) {
                    int g = 0;
                    for (int j = 0; j < k; j++)
                        if (r[i][j] <= a[j])
                            g++;
                    if (g == k) {
                        System.out.println("Rînd bun " + (i));
                        quantityOfGoodStrings++;
                        for (int j = 0; j < k; j++) {
                            a[j] += r[i][j];
                            r[i][j] = 0;

                            s[i] = 1;
                            i = 0;
                        }
                    }
                    if (quantityOfGoodStrings == n) {
                        System.out.println("Stare stabilă");
                        return;
                    } else {
                        if (i == (n - 1)) {
                            System.out.println("Stare nestabilă");
                            return;
                        }
                    }
                }
            }
        }
    }
}
