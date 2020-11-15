import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class CiclistaPocoTardio implements Runnable{

    private final String nombre;
    private final Phaser phaser;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public CiclistaPocoTardio(String nombre, Phaser phaser) {
        Objects.requireNonNull(nombre);
        Objects.requireNonNull(phaser);
        this.nombre = nombre;
        this.phaser = phaser;
    }



    @Override
    public void run() {

        phaser.register();
        try {
            irAGasolinera();
        } catch (InterruptedException e) {
            System.out.println("He sido interrumpiod");
            return;
        }
        try{
            irAVenta();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            phaser.awaitAdvanceInterruptibly(phaser.arrive());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            volverAGasolinera();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            phaser.awaitAdvanceInterruptibly(phaser.arrive());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            volverACasa();
        } catch (InterruptedException e) {
            e.printStackTrace();
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