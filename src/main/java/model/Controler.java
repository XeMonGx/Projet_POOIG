package model;

import gui.Menu;
import gui.dominos.Jeu_Dominos;
import model.carcassonne.AffGestionTuile;
import model.carcassonne.AffichageCarc;
import model.carcassonne.Piece.DonneeCarte;
import model.carcassonne.Plateau.Game;

public class Controler {

    private Menu menu;
    private Jeu_Dominos dominos;
    private AffichageCarc aff;
    private AffGestionTuile agt;
    private Game game;

    public Controler(Menu menu){
        this.menu = menu;
    }
    public Controler(Jeu_Dominos dominos){
        this.dominos = dominos;
    }
    public Controler(AffichageCarc aff, AffGestionTuile agt, Game game){
        this.aff = aff;
        this.agt = agt;
        this. game = game;
    }

    public void menuButtonPresed(){
        menu.getButtonJouer().addActionListener(e -> {
            menu.getButtonJouer().setVisible(false);
            menu.getDominos().setVisible(true);
            menu.getCarcassonne().setVisible(true);
            menu.validate();
        });
    }

    public void domimosButtunPresed(){

        dominos.getTurnLeft().addActionListener(e -> {
            dominos.getTuile().tournerGauche();
            dominos.getSelectTuile().miseAJourTuile(dominos.getTuile().toString());
            dominos.repaint();
        });
        dominos.getTurnRight().addActionListener(e -> {
            dominos.getTuile().tournerDroite();
            dominos.getSelectTuile().miseAJourTuile(dominos.getTuile().toString());
            dominos.repaint();
        });
        dominos.getSubmit().addActionListener(e -> {
            if(dominos.getPlateau().sacEmpty() && dominos.getAffPlateau().addTuile(dominos.getTuile())){
                dominos.setTuile(dominos.getPlateau().nextTuile());
                dominos.getSelectTuile().miseAJourTuile(dominos.getTuile().toString());
                dominos.repaint();
            }
        });
        dominos.getSkip().addActionListener(e -> {
            if(!dominos.getPlateau().sacEmpty()){
                dominos.setTuile(dominos.getPlateau().nextTuile());
                dominos.getSelectTuile().miseAJourTuile(dominos.getTuile().toString());
                dominos.repaint();
            }
        });

    }

    public void carcassonneButtunPresed(){
        agt.getRotG().addActionListener(
            e -> {
                agt.getCarteComplet().rotationGauche();
                agt.nextTuile(agt.getCarteComplet(), agt.getCarteComplet().getRot());
            }
        );
        agt.getValider().addActionListener(
            e -> {
                if(game.verifPosition() && aff.getModeMouv()){
                    aff.ajoutTuile(agt.getCarteComplet());
                    agt.boutonTuile(false);
                    agt.boutonPion(true);
                    agt.getJLabel().setText("Poser un pion ?");
                }else{
                    //aff.setModeMouv(false);
                    aff.afficherMessage("Emplacement ou sens de la tuile invalide à cette position");
                }
            }
        );
        agt.getRotD().addActionListener(
            e -> {
                agt.getCarteComplet().rotationDroite();
                agt.nextTuile(agt.getCarteComplet(), agt.getCarteComplet().getRot());
            }
        );
        agt.getRepioche().addActionListener(
            e -> {
                game.repiocher();
            }
        );
        agt.getPoser().addActionListener(
            e -> {
                agt.changeMenu(agt.getMenuBis());
            }
        );
        agt.getSkip().addActionListener(
            e -> {
                agt.nextMove();
            }
        );
        agt.getJNO().addActionListener(
            e -> {
                game.addPion(DonneeCarte.NORD_OUEST);
                agt.nextMove();
            }
        );
        agt.getJN().addActionListener(
            e -> {
                game.addPion(DonneeCarte.NORD);
                agt.nextMove();
            }
        );
        agt.getJNE().addActionListener(
            e -> {
                game.addPion(DonneeCarte.NORD_EST);
                agt.nextMove();
            }
        );
        agt.getJE().addActionListener(
            e -> {
                game.addPion(DonneeCarte.EST);
                agt.nextMove();
            }
        );
        agt.getJSE().addActionListener(
            e -> {
                game.addPion(DonneeCarte.SUD_EST);
                agt.nextMove();
            }
        );
        agt.getJS().addActionListener(
            e -> {
                game.addPion(DonneeCarte.SUD);
                agt.nextMove();
            }
        );
        agt.getJO().addActionListener(
            e -> {
                game.addPion(DonneeCarte.SUD_OUEST);
                agt.nextMove();
            }
        );
        agt.getJO().addActionListener(
            e -> {
                game.addPion(DonneeCarte.OUEST);
                agt.nextMove();
            }
        );
        agt.getJC().addActionListener(
            e -> {
                game.addPion(DonneeCarte.CENTRE);
                agt.nextMove();
            }
        );
        agt.getAnnuler().addActionListener(
            e -> {
                agt.nextMove();
            }
        );
    }

}
