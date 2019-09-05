package cs4620.filters;

/**
 * A linear interpolation filter.  Can be used to make FilterResampler mimic LinearResampler
 * (for magnification) as a debugging test.
 * 
 * @author srm
 */
public class LinearFilter implements Filter {

    @Override
    public float radius() {
        return 1.0f;
    }

    @Override
    public float evaluate(float x) {
        return Math.max(0, 1 - Math.abs(x));
    }
}
