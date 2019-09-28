package ray1.shader;

public class MicrofacetBeckmann extends Microfacet {
	
	@Override
	public void init() {
		this.brdf = new BeckmannBRDF(diffuseColor, texture, microfacetColor, nt, alpha);
	}
}
