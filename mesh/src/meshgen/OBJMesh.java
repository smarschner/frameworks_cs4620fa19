package meshgen;

import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import math.Vector2;
import math.Vector3;

/**
 * Mesh Data Represented In An OBJ Format
 * 
 * @author Cristian, ers273
 *
 */
public class OBJMesh {

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
	 * @note Although this is marked final, you can still modify it directly, e.g., using add(), etc.
	 */
	public final ArrayList<Vector3> positions;
	/**
	 * List of vertex texture coordinates
	 * @note Although this is marked final, you can still modify it directly, e.g., using add(), etc.
	 */
	public final ArrayList<Vector2> uvs;
	/**
	 * List of vertex normals
	 * @note Although this is marked final, you can still modify it directly, e.g., using add(), etc.
	 */
	public final ArrayList<Vector3> normals;
	/**
	 * List of faces that comprise the mesh
	 * @note Although this is marked final, you can still modify it directly, e.g., using add(), etc.
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
			// Remove comments, then split.
			String[] tokens = line.replaceAll("#.*", "").split("\\s+");
			if (tokens.length == 0 || (tokens.length == 1 && tokens[0].length() == 0)) { // Blank line
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
		boolean isValid = true;
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
						isValid = false;
						continue;
					} else {
						return false;
					}
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
			System.out.println("Info: mesh lists " + nPos + " vertex positions.");
			if (nDupPos > 0) {
				System.out.println("Warning: mesh contains " + nDupPos + " pairs of duplicate positions.");
			}
		}

		int nUVs = 0;
		boolean unnormalizedUVs = false;
		if (uvs != null && !uvs.isEmpty()) {
			nUVs = uvs.size();

			for (int i=0; i<uvs.size(); i++) {
				if (uvs.get(i) == null) {
					if (verbose) {
						System.out.println("Error: null vertex texture coordinate found: index " + i + " (0 based)");
						isValid = false;
						continue;
					} else {
						return false;
					}
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
						isValid = false;
						continue;
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
		ArrayList<TreeSet<Integer>> normalsAtPosition = new ArrayList<>(nPos);
		ArrayList<TreeSet<Integer>> uvsAtPosition = new ArrayList<>(nPos);

		if (verbose) {
			for (int i=0; i<nPos; i++) {
				normalsAtPosition.add(new TreeSet<>());
				uvsAtPosition.add(new TreeSet<>());
			}
		}

		int nFaces = 0;
		boolean isTriMesh = true;
		if (faces != null && !faces.isEmpty()) {
			nFaces = faces.size();
			for (int i=0; i<faces.size(); i++) {
				if (faces.get(i) == null) {
					if (verbose) {
						System.out.println("Error: null face found: index " + i + " (0 based)");
						isValid = false;
						continue;
					} else {
						return false;
					}
				}

				OBJFace f = faces.get(i);
				if (f.positions == null) {
					if (verbose) {
						System.out.println("Error: face found with null position index list: face index " + i + " (0 based)");
						isValid = false;
						continue;
					} else {
						return false;
					}
				}
				int nVerts = f.positions.length;
				if (nVerts < 3) {
					if (verbose) {
						System.out.println("Error: face found with fewer than 3 vertices: face index " + i + " (0 based)");
						isValid = false;
					} else {
						return false;
					}
				} else if (nVerts > 3) {
					isTriMesh = false;
				}
				if (f.uvs != null && f.uvs.length != nVerts) {
					if (verbose) {
						System.out.println("Error: face found with differing number of position and texture coordinate indices: face index " + i + " (0 based)");
						isValid = false;
					} else {
						return false;
					}
				}
				if (f.normals != null && f.normals.length != nVerts) {
					if (verbose) {
						System.out.println("Error: face found with differing number of position and normal indices: face index " + i + " (0 based)");
						isValid = false;
					} else {
						return false;
					}
				}

				for (int j=0; j<nVerts; j++) {
					int posIndex = f.positions[j] - OBJFace.indexBase;
					boolean posIndexValid = posIndex >= 0 && posIndex < nPos;
					if (!posIndexValid) {
						if (verbose) {
							System.out.println("Error: face found with out of bounds position index:");
							System.out.println("Face index " + i + " (0 based) references position " + posIndex + " (0 based)");
							isValid = false;
						} else {
							return false;
						}
					} else if (verbose) {
						posUsed[posIndex] = true;
					}

					if (f.uvs != null) {
						int uvIndex = f.uvs[j] - OBJFace.indexBase;
						if (uvIndex < 0 || uvIndex >= nUVs) {
							if (verbose) {
								System.out.println("Error: face found with out of bounds texture coordinate index:");
								System.out.println("Face index " + i + " (0 based) references tex coord " + uvIndex + " (0 based)");
								isValid = false;
							} else {
								return false;
							}
						} else if (posIndexValid && verbose) {
							uvUsed[uvIndex] = true;
							uvsAtPosition.get(posIndex).add(uvIndex);
						}
					}

					if (f.normals != null) {
						int normalIndex = f.normals[j] - OBJFace.indexBase;
						if (normalIndex < 0 || normalIndex >= nNormals) {
							if (verbose) {
								System.out.println("Error: face found with out of bounds normal index:");
								System.out.println("Face index " + i + " (0 based) references normal " + normalIndex + " (0 based)");
								isValid = false;
							} else {
								return false;
							}
						} else if (posIndexValid && verbose) {
							normalUsed[normalIndex] = true;
							normalsAtPosition.get(posIndex).add(normalIndex);
						}
					}
				}
			}
		}

		if (verbose) {
			int nDupUVs = 0;
			int nDupNormals = 0;
			for (int i=0; i<nPos; i++) {
				TreeSet<Integer> uvts = uvsAtPosition.get(i);
				while(!uvts.isEmpty()) {
					int uv1 = uvts.pollFirst();
					Iterator<Integer> uvit = uvts.iterator();
					while (uvit.hasNext()) {
						int uv2 = uvit.next();
						if (uvs.get(uv1).equalsApprox(uvs.get(uv2))) {
							nDupUVs++;
						}
					}
				}

				TreeSet<Integer> nts = normalsAtPosition.get(i);
				while(!nts.isEmpty()) {
					int n1 = nts.pollFirst();
					Iterator<Integer> nit = nts.iterator();
					while (nit.hasNext()) {
						int n2 = nit.next();
						if (normals.get(n1).equalsApprox(normals.get(n2))) {
							nDupNormals++;
						}
					}
				}
			}

			if (nDupUVs > 0) {
				System.out.println("Warning: mesh contains " + nDupUVs + " pairs of duplicate texture coordinates.");
			}
			if (nDupNormals > 0) {
				System.out.println("Warning: mesh contains " + nDupNormals + " pairs of duplicate normals.");
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
			System.out.println("Info: mesh lists " + nFaces + " faces.");
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

		return isValid;
	}

	
	public static boolean advanceCompare(OBJMesh m1, OBJMesh m2, boolean verbose, float epsilon) {
		
		// Extra Credit: Advance Mesh Comparison (10pt)
		// TODO:
		// Reduce the complexity from \(O(n^2)\) to \(O(n \log n)\)
		
		return true;
	}
	
	
	/**
	 * Returns true if two OBJMeshes are equivalent.
	 * Equivalent means all of the following are true:
	 * 1) The two meshes have the same number of faces.
	 * 2) For each face in m1, a face exists in m2 with the same vertex positions, texture coordinates, and normals (if applicable)
	 * 3) For each face in m2, a face exists in m1 with the same vertex positions, texture coordinates, and normals (if applicable)
	 * If verbose is true and this method returns false, the reason the meshes are not equivalent will be printed.
	 * If there are multiple reasons, they are all printed.
	 * All floating point comparisons are assumed to match if within the epsilon value provided.
	 * @warning This method assumes that m1.isValid() and m2.isValid() are both true.
	 * @warning This method may return false incorrectly if both m1 and m2 have coincident faces with differing UVs or normals.
	 * @note This method does a brute force comparison between all faces of both input meshes...be patient if using large meshes.
	 */
	public static boolean compare(OBJMesh m1, OBJMesh m2, boolean verbose, float eps) {
		if (verbose) {
			System.out.println("Comparing meshes with epsilon=" + eps);
		}
		boolean meshesMatch = true;
		if (m1.faces.size() != m2.faces.size()) {
			if (verbose) {
				System.out.println("m1 and m2 have a differing number of faces (" + m1.faces.size() + " and " + m2.faces.size() + " respectively).");
				meshesMatch = false;
			} else {
				return false;
			}
		}

		boolean[] m2matches = new boolean[m2.faces.size()];
		for (int i1=0; i1<m1.faces.size(); i1++) {
			OBJFace f1 = m1.faces.get(i1);
			boolean foundMatch = false;
			for (int i2=0; !foundMatch && i2<m2.faces.size(); i2++) {
				OBJFace f2 = m2.faces.get(i2);
				CompareFacesResult comp = compareFaces(m1, f1, m2, f2, eps);

				if (verbose) {
					switch (comp) {
					case UVMismatch:
						System.out.println("Face " + i1 + " on m1 matches face " + i2 + " on m2, but the UV coordinates differ.");
						meshesMatch = false;
						break;
					case NormalMismatch:
						System.out.println("Face " + i1 + " on m1 matches face " + i2 + " on m2, but the normals differ.");
						meshesMatch = false;
						break;
					case UVNormalMismatch:
						System.out.println("Face " + i1 + " on m1 matches face " + i2 + " on m2, but the UV coordinates and normals differ.");
						meshesMatch = false;
						break;
					case RevMatch:
						System.out.println("Face " + i1 + " on m1 matches face " + i2 + " on m2, but the faces have different winding orders.");
						meshesMatch = false;
						break;
					case RevUVMismatch:
						System.out.println("Face " + i1 + " on m1 matches face " + i2 + " on m2, but the UV coordinates differ and the faces " +
							"have different winding orders.");
						meshesMatch = false;
						break;
					case RevNormalMismatch:
						System.out.println("Face " + i1 + " on m1 matches face " + i2 + " on m2, but the normals differ and the faces have " +
							"different winding orders.");
						meshesMatch = false;
						break;
					case RevUVNormalMismatch:
						System.out.println("Face " + i1 + " on m1 matches face " + i2 + " on m2, but the UV coordinates and normals differ " +
							"and the faces have different winding orders.");
						meshesMatch = false;
						break;
					default:
						break;
					}
				}

				if (comp != CompareFacesResult.Mismatch) {
					foundMatch = true;
					m2matches[i2] = true;
				}
			}

			if (!foundMatch) {
				if (verbose) {
					System.out.println("Unable to find a match between face " + i1 + " on m1 and any face on m2.");
					meshesMatch = false;
				} else {
					return false;
				}
			}
		}

		for (int i2=0; i2<m2.faces.size(); i2++) {
			if (m2matches[i2]) continue;
			OBJFace f2 = m2.faces.get(i2);
			boolean foundMatch = false;
			for (int i1=0; !foundMatch && i1<m1.faces.size(); i1++) {
				OBJFace f1 = m1.faces.get(i1);
				CompareFacesResult comp = compareFaces(m1, f1, m2, f2, eps);

				// No need to check matches; they've already been reported

				if (comp != CompareFacesResult.Mismatch) {
					foundMatch = true;
				}
			}

			if (!foundMatch) {
				if (verbose) {
					System.out.println("Unable to find a match between face " + i2 + " on m2 and any face on m1.");
					meshesMatch = false;
				} else {
					return false;
				}
			}
		}

		return meshesMatch;
	}

