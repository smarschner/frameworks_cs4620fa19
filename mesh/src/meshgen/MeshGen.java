package meshgen;

import java.io.IOException;
import math.Vector2;
import math.Vector3;

class MeshGen {
	public static void main(String[] args) {
		if (args == null || args.length < 2) {
			System.err.println("Error: not enough input arguments.");
			printUsage();
			System.exit(1);
		}

		if (args[0].equals("-g")) { // Generate mesh
			int divisionsU = 32;
			int divisionsV = 16;
			float minorRadius = 0.25f;
			String outputFilename = null;

			for (int i=2; i<args.length; i += 2) {
				if (i+1 == args.length) {
					System.err.println("Error: expected argument after \"" + args[i] + "\" flag.");
					printUsage();
					System.exit(1);
				}
				if (args[i].equals("-n")) { // Divisions latitude
					divisionsU = Integer.parseInt(args[i+1]);
				} else if (args[i].equals("-m")) { // Divisions longitude
					divisionsV = Integer.parseInt(args[i+1]);
				} else if (args[i].equals("-r")) { // Inner radius
					minorRadius = Float.parseFloat(args[i+1]);
				} else if (args[i].equals("-o")) { // Output filename
					outputFilename = args[i+1];
				} else {
					System.err.println("Error: Unknown option \"" + args[i] + "\"");
					printUsage();
					System.exit(1);
				}
			}

			if (outputFilename == null) {
				System.err.println("Error: expected -o argument.");
				printUsage();
				System.exit(1);
			}

			OBJMesh outputMesh = null;
			if (args[1].equals("cylinder")) {
				outputMesh = cylinder(divisionsU);
			} else if (args[1].equals("sphere")) {
				outputMesh = sphere(divisionsU, divisionsV);
			} else if (args[1].equals("torus")) {
				outputMesh = torus(divisionsU, divisionsV, minorRadius);
			} else {
				System.err.println("Error: expected geometry type.");
				printUsage();
				System.exit(1);
			}

			System.out.println("Output mesh is valid: " + outputMesh.isValid(true));

			try {
				outputMesh.writeOBJ(outputFilename);
			} catch (IOException e) {
				System.err.println("Error: could not write file " + outputFilename);
				System.exit(1);
			}

		} else if (args[0].equals("-i")) { // Assign normals
			if (!args[2].equals("-o")) {
				System.err.println("Error: expected -o argument.");
				printUsage();
				System.exit(1);
			}
			OBJMesh inputMesh = null;
			try {
				inputMesh = new OBJMesh(args[1]);
			} catch (OBJMesh.OBJFileFormatException e) {
				System.err.println("Error: Malformed input OBJ file: " + args[1]);
				System.err.println(e);
				System.exit(1);
			} catch (IOException e) {
				System.err.println("Error: could not read file " + args[1]);
				System.err.println(e);
				System.exit(1);
			}
			OBJMesh outputMesh = createNormals(inputMesh);

			System.out.println("Output mesh is valid: " + outputMesh.isValid(true));

			try {
				outputMesh.writeOBJ(args[3]);
			} catch (IOException e) {
				System.err.println("Error: could not write file " + args[3]);
				System.exit(1);
			}

		} else if (args[0].equals("-v")) { // Verify an OBJ file
			if (args.length != 2) {
				System.err.println("Error: expected an input file argument.");
				printUsage();
				System.exit(1);
			}
			OBJMesh mesh = null;
			try {
				mesh = new OBJMesh(args[1]);
			} catch (OBJMesh.OBJFileFormatException e) {
				System.err.println("Error: Malformed input OBJ file: " + args[1]);
				System.err.println(e);
				System.exit(1);
			} catch (IOException e) {
				System.err.println("Error: could not read file " + args[1]);
				System.err.println(e);
				System.exit(1);
			}
			System.out.println("Input mesh is valid OBJ syntax: " + mesh.isValid(true));

		} else if (args[0].equals("-c")) { // Compare two OBJ files
			float eps = 1e-5f;
			if (args.length != 3 && args.length != 5) {
				System.err.println("Error: expected 2 input file arguments and optional epsilon.");
				printUsage();
				System.exit(1);
			}
			if (args.length == 5) {
				if (!args[1].equals("-e")) {
					System.err.println("Error: expected -e flag after -c.");
					printUsage();
					System.exit(1);
				}
				eps = Float.parseFloat(args[2]);
			}
			OBJMesh m1 = null, m2 = null;
			int m1arg = (args.length == 3) ? 1 : 3;
			int m2arg = (args.length == 3) ? 2 : 4;
			try {
				m1 = new OBJMesh(args[m1arg]);
			} catch (OBJMesh.OBJFileFormatException e) {
				System.err.println("Error: Malformed input OBJ file: " + args[m1arg]);
				System.err.println(e);
				System.exit(1);
			} catch (IOException e) {
				System.err.println("Error: could not read file " + args[m1arg]);
				System.err.println(e);
				System.exit(1);
			}
			try {
				m2 = new OBJMesh(args[m2arg]);
			} catch (OBJMesh.OBJFileFormatException e) {
				System.err.println("Error: Malformed input OBJ file: " + args[m2arg]);
				System.err.println(e);
				System.exit(1);
			} catch (IOException e) {
				System.err.println("Error: could not read file " + args[m2arg]);
				System.err.println(e);
				System.exit(1);
			}
			System.out.println("Meshes are equivalent: " + OBJMesh.compare(m1, m2, true, eps));

		} else {
			System.err.println("Error: Unknown option \"" + args[0] + "\".");
			printUsage();
			System.exit(1);
		}
	}

