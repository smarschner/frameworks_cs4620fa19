package cs4620.filters;

public interface ResampleEngine {

    /**
     * This method computes the destination image <tt>dst</tt> using image content that comes from a rectangle 
     * in the source image <tt>src</tt>, maintaining the existing size of the destination image.  The source 
     * rectangle extends from <tt>left</tt> to <tt>right</tt> and <tt>bottom</tt> to <tt>top</tt> and is measured 
     * in units of input pixels.  For instance, setting the source and destination sizes both to width n
     * and height m and the rectangle to (-0.5, n - 0.5, -0.5, m - 0.5) produces an unshifted, unscaled (though 
     * possibly slightly blurred, depending on the resampling algorithm) copy of the source image.
     * 
     * @param src     The source image
     * @param dst     The destination image; its dimensions are not changed
     * @param left    The x coordinate of the left edge of the source rectangle
     * @param bottom  The y coordinate of the bottom edge of the source rectangle
     * @param right   The x coordinate of the right edge of the source rectangle
     * @param top     The y coordinate of the top edge of the source rectangle
     */
    public void resample(SimpleImage src, SimpleImage dst, double left, double bottom, double right, double top);

}
