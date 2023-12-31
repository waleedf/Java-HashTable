
import lab3.QuadraticProbingHash.Record;

public class LinearProbingHash implements HashInterface {
    Record[] table;
    int collisions;

    private static class Record {
        Integer key;
        Integer value;

        public Record(final Integer key1, final Integer value1) {
            key = key1;
            value = value1;
        }
    }

    public LinearProbingHash(int initialSize) {
        table = new Record[initialSize];

        collisions = 0;
    }

    @Override
    public Integer get(final Integer key) {
        final int index = lookUp(key);

        if (index > table.length) 
            return null;
        

        Record p = table[index];

        return p.value;
    }

    @Override
    public void put(final Integer key, final Integer value) throws RuntimeException {
        final int index = lookUp(key);

        if (index > table.length) {
            throw new RuntimeException("Table is full");
        }

        Record p = table[index];

        if (p == null) {
            table[index] = new Record(key, value);
        } else {
            p.value = value;
        }
    }

    @Override
    public final int getCollisions() {
        return collisions;
    }

    private static int hash(final Integer key) {
        // scramble the key via bitwise operations
    	// for simplicity, it is assumed that Key is some kind of number
        return (key >> 8) | ((key&0xff)<<16);
    }

    private int hashIndex(final Integer key) {
        final int index = hash(key);

        return index % table.length;
    }
    public String printTable(String description){
    	String output = "\n\n*** Begin Linear Probing " + description + "**** \nPrint table_.size() = " + table.length;    	
    	int i = 0;
    	for(Record r: this.table)
    	{		if(r != null)
    				output += ("\nIndex=" + i++ + " key=" + r.key + " value=" + r.value);
    			if(r == null)
    				output += ("\nIndex=" + i++ + " key=" + "NULL" + " value=" + "NULL");
    	}
    	return output += "\nLinear Probing Collisions: " + this.getCollisions();
    }
    
    private int lookUp(Integer key) {
        final int startIndex = hashIndex(key);
        int index = startIndex;

        while (true) {
            final Record p = table[index];

            if (p == null || p.key.equals(key)) {
                return index;
            }

            collisions ++;
            index ++;
            index %= table.length;

            if (index == startIndex) {
                return table.length + 1;
            }
        }
    }
}