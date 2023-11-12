public class Value {
    private int value;
    private int counter;

    // Constructeur
    public Value(int value, int counter) {
        this.value = value;
        this.counter = counter;
    }


    @Override
    public String toString() {
        return "Value{" +
                "value=" + value +
                ", counter=" + counter +
                '}';
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}