package model.dominos;

import model.Plateau;

public class Domino_Plateau implements Plateau {

    private Domino_Tuile[][] plateau;
    private Domino_Sac sac;
    private int size;

    /*
        Constructeur qui doit créer un tableau de tuile de taille (x+2)(x+2), +2 pour facilité la vérification des
        conditions par la suite.
     */
    public Domino_Plateau(int n){
        plateau = new Domino_Tuile[n+2][n+2];
        int x = (int) (1+Math.random()*n);
        int y = (int) (1+Math.random()*n);
        plateau[x][y] = new Domino_Tuile();
        sac = new Domino_Sac(n*n);
        size = n;
    }

    public String espace(int n){
        return Domino_Tuile.espace(n);
    }

    public void tiret(){
        String tiret = "";
        for(int i=0;i<12*(plateau.length-2);i++){
            tiret+="-";
        }
        System.out.println(tiret);
    }

    public void affiche(){      // Affiche le Domino_Plateau sur le terminal avec les tuiles poser et les coordonnées.
        tiret();
        for(int i=1;i<plateau.length-1;i++){
            String l1 = "";
            String l2 = "";
            String l3 = "";
            String l4 = "";
            String l5 = "";
            for(int j=1;j<plateau[i].length-1;j++) {
                if (plateau[i][j] != null) {
                    l1 += espace(2) + plateau[i][j].tuile[0][0] + espace(2) + plateau[i][j].tuile[0][1] + espace(2) + plateau[i][j].tuile[0][2] + espace(3);
                    l2 += plateau[i][j].tuile[3][2] + espace(9) + plateau[i][j].tuile[1][0] + espace(1);
                    l3 += plateau[i][j].tuile[3][1] + espace(9) + plateau[i][j].tuile[1][1] + espace(1);
                    l4 += plateau[i][j].tuile[3][0] + espace(9) + plateau[i][j].tuile[1][2] + espace(1);
                    l5 += espace(2) + plateau[i][j].tuile[2][2] + espace(2) + plateau[i][j].tuile[2][1] + espace(2) + plateau[i][j].tuile[2][0] + espace(3);
                } else {
                    l1 += espace(12);
                    l2 += espace(12);
                    l3 += espace(4)+i+"/"+j+espace(5);
                    l4 += espace(12);
                    l5 += espace(12);
                }
            }
            System.out.println(l1);
            System.out.println(l2);
            System.out.println(l3);
            System.out.println(l4);
            System.out.println(l5);
        }
        tiret();
    }

    public static boolean arrayEquals(int[] t1, int[] t2){              // Vérifie que les deux bords des tuiles soit les mêmes
        for(int i=0;i<t1.length;i++){
            if(t1[2-i] != t2[i]) return false;
        }
        return true;
    }

    public boolean addVerifTop(int x, int y, Domino_Tuile tuile){       // Vérifie le haut
        if(plateau[x-1][y] == null) return true;
        return arrayEquals(tuile.tuile[0], plateau[x-1][y].tuile[2]);
    }

    public boolean addVerifRight(int x, int y, Domino_Tuile tuile){     // Vérifie la droite
        if(plateau[x][y+1] == null) return true;
        return arrayEquals(tuile.tuile[1], plateau[x][y+1].tuile[3]);
    }

    public boolean addVerifBottum(int x, int y, Domino_Tuile tuile){    // Vérifie le bas
        if(plateau[x+1][y] == null) return true;
        return arrayEquals(tuile.tuile[2], plateau[x+1][y].tuile[0]);
    }

    public boolean addVerifLeft(int x, int y, Domino_Tuile tuile){      // Vérifie la gauche
        if(plateau[x][y-1] == null) return true;
        return arrayEquals(tuile.tuile[3], plateau[x][y-1].tuile[1]);
    }

    /*
        Vérifie que toutes les conditions soient respectées (Tuile déjà présent, pas de Tuile autour et chiffre different).
     */

    @Override
    public boolean addVerif(int x, int y, Domino_Tuile tuile){
        if(this.plateau[x][y] != null){
            System.out.println("Il y a déjà une tuile.");
            return false;
        }else{
            if(this.plateau[x][y+1] == null && this.plateau[x][y-1] == null && this.plateau[x+1][y] == null && this.plateau[x-1][y] == null){
                System.out.println("Pas de tuile au tour.");
                return false;
            }else{
                if(!(addVerifTop(x,y,tuile) && addVerifBottum(x,y,tuile) && addVerifLeft(x,y,tuile) && addVerifRight(x,y,tuile))){
                    System.out.println("Chiffre different.");
                    return false;
                }
                return true;
            }
        }
    }

    public void addTuile(int x, int y, Domino_Tuile tuile){             // Ajoute la Tuile sur le plateau aux positions x, y
        if(addVerif(x,y,tuile)){
            plateau[x][y] = tuile;
        }
    }

    public Domino_Tuile nextTuile(){                                    // Avoir la Tuile suivante
        return sac.nextTuile();
    }

    public int getSize() {                                              // Avoir la taille du plateau
        return size;
    }

    public boolean sacEmpty(){                                          // Si le n'est pas vide
        return sac.sacEmpty();
    }

    public Domino_Tuile[][] getPlateau() {                              // Avoir le plateau
        return plateau;
    }

}