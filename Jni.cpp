#include "pch.h"
#include "Jni.h"

using namespace std;

#include <stdlib.h>  
#include <stdio.h>  
#include <iostream>
#include <vector>
#include <time.h>  

#include <fstream>

bool notCol[7];         //array che ha 1 se il bot non deve entrare nella colonna

vector<int> vetPrior;           //livello priorita' della sequenza       
        //[1 = mossa vittoria; 2 = mossa per parare avversario; 3 = colonna adiacente ad avversario; 4 = mossa per creazione di una possibile sequenza; 5 = scelta strategica; 6 = random]
vector<int> vetRig;             //riga in cui nel caso inserire il gettone player2
vector<int> vetCol;             //colonna in cui nel caso inserire il gettone player2

void sceltaBot(jint[][7], bool);      	//Verifica se player1 puo' vincere
void sceltaBot2(jint[][7], bool);		//Verifica se player2(bot) puo' vincere
void sceltaBot3(jint[][7]);				//Previene la formazione di sequenza di 4 da entrambi i lati della riga
void sceltaBot4(jint[][7], bool);		//trova coordinate casuali(liv: 0, 1) oppure nella cella con piu' possibilita' di vittoria il player2(bot) (liv: 2, 3)

void pulisciVet(bool);                  //pulizia vettori: vetPrior, vetRig e vetCol
void pulisciVet();		                //porta tutti gli elementi dell'array notCol a 0

bool assegnaBot(int, int, int);			    //sceglie la mossa di player2 basandosi su un sistema di priorita'

jint arr[3];    //rig - col - nK
string sstt = "";

void stampaMat2(jint[][7]);


JNIEXPORT jintArray JNICALL Java_Jni_getMossaBot
(JNIEnv* env, jobject obj, jint liv, jobjectArray matt, jint nK, jint nF) {
    jint mat[6][7];

    jint rig = env->GetArrayLength(matt);
    for (int i = 0; i < rig; i++) {
        jintArray arr = (jintArray)(env->GetObjectArrayElement(matt, i));
        jint col = env->GetArrayLength(arr);
        jint arRig[7];      //TODO: lubìnghezza prefissata
        env->GetIntArrayRegion(arr, 0, col, arRig);

        //TODO: ATTENZIONE ALLE ASSEGNAZIONI    ->      POSSIBILE ERRORE 0 = !0
        for (int j = 0; j < col; j++)
            mat[i][j] = arRig[j];
    }

    //stampaMat2(mat);
    srand((unsigned)time(NULL));

    if (liv == 0) {
        sceltaBot(mat, false);
        sceltaBot4(mat, false);
    }

    if (liv == 1) {
        sceltaBot(mat, true);
        sceltaBot2(mat, false);			//limitata dentro assegnaBot
        sceltaBot4(mat, false);
    }

    if (liv == 2 || liv == 3) {
        sceltaBot(mat, true);
        sceltaBot2(mat, true);
        sceltaBot3(mat);
        sceltaBot4(mat, true);
    }

    assegnaBot(liv, nK, nF);

    jintArray ar = env->NewIntArray(3);
    env->SetIntArrayRegion(ar, 0, 3, arr);

    return ar;
}

void pulisciVet()
{
    for (int i = 0; i < 7; i++)
        notCol[i] = 0;
}
void pulisciVet(bool z)
{
    vetPrior.clear();
    vetRig.clear();
    vetCol.clear();
}

void stampaMat2(jint Mat[][7]) {
    fstream f1;
    f1.open("C:\\Users\\gaeta\\Documents\\java\\p1v5\\Dll7\\Dll7\\Testo.txt", ios::app);

    f1 << "MATRICE:\n";
    for (int i = 0; i < 1; i++) {
        for (int j = 0; j < 1; j++) {
            Mat[i][j] = 0;
            f1 << Mat[i][j] << "\t";
        }
        f1 << endl;
    }

    f1.close();
}


