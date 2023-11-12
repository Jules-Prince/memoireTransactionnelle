public class TransactionAbort extends Exception {
    // Constructeur par défaut
    public TransactionAbort() {
        super("Transaction aborted.");
    }

    // Constructeur avec un message personnalisé
    public TransactionAbort(String message) {
        super(message);
    }
}