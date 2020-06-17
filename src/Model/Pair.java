package Model;

import java.util.Objects;

/**
 *
 * @param <T> - first element
 * @param <D> - second element
 *           a class which holds a pair of two things
 */
public class Pair<T,D> {
    private T first;
    private D second;
    public Pair(T first, D second){
        this.first = first;
        this.second = second;
    }
    public Pair(){

    }

    /**
     *
     * @return - getter for the first element
     */
    public T get_first(){
        return this.first;
    }

    /**
     *
     * @return - getter for the second element
     */
    public D get_second(){
        return this.second;
    }

    /**
     *
     * @param another - setter for the first element
     */
    public void set_first(T another){
        this.first = another;
    }

    /**
     *
     * @param another - setter for the second element
     */
    public void set_second(D another){
        this.second  = another;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) &&
                Objects.equals(second, pair.second);
    }


}
