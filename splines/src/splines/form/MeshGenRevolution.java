package splines.form;

import common.BasicType;
import util.MeshDataConvert;
import mesh.MeshData;
import mesh.OBJMesh;
import mesh.gen.MeshGenOptions;
import mesh.gen.MeshGenerator;
import splines.SplineCurve;

public class MeshGenRevolution extends MeshGenerator {

	SplineCurve toRevolve;
	float scale;
	float sliceTolerance;
	
	@Override
	public void generate(MeshData outData, MeshGenOptions opt) {
		OBJMesh mesh = new OBJMesh();
		SplineCurve.build3DRevolution(toRevolve, mesh, scale, sliceTolerance);
		// Convert from old mesh framework to new mesh framework
		MeshDataConvert.convertToMeshData(outData, mesh);
	}

	@Override
	public BasicType getType() {
		return null;
	}

	public void setSplineToRevolve(SplineCurve spline) {
		this.toRevolve= spline;
	}
	
	public void setScale(float scale) {
		this.scale= scale;
	}
	
	public void setSliceTolerance(float tolerance) {
		this.sliceTolerance = tolerance;
	}
}
