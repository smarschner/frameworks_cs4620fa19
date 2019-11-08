package egl.math;

/**
 * An RGBA color, stored internally as 4 bytes.
 * 
 * @author Cristian, eschweic
 *
 */
public class Color {
    public static final Color Transparent = new Color(0, 0, 0, 0);
    public static final Color AliceBlue = new Color(240, 248, 255, 255);
    public static final Color AntiqueWhite = new Color(250, 235, 215, 255);
    public static final Color Aqua = new Color(0, 255, 255, 255);
    public static final Color Aquamarine = new Color(127, 255, 212, 255);
    public static final Color Azure = new Color(240, 255, 255, 255);
    public static final Color Beige = new Color(245, 245, 220, 255);
    public static final Color Bisque = new Color(255, 228, 196, 255);
    public static final Color Black = new Color(0, 0, 0, 255);
    public static final Color BlanchedAlmond = new Color(255, 235, 205, 255);
    public static final Color Blue = new Color(0, 0, 255, 255);
    public static final Color BlueViolet = new Color(138, 43, 226, 255);
    public static final Color Brown = new Color(165, 42, 42, 255);
    public static final Color BurlyWood = new Color(222, 184, 135, 255);
    public static final Color CadetBlue = new Color(95, 158, 160, 255);
    public static final Color Chartreuse = new Color(127, 255, 0, 255);
    public static final Color Chocolate = new Color(210, 105, 30, 255);
    public static final Color Coral = new Color(255, 127, 80, 255);
    public static final Color CornflowerBlue = new Color(100, 149, 237, 255);
    public static final Color Cornsilk = new Color(255, 248, 220, 255);
    public static final Color Crimson = new Color(220, 20, 60, 255);
    public static final Color Cyan = new Color(0, 255, 255, 255);
    public static final Color DarkBlue = new Color(0, 0, 139, 255);
    public static final Color DarkCyan = new Color(0, 139, 139, 255);
    public static final Color DarkGoldenrod = new Color(184, 134, 11, 255);
    public static final Color DarkGray = new Color(169, 169, 169, 255);
    public static final Color DarkGreen = new Color(0, 100, 0, 255);
    public static final Color DarkKhaki = new Color(189, 183, 107, 255);
    public static final Color DarkMagenta = new Color(139, 0, 139, 255);
    public static final Color DarkOliveGreen = new Color(85, 107, 47, 255);
    public static final Color DarkOrange = new Color(255, 140, 0, 255);
    public static final Color DarkOrchid = new Color(153, 50, 204, 255);
    public static final Color DarkRed = new Color(139, 0, 0, 255);
    public static final Color DarkSalmon = new Color(233, 150, 122, 255);
    public static final Color DarkSeaGreen = new Color(143, 188, 139, 255);
    public static final Color DarkSlateBlue = new Color(72, 61, 139, 255);
    public static final Color DarkSlateGray = new Color(47, 79, 79, 255);
    public static final Color DarkTurquoise = new Color(0, 206, 209, 255);
    public static final Color DarkViolet = new Color(148, 0, 211, 255);
    public static final Color DeepPink = new Color(255, 20, 147, 255);
    public static final Color DeepSkyBlue = new Color(0, 191, 255, 255);
    public static final Color DimGray = new Color(105, 105, 105, 255);
    public static final Color DodgerBlue = new Color(30, 144, 255, 255);
    public static final Color Firebrick = new Color(178, 34, 34, 255);
    public static final Color FloralWhite = new Color(255, 250, 240, 255);
    public static final Color ForestGreen = new Color(34, 139, 34, 255);
    public static final Color Fuchsia = new Color(255, 0, 255, 255);
    public static final Color Gainsboro = new Color(220, 220, 220, 255);
    public static final Color GhostWhite = new Color(248, 248, 255, 255);
    public static final Color Gold = new Color(255, 215, 0, 255);
    public static final Color Goldenrod = new Color(218, 165, 32, 255);
    public static final Color Gray = new Color(128, 128, 128, 255);
    public static final Color Green = new Color(0, 128, 0, 255);
    public static final Color GreenYellow = new Color(173, 255, 47, 255);
    public static final Color Honeydew = new Color(240, 255, 240, 255);
    public static final Color HotPink = new Color(255, 105, 180, 255);
    public static final Color IndianRed = new Color(205, 92, 92, 255);
    public static final Color Indigo = new Color(75, 0, 130, 255);
    public static final Color Ivory = new Color(255, 255, 240, 255);
    public static final Color Khaki = new Color(240, 230, 140, 255);
    public static final Color Lavender = new Color(230, 230, 250, 255);
    public static final Color LavenderBlush = new Color(255, 240, 245, 255);
    public static final Color LawnGreen = new Color(124, 252, 0, 255);
    public static final Color LemonChiffon = new Color(255, 250, 205, 255);
    public static final Color LightBlue = new Color(173, 216, 230, 255);
    public static final Color LightCoral = new Color(240, 128, 128, 255);
    public static final Color LightCyan = new Color(224, 255, 255, 255);
    public static final Color LightGoldenrodYellow = new Color(250, 250, 210, 255);
    public static final Color LightGreen = new Color(144, 238, 144, 255);
    public static final Color LightGray = new Color(211, 211, 211, 255);
    public static final Color LightPink = new Color(255, 182, 193, 255);
    public static final Color LightSalmon = new Color(255, 160, 122, 255);
    public static final Color LightSeaGreen = new Color(32, 178, 170, 255);
    public static final Color LightSkyBlue = new Color(135, 206, 250, 255);
    public static final Color LightSlateGray = new Color(119, 136, 153, 255);
    public static final Color LightSteelBlue = new Color(176, 196, 222, 255);
    public static final Color LightYellow = new Color(255, 255, 224, 255);
    public static final Color Lime = new Color(0, 255, 0, 255);
    public static final Color LimeGreen = new Color(50, 205, 50, 255);
    public static final Color Linen = new Color(250, 240, 230, 255);
    public static final Color Magenta = new Color(255, 0, 255, 255);
    public static final Color Maroon = new Color(128, 0, 0, 255);
    public static final Color MediumAquamarine = new Color(102, 205, 170, 255);
    public static final Color MediumBlue = new Color(0, 0, 205, 255);
    public static final Color MediumOrchid = new Color(186, 85, 211, 255);
    public static final Color MediumPurple = new Color(147, 112, 219, 255);
    public static final Color MediumSeaGreen = new Color(60, 179, 113, 255);
    public static final Color MediumSlateBlue = new Color(123, 104, 238, 255);
    public static final Color MediumSpringGreen = new Color(0, 250, 154, 255);
    public static final Color MediumTurquoise = new Color(72, 209, 204, 255);
    public static final Color MediumVioletRed = new Color(199, 21, 133, 255);
    public static final Color MidnightBlue = new Color(25, 25, 112, 255);
    public static final Color MintCream = new Color(245, 255, 250, 255);
    public static final Color MistyRose = new Color(255, 228, 225, 255);
    public static final Color Moccasin = new Color(255, 228, 181, 255);
    public static final Color NavajoWhite = new Color(255, 222, 173, 255);
    public static final Color Navy = new Color(0, 0, 128, 255);
    public static final Color OldLace = new Color(253, 245, 230, 255);
    public static final Color Olive = new Color(128, 128, 0, 255);
    public static final Color OliveDrab = new Color(107, 142, 35, 255);
    public static final Color Orange = new Color(255, 165, 0, 255);
    public static final Color OrangeRed = new Color(255, 69, 0, 255);
    public static final Color Orchid = new Color(218, 112, 214, 255);
    public static final Color PaleGoldenrod = new Color(238, 232, 170, 255);
    public static final Color PaleGreen = new Color(152, 251, 152, 255);
    public static final Color PaleTurquoise = new Color(175, 238, 238, 255);
    public static final Color PaleVioletRed = new Color(219, 112, 147, 255);
    public static final Color PapayaWhip = new Color(255, 239, 213, 255);
    public static final Color PeachPuff = new Color(255, 218, 185, 255);
    public static final Color Peru = new Color(205, 133, 63, 255);
    public static final Color Pink = new Color(255, 192, 203, 255);
    public static final Color Plum = new Color(221, 160, 221, 255);
    public static final Color PowderBlue = new Color(176, 224, 230, 255);
    public static final Color Purple = new Color(128, 0, 128, 255);
    public static final Color Red = new Color(255, 0, 0, 255);
    public static final Color RosyBrown = new Color(188, 143, 143, 255);
    public static final Color RoyalBlue = new Color(65, 105, 225, 255);
    public static final Color SaddleBrown = new Color(139, 69, 19, 255);
    public static final Color Salmon = new Color(250, 128, 114, 255);
    public static final Color SandyBrown = new Color(244, 164, 96, 255);
    public static final Color SeaGreen = new Color(46, 139, 87, 255);
    public static final Color SeaShell = new Color(255, 245, 238, 255);
    public static final Color Sienna = new Color(160, 82, 45, 255);
    public static final Color Silver = new Color(192, 192, 192, 255);
    public static final Color SkyBlue = new Color(135, 206, 235, 255);
    public static final Color SlateBlue = new Color(106, 90, 205, 255);
    public static final Color SlateGray = new Color(112, 128, 144, 255);
    public static final Color Snow = new Color(255, 250, 250, 255);
    public static final Color SpringGreen = new Color(0, 255, 127, 255);
    public static final Color SteelBlue = new Color(70, 130, 180, 255);
    public static final Color Tan = new Color(210, 180, 140, 255);
    public static final Color Teal = new Color(0, 128, 128, 255);
    public static final Color Thistle = new Color(216, 191, 216, 255);
    public static final Color Tomato = new Color(255, 99, 71, 255);
    public static final Color Turquoise = new Color(64, 224, 208, 255);
    public static final Color Violet = new Color(238, 130, 238, 255);
    public static final Color Wheat = new Color(245, 222, 179, 255);
    public static final Color White = new Color(255, 255, 255, 255);
    public static final Color WhiteSmoke = new Color(245, 245, 245, 255);
    public static final Color Yellow = new Color(255, 255, 0, 255);
    public static final Color YellowGreen = new Color(154, 205, 50, 255);
	
