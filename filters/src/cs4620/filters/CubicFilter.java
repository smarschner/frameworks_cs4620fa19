package cs4620.filters;

/**
 * A piecewise cubic filter in the style of Mitchell and Netravali.  This family of filters
 * is parameterized by the real numbers B and C, and it includes the Catmull-Rom spline (B=0,
 * C=0.5) and the B-spline (B=1, C=0) as special cases, as well as M-N's recommended filter,
 * with B=C=1/3.
 * 
 * <p>
 * Don P. Mitchell and Arun N. Netravali. 1988. Reconstruction filters in computer-graphics. 
 * In Proceedings of SIGGRAPH '88.  https://doi.org/10.1145/54852.378514
 * 
 * @author srm
 */
public class CubicFilter implements Filter {

    float B, C;

    CubicFilter(float B, float C) {
        this.B = B;
        this.C = C;
    }

    @Override
    public float radius() {
        return 2.0f;
    }

    @Override
    public float evaluate(float x) {
        x = Math.abs(x);

        float x2 = x*x;
        float x3 = x2*x;

        if (x < 1) {
            return 1.0f/6.0f * ((12-9*B-6*C)*x3
                    + (-18+12*B+6*C) * x2 + (6-2*B));
        } else if (x < 2) {
            return 1.0f/6.0f * ((-B-6*C)*x3 + (6*B+30*C) * x2
                    + (-12*B-48*C) * x + (8*B + 24*C));
        } else {
            return 0.0f;
        }
    }

}