	private enum CompareFacesResult {
		Mismatch, // The face positions do not match
		Match, // The face positions match; if the face has UVs/normals, these match as well
		UVMismatch, // The face positions match, but the UVs don't
		NormalMismatch, // The face positions match, but the normals don't
		UVNormalMismatch, // The face positions match, but neither the UVs nor the normals do
		RevMatch, // The face positions match, but vertices are listed in opposite order
		RevUVMismatch, // The face positions match, but vertices are listed in opposite order; UVs don't match
		RevNormalMismatch, // The face positions match, but vertices are listed in opposite order; normals don't match
		RevUVNormalMismatch, // The face positions match, but vertices are listed in opposite order; UVs and normals don't match
	}

	/**
	 * Compares two faces from two meshes to see if they are equivalent.
	 * All vector comparisons use equalsApprox with the given epsilon value.
	 */
	private static CompareFacesResult compareFaces(OBJMesh m1, OBJFace f1, OBJMesh m2, OBJFace f2, float eps) {
		if (f1.numVerts() != f2.numVerts()) return CompareFacesResult.Mismatch;
		boolean defaultUVsMatch = (f1.hasUVs() == f2.hasUVs());
		boolean defaultNormalsMatch = (f1.hasNormals() == f2.hasNormals());

		// If the faces are equivalent, the vertex on f1 must match something on f2
		Vector3 v1 = m1.getPosition(f1, 0);
		Vector2 vt1 = f1.hasUVs() ? m1.getUV(f1, 0) : null;
		Vector3 vn1 = f1.hasNormals() ? m1.getNormal(f1, 0) : null;
		int offset = 0;
		for (; offset<f2.numVerts(); offset++) {
			if (v1.equalsApprox(m2.getPosition(f2, offset), eps)) {
				// f1 and f2 share a vertex in space
				// Try same winding order on f1 and f2
				boolean facesMatch = true;
				boolean uvsMatch = defaultUVsMatch;
				boolean normalsMatch = defaultNormalsMatch;
				for (int i=0; facesMatch && i<f1.numVerts(); i++) {
					int j = (offset+i) % f2.numVerts();
					facesMatch = m1.getPosition(f1, i).equalsApprox(m2.getPosition(f2, j), eps);
					if (facesMatch && defaultUVsMatch && f1.hasUVs()) {
						uvsMatch &= m1.getUV(f1, i).equalsApprox(m2.getUV(f2, j), eps);
					}
					if (facesMatch && defaultNormalsMatch && f1.hasNormals()) {
						normalsMatch &= m1.getNormal(f1, i).equalsApprox(m2.getNormal(f2, j), eps);
					}
				}
				if (facesMatch) {
					if (uvsMatch && normalsMatch) {
						return CompareFacesResult.Match; // We have a perfect match!
					} else if (normalsMatch) {
						return CompareFacesResult.UVMismatch; // Positions match, but UVs don't
					} else if (uvsMatch) {
						return CompareFacesResult.NormalMismatch; // Positions match, but normals don't
					}
					return CompareFacesResult.UVNormalMismatch; // Positions match, but UVs and normals don't
				}

				// Otherwise, try reverse winding order
				facesMatch = true;
				uvsMatch = defaultUVsMatch;
				normalsMatch = defaultNormalsMatch;
				for (int i=0; facesMatch && i<f1.numVerts(); i++) {
					int j = (offset-i+f2.numVerts()) % f2.numVerts();
					facesMatch = m1.getPosition(f1, i).equalsApprox(m2.getPosition(f2, j), eps);
					if (facesMatch && defaultUVsMatch && f1.hasUVs()) {
						uvsMatch &= m1.getUV(f1, i).equalsApprox(m2.getUV(f2, j), eps);
					}
					if (facesMatch && defaultNormalsMatch && f1.hasNormals()) {
						normalsMatch &= m1.getNormal(f1, i).equalsApprox(m2.getNormal(f2, j), eps);
					}
				}
				if (facesMatch) {
					if (uvsMatch && normalsMatch) {
						return CompareFacesResult.RevMatch; // We have a match, but with reverse winding order.
					} else if (normalsMatch) {
						return CompareFacesResult.RevUVMismatch; // Positions match with reverse winding order, but UVs don't
					} else if (uvsMatch) {
						return CompareFacesResult.RevNormalMismatch; // Positions match with reverse winding order, but normals don't
					}
					return CompareFacesResult.RevUVNormalMismatch; // Positions match with reverse winding order, but UVs and normals don't.
				}

				// Otherwise, try next offset
			}
		}
		return CompareFacesResult.Mismatch; // No match.
	}
}
