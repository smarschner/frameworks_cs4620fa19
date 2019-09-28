package ray1.surface;

import java.util.ArrayList;
import ray1.IntersectionRecord;
import ray1.Ray;
import egl.math.Vector3;
import egl.math.Vector2;
import ray1.OBJMesh;
import ray1.OBJFace;

/**
 * A class that represents an Axis-Aligned box. When the scene is built, the Box is
 * split up into a Mesh of 12 Triangles.
 * 
 * @author sjm324
 *
 */
public class Box extends Surface {
  
  /* The mesh that represents this Box. */
  private Mesh mesh;
	
  /* The corner of the box with the smallest x, y, and z components. */
  protected final Vector3 minPt = new Vector3();
  public void setMinPt(Vector3 minPt) { this.minPt.set(minPt); }
  
  /* The corner of the box with the largest x, y, and z components. */
  protected final Vector3 maxPt = new Vector3();
  public void setMaxPt(Vector3 maxPt) { this.maxPt.set(maxPt); }
  
  /* Generate a Triangle mesh that represents this Box. */
  private void buildMesh() {
    // Create the OBJMesh
    OBJMesh box= new OBJMesh();
       
    // Add positions
    box.positions.add(new Vector3((float) minPt.x, (float) minPt.y, (float) minPt.z));
    box.positions.add(new Vector3((float) minPt.x, (float) maxPt.y, (float) minPt.z));
    box.positions.add(new Vector3((float) maxPt.x, (float) maxPt.y, (float) minPt.z));
    box.positions.add(new Vector3((float) maxPt.x, (float) minPt.y, (float) minPt.z));
    box.positions.add(new Vector3((float) minPt.x, (float) minPt.y, (float) maxPt.z));
    box.positions.add(new Vector3((float) minPt.x, (float) maxPt.y, (float) maxPt.z));
    box.positions.add(new Vector3((float) maxPt.x, (float) maxPt.y, (float) maxPt.z));
    box.positions.add(new Vector3((float) maxPt.x, (float) minPt.y, (float) maxPt.z));
    
    box.normals.add(new Vector3(1,0,0));
    box.normals.add(new Vector3(-1,0,0));
    box.normals.add(new Vector3(0,1,0));
    box.normals.add(new Vector3(0,-1,0));
    box.normals.add(new Vector3(0,0,1));
    box.normals.add(new Vector3(0,0,-1));
    
    // Initialize the uvs randomly
    for (int i=0;i<box.positions.size();i++) {
    	box.uvs.add(new Vector2(0,0));
    }
    
    OBJFace tri1 = new OBJFace(3,true,true);
    tri1.setVertex(0, 0, 0, 5);
	tri1.setVertex(1, 2, 2, 5);
	tri1.setVertex(2, 1, 1, 5);
	box.faces.add(tri1);
	
	OBJFace tri2 = new OBJFace(3,true,true);
    tri2.setVertex(0, 0, 0, 5);
	tri2.setVertex(1, 3, 3, 5);
	tri2.setVertex(2, 2, 2, 5);
	box.faces.add(tri2);

	OBJFace tri3 = new OBJFace(3,true,true);
    tri3.setVertex(0, 0, 0, 1);
	tri3.setVertex(1, 1, 1, 1);
	tri3.setVertex(2, 5, 5, 1);
	box.faces.add(tri3);

	OBJFace tri4 = new OBJFace(3,true,true);
    tri4.setVertex(0, 0, 0, 1);
	tri4.setVertex(1, 5, 5, 1);
	tri4.setVertex(2, 4, 4, 1);
	box.faces.add(tri4);

	OBJFace tri5 = new OBJFace(3,true,true);
    tri5.setVertex(0, 0, 0, 3);
	tri5.setVertex(1, 4, 4, 3);
	tri5.setVertex(2, 7, 7, 3);
	box.faces.add(tri5);

	OBJFace tri6 = new OBJFace(3,true,true);
    tri6.setVertex(0, 0, 0, 3);
	tri6.setVertex(1, 7, 7, 3);
	tri6.setVertex(2, 3, 3, 3);
	box.faces.add(tri6);

	OBJFace tri7 = new OBJFace(3,true,true);
    tri7.setVertex(0, 4, 4, 4);
	tri7.setVertex(1, 6, 6, 4);
	tri7.setVertex(2, 5, 5, 4);
	box.faces.add(tri7);

	OBJFace tri8 = new OBJFace(3,true,true);
    tri8.setVertex(0, 4, 4, 4);
	tri8.setVertex(1, 7, 7, 4);
	tri8.setVertex(2, 6, 6, 4);
	box.faces.add(tri8);

	OBJFace tri9 = new OBJFace(3,true,true);
    tri9.setVertex(0, 2, 2, 2);
	tri9.setVertex(1, 5, 5, 2);
	tri9.setVertex(2, 6, 6, 2);
	box.faces.add(tri9);

	OBJFace tri10 = new OBJFace(3,true,true);
    tri10.setVertex(0, 2, 2, 2);
	tri10.setVertex(1, 1, 1, 2);
	tri10.setVertex(2, 5, 5, 2);
	box.faces.add(tri10);

	OBJFace tri11 = new OBJFace(3,true,true);
    tri11.setVertex(0, 2, 2, 0);
	tri11.setVertex(1, 6, 6, 0);
	tri11.setVertex(2, 7, 7, 0);
	box.faces.add(tri11);

	OBJFace tri12 = new OBJFace(3,true,true);
    tri12.setVertex(0, 2, 2, 0);
	tri12.setVertex(1, 7, 7, 0);
	tri12.setVertex(2, 3, 3, 0);
	box.faces.add(tri12);
   
    this.mesh= new Mesh(box);
    this.mesh.shader = this.shader;
  }
  
  public boolean intersect(IntersectionRecord outRecord, Ray ray) {
  	return false;
  }
  
  public void appendRenderableSurfaces(ArrayList<Surface> in) {
	  buildMesh();
	  mesh.appendRenderableSurfaces(in);
  }
  
  
  /**
   * @see Object#toString()
   */
  public String toString() {
    return "Box ";
  }

}