void sceltaBot(jint Mat[][7], bool p)
{
    pulisciVet();
    //verifico possibilità di vittoria da parte dell'avversario
    for (int i = 0; i <= 5; i++)
        for (int j = 0; j <= 6; j++)
        {

            //righe
            if ((((Mat[i][j] == Mat[i][j + 1] || Mat[i][j] == Mat[i][j + 2]) && Mat[i][j] == Mat[i][j + 3])
                || (Mat[i][j] == Mat[i][j + 1] && (Mat[i][j] == Mat[i][j + 2] || Mat[i][j] == Mat[i][j + 3])))
                && (Mat[i][j] != 0) && (Mat[i][j] != 2 && Mat[i][j + 1] != 2 && Mat[i][j + 2] != 2 && (Mat[i][j + 3] != 2 || Mat[i][j - 1] != 2)))
            {
                //creo array con possibile formazione di quartetto
                int Vet[5];     //-1 <-to-> 3
                for (int v = 0; v < 5; v++)
                    if (j + v - 1 < 0 || j + v - 1 > 6)      //se la colonna corrispondente è al di fuori della matrice stabilisci player2
                        Vet[v] = 2;
                    else
                        Vet[v] = Mat[i][j + v - 1];

                int e = 0;
                //conto il numero di uno nel vettore
                for (int p = 0; p < 5; p++)
                    if (Vet[p] == 1)
                        e++;

                if (e == 3)      //se il vettore abbia 3 uno
                {
                    bool r = true;      //1 = c'è possibilità che l'avversario alla prossima mossa possa vincere

                    for (int vv = 0; vv < 5; vv++)
                    {
                        //scorro il vettore partendo dal centro:    53124
                        int nV = -1;         // colonna in cui inserire il gettone player2  ||NB BASANDOSI SULL'ARRAY -1 <-to-> 3
                        if (vv == 0 && Vet[2] == 0)
                            nV = 2;
                        else if (vv == 1 && Vet[3] == 0)
                            nV = 3;
                        else if (vv == 2 && Vet[1] == 0)
                            nV = 1;
                        else if (vv == 3 && Vet[4] == 0)
                            nV = 4;
                        else if (vv == 4 && Vet[0] == 0)
                            nV = 0;

                        if (nV != -1)        //se esiste almeno un buco per impedire la sequenza di 4
                        {
                            if ((Mat[i + 1][j + nV - 1] != 0 || i == 5) && Mat[i][j + nV - 1] == 0 && (r || nV == 0))
                            {
                                vetPrior.push_back(2);      //livello priorità = 2
                                vetRig.push_back(i);
                                vetCol.push_back(j + nV - 1);
                            }
                            if ((Mat[i + 1][j + nV - 1] == 0 && i != 5) && (Mat[i + 2][j + nV - 1] != 0 || i + 1 == 5))         //se colonna ha base vuota, oppure è all'ultima riga: dico al bot di non selezionare quella colonna
                            {
                                notCol[j + nV - 1] = 1;
                                r = false;
                            }
                        }
                    }
                }
            }

            //colonne
            if ((Mat[i][j] == Mat[i + 1][j] && Mat[i][j] == Mat[i + 2][j])
                && (Mat[i][j] != 2 && Mat[i + 1][j] != 2 && Mat[i + 2][j] != 2
                    && (Mat[i + 3][j] != 2 || Mat[i - 1][j] != 2)) && (Mat[i][j] != 0 || Mat[i + 1][j] != 0))
                if ((i <= 3 && i >= 1) && Mat[i - 1][j] == 0)
                {
                    vetPrior.push_back(2);      //livello priorità = 2
                    vetRig.push_back(i - 1);
                    vetCol.push_back(j);
                }

            if (p)		//se siamo al livello 3
            {
                //diagonale ++
                if ((((Mat[i][j] == Mat[i + 1][j + 1] || Mat[i][j] == Mat[i + 2][j + 2])
                    && Mat[i][j] == Mat[i + 3][j + 3]) || (Mat[i][j] == Mat[i + 1][j + 1] && (Mat[i][j] == Mat[i + 2][j + 2]
                        || Mat[i][j] == Mat[i + 3][j + 3]))) && (Mat[i][j] != 0) && (Mat[i][j] != 2 && Mat[i + 1][j + 1] != 2
                            && Mat[i + 2][j + 2] != 2 && (Mat[i + 3][j + 3] != 2 || Mat[i - 1][j - 1] != 2)))
                {
                    //creo array con possibile formazione di quartetto
                    int Vet[5];     //-1 <-to-> 3
                    for (int v = 0; v < 5; v++)
                        if ((j + v - 1 < 0 || j + v - 1 > 6) || (i + v - 1 < 0 || i + v - 1 > 5))        //se la colonna o la riga corrispondente è al di fuori della matrice stabilisci player2
                            Vet[v] = 2;
                        else
                            Vet[v] = Mat[i + v - 1][j + v - 1];

                    int e = 0;
                    //conto il numero di uno nel vettore
                    for (int p = 0; p < 5; p++)
                        if (Vet[p] == 1)
                            e++;

                    if (e == 3)          //se ci sono aleno 3 uno
                    {
                        bool r = true;

                        for (int vv = 0; vv < 5; vv++)
                        {
                            //scorro il vettore partendo dal centro:    53124
                            int nV = -1;         // colonna in cui inserire il gettone player2  ||NB BASANDOSI SULL'ARRAY -1 <-to-> 3
                            if (vv == 0 && Vet[2] == 0)
                                nV = 2;
                            else if (vv == 1 && Vet[3] == 0)
                                nV = 3;
                            else if (vv == 2 && Vet[1] == 0)
                                nV = 1;
                            else if (vv == 3 && Vet[4] == 0)
                                nV = 4;
                            else if (vv == 4 && Vet[0] == 0)
                                nV = 0;

                            if (nV != -1)        //se esiste almeno un buco per impedire il 4tetto
                            {
                                if ((Mat[i + nV][j + nV - 1] != 0 || (i + nV - 1) == 5) && Mat[i + nV - 1][j + nV - 1] == 0 && (r || nV == 0))        //se hai la base sotto la diagonale oppure se sei alla riga 5
                                {
                                    vetPrior.push_back(2);      //livello priorità = 2
                                    vetRig.push_back(i + nV - 1);
                                    vetCol.push_back(j + nV - 1);
                                }                             //altrimenti se nella colonna precedente sotto è vuoto guarda le altre colonne
                                if ((Mat[i + nV][j + nV - 1] == 0 && i + nV != 5) && (Mat[i + nV + 1][j + nV - 1] != 0 || i + nV == 5))         //se colonna ha base vuota, dico al bot di non selezionare quella colonna
                                {
                                    notCol[j + nV - 1] = 1;
                                    r = false;
                                }
                            }
                        }
                    }
                }

                //diagonale -+
                if ((((Mat[i][j] == Mat[i - 1][j + 1] || Mat[i][j] == Mat[i - 2][j + 2])
                    && Mat[i][j] == Mat[i - 3][j + 3]) || (Mat[i][j] == Mat[i - 1][j + 1]
                        && (Mat[i][j] == Mat[i - 2][j + 2] || Mat[i][j] == Mat[i - 3][j + 3])))
                    && (Mat[i][j] != 0) && (Mat[i][j] != 2 && Mat[i - 1][j + 1] != 2 && Mat[i - 2][j + 2] != 2
                        && (Mat[i - 3][j + 3] != 2 || Mat[i + 1][j - 1] != 2)))
                {
                    //creo array con possibile formazione di quartetto
                    int Vet[5];     //-1 <-to-> 3
                    for (int v = 0; v < 5; v++)
                        if ((j + v - 1 < 0 || j + v - 1 > 6) || (i - v + 1 < 0 || i - v + 1 > 5))        //se la colonna o la riga corrispondente è al di fuori della matrice stabilisci player2
                            Vet[v] = 2;
                        else
                            Vet[v] = Mat[i - v + 1][j + v - 1];

                    int e = 0;
                    //conto il numero di uno nel vettore
                    for (int p = 0; p < 5; p++)
                        if (Vet[p] == 1)
                            e++;

                    if (e == 3)          //se ci sono aleno 3 uno
                    {
                        bool r = true;

                        for (int vv = 0; vv < 5; vv++)
                        {
                            //scorro il vettore partendo dal centro:    53124
                            int nV = -1;         // colonna in cui inserire il gettone player2  ||NB BASANDOSI SULL'ARRAY -1 <-to-> 3
                            if (vv == 0 && Vet[2] == 0)
                                nV = 2;
                            else if (vv == 1 && Vet[3] == 0)
                                nV = 3;
                            else if (vv == 2 && Vet[1] == 0)
                                nV = 1;
                            else if (vv == 3 && Vet[4] == 0)
                                nV = 4;
                            else if (vv == 4 && Vet[0] == 0)
                                nV = 0;

                            if (nV != -1)
                            {
                                if ((Mat[i - nV + 2][j + nV - 1] != 0 || (i - nV + 1) == 5) && Mat[i - nV + 1][j + nV - 1] == 0 && (r || nV == 0))        //se hai la base sotto la diagonale oppure se sei alla riga 5
                                {
                                    vetPrior.push_back(2);      //livello priorità = 2
                                    vetRig.push_back(i - nV + 1);
                                    vetCol.push_back(j + nV - 1);
                                }                      //altrimenti se nella colonna precedente sotto è vuoto guarda le altre colonne
                                if ((Mat[i - nV + 2][j + nV - 1] == 0 && i - nV + 1 != 5) && (Mat[i - nV + 3][j + nV - 1] != 0 || i - nV + 2 == 5))         //se colonna ha base vuota, dico al bot di non selezionare quella colonna
                                {
                                    notCol[j + nV - 1] = 1;
                                    r = false;
                                }
                            }
                        }
                    }
                }
            }
        }
}

