package cs4620.filters;

/**
 * A placeholder implementation that just copies the lower-left rectangle of the source image
 * pixel-for-pixel to the destination.
 * 
 * @author srm
 */
public class PixelCopier implements ResampleEngine {

    @Override
    public void resample(SimpleImage src, SimpleImage dst, double left, double bottom, double right, double top) {
        // A trivial version of "resampling" that just copies the bottom left pixels
        byte[] srcData = src.getData();
        byte[] dstData = dst.getData();
        int nxSrc = src.getWidth(), nySrc = src.getHeight();
        int nxDst = dst.getWidth(), nyDst = dst.getHeight();
        for (int iyDst = 0; iyDst < nyDst; iyDst++)
            for (int ixDst = 0; ixDst < nxDst; ixDst++) {
            	//boundary situation
                int ixSrc = Math.min(nxSrc-1, Math.max(0, ixDst % nxSrc + (int) left)), iySrc =  Math.min(nySrc-1, Math.max(0, iyDst % nySrc + (int) bottom));
                //A trivial get/setPixel operation
                dstData[0 + 3 * (ixDst + nxDst * iyDst)] = srcData[0 + 3 * (ixSrc + nxSrc * iySrc)];
                dstData[1 + 3 * (ixDst + nxDst * iyDst)] = srcData[1 + 3 * (ixSrc + nxSrc * iySrc)];
                dstData[2 + 3 * (ixDst + nxDst * iyDst)] = srcData[2 + 3 * (ixSrc + nxSrc * iySrc)];
            }
    }

}
