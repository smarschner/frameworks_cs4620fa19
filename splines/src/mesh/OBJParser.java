package mesh;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import egl.math.Vector2;
import egl.math.Vector3;
import egl.math.Vector3i;

public class OBJParser {
	/**
	 * Default Tolerance In The Squared Distance Between Positions
	 */
	private static final float TOLERANCE_POSITION = 0.0001f;
	/**
	 * Default Tolerance In The Squared Distance Between Texture Coordinates
	 */
	private static final float TOLERANCE_UV = 0.001f;
	/**
	 * Default Tolerance In The Dot Product Between Normals
	 */
	private static final float TOLERANCE_NORMAL = 0.990f;

	/**
	 * Reads In An OBJ Mesh
	 * @param file OBJ File
	 * @return A Mesh
	 */
	public static OBJMesh_Archive parse(String file) {
		return parse(file, false, false);
	}
	/**
	 * Reads In An OBJ Mesh
	 * @param file OBJ File
	 * @return A Mesh
	 */
	public static OBJMesh_Archive parse(String file, boolean discardTexCoords, boolean discardNormals) {
		return parse(file, discardTexCoords, discardNormals, -1, -1, Float.MAX_VALUE);
	}
	/**
	 * Reads In An OBJ Mesh
	 * @param file OBJ File
	 * @return A Mesh
	 */
	public static OBJMesh_Archive parseWithMerging(String file) {
		return parseWithMerging(file, false, false);
	}	
	/**
	 * Reads In An OBJ Mesh
	 * @param file OBJ File
	 * @return A Mesh
	 */
	public static OBJMesh_Archive parseWithMerging(String file, boolean discardTexCoords, boolean discardNormals) {
		return parse(file, discardTexCoords, discardNormals, TOLERANCE_POSITION, TOLERANCE_UV, TOLERANCE_NORMAL);
	}	
	/**
	 * Reads In An OBJ Mesh With Possibility To Discard Certain Information
	 * @param file OBJ File
	 * @param discardTexCoords True If Mesh Should Not Contain Texture Coordinates
	 * @param discardNormals True If Mesh Should Not Contain Normals
	 * @return
	 */
	public static OBJMesh_Archive parse(String file, boolean discardTexCoords, boolean discardNormals, float tPosSq, float tUVSq, float tNormDot) {
		try {
			BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

			// For Minifying The Mesh
			ArrayList<Integer> posInds = new ArrayList<>(), uvInds = new ArrayList<>(), normInds = new ArrayList<>();
			PosComparer cmpPos = new PosComparer(tPosSq);
			UVComparer cmpUV = new UVComparer(tUVSq);
			NormComparer cmpNorm = new NormComparer(tNormDot);
			
			OBJMesh_Archive mesh = new OBJMesh_Archive();
			HashMap<Vector3i, Integer> vertMap = new HashMap<>();
			
			// Read File Line By Line
			String line;
			while ((line = r.readLine()) != null) {
				Vector3 v3;
				Vector3i tri, vert;
				Vector2 v2;

				String[] splits = line.split("\\s+");
				if(splits[0].equals("v")) {
					// Add Position Component
					if(splits.length != 4) continue;
					v3 = new Vector3();
					v3.x = Float.parseFloat(splits[1]);
					v3.y = Float.parseFloat(splits[2]);
					v3.z = Float.parseFloat(splits[3]);
					posInds.add(indexOfUnique(mesh.positions, v3, cmpPos));
				}
				else if(splits[0].equals("vn")) {
					if(discardNormals) continue;
					
					// Add Normal Component
					if(splits.length != 4) continue;
					v3 = new Vector3();
					v3.x = Float.parseFloat(splits[1]);
					v3.y = Float.parseFloat(splits[2]);
					v3.z = Float.parseFloat(splits[3]);
					normInds.add(indexOfUnique(mesh.normals, v3, cmpNorm));
				}
				else if(splits[0].equals("vt")) {
					if(discardTexCoords) continue;
					
					// Add Texture Coordinate Component
					if(splits.length != 3) continue;
					v2 = new Vector2();
					v2.x = Float.parseFloat(splits[1]);
					v2.y = Float.parseFloat(splits[2]);
					uvInds.add(indexOfUnique(mesh.uvs, v2, cmpUV));
				}
				else if(splits[0].equals("f")) {
					// Add A Triangle
					if(splits.length != 4) continue;
					tri = new Vector3i();
					
					// Set The Triangle's 3 Vertex Indices
					for(int i = 0;i < 3;i++) {
						// Create The Vertex
						String[] vInds = splits[i + 1].split("/");
						vert = new Vector3i(0, 0, 0);
						switch (vInds.length) {
						case 1:
							vert.x = Integer.parseInt(vInds[0]);
							break;
						case 2:
							vert.x = Integer.parseInt(vInds[0]);
							if(!discardTexCoords) vert.y = Integer.parseInt(vInds[1]);
							break;
						case 3:
							vert.x = Integer.parseInt(vInds[0]);
							if(!discardTexCoords && !vInds[1].isEmpty()) vert.y = Integer.parseInt(vInds[1]);
							if(!discardNormals) vert.z = Integer.parseInt(vInds[2]);
							break;
						default:
							continue;
						}
						
						// It Was Using One-Based Indexing
						vert.sub(1, 1, 1);
						
						// Get The Unique Indices
						vert.x = posInds.get(vert.x);
						if(vert.y >= 0) vert.y = uvInds.get(vert.y);
						if(vert.z >= 0) vert.z = normInds.get(vert.z);
						
						// Get The Vertex Index For The Triangle
						if(vertMap.containsKey(vert)) {
							// Vertex Already Found
							tri.set(i, vertMap.get(vert));
						}
						else {
							// New Vertex Found
							vertMap.put(vert, mesh.vertices.size());
							tri.set(i, mesh.vertices.size());
							
							// Add New Vertex To Mesh
							mesh.vertices.add(vert);
						}
					}
					
					// Add The Triangle
					mesh.triangles.add(tri);
				}
			}
			r.close();

			return mesh;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		return null;
	}

	/**
	 * Converts A Mesh Into OBJ Format
	 * @param data Mesh
	 * @return An OBJ-Formatted Mesh
	 */
	public static OBJMesh_Archive convertWithMerging(MeshData data) {
		return convert(data, TOLERANCE_POSITION, TOLERANCE_UV, TOLERANCE_NORMAL);
	}
	/**
	 * Converts A Mesh Into OBJ Format
	 * @param data Mesh
	 * @return An OBJ-Formatted Mesh
	 */
	public static OBJMesh_Archive convert(MeshData data) {
		return convert(data, -1, -1, Float.MAX_VALUE);
	}
	/**
	 * Converts A Mesh Into OBJ Format Merging Data Within Tolerance Levels
	 * @param data Mesh
	 * @param tPosSq Tolerance For Squared Distance Between Positions [0,Infinity)
	 * @param tUVSq Tolerance For Squared Distance Between Texture Coordinates [0,1)
	 * @param tNormDot Tolerance For Squared Distance Between Texture Coordinates [1,-1]
	 * @return An OBJ-Formatted Mesh
	 */
	public static OBJMesh_Archive convert(MeshData data, float tPosSq, float tUVSq, float tNormDot) {
		if(data == null || !data.hasData()) return null;

		OBJMesh_Archive mesh = new OBJMesh_Archive();
		Vector3 v3;
		Vector2 v2;
		
		// For Minifying The Mesh
		int[] posInds = null, uvInds = null, normInds = null;
		PosComparer cmpPos = new PosComparer(tPosSq);
		UVComparer cmpUV = new UVComparer(tUVSq);
		NormComparer cmpNorm = new NormComparer(tNormDot);		
		
		// Hash Positions
		posInds = new int[data.vertexCount];
		posInds[0] = 0;
		mesh.positions.add(new Vector3(
			data.positions.get(0),	
			data.positions.get(1),	
			data.positions.get(2)
			));
		for(int i = 1;i < posInds.length;i++) {
			// Extract Position
			v3 = new Vector3(
				data.positions.get(i * 3),
				data.positions.get(i * 3 + 1),
				data.positions.get(i * 3 + 2)
				);
			posInds[i] = indexOfUnique(mesh.positions, v3, cmpPos);
		}
		
		// Hash Normals
		if(data.hasNormals()) {
			normInds = new int[data.vertexCount];
			normInds[0] = 0;
			mesh.normals.add(new Vector3(
				data.normals.get(0),	
				data.normals.get(1),	
				data.normals.get(2)
				));
			for(int i = 1;i < normInds.length;i++) {
				// Extract Position
				v3 = new Vector3(
					data.normals.get(i * 3),
					data.normals.get(i * 3 + 1),
					data.normals.get(i * 3 + 2)
					);
				normInds[i] = indexOfUnique(mesh.normals, v3, cmpNorm);
			}
		}
		
		// Hash UVs
		if(data.hasUVs()) {
			uvInds = new int[data.vertexCount];
			uvInds[0] = 0;
			mesh.uvs.add(new Vector2(
				data.uvs.get(0),	
				data.uvs.get(1)	
				));
			for(int i = 1;i < uvInds.length;i++) {
				// Extract Position
				v2 = new Vector2(
					data.uvs.get(i * 2),
					data.uvs.get(i * 2 + 1)
					);
				uvInds[i] = indexOfUnique(mesh.uvs, v2, cmpUV);
			}
		}
		
		// Create Vertices And Triangles
		HashMap<Vector3i, Integer> vertMap = new HashMap<>();
		for(int i = 0;i < data.indexCount;) {
			Vector3i tri = new Vector3i();
			for(int vi = 0;vi < 3;vi++) {
				Vector3i vert = new Vector3i();
				int vertIndex = data.indices.get(i);
				vert.x = posInds[vertIndex];
				vert.y = (uvInds == null) ? -1 : uvInds[vertIndex];
				vert.z = (normInds == null) ? -1 : normInds[vertIndex];
				i++;
				
				if(vertMap.containsKey(vert)) {
					tri.set(vi, vertMap.get(vert));
				}
				else {
					tri.set(vi, vertMap.size());
					vertMap.put(vert, vertMap.size());
					mesh.vertices.add(vert);
				}
			}
			mesh.triangles.add(tri);
		}
		
		return mesh;
	}
	
	private static <T> int indexOfUnique(ArrayList<T> arr, T obj, Comparator<T> comp) {
		// Find It Was Already Put In
		boolean foundDuplicate = false;
		for(int ii = 0;ii < arr.size() && !foundDuplicate;ii++) {
			if(comp.compare(arr.get(ii), obj) == 0) {
				foundDuplicate = true;
				return ii;
			}
		}
		
		if(!foundDuplicate) {
			// Add The New Position
			int ii = arr.size();
			arr.add(obj);
			return ii;
		}
		
		return -1;
	}
	
	/**
	 * Writes Out A Mesh In OBJ Format
	 * @param w Output Stream
	 * @param mesh Mesh
	 */
	public static void write(PrintWriter w, OBJMesh_Archive mesh) {
		if(mesh == null || !mesh.hasData()) return;

		// Write Positions
		for(Vector3 v : mesh.positions) {
			w.write(String.format("v %f %f %f\n", v.x, v.y, v.z));
		}
		// Write Normals
		for(Vector3 v : mesh.normals) {
			w.write(String.format("vn %f %f %f\n", v.x, v.y, v.z));
		}
		// Write UVs
		for(Vector2 v : mesh.uvs) {
			w.write(String.format("vt %f %f\n", v.x, v.y));
		}
		
		// Write Triangles
		if(mesh.hasUVs()) {
			if(mesh.hasNormals()) {
				for(Vector3i t : mesh.triangles) {
					w.write("f");
					for(int v = 0;v < 3; v++) {
						Vector3i vert = mesh.vertices.get(t.get(v));
						w.write(String.format(" %d/%d/%d", vert.x + 1, vert.y + 1, vert.z + 1));
					}
					w.write("\n");
				}
			}
			else {
				for(Vector3i t : mesh.triangles) {
					w.write("f");
					for(int v = 0;v < 3; v++) {
						Vector3i vert = mesh.vertices.get(t.get(v));
						w.write(String.format(" %d/%d", vert.x + 1, vert.y + 1));
					}
					w.write("\n");
				}
			}
		}
		else if(mesh.hasNormals()) {
			for(Vector3i t : mesh.triangles) {
				w.write("f");
				for(int v = 0;v < 3; v++) {
					Vector3i vert = mesh.vertices.get(t.get(v));
					w.write(String.format(" %d//%d", vert.x + 1, vert.z + 1));
				}
				w.write("\n");
			}
		}
		else {
			for(Vector3i t : mesh.triangles) {
				w.write("f");
				for(int v = 0;v < 3; v++) {
					Vector3i vert = mesh.vertices.get(t.get(v));
					w.write(String.format(" %d", vert.x + 1));
				}
				w.write("\n");
			}
		}
	}

	static class PosComparer implements Comparator<Vector3> {
		private float d;
		public PosComparer(float _d) { d = _d; }
		@Override
		public int compare(Vector3 o1, Vector3 o2) {
			return o1.distSq(o2) <= d ? 0 : 1;
		}
	}
	static class UVComparer implements Comparator<Vector2> {
		private float d;
		public UVComparer(float _d) { d = _d; }
		@Override
		public int compare(Vector2 o1, Vector2 o2) {
			return o1.distSq(o2) <= d ? 0 : 1;
		}
	}
	static class NormComparer implements Comparator<Vector3> {
		private float d;
		public NormComparer(float _d) { d = _d; }
		@Override
		public int compare(Vector3 o1, Vector3 o2) {
			return o1.dot(o2) >= d ? 0 : 1;
		}
	}
}

