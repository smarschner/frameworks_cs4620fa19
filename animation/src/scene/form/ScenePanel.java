package scene.form;

/*
 * I adapted this from an Oracle example, and, as per their copyright instructions, I included this copyright message:
 * 
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import common.Scene;
import common.Scene.NameBindSceneObject;
import common.SceneCamera;
import common.SceneLight;
import common.SceneObject;
import common.event.SceneReloadEvent;
import scene.SceneApp;
import ext.java.Parser;

public class ScenePanel extends JPanel implements ValueUpdatable {
	/**
	 * UID
	 */
	private static final long serialVersionUID = -504094489248280663L;

	protected DefaultMutableTreeNode rootNode;
	protected DefaultTreeModel treeModel;
	protected JTree tree;

	protected ArrayList<ValueUpdatable> updatables = new ArrayList<ValueUpdatable>();

	SceneApp app;
	
	JPanel editPanel = new JPanel(new CardLayout());
	HashMap<DefaultMutableTreeNode, String> nodeToEditor = new HashMap<>();
	JTextField txtObjectName = new JTextField();
	
	public ScenePanel(SceneApp a) {
		super(new BorderLayout());
		app = a;

		rootNode = new DefaultMutableTreeNode(Scene.ROOT_NODE_NAME);
		treeModel = new DefaultTreeModel(rootNode);

		tree = new JTree(treeModel);
		tree.setEditable(false);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
		tree.setExpandsSelectedPaths(true);
		tree.setShowsRootHandles(true);

		JScrollPane scrollPane = new JScrollPane(tree);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				
		// add a selection listener to tree, now that we've filled up the CardLayout
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
				CardLayout cl = (CardLayout)(editPanel.getLayout());
				cl.show(editPanel, nodeToEditor.get(node));
			}
		});

		JPanel actionPanel = new JPanel();
		actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.X_AXIS));
		
		
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
		
		JButton loadScene = new JButton("Load Scene XML");
		loadScene.setAlignmentX(Component.CENTER_ALIGNMENT);
		loadScene.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ControlWindow controlWindow = (ControlWindow) SwingUtilities.getWindowAncestor(ScenePanel.this);
				FileDialog fd = new FileDialog(controlWindow);
				fd.setVisible(true);
				for(File f : fd.getFiles()) {
					String file = f.getAbsolutePath();
					if(file != null) {
						Parser p = new Parser();
						Object o = p.parse(file, Scene.class);
						if(o != null) {
							Scene old = app.scene;
							app.scene = (Scene)o;
							if(old != null) old.sendEvent(new SceneReloadEvent(file));
							return;}}}}});
		actionPanel.add(loadScene);
		
		JButton newObject = new JButton("Add Blank");
		newObject.setAlignmentX(Component.CENTER_ALIGNMENT);
		newObject.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Get A Good Name
				String name = txtObjectName.getText();
				if(name == null) return;
				name = name.trim();
				
				// Create A Scene Object
				SceneObject o;
				if(name.toLowerCase().startsWith("camera-")) {
					name = name.substring(7);
					o = new SceneCamera();
				}
				else if (name.toLowerCase().startsWith("light-")) {
					name = name.substring(6);
					o = new SceneLight();
				}
				else {
					o = new SceneObject();
				}
				if(name.length() < 1) return;
				
				// Add New Object If Non-existent
				SceneObject so = app.scene.objects.get(name);
				if(so == null) {
					app.scene.addObject(new NameBindSceneObject(name, o));
				}
			}
		});
		actionPanel.add(newObject);
		actionPanel.add(txtObjectName);
		
		JButton deleteObject = new JButton("Delete");
		deleteObject.setAlignmentX(Component.CENTER_ALIGNMENT);
		deleteObject.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(tree.isSelectionEmpty()) return;
				TreePath path = tree.getSelectionPath();
				if(path == null) return;
				DefaultMutableTreeNode name = (DefaultMutableTreeNode)path.getLastPathComponent();
				app.scene.removeObject((String)name.getUserObject());
			}
		});
		actionPanel.add(deleteObject);
		
		
		add(scrollPane);
		southPanel.add(actionPanel);
		southPanel.add(editPanel);
		add(southPanel, BorderLayout.SOUTH);
	}

	public void setScene() {
		rebuildSceneStructure();
	}
	
	public void rebuildSceneStructure() {
		//each node has its own editor
		editPanel.removeAll();
		editPanel.add(new JPanel(), "default");

		nodeToEditor.clear();
		rootNode.removeAllChildren();
		treeModel.reload(rootNode);

		HashMap<String, SceneObject> nameMap = new HashMap<String, SceneObject>();
		HashMap<SceneObject, DefaultMutableTreeNode> objectToNodeMap = new HashMap<SceneObject, DefaultMutableTreeNode>();
		HashMap<DefaultMutableTreeNode, SceneObject> nodeToObjectMap = new HashMap<DefaultMutableTreeNode, SceneObject>();

		for(SceneObject x : app.scene.objects) {
			String nodeName = null;
			JPanel editor = null;
			if(x instanceof SceneLight)
				editor = new LightEditor(app.scene, (SceneLight)x);
			else if(x instanceof SceneCamera)
				editor = new CameraEditor(app.scene, (SceneCamera)x);
			else
				editor = new ObjectEditor(app.scene, x);

			nodeName = x.getID().name;
			DefaultMutableTreeNode newNode;
			if(nodeName.equals(Scene.ROOT_NODE_NAME)) newNode = rootNode;
			else newNode = new DefaultMutableTreeNode(nodeName);

			//add to our editing panel
			nodeToEditor.put(newNode, nodeName);
			editPanel.add(editor, nodeName);

			//We'll need these to make the tree structure
			nodeToObjectMap.put(newNode, x);
			objectToNodeMap.put(x, newNode);
			nameMap.put(x.getID().name, x);
		}

		//make tree structure
		for(Map.Entry<DefaultMutableTreeNode, SceneObject> entry : nodeToObjectMap.entrySet()) {
			DefaultMutableTreeNode node = entry.getKey();
			SceneObject obj = entry.getValue();
			if(obj.parent != null) {
				DefaultMutableTreeNode parent = objectToNodeMap.get(nameMap.get(obj.parent));
				if(parent != null) parent.add(node);
			}
		}

		tree.repaint();
	}

	@SuppressWarnings("unchecked")
	public void select(String name) {
		Enumeration<TreeNode> e = rootNode.breadthFirstEnumeration();
		while(e.hasMoreElements()) {
			DefaultMutableTreeNode n = (DefaultMutableTreeNode)e.nextElement();
			if(((String)n.getUserObject()).equals(name)) {
				TreePath p = new TreePath(treeModel.getPathToRoot(n));
				tree.setSelectionPath(p);
				tree.expandPath(p);
				return;
			}
		}
	}
	public void deselect() {
		tree.setSelectionPath(null);
	}

	@Override
	public void updateValues() {
		for(Component c : editPanel.getComponents())
		{
			if(c instanceof ValueUpdatable) ((ValueUpdatable)c).updateValues();
		}

	}
}
