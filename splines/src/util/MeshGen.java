package util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import mesh.MeshConverter;
import mesh.MeshData;
import mesh.OBJMesh_Archive;
import mesh.OBJParser;
import mesh.gen.MeshGenCube;
import mesh.gen.MeshGenCylinder;
import mesh.gen.MeshGenOptions;
import mesh.gen.MeshGenSphere;
import mesh.gen.MeshGenTorus;
import mesh.gen.MeshGenerator;
import egl.math.Vector2;
import egl.math.Vector2i;
import egl.math.Vector3;
import egl.math.Vector3i;

/**
 * Assignment 1 Entry Point
 * 
 * Command Line Mesh Generator Utility
 * @author Cristian
 *
 */
public class MeshGen {
	// Default Surface Generation Values And Limits
	public static final int DEFAULT_DIV_LAT = 8;
	public static final Vector2i LIMITS_LAT = new Vector2i(1, 100);
	public static final int DEFAULT_DIV_LONG = 12;
	public static final Vector2i LIMITS_LONG = new Vector2i(1, 100);
	public static final float DEFAULT_INNER_RADIUS = 0.25f;
	public static final Vector2 LIMITS_INNER_RADIUS = new Vector2(0.001f, 1.0f);

	// Surface Modifiers
	public static final String INPUT_ARG_LAT = "-m";
	public static final String INPUT_ARG_LONG = "-n";
	public static final String INPUT_ARG_INNER_RADIUS = "-r";
	public static final String INPUT_ARG_NORMALS_SMOOTH = "-nv";
	public static final String INPUT_ARG_NORMALS_FACETED = "-nf";
	public static final String INPUT_ARG_OPT = "-opt";
	
	// Surface Types
	public static final String INPUT_ARG_GEN_CUBE = "cube";
	public static final String INPUT_ARG_GEN_CYLINDER = "cylinder";
	public static final String INPUT_ARG_GEN_SPHERE = "sphere";
	public static final String INPUT_ARG_GEN_TORUS = "torus";
	public static final String INPUT_ARG_FILE_INPUT = "-f";
	
	// Surface Output
	public static final String INPUT_ARG_FILE_OUTPUT = "-o";
	public static final int MINIMUM_ARG_COUNT = 3;
	
	public static void main(String[] args) {
		// Check For A Minimal Amount Of Arguments
		if(args == null || args.length < MINIMUM_ARG_COUNT) {
			System.out.println("Arguments To Program Must Be At The Very Least:");
			System.out.println("meshgen <surface> " + INPUT_ARG_FILE_OUTPUT + " <path>");
			return;
		}

		// Default Surface Generation Options
		MeshGenOptions opt = new MeshGenOptions();
		opt.divisionsLatitude = DEFAULT_DIV_LAT;
		opt.divisionsLongitude = DEFAULT_DIV_LONG;
		opt.innerRadius = DEFAULT_INNER_RADIUS;

		// Command Modifiers
		boolean useSmoothNormals = true;
		boolean fullMeshOptimization = false;
		MeshGenerator gen = null;
		String fileIn = null;
		String fileOut = null;
		
		// Parse The Input Arguments
		for(int i = 0;i < args.length;i++) {
			int v;
			float r;
			switch(args[i].toLowerCase()) {
			case INPUT_ARG_LAT:
				i++;
				if(i >= args.length) break;
				v = Integer.parseInt(args[i]);
				if(v >= LIMITS_LAT.x && v <= LIMITS_LAT.y) {
					opt.divisionsLatitude = v;
				}
				break;
			case INPUT_ARG_LONG:
				i++;
				if(i >= args.length) break;
				v = Integer.parseInt(args[i]);
				if(v >= LIMITS_LONG.x && v <= LIMITS_LONG.y) {
					opt.divisionsLongitude = v;
				}
				break;
			case INPUT_ARG_INNER_RADIUS:
				i++;
				if(i >= args.length) break;
				r = Float.parseFloat(args[i]);
				if(r >= LIMITS_INNER_RADIUS.x && r <= LIMITS_INNER_RADIUS.y) {
					opt.innerRadius = r;
				}
				break;
			case INPUT_ARG_NORMALS_SMOOTH:
				useSmoothNormals = true;
				break;
			case INPUT_ARG_NORMALS_FACETED:
				useSmoothNormals = false;
				break;
			case INPUT_ARG_OPT:
				fullMeshOptimization = true;
				break;
			case INPUT_ARG_GEN_SPHERE:
				gen = new MeshGenSphere();
				break;
			case INPUT_ARG_GEN_CUBE:
				gen = new MeshGenCube();
				break;
			case INPUT_ARG_GEN_TORUS:
				gen = new MeshGenTorus();
				break;
			case INPUT_ARG_GEN_CYLINDER:
				gen = new MeshGenCylinder();
				break;
			case INPUT_ARG_FILE_INPUT:
				i++;
				if(i >= args.length) break;
				fileIn = args[i];
				break;
			case INPUT_ARG_FILE_OUTPUT:
				i++;
				if(i >= args.length) break;
				fileOut = args[i];
				break;
			default:
				break;
			}
		}


		// Mandatory Output File
		if(fileOut == null) {
			System.out.println("Expected An Output File");
			return;
		}

		// Attempt To Create The Output File
		PrintWriter w;
		try {
			w = new PrintWriter(fileOut);
		} catch (FileNotFoundException e) {
			System.out.println("Could Not Create Output File");
			return;
		}

		if(gen != null) {
			// Generate The Surface Data
			MeshData outData = new MeshData();
			gen.generate(outData, opt);
			
			// Write Data (No Optimizations Done)
			OBJParser.write(w, OBJParser.convert(outData));
		}
		else if (fileIn != null) {
			// Load Optimized Surface Data (Only The Positional Data And Maybe With UV Seams)
			OBJMesh_Archive mesh = OBJParser.parseWithMerging(fileIn, fullMeshOptimization, true);
			
			ArrayList<Vector3> expPos = mesh.positions;
			if(!fullMeshOptimization) {
				// Cut Up Mesh On The UV Seams
				expPos = new ArrayList<Vector3>();
				for(Vector3i v : mesh.vertices) {
					expPos.add(mesh.positions.get(v.x));
					v.x = expPos.size() - 1;
				}				
			}
			
			// Set The Triangle Indices To The Vertex's Position Index
			for(Vector3i t : mesh.triangles) {
				t.x = mesh.vertices.get(t.x).x;
				t.y = mesh.vertices.get(t.y).x;
				t.z = mesh.vertices.get(t.z).x;
			}
			
			// Convert The Data
			MeshData convMesh = useSmoothNormals
				? MeshConverter.convertToVertexNormals(expPos, mesh.triangles)
				: MeshConverter.convertToFaceNormals(expPos, mesh.triangles);
			
			// Rewrite Data (No Optimizations Done)
			OBJParser.write(w, OBJParser.convert(convMesh));
		}
		else {
			// No Task Given
			System.out.println("No Data Given To Generate Or Convert");
			w.close();
			return;
		}
		
		// Close The Output Stream
		w.close();
		System.out.printf("Operation Successful %s", args[0]);
	}
}