    /** The red component. */
	public byte R;
	
	/** The green component. */
    public byte G;
    
    /** The blue component. */
    public byte B;
    
    /** The alpha component. */
    public byte A;

    /*
     * CONSTRUCTORS
     */
    
    /**
     * Construct a color, specifying its components as bytes.
     * @param r The red component.
     * @param g The green component.
     * @param b The blue component.
     * @param a The alpha component.
     */
    public Color(byte r, byte g, byte b, byte a) {
        R = r;
        G = g;
        B = b;
        A = a;
    }
    
    /**
     * Construct a color, specifying its components as bytes. The alpha value is assumed
     * to be 255.
     * @param r The red component.
     * @param g The green component.
     * @param b The blue component.
     */
    public Color(byte r, byte g, byte b) {
    	this(r, g, b, (byte)255);
    }
    
    /**
     * Construct a color, specifying its components as ints.
     * @param r The red component.
     * @param g The green component.
     * @param b The blue component.
     * @param a The alpha component.
     */
    public Color(int r, int g, int b, int a) {
        R = (byte)r;
        G = (byte)g;
        B = (byte)b;
        A = (byte)a;
    }
    
    /**
     * Construct a color, specifying its components as ints. The alpha value is assumed
     * to be 255.
     * @param r The red component.
     * @param g The green component.
     * @param b The blue component.
     */
    public Color(int r, int g, int b) {
    	this(r, g, b, 255);
    }
    
