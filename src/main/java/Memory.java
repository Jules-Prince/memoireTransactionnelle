public class Memory {
    private Value[] values;
    private int clock;

    // Constructeur pour allouer une mémoire de taille donnée
    public Memory(int size) {
        values = new Value[size];
        for (int i = 0; i < size; i++) {
            values[i] = new Value(0, 0);
        }
        clock = 0;
    }

    // Accesseur pour l'horloge (clock)
    public int getClock() {
        return clock;
    }

    // Accesseur pour le tableau de Value
    public Value[] getValues() {
        return values;
    }

    public void incrementClock() {
        synchronized (this) {
            clock++;
        }
    }

    public static Memory memory = new Memory(1024);

}