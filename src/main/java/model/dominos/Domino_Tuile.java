package model.dominos;

import model.Tuile;

public class Domino_Tuile implements Tuile {

    int[][] tuile;

    public Domino_Tuile(){
        tuile = new int[4][3];
        for(int i=0;i<4;i++){
            for(int j=0;j<3;j++){
                tuile[i][j] = (int) (Math.random() * 4);
            }
        }
    }

    @Override
    public void turnRight(){            // Faire tourner la tuile vers là droite
        int[] tmp = tuile[3];
        for(int i=3;i>0;i--){
            tuile[i] = tuile[i-1];
        }
        tuile[0] = tmp;
    }

    @Override
    public void turnLeft(){             // Faire tourner la tuile vers là gauche
        int[] tmp = tuile[0];
        for(int i=1;i<4;i++){
            tuile[i-1] = tuile[i];
        }
        tuile[3] = tmp;
    }

    public static String espace(int n){
        String esp = "";
        for(int i=0;i<n;i++){
            esp+=" ";
        }
        return esp;
    }

    @Override
    public String toString(){   // Mettre la tuile en String pour facilité l'affichage avec l'Interface Graphique
        return  " "+tuile[0][0]+""+tuile[0][1]+""+tuile[0][2]+" "+
                tuile[3][2]+espace(3)+tuile[1][0]+
                tuile[3][1]+espace(3)+tuile[1][1]+
                tuile[3][0]+espace(3)+tuile[1][2]+
                " "+tuile[2][2]+""+tuile[2][1]+""+tuile[2][0]+" ";
    }

    public void affiche(){  // Affiche la tuile dans le Terminal
        System.out.println(
                espace(2)+tuile[0][0]+espace(2)+tuile[0][1]+espace(2)+tuile[0][2]+"\n"+
                tuile[3][2]+espace(9)+tuile[1][0]+"\n"+
                tuile[3][1]+espace(9)+tuile[1][1]+"\n"+
                tuile[3][0]+espace(9)+tuile[1][2]+"\n"+
                espace(2)+tuile[2][2]+espace(2)+tuile[2][1]+espace(2)+tuile[2][0]
        );
    }
}
