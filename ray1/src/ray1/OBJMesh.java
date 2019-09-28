package ray1;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import egl.math.Vector2;
import egl.math.Vector3;

/**
 * Mesh Data Represented In An OBJ Format
 * 
 * @author Cristian, ers273
 *
 */
public class OBJMesh {

	@SuppressWarnings("serial")
	public class OBJFileFormatException extends IOException {
		public OBJFileFormatException() {
			super();
		}

		public OBJFileFormatException(String error) {
			super(error);
		}
	}

	/**
	 * List of vertex positions
	 */
	public final ArrayList<Vector3> positions;
	/**
	 * List of vertex texture coordinates
	 */
	public final ArrayList<Vector2> uvs;
	/**
	 * List of vertex normals
	 */
	public final ArrayList<Vector3> normals;
	/**
	 * List of faces that comprise the mesh
	 */
	public final ArrayList<OBJFace> faces;
	
	/**
	 * Constructs an empty mesh
	 */
	public OBJMesh() {
		positions = new ArrayList<>();
		uvs = new ArrayList<>();
		normals = new ArrayList<>();
		faces = new ArrayList<>();
	}

	/**
	 * Constructs a mesh from an existing OBJ file.
	 * See parseOBJ() for a description of the Exceptions thrown by this method.
	 */
	public OBJMesh(String filename) throws IOException, OBJFileFormatException {
		this();
		parseOBJ(filename);
	}

	/**
	 * Returns the position associated with a vertex, given a face and the index of the vertex.
	 */
	public Vector3 getPosition(OBJFace face, int vertexIndex) {
		return positions.get(face.positions[vertexIndex] - OBJFace.indexBase);
	}

	/**
	 * Returns the texture coordinates associated with a vertex, given a face and the index of the vertex.
	 * @note This method assumes the face has texture coordinate data.
	 */
	public Vector2 getUV(OBJFace face, int vertexIndex) {
		return uvs.get(face.uvs[vertexIndex] - OBJFace.indexBase);
	}

	/**
	 * Returns the normal associated with a vertex, given a face and the index of the vertex.
	 * @note This method assumes the face has normal data.
	 */
	public Vector3 getNormal(OBJFace face, int vertexIndex) {
		return normals.get(face.normals[vertexIndex] - OBJFace.indexBase);
	}

	/**
	 * Parses an OBJ file and sets this to its contents.
	 * @throws IOException if there is a problem reading the file.
	 * @throws OBJFileFormatException if the input OBJ file is malformed.
	 */
	public void parseOBJ(String filename) throws IOException, OBJFileFormatException {
		positions.clear();
		uvs.clear();
		normals.clear();
		faces.clear();

		BufferedReader reader = Files.newBufferedReader(Paths.get(filename));
		String line;
		while ((line = reader.readLine()) != null) {
			String[] tokens = line.split("\\s+");
			if (tokens[0].charAt(0) == '#') { // Comment
				continue;
			} else if (tokens[0].equals("s") || tokens[0].equals("o")) {
				continue;
			} else if (tokens[0].equals("v")) { // Vertex position
				if (tokens.length != 4) {
					throw new OBJFileFormatException("Malformed vertex position specification: " + line);
				}
				Vector3 v = new Vector3(
					Float.parseFloat(tokens[1]),
					Float.parseFloat(tokens[2]),
					Float.parseFloat(tokens[3]));
				positions.add(v);
			} else if (tokens[0].equals("vt")) { // Vertex texture coordinate (UV)
				if (tokens.length != 3) {
					throw new OBJFileFormatException("Malformed vertex texture coordinate specification: " + line);
				}
				Vector2 vt = new Vector2(
					Float.parseFloat(tokens[1]),
					Float.parseFloat(tokens[2]));
				uvs.add(vt);
			} else if (tokens[0].equals("vn")) { // Vertex normal
				if (tokens.length != 4) {
					throw new OBJFileFormatException("Malformed vertex normal specification: " + line);
				}
				Vector3 vn = new Vector3(
					Float.parseFloat(tokens[1]),
					Float.parseFloat(tokens[2]),
					Float.parseFloat(tokens[3]));
				normals.add(vn);
			} else if (tokens[0].equals("f")) { // Face
				if (tokens.length < 4) {
					throw new OBJFileFormatException("Malformed face specification: " + line);
				}
				int nVerts = tokens.length-1;
				OBJFace f = new OBJFace(nVerts, false, false);
				for (int i=0; i<nVerts; i++) {
					String[] vertexTokens = tokens[i+1].split("/");
					switch (vertexTokens.length) {
					case 3:
						if (!vertexTokens[2].equals("")) {
							if (f.normals == null) {
								f.normals = new int[nVerts];
							}
							f.normals[i] = Integer.parseInt(vertexTokens[2]) + OBJFace.indexBase - 1;
						}
						// Intentionally fall through
					case 2:
						if (!vertexTokens[1].equals("")) {
							if (f.uvs == null) {
								f.uvs = new int[nVerts];
							}
							f.uvs[i] = Integer.parseInt(vertexTokens[1]) + OBJFace.indexBase - 1;
						}
						// Intentionally fall through
					case 1:
						f.positions[i] = Integer.parseInt(vertexTokens[0]) + OBJFace.indexBase - 1;
						break;

					default:
						throw new OBJFileFormatException("Malformed face specification: " + line);
					}
				}
				faces.add(f);
			} else {
				System.err.println("Warning: ignored unrecognized OBJ specifier: " + tokens[0]);
			}
		}

		reader.close();
	}