void sceltaBot2(jint Mat[][7], bool p)
{
    //verifico possibilità di sequenza di 4 player2(bot)
    for (int i = 5; i >= 0; i--)         //decrescente per far analizzare prima le possibili combinazioni più accessibili al bot
        for (int j = 0; j <= 6; j++)
        {
            //righe
            if ((Mat[i][j] == 2) && ((Mat[i][j] == Mat[i][j + 1] || Mat[i][j] == Mat[i][j + 2])
                || Mat[i][j] == Mat[i][j + 3]) && (Mat[i][j] != 1 && Mat[i][j + 1] != 1 && Mat[i][j + 2] != 1
                    && (Mat[i][j + 3] != 1 || Mat[i][j - 1] != 1)))     //j + 3 < 7
            {
                //creo array con possibile formazione di quartetto
                int Vet[5];     //-1 <-to-> 3
                for (int v = 0; v < 5; v++)
                {
                    if (j + v - 1 < 0 || j + v - 1 > 6)      //se la colonna corrispondente è al di fuori della matrice stabilisci player1(da non considerare)
                        Vet[v] = 1;
                    else
                        Vet[v] = Mat[i][j + v - 1];
                }

                //verifico livello di priorità contando il numero di 1
                int num2[2] = { 0, 0 };       //[0] = contatore di numero di 2 nel vettore; [1] = contatore numero 0
                for (int kk = 0; kk < 5; kk++)
                {
                    if (Vet[kk] == 2)
                        num2[0] += 1;
                    if (Vet[kk] == 0)
                        num2[1] += 1;
                }
                bool r = true;          //1 = possibilità di vittoria, 0 = caso in cui non è presente la base al di sotto della cella in cui si deve fare la mossa

                for (int vv = 0; vv < 5; vv++)			//scorro il vettore co nbsequenza   53124
                {
                    int nV = -1;
                    if (vv == 0 && Vet[2] == 0)
                        nV = 2;
                    else if (vv == 1 && Vet[3] == 0)
                        nV = 3;
                    else if (vv == 2 && Vet[1] == 0)
                        nV = 1;
                    else if (vv == 3 && Vet[4] == 0)
                        nV = 4;
                    else if (vv == 4 && Vet[0] == 0)
                        nV = 0;

                    if (nV != -1)
                    {
                        if (Mat[i + 1][j + nV - 1] != 0 || i == 5)         //se esiste una base oppure sei all'ultima riga
                        {
                            if (num2[0] == 3 && num2[1] >= 1 && r)     //se ci sono tre 2 e almeno una cella vuota
                            {
                                vetPrior.push_back(1);      //livello priorità = 1
                                vetRig.push_back(i);
                                vetCol.push_back(j + nV - 1);
                                break;
                            }
                            else if ((num2[0] == 2 || !r) && num2[1] >= 2)      //se ci sono due 2 e almeno due celle vuote     //((num2[0] == 2 || (!r && num2[0] == 3)) && num2[1] >= 2)
                            {
                                vetPrior.push_back(4);      //livello priorità = 4
                                vetRig.push_back(i);
                                vetCol.push_back(j + nV - 1);
                                break;
                            }
                        }
                        else if ((Mat[i + 1][j + nV - 1] == 0 && i != 5) && num2[1] == 1)   //&& Mat[i][j + nV - 1] == 0   //se non è presente la base al di sotto ed è presente una sola cella vuota non considerare la situazione
                            break;
                        else if ((Mat[i + 1][j + nV - 1] == 0 && i != 5) && nV != 4)        //caso num2[1] == 1 && no base al disotto nel mezzo del vettore
                            r = false;      //se non hai la base al disotto la priorità corrisponderà a 4
                    }
                }
            }

            //colonne       (da alto a basso)
            if ((Mat[i][j] == 2) && (Mat[i][j] == Mat[i + 1][j] || Mat[i][j] == Mat[i + 2][j])
                && (Mat[i][j] != 1 && Mat[i + 1][j] != 1 && Mat[i + 2][j] != 1 && Mat[i - 1][j] != 1))
            {
                if ((i <= 3 && i >= 1) && Mat[i - 1][j] == 0)        //se entra nel range della matrice
                {
                    int Vet[3];
                    for (int v = 0; v < 3; v++)
                        Vet[v] = Mat[i + v][j];

                    //verifico livello di priorità contando il numero di 1
                    int num2 = 0;       //contatore di numero di 2 nel vettore
                    for (int kk = 0; kk < 3; kk++)
                        if (Vet[kk] == 2)
                            num2++;

                    if (num2 == 3)
                    {
                        vetPrior.push_back(1);      //livello priorità = 1
                        vetRig.push_back(i - 1);
                        vetCol.push_back(j);
                        break;
                    }
                    else if (num2 == 2)
                    {
                        vetPrior.push_back(4);      //livello priorità = 4
                        vetRig.push_back(i - 1);
                        vetCol.push_back(j);
                        break;
                    }
                }
            }

            if (p)
            {
                //diagonale ++
                if ((Mat[i][j] == 2) && (((Mat[i][j] == Mat[i + 1][j + 1] || Mat[i][j] == Mat[i + 2][j + 2])
                    || Mat[i][j] == Mat[i + 3][j + 3])) && (Mat[i][j] != 1 && Mat[i + 1][j + 1] != 1
                        && Mat[i + 2][j + 2] != 1 && (Mat[i + 3][j + 3] != 1 || Mat[i - 1][j - 1] != 1)))
                {
                    int Vet[5];     //-1 <-to-> 3
                    for (int v = 0; v < 5; v++)
                        if ((j + v - 1 < 0 || j + v - 1 > 6) || (i + v - 1 < 0 || i + v - 1 > 5))        //se la colonna o la riga corrispondente è al di fuori della matrice stabilisci player1
                            Vet[v] = 1;
                        else
                            Vet[v] = Mat[i + v - 1][j + v - 1];

                    int num2[2] = { 0, 0 };
                    for (int kk = 0; kk < 5; kk++)
                    {
                        if (Vet[kk] == 2)
                            num2[0] += 1;
                        if (Vet[kk] == 0)
                            num2[1] += 1;
                    }

                    bool r = true;          //1 = possibilità di vittoria, 0 = caso in cui non è presente la base al di sotto della cella in cui si deve fare la mossa

                    //scorro vettore in ordine 53124
                    for (int vv = 0; vv < 5; vv++)
                    {
                        int nV = -1;
                        if (vv == 0 && Vet[2] == 0)
                            nV = 2;
                        else if (vv == 1 && Vet[3] == 0)
                            nV = 3;
                        else if (vv == 2 && Vet[1] == 0)
                            nV = 1;
                        else if (vv == 3 && Vet[4] == 0)
                            nV = 4;
                        else if (vv == 4 && Vet[0] == 0)
                            nV = 0;

                        if (nV != -1)
                        {
                            if ((Mat[i + nV][j + nV - 1] != 0 || (i + nV - 1) == 5) && Mat[i + nV - 1][j + nV - 1] == 0)        //se hai la base sotto la diagonale oppure se sei alla riga 5
                            {
                                if (num2[0] == 3 && num2[1] >= 1 && r)
                                {
                                    vetPrior.push_back(1);      //livello priorità = 1
                                    vetRig.push_back(i + nV - 1);
                                    vetCol.push_back(j + nV - 1);
                                    break;
                                }
                                else if ((num2[0] == 2 || !r) && num2[1] >= 2)
                                {
                                    vetPrior.push_back(4);      //livello priorità = 4
                                    vetRig.push_back(i + nV - 1);
                                    vetCol.push_back(j + nV - 1);
                                    break;
                                }
                            }
                            else if ((Mat[i + nV][j + nV - 1] == 0 && i + nV != 5) && num2[1] == 1)   //se non è presente la base al di sotto non considerare la situazione
                                break;
                            else if ((Mat[i + nV][j + nV - 1] == 0 && i + nV != 5) && nV != 4 || (Mat[i + nV][j + nV - 1] == 0 && i + nV - 1 != 5 && nV == 3))      //se non è presente la base al di sotto, caso in cui la testa è alla quartultima riga
                                r = false;      //se non hai la base al disotto la priorità corrisponderà a 4
                        }
                    }
                }

                //diagonale -+
                if ((Mat[i][j] == 2) && (((Mat[i][j] == Mat[i - 1][j + 1] || Mat[i][j] == Mat[i - 2][j + 2])
                    || Mat[i][j] == Mat[i - 3][j + 3])) && (Mat[i][j] != 1 && Mat[i - 1][j + 1] != 1
                        && Mat[i - 2][j + 2] != 1 && (Mat[i - 3][j + 3] != 1 || Mat[i + 1][j - 1] != 1)))
                {
                    //creo array con possibile formazione di quartetto
                    int Vet[5];     //-1 <-to-> 3
                    for (int v = 0; v < 5; v++)
                        if ((j + v - 1 < 0 || j + v - 1 > 6) || (i - v + 1 < 0 || i - v + 1 > 5))        //se la colonna o la riga corrispondente è al di fuori della matrice stabilisci player1
                            Vet[v] = 1;
                        else
                            Vet[v] = Mat[i - v + 1][j + v - 1];

                    int num2[2] = { 0, 0 };               //da mettere fuori dal for(vv)
                    for (int kk = 0; kk < 5; kk++)
                    {
                        if (Vet[kk] == 2)
                            num2[0] += 1;
                        if (Vet[kk] == 0)
                            num2[1] += 1;
                    }

                    bool r = true;          //1 = possibilità di vittoria, 0 = caso in cui non è presente la base al di sotto della cella in cui si deve fare la mossa

                    for (int vv = 0; vv < 5; vv++)
                    {
                        int nV = -1;
                        if (vv == 0 && Vet[2] == 0)
                            nV = 2;
                        else if (vv == 1 && Vet[3] == 0)
                            nV = 3;
                        else if (vv == 2 && Vet[1] == 0)
                            nV = 1;
                        else if (vv == 3 && Vet[4] == 0)
                            nV = 4;
                        else if (vv == 4 && Vet[0] == 0)
                            nV = 0;

                        if (nV != -1)
                        {
                            if ((Mat[i - nV + 2][j + nV - 1] != 0 || (i - nV + 1) == 5) && Mat[i - nV + 1][j + nV - 1] == 0)        //se hai la base sotto la diagonale oppure se sei alla riga 5
                            {
                                if (num2[0] == 3 && num2[1] >= 1 && r)
                                {
                                    vetPrior.push_back(1);      //livello priorità = 1
                                    vetRig.push_back(i - nV + 1);
                                    vetCol.push_back(j + nV - 1);
                                    break;
                                }
                                else if ((num2[0] == 2 || !r) && num2[1] >= 2)
                                {
                                    vetPrior.push_back(4);      //livello priorità = 4
                                    vetRig.push_back(i - nV + 1);
                                    vetCol.push_back(j + nV - 1);
                                    break;
                                }
                            }
                            else if ((Mat[i - nV + 2][j + nV - 1] == 0 && i - nV + 1 != 5) && num2[1] == 1)   //se non è presente la base al di sotto non considerare la situazione
                                break;
                            else if ((Mat[i - nV + 2][j + nV - 1] == 0 && i - nV + 1 != 5) && nV != 4)      
                                r = false;      //se non hai la base al disotto la priorità corrisponderà a 4
                        }
                    }
                }
            }
        }
}

