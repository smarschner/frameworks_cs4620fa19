package scene.form;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import common.Scene;
import common.SceneObject;
import common.event.SceneCollectionModifiedEvent;
import common.event.SceneDataType;
import common.event.SceneObjectResourceEvent;
import common.event.SceneTransformationEvent;


/*
 * Object editor is a JPanel that supports editing of parameters for objects, which includes the
 * object's transformation matrix, material, and mesh.
 */
public class ObjectEditor extends JPanel implements ValueUpdatable {
	/**
	 * UID
	 */
	private static final long serialVersionUID = 3903094708323383337L;
	
	protected static final DecimalFormat decimalFormat = (DecimalFormat)NumberFormat.getNumberInstance();
	static {
		decimalFormat.setGroupingUsed(false);
	}
	
	protected Scene scene;
	protected SceneObject myObject;
	
	protected TransformationMatrixEditPanel matrixEditor;
	protected ObjectEditPanel objectEditor;
	
	//This will be a boxlayout of JPanels
	//The first panel supports matrix editing
	//The second panel supports mesh/material string editing
	public ObjectEditor(Scene s, SceneObject objectIn) {
		scene = s;
		myObject = objectIn;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		objectEditor = new ObjectEditPanel();
		matrixEditor = new TransformationMatrixEditPanel(myObject);
		add(objectEditor);
		add(matrixEditor);
	}
	
	//String editing panel
	protected class ObjectEditPanel extends JPanel implements ValueUpdatable {
		/**
		 * UID
		 */
		private static final long serialVersionUID = 8182829943181488386L;

		JTextField parentText = new JTextField();
		JTextField meshText = new JTextField(20);
		JTextField materialText = new JTextField(20);

		public ObjectEditPanel() {
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			JLabel panelLabel = new JLabel("Mesh/Material Editing");
			panelLabel.setAlignmentX(CENTER_ALIGNMENT);
			this.add(panelLabel);
			
			ActionListener al = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String s = meshText.getText();
					if(s.length() < 1) s = null;
					if(!myObject.hasMesh(s)) {
						myObject.setMesh(s);
						scene.sendEvent(new SceneObjectResourceEvent(myObject, SceneObjectResourceEvent.Type.Mesh));
					}
					
					s = materialText.getText();
					if(s.length() < 1) s = null;
					if(!myObject.hasMaterial(s)) {
						myObject.setMaterial(s);
						scene.sendEvent(new SceneObjectResourceEvent(myObject, SceneObjectResourceEvent.Type.Material));
					}
				}
			};
			
			//Mesh Panel
			JPanel meshPanel = new JPanel();
			meshPanel.setLayout(new BoxLayout(meshPanel, BoxLayout.X_AXIS));
			JLabel meshPanelLabel = new JLabel("Mesh Name:");
			meshPanelLabel.setAlignmentX(RIGHT_ALIGNMENT);
			meshPanel.add(meshPanelLabel);
			meshText.setText(myObject.mesh);
			meshPanel.add(meshText);
			meshText.addActionListener(al);
			add(meshPanel);
			
			//Material Panel
			JPanel materialPanel = new JPanel();
			materialPanel.setLayout(new BoxLayout(materialPanel, BoxLayout.X_AXIS));
			JLabel materialPanelLabel = new JLabel("Material Name:");
			materialPanelLabel.setAlignmentX(RIGHT_ALIGNMENT);
			materialPanel.add(materialPanelLabel);
			materialText.setText(myObject.material);
			materialPanel.add(materialText);
			materialText.addActionListener(al);
			add(materialPanel);
			
			//Parent Panel
			JPanel parentPanel = new JPanel();
			parentPanel.setLayout(new BoxLayout(parentPanel, BoxLayout.X_AXIS));
			JLabel parentPanelLabel = new JLabel("Parent Name:");
			parentPanelLabel.setAlignmentX(RIGHT_ALIGNMENT);
			parentPanel.add(parentPanelLabel);
			parentText.setText(myObject.parent);
			parentPanel.add(parentText);
			parentText.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String s = parentText.getText();
					if(s.length() < 1) s = Scene.ROOT_NODE_NAME;
					if(!myObject.hasParent(s)) {
						myObject.setParent(s);
						scene.sendEvent(new SceneCollectionModifiedEvent(SceneDataType.Object, myObject.getID().name, false));
					}
				}
			});
			add(parentPanel);
		}

		@Override
		public void updateValues() {
			if(!meshText.isFocusOwner())
				meshText.setText(myObject.mesh);
			if(!materialText.isFocusOwner())
				materialText.setText(myObject.material);
			if(!parentText.isFocusOwner())
				parentText.setText(myObject.parent);
			if(!matrixEditor.isFocusOwner())
				matrixEditor.updateValues();
		}
	}

	protected class TransformationMatrixEditPanel extends JPanel implements ValueUpdatable {
		/**
		 * UID
		 */
		private static final long serialVersionUID = 1L;

		JFormattedTextField[][] fields = new JFormattedTextField[4][4];
		final SceneObject myObject;

		public TransformationMatrixEditPanel(SceneObject o) {
			myObject = o;

			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			JLabel panelLabel = new JLabel("Transformation Editing");
			panelLabel.setAlignmentX(CENTER_ALIGNMENT);
			this.add(panelLabel);
			//MATRIX UPDATE PANEL
			JPanel matrixUpdateFrame = new JPanel();
			matrixUpdateFrame.setLayout(new GridLayout(4,4));
			ActionListener al = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					boolean isChanged = false;
					for(int y = 0;y < 4;y++) {
						for(int x = 0;x < 4;x++) {
							float f = new Float(fields[y][x].getValue().toString());
							if(f != myObject.transformation.m[x * 4 + y]) {
								myObject.transformation.m[x * 4 + y] = f;
								isChanged = true;
							}
						}
					}
					if(isChanged) scene.sendEvent(new SceneTransformationEvent(myObject));
				}
			};
			for(int y = 0;y < 4;y++) {
				for(int x = 0;x < 4;x++) {
					JFormattedTextField curField = new JFormattedTextField(decimalFormat);
					if(y == 3) curField.setEditable(false);
					else curField.setEditable(true);

					curField.setColumns(6);
					curField.setValue(myObject.transformation.m[x * 4 + y]);
					fields[y][x] = curField;
					matrixUpdateFrame.add(curField);
					curField.addActionListener(al);
				}						
			}
			
			add(matrixUpdateFrame);
		}

		@Override
		public void updateValues() {
			for(int y = 0;y < 4;y++) {
				for(int x = 0;x < 4;x++) {
					JFormattedTextField curField = fields[y][x];
					if(!curField.isFocusOwner()) {
						curField.setValue(myObject.transformation.m[x * 4 + y]);
					}
				}						
			}
		}
	}

	@Override
	public void updateValues() {
		matrixEditor.updateValues();
		objectEditor.updateValues();
	}
}
