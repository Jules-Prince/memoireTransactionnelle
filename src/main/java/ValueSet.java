public class ValueSet {
    public Value map[];
    public int elements[];
    public int n_elements;

    public ValueSet(int max) {
        this.map = new Value[max];
        this.elements = new int[max];
    }

    public Value get(int idx) {
        return map[idx];
    }

    public boolean set(int idx, Value val) {
        if (get(idx) != null) {
            return true;
        }

        elements[n_elements++] = idx;
        map[idx] = val;

        return false;
    }

    public void clear() {
        for (int i = 0; i < n_elements; i++) {
            map[elements[i]] = null;
        }
        n_elements = 0;
    }

    public boolean checkRead(int clock) {
        int validCount = 0;

        for (int i = 0; i < n_elements; i++) {
            int idx = elements[i];
            Value value = map[idx];

            if (value != null && value.getCounter() >= clock) {
            } else {
                elements[validCount] = idx;
                validCount++;
            }
        }

        n_elements = validCount;

        return validCount == n_elements;
    }
      
    public boolean checkWrite(int clock) {
        boolean isValid = true;

        for (int i = 0; i < n_elements; i++) {
            int idx = elements[i];
            Value value = map[idx];

            if (value != null && value.getCounter() >= clock) {
                isValid = false; 
            }
        }

        if (isValid) {
            for (int i = 0; i < n_elements; i++) {
                int idx = elements[i];
                Value value = map[idx];
                Memory.memory.getValues()[idx] = value;
                value.setCounter(Memory.memory.getClock());
            }
        }

        n_elements = 0;

        return isValid;
    }

}