void sceltaBot3(jint Mat[][7])
{
    //verifico possibilità di fregatura da parte di player1
    for (int i = 5; i >= 0; i--)         //decrescente per far analizzare prima le possibili combinazioni più accessibili al bot
        for (int j = 0; j <= 6; j++)
        {
            //righe
            if ((Mat[i][j] == 1) && ((Mat[i][j] == Mat[i][j + 1] || Mat[i][j] == Mat[i][j + 2])
                && (Mat[i][j] != 2 && Mat[i][j + 1] != 2 && Mat[i][j + 2] != 2
                    && (Mat[i][j + 3] != 2 || Mat[i][j - 1] != 2)) && (j + 2 < 7)))     //j + 3 < 7
            {
                bool kk = true;
                //verifico che esista una base
                for (int qq = 0; qq < 7; qq++)
                    if (Mat[i + 1][j + qq - 1] == 0)
                    {
                        kk = false;
                        break;
                    }

                //se la base esiste allora procedi
                if (i == 5 || kk)
                {
                    //creo array con possibile formazione di quartetto
                    int Vet[5];     //-1 <-to-> 3
                    for (int v = 0; v < 5; v++)
                        if (j + v - 1 < 0 || j + v - 1 > 6)      //se la colonna corrispondente è al di fuori della matrice stabilisci player2(da non considerare)
                            Vet[v] = 2;
                        else
                            Vet[v] = Mat[i][j + v - 1];

                    int num2[2] = { 0, 0 };       //contatore di numero di 2 nel vettore,  [1] = contatore di 0
                    for (int kk = 0; kk < 5; kk++)
                    {
                        if (Vet[kk] == 1)
                            num2[0] += 1;
                        if (Vet[kk] == 0)
                            num2[1] += 1;
                    }

                    for (int vv = 0; vv < 5; vv++)           //  53124
                    {
                        int nV = -1;
                        if (vv == 0 && Vet[2] == 0)
                            nV = 2;
                        else if (vv == 1 && Vet[3] == 0)
                            nV = 3;
                        else if (vv == 2 && Vet[1] == 0)
                            nV = 1;
                        else if (vv == 3 && Vet[4] == 0)
                            nV = 4;
                        else if (vv == 4 && Vet[0] == 0)
                            nV = 0;

                        if (nV != -1)
                        {
                            if (((Mat[i + 1][j + nV - 1] != 0 || i == 5) && Mat[i][j + nV - 1] == 0) && (num2[0] >= 2 && num2[1] >= 2))
                            {
                                vetPrior.push_back(3);      //livello priorità = 3
                                vetRig.push_back(i);
                                vetCol.push_back(j + nV - 1);
                                break;
                            }
                        }
                    }
                }
            }
        }
}

