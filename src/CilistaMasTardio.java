import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class CilistaMasTardio implements Runnable {


    private final String nombre;
    private final Phaser phaser;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public CilistaMasTardio(String nombre, Phaser phaser) {
        Objects.requireNonNull(nombre);
        Objects.requireNonNull(phaser);
        this.nombre = nombre;
        this.phaser = phaser;
    }



    @Override
    public void run() {

        if(!phaser.isTerminated()){
            int joinPhase = phaser.register();
            System.out.printf("%s -> %s Se ha unido a sus amigos en la fase #%d \n",LocalTime.now().format(dateTimeFormatter),nombre,joinPhase);
            try {
                irAGasolinera();
            } catch (InterruptedException e) {
                System.out.println("He sido interrumpido");
                return;
            }

            if (joinPhase<= PhaserCiclistas.LLEGADA_GASOLINERA){
                try {
                    phaser.awaitAdvanceInterruptibly(phaser.arrive());
                } catch (InterruptedException e) {
                    System.out.println("He sido interrumpido");
                    return;
                }
            }
            try {
                irAVenta();
            } catch (InterruptedException e) {
                System.out.println("He sido interrumpido");
                return;
            }

            if (joinPhase <= PhaserCiclistas.LLEGADA_VENTA){
                try {
                    phaser.awaitAdvanceInterruptibly(phaser.arrive());
                } catch (InterruptedException e) {
                    System.out.println("He sido interrumpido");
                    return;
                }
            }
            try {
                volverAGasolinera();
            } catch (InterruptedException e) {
                System.out.println("He sido interrumpido");
                return;
            }
            if (joinPhase <= PhaserCiclistas.VUELTA_GASOLINERA){
                try {
                    phaser.awaitAdvanceInterruptibly(phaser.arrive());
                } catch (InterruptedException e) {
                    System.out.println("He sido interrumpido");
                    return;
                }
            }
            try {
                volverACasa();
            } catch (InterruptedException e) {
                System.out.println("He sido interrumpido");
                return;
            }
        }
        else {
            System.out.printf("%s -> %s se ha despertado demasiado tarde...",LocalTime.now().format(dateTimeFormatter),nombre);
        }


    }

    private void irAGasolinera() throws InterruptedException {

        System.out.printf("%s -> %s ha salido de su casa \n", LocalTime.now().format(dateTimeFormatter),nombre);
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(3)+1);
        System.out.printf("%s -> %s ha llegado a la gasolinera \n", LocalTime.now().format(dateTimeFormatter),nombre);

    }

    private void irAVenta() throws InterruptedException {
        System.out.printf("%s -> %s ha salido de la gasolinera \n", LocalTime.now().format(dateTimeFormatter),nombre);
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(5,11));
        System.out.printf("%s -> %s ha llegado a la venta \n", LocalTime.now().format(dateTimeFormatter),nombre);
    }

    private void volverAGasolinera() throws InterruptedException {
        System.out.printf("%s -> %s ha salido de la venta \n", LocalTime.now().format(dateTimeFormatter),nombre);
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(5,11));
        System.out.printf("%s -> %s ha llegado a la gasolinera \n", LocalTime.now().format(dateTimeFormatter),nombre);
    }


    private void volverACasa() throws InterruptedException {

        System.out.printf("%s -> %s ha salido de su gasolinera \n", LocalTime.now().format(dateTimeFormatter),nombre);
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(3)+1);
        System.out.printf("%s -> %s YA EN CASA \n", LocalTime.now().format(dateTimeFormatter),nombre);

    }

}