	public static void printUsage() {
		System.out.println("Usage:");
		System.out.println("(1) MeshGen -g <cylinder|sphere|torus> [-n <divisionsLatitude>] [-m <divisionsLongitude>] [-r <innerRadius>] -o <output.obj>");
		System.out.println("(2) MeshGen -i <input.obj> -o <output.obj>");
		System.out.println("(3) MeshGen -v <input.obj>");
		System.out.println("(4) MeshGen -c [-e <epsilon>] <m1.obj> <m2.obj>");
		System.out.println();
		System.out.println("(1) creates an OBJ mesh of a cylinder, sphere, or torus.");
		System.out.println("Cylinder ignores -n and -r flags, sphere ignores the -r flag.");
		System.out.println("(2) takes in an OBJ mesh, strips it of normals (if any), and assigns new normals based on the normals of its faces.");
		System.out.println("(3) verifies that an input OBJ mesh file conforms to the OBJ standard.");
		System.out.println("(4) compares two input OBJ files and checks if they are equivalent up to an optional epsilon parameter (by default, epsilon=1e-5).");
	}

	public static OBJMesh cylinder(int divisions) {
		OBJMesh outputMesh = new OBJMesh();
		
		// Task1: Generate Cylinder (30pt)
		// TODO:
		// Calculate Vertices (positions, uvs, and normals )
		// Calculate indices in faces (use OBJFace class)

		return outputMesh;
	}

	public static OBJMesh sphere(int divisionsU, int divisionsV) {
		OBJMesh outputMesh = new OBJMesh();

		// Task1: Generate Sphere (30pt)
		// TODO:
		// Calculate Vertices (positions, uvs, and normals )
		// Calculate indices in faces (use OBJFace class)
		
		return outputMesh;
	}
	
	public static OBJMesh createNormals(OBJMesh inputMesh) {
		OBJMesh outputMesh = new OBJMesh();
		
		// Task2: Compute Normals (40pt)
		// TODO:
		// Copy position data
		// Copy UV data
		// Each vertex gets a unique normal
		// Initialize output faces
		// Calculate face normals, distribute to adjacent vertices
		// Normalize new normals

		return outputMesh;
	}
	
	//
	// The following are extra credits, it is not required, do as you are interested in it.
	//

	public static OBJMesh torus(int divisionsU, int divisionsV, float minorRadius) {
		OBJMesh outputMesh = new OBJMesh();

		// Extra Credit: Generate Turos (10pt)
		// TODO:
		// Calculate vertices: positions, uvs and normals 
		// Calculate indices on faces (use OBJFace class)

		return outputMesh;
	}
	
	public static OBJMesh geodesicSphere(int divisionU, int divisionV) {
		OBJMesh outputMesh = new OBJMesh();

		// Extra Credit: Geodesic Sphere (10pt)
		// TODO:
		// Calculate vertices: positions, uvs and normals 
		// Calculate indices on faces (use OBJFace class)

		return outputMesh;
	}
	
//	Extension
	
//	public static OBJMesh triangulatePolygons(OBJMesh inputMesh) {
//		OBJMesh outputMesh = new OBJMesh();
//		
//		// Extra Credits: Triangluate the faces that has more than three edges. (10pt)
//		// TODO:
//		// copy
//		// check
//		// divide into triangles
//		// add to output mesh
//		
//		
//		return outputMesh;
//	}


}