void sceltaBot4(jint Mat[][7], bool p)
{
    if (p)        //livello 3, 2
    {
        /*
            scelta seguendo possibilità max di combianzioni
            3   4   5   7   5   4   3
            4   6   8   10  8   6   4
            5   8   11  13  11  8   5
            5   8   11  13  11  8   5
            4   6   8   10  8   6   4
            3   4   5   7   5   4   3
        */
        int comb[6][7] = {
            {3,  4,  5,  7,  5,  4,  3},
            {4,  6,  8,  10, 8,  6,  4},
            {5,  8,  11, 13, 11, 8,  5},
            {5,  8,  11, 13, 11, 8,  5},
            {4,  6,  8,  10, 8,  6,  4},
            {3,  4,  5,  7,  5,  4,  3}
        };

        //scorro colonne cercando la colonna col max numero di combinazioni
        int max = -1;
        int rig = 0;
        int col = 0;
        for (int j = 0; j < 7; j++)
            for (int i = 5; i >= 0; i--)
            {
                if (comb[i][j] > max && notCol[j] == 0 && Mat[i][j] == 0)
                {
                    max = comb[i][j];
                    rig = i;
                    col = j;
                }
                if (Mat[i][j] == 0)
                    break;
            }

        if (max != -1)       //se è stato possibile trovare una cella libera
        {
            vetPrior.push_back(5);      //livello priorità = 5
            vetRig.push_back(rig);
            vetCol.push_back(col);
        }
        else               //altrimenti esegui scelta casuale
            sceltaBot4(Mat, false);

    }
    else
    {
        //altrimenti random -- livello 0, 1
        int c = rand() % 7;
        if (Mat[0][c] == 0)      //se c'è sempre spazio nella colonna corrispondente per il gettone
        {
            vetPrior.push_back(6);      //livello priorità = 6
            vetRig.push_back(-1);       //la riga viene rilevata al momento dell'assegnazione
            vetCol.push_back(c);
        }
        else
            sceltaBot4(Mat, false);
    }
}

