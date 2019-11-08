package scene.form;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import common.Scene;
import scene.SceneApp;

public class ControlWindow extends JFrame {
	/**
	 * UID
	 */
	private static final long serialVersionUID = -7483532373533818649L;
	
	public final HashMap<String, Component> tabs = new HashMap<>();
	private JTabbedPane tabsPanel;
	
	ControlWindowUpdater sceneUpdater;
	public Scene scene;
	public final SceneApp app;
	
	public ControlWindow(SceneApp a) {
		super("Data Control");
		app = a;
		scene = app.scene;
		
		setSize(400, 600);
		setResizable(false);
		
		
		ArrayList<String> orderedTabs = new ArrayList<String>();
		orderedTabs.add("Object"); orderedTabs.add("Mesh"); orderedTabs.add("Material"); orderedTabs.add("Texture");
		
		tabs.put("Object", new ScenePanel(app));
		tabs.put("Mesh", new RPMeshData(this));
		tabs.put("Material", new RPMaterialData(this));
		tabs.put("Texture", new RPTextureData(this));
	
		tabsPanel = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
		add(tabsPanel);
		for(String s : orderedTabs) {
			System.out.println(s);
			tabsPanel.add(s, (Component)tabs.get(s));
		}
	}
	public void run() {
		sceneUpdater = new ControlWindowUpdater(app, this);
		
		Thread t = new Thread(sceneUpdater);
		t.start();
		
		setVisible(true);
		setEnabled(true);
		
		t.interrupt();
	}

	
	public void tabToForefront(String name) {
		Component c = tabs.get(name);
		if(c == null) return;
		
		tabsPanel.setSelectedComponent(c);
	}
}
