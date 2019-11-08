package scene.form;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;

import common.Material;
import common.Scene.NameBindMaterial;
import ext.java.Parser;

public class RPMaterialData extends ResourcePanel {
	/**
	 * UID
	 */
	private static final long serialVersionUID = 4866178940566055368L;
	
	protected JButton btnAddSimple = new JButton();	
	protected NewMaterialWindow matWindow;
	
	public RPMaterialData(ControlWindow parent) {
		super(parent);
		matWindow = new NewMaterialWindow(frame);
		
		pnlButtons.add(btnAddSimple);
		btnAddSimple.setText("Add");
		btnAddSimple.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addBasic();
			}
		});
	}

	public void addBasic() {
		NameBindMaterial res = matWindow.getInput();
		if(res != null) scene.addMaterial(res);
	}
	@Override
	public void onFileLoad(String file) {
		File f = new File(file);
		if(!f.exists()) return;
		String matName = getUniqueName(f.getName());
		if(matName == null) return;
		
		Parser parser = new Parser();
		Material m = (Material)parser.parse(file, Material.class);
		scene.addMaterial(new NameBindMaterial(matName, m));
	}
	@Override
	public void onDeletion(String name) {
		scene.removeMaterial(name);
	}
}