bool assegnaBot(int lev, int nK, int nF)
{
    arr[2] = 0;

    fstream f1;
    f1.open("C:\\Users\\gaeta\\Documents\\java\\p1v5\\Dll7\\Dll7\\Testo.txt", ios::app);
    //CONTINUARE CON SCRITTURA SU FILE

        //verifico esisitenza di coordinate a priorit� 1
    for (int i = 0; i < vetPrior.size(); i++)
        if (vetPrior[i] == 1 && lev != 0)
        {
            int rig = vetRig[i];
            int col = vetCol[i];
            //assegna(Mat, 2, rig, col);
            //cout << "PRIOR 1\t " << vetRig[i] << "  " << vetCol[i];

            f1 << "PRIOR 1\t " << vetRig[i] << "  " << vetCol[i] << endl;
            f1.close();

            arr[0] = vetRig[i];
            arr[1] = vetCol[i];

            pulisciVet(true);
            break;
            return false;
        }

    //verifico esisitenza di coordinate a priorit� 2
    for (int i = 0; i < vetPrior.size(); i++)
        if (vetPrior[i] == 2)
        {
            int rig = vetRig[i];
            int col = vetCol[i];
            if ((notCol[col] == 0 && lev == 3) || lev != 3)        //se la mossa non porta la vittoria di player1
            {
                nK++;
                if (nK <= nF || lev == 3)        //se hai superato nK(numero mosse bloccate) oppure sei al livello 3
                {
                    //assegna(Mat, 2, rig, col);
                    //cout << "PRIOR 2\t " << vetRig[i] << "  " << vetCol[i];

                    f1 << "PRIOR 2\t " << vetRig[i] << "  " << vetCol[i] << endl;
                    f1.close();

                    arr[0] = vetRig[i];
                    arr[1] = vetCol[i];
                    arr[2] = nK;

                    pulisciVet(true);
                    break;
                    return false;
                }
            }
        }

    //verifico esisitenza di coordinate a priorit� 3
    for (int i = 0; i < vetPrior.size(); i++)
        if (vetPrior[i] == 3 && lev != 1)
        {
            int rig = vetRig[i];
            int col = vetCol[i];
            if ((notCol[col] == 0 && lev == 3) || lev != 3)
            {
                //assegna(Mat, 2, rig, col);
                //cout << "PRIOR 3\t " << vetRig[i] << "  " << vetCol[i];

                f1 << "PRIOR 3\t " << vetRig[i] << "  " << vetCol[i] << endl;
                f1.close();

                arr[0] = vetRig[i];
                arr[1] = vetCol[i];

                pulisciVet(true);
                break;
                return false;
            }
        }

    //verifico esisitenza di coordinate a priorit� 4
    for (int i = 0; i < vetPrior.size(); i++)
        if (vetPrior[i] == 4)
        {
            int rig = vetRig[i];
            int col = vetCol[i];
            if (notCol[col] == 0 && lev == 3)
            {
                //assegna(Mat, 2, rig, col);
                //cout << "PRIOR 4\t " << vetRig[i] << "  " << vetCol[i];

                f1 << "PRIOR 4\t " << vetRig[i] << "  " << vetCol[i] << endl;
                f1.close();

                arr[0] = vetRig[i];
                arr[1] = vetCol[i];

                pulisciVet(true);
                break;
                return false;
            }
        }

    //verifico esisitenza di coordinate a priorit� 5
    for (int i = 0; i < vetPrior.size(); i++)
        if (vetPrior[i] == 5)
        {
            int rig = vetRig[i];
            int col = vetCol[i];
            //assegna(Mat, 2, rig, col);
            //cout << "PRIOR 5\t " << vetRig[i] << "  " << vetCol[i];

            f1 << "PRIOR 5\t " << vetRig[i] << "  " << vetCol[i] << endl;
            f1.close();

            arr[0] = vetRig[i];
            arr[1] = vetCol[i];

            pulisciVet(true);
            break;
            return false;
        }

    //verifico esisitenza di coordinate a priorit� 6
    for (int i = 0; i < vetPrior.size(); i++)
        if (vetPrior[i] == 6)
        {
            //riempiCol(true, vetCol[i], Mat);
            //cout << "PRIOR 6\t" << vetRig[i] << "  " << vetCol[i];

            f1 << "PRIOR 6\t " << vetRig[i] << "  " << vetCol[i] << endl;
            f1.close();

            arr[0] = vetRig[i];
            arr[1] = vetCol[i];

            pulisciVet(true);
            break;
            return false;
        }

    f1.close();

    return false;
}
