import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Phaser;

public class PhaserCiclistas extends Phaser {

    public static final int LLEGADA_GASOLINERA = 0;
    public static final int LLEGADA_VENTA = 1;
    public static final int VUELTA_GASOLINERA = 2;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    protected boolean onAdvance(int phase, int registeredParties) {
        switch (phase) {
            case LLEGADA_GASOLINERA:
                System.out.printf("%s -> Los %d ciclistas han llegado a la gasolinera \n", LocalTime.now().format(dateTimeFormatter), registeredParties);
                break;
            case LLEGADA_VENTA:
                System.out.printf("%s -> Los %d ciclistas han llegado a la venta \n", LocalTime.now().format(dateTimeFormatter), registeredParties);
                break;
            case VUELTA_GASOLINERA:
                System.out.printf("%s -> Los %d ciclistas han llegado a la gasolibera de vuelta \n", LocalTime.now().format(dateTimeFormatter), registeredParties);
                return true;

        }
        return super.onAdvance(phase,registeredParties);
    }
}
