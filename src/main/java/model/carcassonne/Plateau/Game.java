package model.carcassonne.Plateau;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import gui.carcassonne.AffGestionTuile;
import gui.carcassonne.AffichageCarc;
import model.Controler;
import model.carcassonne.Piece.CarteComplet;
import model.carcassonne.Piece.DonneeCarte;
import model.carcassonne.Piece.SacCarte;
import model.carcassonne.Pion.Joueur;

public class Game {
    private AffichageCarc aff;
    private AffGestionTuile agt;
    private Controler controler;

    private SacCarte sac;
    private CarteComplet prochainePioche;

    private List<Joueur> listeJ;
    private int compteur;

    private int x, y, scale;

    /* Class en charge des tours de la partie. */
    public Game(AffichageCarc aff, AffGestionTuile agt, int nbJoueur) {
        this.aff = aff;
        this.agt = agt;
        initJoueur(nbJoueur);
        sac = new SacCarte();
        prochaineTuile();
        controler = new Controler(aff, agt, this);
        controler.carcassonneButtunPresed();
    }

    public void placement(int x, int y, int scale) {
        this.x = x;
        this.y = y;
        this.scale = scale;
    }

    /*
     * Met à jour la prochaine tuile pioché et actualise
     * la fenêtre de l'éditeur.
     */
    public void prochainTour() {
        if (!finJeu()) {
            increCompteur();
            prochaineTuile();
            agt.nextTuile(prochainePioche, prochainePioche.getRot());
        } else {
            aff.afficherMessage("La partie est terminé");
        }

    }

    /*
     * Importe les 4 tuiles autour de l'emplacement séléctionner
     * puis les envoie en comparaison pour détérminer si cette
     * position est valide pour la tuile en main.
     */
    public boolean verifPosition() {
        boolean status = true;
        if (aff.getCompo(x + 1, y) != null) {
            if (!aff.getCompo(x + 1, y).getCarteComplet().comparDG(prochainePioche)) {
                status = false;
            }
        }
        if (aff.getCompo(x - 1, y) != null) {
            if (!prochainePioche.comparDG(aff.getCompo(x - 1, y).getCarteComplet())) {
                status = false;
            }
        }
        if (aff.getCompo(x, y - 1) != null) {
            if (!aff.getCompo(x, y - 1).getCarteComplet().comparHB(prochainePioche)) {
                status = false;
            }
        }
        if (aff.getCompo(x, y + 1) != null) {
            if (!prochainePioche.comparHB(aff.getCompo(x, y + 1).getCarteComplet())) {
                status = false;
            }
        }
        return status;
    }

    /* Le jeu s'arrête dès que la pioche est vide. */
    private boolean finJeu() {
        if (sac.getSac().size() == 0) {
            return true;
        }
        return false;
    }

    /*
     * Renvoie la tuile actuel dans la pioche
     * puis en repioche une.
     */
    public void repiocher() {
        sac.retourDansSac(prochainePioche);
        prochaineTuile();
        agt.nextTuile(prochainePioche, prochainePioche.getRot());
    }

    /* Initialise les pions de chaque joueurs */
    public void initJoueur(int nbJ) {
        compteur = -1;
        listeJ = new ArrayList<Joueur>();
        listeJ.add(new Joueur(10, Color.blue, "bleu"));
        if (nbJ > 1) {
            listeJ.add(new Joueur(10, Color.yellow, "jaune"));
        }
        if (nbJ > 2) {
            listeJ.add(new Joueur(10, Color.green, "vert"));
        }
        if (nbJ > 3) {
            listeJ.add(new Joueur(10, Color.red, "rouge"));
        }
        if (nbJ > 4) {
            listeJ.add(new Joueur(10, Color.gray, "gris"));
        }
        if (nbJ > 5) {
            listeJ.add(new Joueur(10, Color.black, "noir"));
        }
    }

    /* Confirme l'ajout d'un pion sur le plateau */
    public void addPion(DonneeCarte d) {
        if (listeJ.get(compteur).pionDispo()) {
            listeJ.get(compteur).addPionOnBoard(d, x, y);
            aff.addPionOnBoard(x, y, listeJ.get(compteur).getColorName(), d);
        } else {
            aff.afficherMessage("Plus de pion disponible");
            agt.nextMove();
        }
    }

    /* Indique les coordonnées en X du pion */
    public int getX(DonneeCarte d) {
        switch (d) {
            case NORD_EST:
                return scale - (scale / 3);
            case EST:
                return scale - (scale / 3);
            case SUD_EST:
                return scale - (scale / 3);
            case NORD:
                return scale / 3;
            case CENTRE:
                return scale / 3;
            case SUD:
                return scale / 3;
            case NORD_OUEST:
                return 0;
            case OUEST:
                return 0;
            case SUD_OUEST:
                return 0;
            default:
                return -1;
        }
    }

    /* Indique les coordonnées en Y du pion */
    public int getY(DonneeCarte d) {
        switch (d) {
            case NORD_EST:
                return 0;
            case NORD:
                return 0;
            case NORD_OUEST:
                return 0;
            case OUEST:
                return scale / 3;
            case CENTRE:
                return scale / 3;
            case EST:
                return scale / 3;
            case SUD_EST:
                return scale - (scale / 3);
            case SUD:
                return scale - (scale / 3);
            case SUD_OUEST:
                return scale - (scale / 3);
            default:
                return -1;
        }
    }

    /* Pioche la prochaine tuile. */
    public void prochaineTuile() {
        prochainePioche = sac.nextTuile();
    }

    /* Permet de définir c'est le tour de quel joueur; */
    public void increCompteur() {
        compteur = (compteur + 1) % listeJ.size();
    }

    public CarteComplet getPP() {
        return prochainePioche;
    }

    public Joueur joueurAct() {
        return listeJ.get(compteur);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setControle(Controler controler) {
        this.controler = controler;
    }

    public Controler getControle() {
        return controler;
    }
}