    /**
     * Copy constructor.
     * @param c The color to be copied.
     */
    public Color(Color c) {
    	this(c.R, c.G, c.B, c.A);
    }
    
    /**
     * Create a black color: (0, 0, 0, 255).
     */
    public Color() {
    	this(0, 0, 0, 255);
	}
    
    /*
     * SETTERS
     */
    
    /**
     * Set this Color using bytes as parameters.
     * @param r The red component.
     * @param g The green component.
     * @param b The blue component.
     * @param a The alpha component.
     * @return this.
     */
	public Color set(byte r, byte g, byte b, byte a) {
    	R = r;
        G = g;
        B = b;
        A = a;
        return this;
    }
	
	/**
	 * Set this Color using bytes as parameters. The alpha component remains unchanged.
	 * @param r The red component.
	 * @param g The green component.
	 * @param b The blue component.
	 * @return this.
	 */
	public Color set(byte r, byte g, byte b) {
		return set(r, g, b, this.A);
	}
	
	/**
	 * Set this color using ints as parameters.
     * @param r The red component.
     * @param g The green component.
     * @param b The blue component.
     * @param a The alpha component.
	 * @return this
	 */
	public Color set(int r, int g, int b, int a) {
		R = (byte)r;
		G = (byte)g;
		B = (byte)b;
		A = (byte)a;
		return this;
	}
	
