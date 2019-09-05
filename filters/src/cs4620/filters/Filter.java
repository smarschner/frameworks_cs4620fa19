package cs4620.filters;

/**
 * A filter to be used for resampling.
 * 
 * @author srm
 */
public interface Filter {

    /**
     * @return The radius of support of the filter
     */
    float radius();

    /**
     * @param x  The offset between source and destination samples
     * @return The value of the filter for offset x
     */
    float evaluate(float x);
}
