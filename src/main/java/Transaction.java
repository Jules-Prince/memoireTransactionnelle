class Transaction {
    private ValueSet writeSet;
    private ValueSet readSet;
    private int clock;

    public static int commitSuccess = 0;
    public static int commitAbort = 0;


    private Transaction() {
        int mem_size = Memory.memory.getValues().length;
        writeSet = new ValueSet(mem_size);
        readSet = new ValueSet(mem_size);
        clock = Memory.memory.getClock();
    }

    public static ThreadLocal<Transaction> Transaction = new ThreadLocal<Transaction>() {
        protected synchronized Transaction initialValue() {
            return new Transaction();
        }
    };

    
    public void abort() throws TransactionAbort {
        commitAbort++;
        throw new TransactionAbort("Transaction aborted.");
    }

    public void begin() {
        clock = Memory.memory.getClock();
    }

    public int read(int idx) throws TransactionAbort {
        Value valueInWriteSet = writeSet.get(idx);
        if (valueInWriteSet != null) {
            return valueInWriteSet.getValue();
        }
        
        Value value = Memory.memory.getValues()[idx];
    
        if (value.getCounter() <= clock) {
            readSet.set(idx, value);
            return value.getValue();
        } else {
            abort();
            return 0; 
        }
    }    

    public void write(int idx, int value) throws TransactionAbort {
        Value valueInWriteSet = writeSet.get(idx);
        if (valueInWriteSet != null) {
            valueInWriteSet.setValue(value);
        } else {
            Value newValue = new Value(value, clock);
            writeSet.set(idx, newValue);
        }
    }
    

    public void commit() throws TransactionAbort {
        synchronized (Memory.memory) {
            if (!readSet.checkRead(clock)) {
                readSet.clear();
                writeSet.clear();
                abort();
                return; 
            }
    
            if (!writeSet.checkWrite(clock)) {
                writeSet.clear();
                abort();
                return; 
            }
    
            Memory.memory.incrementClock();
    
            readSet.clear();
            writeSet.clear();
            commitSuccess++;
        }
    }
    
}