package scene.form;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;

import common.Mesh;
import common.Scene.NameBindMesh;
import mesh.gen.MeshGenCube;
import mesh.gen.MeshGenCylinder;
import mesh.gen.MeshGenSphere;
import scene.form.SimpleMeshWindow.Result;

public class RPMeshData extends ResourcePanel {
	/**
	 * UID
	 */
	private static final long serialVersionUID = 41051921499386182L;
	
	protected JButton btnAddSimple;
	protected SimpleMeshWindow wndSimpleMesh;
	
	public RPMeshData(JFrame parent) {
		super(parent);
		wndSimpleMesh = new SimpleMeshWindow(frame);
	}

	@Override
	public void initComponents() {
		super.initComponents();
		btnAddSimple = new JButton();
		pnlButtons.add(btnAddSimple);
		btnAddSimple.setText("Add Basic Mesh");
		btnAddSimple.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Result res = wndSimpleMesh.getInput();
				if(res != null) {
					Mesh m = new Mesh();
					String name;
					switch(res.meshType) {
					case Cube:
						m.setGenerator(new MeshGenCube());
						name = "Cube-";
						break;
					case Cylinder:
						m.setGenerator(new MeshGenCylinder());
						name = "Cylinder-";
						break;
					case Sphere:
						m.setGenerator(new MeshGenSphere());
						name = "Sphere-";
						break;
					default:
						return;
					}
					name += "n" + res.n + "m" + res.m;
					m.genOptions.divisionsLatitude = res.n;
					m.genOptions.divisionsLongitude = res.m;
					
					scene.addMesh(new NameBindMesh(name, m));
				}
			}
		});
	}

	@Override
	public void onFileLoad(String file) {
		File f = new File(file);
		if(!f.exists()) return;
		String meshName = getUniqueName(f.getName());
		if(meshName == null) return;
		
		Mesh m = new Mesh();
		m.setFile(file);
		scene.addMesh(new NameBindMesh(meshName, m));
		return;
	}
	@Override
	public void onDeletion(String name) {
		scene.removeMesh(name);
	}
}
