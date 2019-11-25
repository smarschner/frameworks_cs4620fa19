package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import mesh.MeshData;
import mesh.OBJParser;

public class Msh2Obj {

	/**
	 * Reads a .msh file and passes the data to be converted into .obj
	 *
	 * @param args[0] inputFile
	 * @param args[1] outputFile
	 * 
	 */
	public static void main(String[] argv) {
	//public static final void convertmsh2obj(String inputFile, String outputFile) {

		// Check arguments
		if(argv == null || argv.length < 2) {
			System.out.println("Arguments To Program Must Be:");
			System.out.println("Msh2Obj <inputFile>.msh <outputFile>.obj");
			return;
		}
		
		// Mandatory Output File
		if(argv[1] == null) {
			System.out.println("Expected An Output File");
			System.out.println("Msh2Obj <inputFile>.msh <outputFile>.obj");
			return;
		}
		
		final String inputFile = argv[0];
		final String outputFile = argv[1];
		
		MeshData outData = new MeshData();
		
		int nPoints;
		int nPolys;
		float[] vertices;
		int[] triangles;
		float[] normals;
		float[] texcoords;

		try {
			// Create a buffered reader for the mesh file
			BufferedReader fr = new BufferedReader(new FileReader(inputFile));

			// Read the size of the file
			nPoints = Integer.parseInt(fr.readLine());
			nPolys = Integer.parseInt(fr.readLine());

			// Create arrays for mesh data
			vertices = new float[nPoints*3];
			triangles = new int[nPolys*3];
			normals = null;
			texcoords = null;

			boolean vertsRead = false;
			boolean trisRead = false;

			String line = fr.readLine();
			while(line != null) {
				if(line.equals("vertices")) {
					for (int i = 0; i < vertices.length; ++i) {
						vertices[i] = Float.parseFloat(fr.readLine());
					}
					vertsRead = true;
				}
				else if( line.equals("texcoords") ) {
					texcoords = new float[nPoints * 2];
					for (int i = 0; i < texcoords.length; ++i) {
						texcoords[i] = Float.parseFloat(fr.readLine());
					}
				}
				else if( line.equals("triangles")) {
					for (int i = 0; i < triangles.length; ++i) {
						triangles[i] = Integer.parseInt(fr.readLine());
					}
					trisRead = true;
				}
				else if( line.equals("normals")) {
					normals = new float[nPoints * 3];
					for (int i = 0; i < normals.length; ++i) {
						normals[i] = Float.parseFloat(fr.readLine());
					}
				}
				line = fr.readLine();
			}

			fr.close();

			if( !vertsRead )
				throw new Exception("Broken file - vertices expected");

			if( !trisRead )
				throw new Exception("Broken file - triangles expected.");					
		}
		catch (Exception e) {
			throw new Error("Error reading mesh file: " + inputFile);
		}

		// Attempt To Create The Output File
		PrintWriter pw;
		try {
			pw = new PrintWriter(outputFile);
		} catch (FileNotFoundException e) {
			System.out.println("Could Not Create Output File:" + outputFile);
			return;
		}
		
		outData.vertexCount = nPoints;
		outData.indexCount = nPolys*3;
		
		outData.positions = FloatBuffer.wrap(vertices);
		outData.indices = IntBuffer.wrap(triangles);
		if (texcoords != null)
			outData.uvs =  FloatBuffer.wrap(texcoords);
		if (normals != null)
			outData.normals =  FloatBuffer.wrap(normals);
		
		// Write Data (No Optimizations Done)
		OBJParser.write(pw, OBJParser.convert(outData));
		
		
		pw.flush();
		pw.close();
		
		System.out.println("Read mesh of " + nPoints + " verts, " + nPolys+ " triangles");
		System.out.println("The mesh file " + inputFile + " was converted to " + outputFile);

	}
}
