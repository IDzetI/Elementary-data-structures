/**
 * Created by User on 06.02.2017.
 */
public class BitSet {

    private long[] data;

    /**
     * create Set
     * @param size
     */
    BitSet(int size){
        if(size % 64 > 0)
            data = new long[size/64+1];
        else
            data = new long[size/64];
        for (int i = 0; i< data.length; i++)
            data[i] = 0;
    }

    /**
     * add in set
     * @param element
     */
    public void add(int e){
        int degree, shift;
        degree = e / 64;
        shift = e % 64;

        if(degree < data.length) data[degree] |= 1 << shift;
    }

    /**
     *
     * @param element
     * @return thue if set have element
     */
    public boolean isIncluded(int e){
        int degree, shift;
        degree = e / 64;
        shift = e % 64;
        if(data.length>degree){
            return (data[degree] & (1<<shift)) > 0;
        }
        return false;
    }

}