	/**
	 * Writes the current contents of the mesh to the specified OBJ file.
	 * @warning This method assumes the current mesh is valid.
	 * @throws IOException if there is a problem writing the file.
	 */
	public void writeOBJ(String filename) throws IOException {
		BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename));
		for (Vector3 v : positions) {
			writer.write("v " + v.x + " " + v.y + " " + v.z);
			writer.newLine();
		}
		for (Vector2 vt : uvs) {
			writer.write("vt " + vt.x + " " + vt.y);
			writer.newLine();
		}
		for (Vector3 vn : normals) {
			writer.write("vn " + vn.x + " " + vn.y + " " + vn.z);
			writer.newLine();
		}
		for (OBJFace f : faces) {
			boolean writeUVs = (f.uvs != null);
			boolean writeNormals = (f.normals != null);
			if (!writeUVs && !writeNormals) { // Only write positions
				writer.write("f");
				for (int i=0; i<f.positions.length; i++) {
					int vIndex = f.positions[i] - OBJFace.indexBase + 1;
					writer.write(" " + vIndex);
				}
				writer.newLine();
			} else if (writeUVs && !writeNormals) { // Write positions and texture coords
				writer.write("f");
				for (int i=0; i<f.positions.length; i++) {
					int vIndex = f.positions[i] - OBJFace.indexBase + 1;
					int vtIndex = f.uvs[i] - OBJFace.indexBase + 1;
					writer.write(" " + vIndex + "/" + vtIndex);
				}
				writer.newLine();
			} else if (!writeUVs && writeNormals) { // Write positions and normals
				writer.write("f");
				for (int i=0; i<f.positions.length; i++) {
					int vIndex = f.positions[i] - OBJFace.indexBase + 1;
					int vnIndex = f.normals[i] - OBJFace.indexBase + 1;
					writer.write(" " + vIndex + "//" + vnIndex);
				}
				writer.newLine();
			} else { // Write positions, texture coords, and normals
				writer.write("f");
				for (int i=0; i<f.positions.length; i++) {
					int vIndex = f.positions[i] - OBJFace.indexBase + 1;
					int vtIndex = f.uvs[i] - OBJFace.indexBase + 1;
					int vnIndex = f.normals[i] - OBJFace.indexBase + 1;
					writer.write(" " + vIndex + "/" + vtIndex + "/" + vnIndex);
				}
				writer.newLine();
			}
		}
		writer.close();
	}

	/**
	 * Returns true if the current mesh is valid.
	 * This method performs a number of sanity checks to catch common mistakes with mesh construction.
	 * If verbose is true, diagnostic information will be written to System.out.
	 * @note If this method returns true, that does not necessarily mean that the mesh is correct;
	 * it only means that it complies with the OBJ specification, and that writeOBJ() can be called safely.
	 * If verbose is true, some further warnings may be printed; you should address these even if this method returns true.
	 */
	public boolean isValid(boolean verbose) {
		if (verbose) {
			System.out.println("Verifying mesh...");
		}

		int nPos = 0, nDupPos = 0;
		if (positions != null && !positions.isEmpty()) {
			nPos = positions.size();

			for (int i=0; i<positions.size(); i++) {
				if (positions.get(i) == null) {
					if (verbose) {
						System.out.println("Error: null vertex position found: index " + i + " (0 based)");
					}
					return false;
				}
			}

			for (int i=0; i<positions.size(); i++) {
				for (int j=i+1; j<positions.size(); j++) {
					if (positions.get(i).equalsApprox(positions.get(j))) {
						nDupPos++;
					}
				}
			}
		}
		if (verbose) {
			System.out.println("Info: mesh lists " + nPos + " vertex positions (including " + nDupPos + " pairs of duplicates).");
		}

		int nUVs = 0;
		boolean unnormalizedUVs = false;
		if (uvs != null && !uvs.isEmpty()) {
			nUVs = uvs.size();

			for (int i=0; i<uvs.size(); i++) {
				if (uvs.get(i) == null) {
					if (verbose) {
						System.out.println("Error: null vertex texture coordinate found: index " + i + " (0 based)");
					}
					return false;
				}

				if (uvs.get(i).x > 1.0f || uvs.get(i).x < 0.0f || uvs.get(i).y > 1.0f || uvs.get(i).y < 0.0f) {
					unnormalizedUVs = true;
				}
			}
		}
		if (verbose) {
			System.out.println("Info: mesh lists " + nUVs + " vertex texture coordinates.");
			if (unnormalizedUVs) {
				System.out.println("Warning: mesh contains UV coordinates outside the [0,1] range.");
			}
		}

		int nNormals = 0;
		boolean unnormalizedNormals = false;
		if (normals != null && !normals.isEmpty()) {
			nNormals = normals.size();

			for (int i=0; i<normals.size(); i++) {
				if (normals.get(i) == null) {
					if (verbose) {
						System.out.println("Error: null vertex normal found: index " + i + " (0 based)");
					}
					return false;
				}

				float len = normals.get(i).len();
				if (Math.abs(len - 1.0f) > 1e-5) {
					unnormalizedNormals = true;
				}
			}
		}
		if (verbose) {
			System.out.println("Info: mesh lists " + nNormals + " vertex normals.");
			if (unnormalizedNormals) {
				System.out.println("Warning: mesh contains normals that are not unit length.");
			}
		}

		boolean[] posUsed = new boolean[nPos];
		boolean[] uvUsed = new boolean[nUVs];
		boolean[] normalUsed = new boolean[nNormals];

		int nFaces = 0;
		boolean isTriMesh = true;
		if (faces != null && !faces.isEmpty()) {
			nFaces = faces.size();
			for (int i=0; i<faces.size(); i++) {
				if (faces.get(i) == null) {
					if (verbose) {
						System.out.println("Error: null face found: index " + i + " (0 based)");
					}
					return false;
				}

				OBJFace f = faces.get(i);
				if (f.positions == null) {
					if (verbose) {
						System.out.println("Error: face found with null position index list: face index " + i + " (0 based)");
					}
					return false;
				}
				int nVerts = f.positions.length;
				if (nVerts < 3) {
					if (verbose) {
						System.out.println("Error: face found with fewer than 3 vertices: face index " + i + " (0 based)");
					}
					return false;
				} else if (nVerts > 3) {
					isTriMesh = false;
				}
				if (f.uvs != null && f.uvs.length != nVerts) {
					if (verbose) {
						System.out.println("Error: face found with differing number of position and texture coordinate indices: face index " + i + " (0 based)");
					}
					return false;
				}
				if (f.normals != null && f.normals.length != nVerts) {
					if (verbose) {
						System.out.println("Error: face found with differing number of position and normal indices: face index " + i + " (0 based)");
					}
					return false;
				}

				for (int j=0; j<nVerts; j++) {
					int posIndex = f.positions[j] - OBJFace.indexBase;
					if (posIndex < 0 || posIndex >= nPos) {
						if (verbose) {
							System.out.println("Error: face found with out of bounds position index.");
							System.out.println("Face index " + i + " (0 based) references position " + posIndex + " (0 based)");
						}
						return false;
					}
					posUsed[posIndex] = true;

					if (f.uvs != null) {
						int uvIndex = f.uvs[j] - OBJFace.indexBase;
						if (uvIndex < 0 || uvIndex >= nUVs) {
							if (verbose) {
								System.out.println("Error: face found with out of bounds texture coordinate index.");
								System.out.println("Face index " + i + " (0 based) references tex coord " + uvIndex + " (0 based)");
							}
							return false;
						}
						uvUsed[uvIndex] = true;
					}

					if (f.normals != null) {
						int normalIndex = f.normals[j] - OBJFace.indexBase;
						if (normalIndex < 0 || normalIndex >= nNormals) {
							if (verbose) {
								System.out.println("Error: face found with out of bounds normal index.");
								System.out.println("Face index " + i + " (0 based) references normal " + normalIndex + " (0 based)");
							}
							return false;
						}
						normalUsed[normalIndex] = true;
					}
				}
			}
		}

		int nUnusedPos = 0;
		for (int i=0; i<nPos; i++) {
			if (!posUsed[i]) {
				nUnusedPos++;
			}
		}
		int nUnusedUVs = 0;
		for (int i=0; i<nUVs; i++) {
			if (!uvUsed[i]) {
				nUnusedUVs++;
			}
		}
		int nUnusedNormals = 0;
		for (int i=0; i<nNormals; i++) {
			if (!normalUsed[i]) {
				nUnusedNormals++;
			}
		}

		if (verbose) {
			System.out.println("Info: mesh lists " + nFaces + " vertex faces.");
			if (!isTriMesh) {
				System.out.println("Warning: mesh contains polygons that are not triangles.");
			}
			if (nUnusedPos > 0) {
				System.out.println("Warning: mesh lists " + nUnusedPos + " vertex positions that are not used by any face.");
			}
			if (nUnusedUVs > 0) {
				System.out.println("Warning: mesh lists " + nUnusedUVs + " vertex texture coordinates that are not used by any face.");
			}
			if (nUnusedNormals > 0) {
				System.out.println("Warning: mesh lists " + nUnusedNormals + " vertex normals that are not used by any face.");
			}
		}

		return true;
	}

	/**
	 * Returns true if two OBJMeshes are equivalent.
	 * Equivalent means all of the following are true:
	 * 1) The two meshes have the same number of faces.
	 * 2) For each face in m1, a face exists in m2 with the same vertex positions, texture coordinates, and normals (if applicable)
	 * 3) For each face in m2, a face exists in m1 with the same vertex positions, texture coordinates, and normals (if applicable)
	 * If verbose is true and this method returns false, the reason the meshes are not equivalent will be printed.
	 * @warning This method assumes that m1.isValid() and m2.isValid() are both true.
	 * @note This method does a brute force comparison between all faces of both input meshes...be patient if using large meshes.
	 */
	public static boolean compare(OBJMesh m1, OBJMesh m2, boolean verbose) {
		if (m1.faces.size() != m2.faces.size()) {
			if (verbose) {
				System.out.println("m1 and m2 have a differing number of faces (" + m1.faces.size() + " and " + m2.faces.size() + " respectively).");
			}
			return false;
		}

		for (int i1=0; i1<m1.faces.size(); i1++) {
			OBJFace f1 = m1.faces.get(i1);
			boolean foundMatch = false;
			for (int i2=0; !foundMatch && i2<m2.faces.size(); i2++) {
				OBJFace f2 = m2.faces.get(i2);
				int comp = compareFaces(m1, f1, m2, f2);
				if (comp == 2 && verbose) {
					System.out.println("Face " + i1 + " on m1 matches face " + i2 + " on m2, but the faces have different winding orders.");
				}
				if (comp > 0) {
					foundMatch = true;
				}
			}

			if (!foundMatch) {
				if (verbose) {
					System.out.println("Unable to find a match between face " + i1 + " on m1 and any face on m2.");
				}
				return false;
			}
		}

		for (int i2=0; i2<m2.faces.size(); i2++) {
			OBJFace f2 = m2.faces.get(i2);
			boolean foundMatch = false;
			for (int i1=0; !foundMatch && i1<m1.faces.size(); i1++) {
				OBJFace f1 = m1.faces.get(i1);
				int comp = compareFaces(m1, f1, m2, f2);
				if (comp == 2 && verbose) {
					System.out.println("Face " + i2 + " on m2 matches face " + i1 + " on m1, but the faces have different winding orders.");
				}
				if (comp > 0) {
					foundMatch = true;
				}
			}

			if (!foundMatch) {
				if (verbose) {
					System.out.println("Unable to find a match between face " + i2 + " on m2 and any face on m1.");
				}
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns 0 if the input faces are not equivalent.
	 * Returns 1 if the input faces are equivalent.
	 * Returns 2 if the input faces are equivalent, but have different winding orders.
	 */
	private static int compareFaces(OBJMesh m1, OBJFace f1, OBJMesh m2, OBJFace f2) {
		if (f1.numVerts() != f2.numVerts()) return 0;
		if (f1.hasUVs() != f2.hasUVs()) return 0;
		if (f1.hasNormals() != f2.hasNormals()) return 0;

		// If the faces are equivalent, the vertex on f1 must match something on f2
		Vector3 v1 = m1.getPosition(f1, 0);
		Vector2 vt1 = f1.hasUVs() ? m1.getUV(f1, 0) : null;
		Vector3 vn1 = f1.hasNormals() ? m1.getNormal(f1, 0) : null;
		int offset = 0;
		for (; offset<f2.numVerts(); offset++) {
			if (v1.equalsApprox(m2.getPosition(f2, offset))) {
				// f1 and f2 share a vertex in space
				if (f1.hasUVs() && !vt1.equalsApprox(m2.getUV(f2, offset))) continue;
				if (f1.hasNormals() && !vn1.equalsApprox(m2.getNormal(f2, offset))) continue;

				// Try same winding order on f1 and f2
				boolean facesMatch = true;
				for (int i=1; facesMatch && i<f1.numVerts(); i++) {
					facesMatch = m1.getPosition(f1, i).equalsApprox(m2.getPosition(f2, (offset+i) % f2.numVerts()));
					if (facesMatch && f1.hasUVs()) {
						facesMatch = m1.getUV(f1, i).equalsApprox(m2.getUV(f2, (offset+i) % f2.numVerts()));
					}
					if (facesMatch && f1.hasNormals()) {
						facesMatch = m1.getNormal(f1, i).equalsApprox(m2.getNormal(f2, (offset+i) % f2.numVerts()));
					}
				}
				if (facesMatch) return 1; // We have a match!

				// Otherwise, try reverse winding order
				facesMatch = true;
				for (int i=1; facesMatch && i<f1.numVerts(); i++) {
					facesMatch = m1.getPosition(f1, i).equalsApprox(m2.getPosition(f2, (offset-i+f2.numVerts()) % f2.numVerts()));
					if (facesMatch && f1.hasUVs()) {
						facesMatch = m1.getUV(f1, i).equalsApprox(m2.getUV(f2, (offset-i+f2.numVerts()) % f2.numVerts()));
					}
					if (facesMatch && f1.hasNormals()) {
						facesMatch = m1.getNormal(f1, i).equalsApprox(m2.getNormal(f2, (offset-i+f2.numVerts()) % f2.numVerts()));
					}
				}
				if (facesMatch) return 2; // We have a match! (With reverse winding order.)

			}
		}
		return 0;
	}
}
