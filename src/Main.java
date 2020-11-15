import java.sql.Time;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final int CICLISTAS_PUNTUALES = 5;

    public static void main(String[] args) throws InterruptedException {

        int i;
        PhaserCiclistas phaser = new PhaserCiclistas();


        for (i = 0;i < CICLISTAS_PUNTUALES; i++){
            new Thread(new Ciclistas("Ciclista # " + i,phaser)).start();
        }

        // No espera a nadie
        new Thread(new CiclistaImpaciente("Impaciente ",phaser)).start();

        TimeUnit.SECONDS.sleep(12);
        //Ciclista que llega tarde
        new Thread(new CiclistaPocoTardio("Tardon ",phaser)).start();

        //Ciclista que llega muy tarde
        TimeUnit.SECONDS.sleep(60);
        new Thread(new CilistaMasTardio("El mas tardón",phaser)).start();

    }
}