	/**
	 * Set this color using ints as parameters. The alpha component remains unchanged.
     * @param r The red component.
     * @param g The green component.
     * @param b The blue component.
	 * @return this
	 */
	public Color set(int r, int g, int b) {
		return set(r, g, b, (int)this.A);
	}
	
	/**
	 * Set this color to the values of a given Colorf. Values from the input Colorf
	 * are clamped between 0.0f and 1.0f before being mapped to a [0,255] range.
	 * 
	 * @param cf The reference color.
	 * @return this.
	 */
	public Color set(Colorf cf) {
		// Adding 0.5f before mapping to bytes gives a "round-to-nearest" effect,
		// rather than truncating to the floor of the value.
		R = (byte)(Math.min(1.0f, Math.max(0.0f, cf.r())) * 255f + 0.5f);
        G = (byte)(Math.min(1.0f, Math.max(0.0f, cf.g())) * 255f + 0.5f);
        B = (byte)(Math.min(1.0f, Math.max(0.0f, cf.b())) * 255f + 0.5f);
        return this;
	}

	/**
	 * Set this color to the values of a given Colord. Values from the input Colord
	 * are clamped between 0.0 and 1.0 before being mapped to a [0,255] range.
	 * 
	 * @param cf The reference color.
	 * @return this.
	 */
	public Color set(Colord cd) {
		// Adding 0.5 before mapping to bytes gives a "round-to-nearest" effect,
		// rather than truncating to the floor of the value.
		R = (byte)(Math.min(1.0, Math.max(0.0, cd.r())) * 255 + 0.5);
        G = (byte)(Math.min(1.0, Math.max(0.0, cd.g())) * 255 + 0.5);
        B = (byte)(Math.min(1.0, Math.max(0.0, cd.b())) * 255 + 0.5);
        return this;
	}
	
	/**
	 * Copy a given Color's components to this Color.
	 * @param c The Color to copy.
	 * @return this.
	 */
    public Color set(Color c) {
    	return set(c.R, c.G, c.B, c.A);
    }
    
    /**
     * Set Color Data From An Integer
     * @param argb ARGB Integer Value
     * @return This
     */
    public Color setIntARGB(int argb) {
    	return set((argb >> 16) & 0xFF, (argb >> 8) & 0xFF, (argb >> 0) & 0xFF, (argb >> 24) & 0xFF);
    }
    
    /*
     * GETTERS
     */
    
    /**
     * Get the red component in the range [0, 255].
     * @return the red component.
     */
    public int r() {
    	return R & 0xFF;
    }
    
    /**
     * Get the green component in the range [0, 255].
     * @return the green component.
     */
    public int g() {
    	return G & 0xFF;
    }
    
    /**
     * Get the blue component in the range [0, 255].
     * @return the blue component.
     */
    public int b() {
    	return B & 0xFF;
    }
    
    /**
     * Get the alpha component in the range [0, 255].
     * @return the alpha component.
     */
    public int a() {
    	return A & 0xFF;
    }
    
    /*
     * MISC FUNCTIONS
     */
    
    /**
	 * This function returns an int which represents this color. The
	 * standard RGB style bit packing is used and is compatible with
	 * java.awt.BufferedImage.TYPE_INT_RGB. (ie - the low 8 bits, 0-7
	 * are for the blue channel, the next 8 are for the green channel,
	 * and the next 8 are for the red channel).  The highest 8 bits
	 * are left untouched.
	 * @return An integer representing this color.
	 */
    public int toIntRGB() {
    	return (r() << 16) | (g() << 8) | (b() << 0);
    }
    
    /**
     * This function returns a Color from a integer of the form
     * java.awt.BufferedImage.[TYPE_INT_RGB, TYPE_INT_ARGB]. In the
     * latter case, the alpha value is ignored; the alpha is always
     * set to 255.
     * @param rgb An integer representing a color.
     * @return A Color object representing the input integer.
     */
    public static Color fromIntRGB(int rgb) {
		return new Color((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, (rgb >> 0) & 0xFF);
    }
    
    @Override
    public String toString() {
    	return "{" + r() + ", " + g() + ", " + b() + ", " + a() + "}";
    }
}
