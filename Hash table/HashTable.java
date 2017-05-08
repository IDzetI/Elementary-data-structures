/**
 * Created by Dzet
 */
@SuppressWarnings("ALL")
public class HashTable<K,V> {

    private int size, capacity;
    private K[] keys;
    private V[] values;

    public HashTable(int capacity){
        this.capacity = capacity;
        keys = (K[]) new Object[capacity];
        values = (V[]) new Object[capacity];
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public int getCapacity() {
        return capacity;
    }

    private int hash(K key){
        return Math.abs(key.hashCode() % capacity);
    }

    public void add(K key, V value){
        int temple, i, j;
        i = temple = hash(key);
        j = 1;

        do{
            if (keys[i] == null)
            {
                keys[i] = key;
                values[i] = value;
                size++;
                return;
            }
            if (keys[i].equals(key))
            {
                values[i] = value;
                return;
            }
            i = (i + j * j++) % capacity;
        } while (i != temple);
    }

    public V get(K key){
        int i = hash(key), j = 1;

        while (keys[i] != null)
        {
            if (keys[i].equals(key))
                return values[i];
            i = (i + j * j++) % capacity;
            System.out.println("i "+ i);
        }
        return null;
    }

    public boolean contains(K key)
    {
        return get(key) !=  null;
    }

    public void remove(K key)
    {
        // if contains -> return
        if ( !contains(key) ) return;

        int i = hash(key), j = 1;

        while (!key.equals(keys[i]))
            i = (i + j * j++) % capacity;

        keys[i] = (K) (values[i] = null);

        //update the remaining keys
        for (i = (i + j * j++) % capacity;
             keys[i] != null;
             i = (i + j * j++) % capacity)
        {
            K templeKey = keys[i];
            V templeValues = values[i];
            keys[i] = (K) (values[i] = null);
            capacity--;
            add(templeKey, templeValues);
        }

        size--;
    }


}