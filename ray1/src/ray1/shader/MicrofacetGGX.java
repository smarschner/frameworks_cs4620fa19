package ray1.shader;

public class MicrofacetGGX extends Microfacet {
	
	@Override
	public void init() {
		this.brdf = new GGXBRDF(diffuseColor, texture, microfacetColor, nt, alpha);
	}
}
