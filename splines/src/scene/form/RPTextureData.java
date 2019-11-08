package scene.form;

import java.io.File;

import javax.swing.JFrame;

import common.Scene.NameBindTexture;
import common.Texture;

public class RPTextureData extends ResourcePanel {
	/**
	 * UID
	 */
	private static final long serialVersionUID = 6605894911925846558L;

	public RPTextureData(JFrame parent) {
		super(parent);
	}

	@Override
	public void onFileLoad(String file) {
		File f = new File(file);
		if(!f.exists()) return;
		String texName = getUniqueName(f.getName());
		if(texName == null) return;
		
		Texture t = new Texture();
		t.setFile(file);
		scene.addTexture(new NameBindTexture(texName, t));
		return;
	}
	@Override
	public void onDeletion(String name) {
		scene.removeTexture(name);
	}
}
