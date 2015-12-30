package colors_flood;

import java.util.Random;

/**
 * Created by Owaa on 17/11/15.
 */
public class Grille {


    int map [][];
    boolean  inColored[][];
    private int ligne;
    private int colone;
    private int nbColors;
    public  Grille (){


    }
    public  Grille (int nbLigne , int nbColone, int nbColors){

        map = new int [nbLigne][nbColone];
        inColored = new boolean[nbLigne][nbColone];

        this.ligne = nbLigne;
        this.colone = nbColone;

        Random rand = new Random();
        for(int i = 0 ; i< nbLigne ;i++){
            for(int j = 0 ; j < nbColone ; j++){

                map[i][j] = rand.nextInt(nbColors) + 1;
                inColored[i][j] = false;

            }
        }
        inColored[0][0] = true;
    }

    // rajouter les methodes de grille

    // getColorBlock

    // setColorBlock

    public boolean changeColors (int colorSelected ){

        boolean victoir = true;
        for(int i = 0 ; i< this.ligne ;i++){
            for(int j = 0 ; j < this.colone ; j++){


                if(this.inColored[i][j] )
                     map[i][j] = colorSelected;
                else {
                    victoir = false;
                    continue;
                }
                // Gauche;

                if(j != 0){
                    if(this.map[i][j-1] == colorSelected &&  this.inColored[i][j]){
                        this.inColored[i][j-1] = true;
                    }
                }


                if(i != 0){
                    if(this.map[i-1][j] == colorSelected &&  this.inColored[i][j]){
                        this.inColored[i-1][j] = true;
                    }
                }
                // Droite
                if(j != (this.colone - 1)){
                    if(this.map[i][ j+1 ] == colorSelected &&  this.inColored[i][j]){
                        this.inColored[i][ j+1 ] = true;
                    }
                }
                if(i != (this.ligne - 1)){
                    if(this.map[i+1][j] == colorSelected &&  this.inColored[i][j]){
                        this.inColored[i+1][j] = true;
                    }
                }

            }
        }

           // return Arrays.asList(this.inColored).contains(false) ;
        return victoir;

    }


    public  void recommencer (  int nbColors) {

        Random rand = new Random();
        for(int i = 0 ; i< this.ligne ;i++){
            for(int j = 0 ; j < this.colone ; j++){

                map[i][j] = rand.nextInt(nbColors) + 1;
                inColored[i][j] = false;

            }
        }
        inColored[0][0] = true;
    }
}
