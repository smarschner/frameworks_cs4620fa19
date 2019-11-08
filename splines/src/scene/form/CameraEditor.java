package scene.form;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import common.Scene;
import common.SceneCamera;
import common.event.SceneTransformationEvent;


/*
 * Camera Editor 
 */
public class CameraEditor extends ObjectEditor implements ValueUpdatable{
	/**
	 * UID
	 */
	private static final long serialVersionUID = 7940833711375708510L;


	SceneCamera myCamera;
	
	JFormattedTextField sizeXField = new JFormattedTextField(decimalFormat);
	JFormattedTextField sizeYField = new JFormattedTextField(decimalFormat);
	JFormattedTextField planeFField = new JFormattedTextField(decimalFormat);
	JFormattedTextField planeBField = new JFormattedTextField(decimalFormat);
    JCheckBox perspCheck = new JCheckBox("Perspective Transform?");

    CameraEditPanel myCameraEditPanel;
    
    
	//This will be a boxlayout of JPanels
	//The first panel supports matrix editing
	//The second panel supports mesh/material string editing
	public CameraEditor(Scene s, SceneCamera camIn) {
		super(s, camIn);
		
		myCamera = camIn;
		myCameraEditPanel = new CameraEditPanel();
		add(myCameraEditPanel);
	}
	
	//Camera editing panel
	class CameraEditPanel extends JPanel implements ValueUpdatable {
		/**
		 * UID
		 */
		private static final long serialVersionUID = 25286360420997240L;

		public CameraEditPanel() {
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			JLabel panelLabel = new JLabel("Camera Editing");
			panelLabel.setAlignmentX(CENTER_ALIGNMENT);
			this.add(panelLabel);
			
			ActionListener al = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					myCamera.isPerspective = perspCheck.isSelected();
					myCamera.imageSize.x = new Double(sizeXField.getValue().toString());
					myCamera.imageSize.y = new Double(sizeYField.getValue().toString());
					myCamera.zPlanes.x = new Double(planeFField.getValue().toString());
					myCamera.zPlanes.y = new Double(planeBField.getValue().toString());
					scene.sendEvent(new SceneTransformationEvent(myCamera));		
				}
			};
			
			//Perspective check panel
		    JPanel perspPanel = new JPanel();
		    perspCheck.setSelected(myCamera.isPerspective);
		    perspPanel.add(perspCheck);
		    perspCheck.addActionListener(al);
			
			//Image Size Panel
			JPanel sizePanel = new JPanel();
			sizePanel.setLayout(new BoxLayout(sizePanel, BoxLayout.X_AXIS));
			
			JLabel sizePlaneLabel = new JLabel("Image Size (x,y):");
			sizePlaneLabel.setAlignmentX(RIGHT_ALIGNMENT);
			sizePanel.add(sizePlaneLabel);
			
			sizeXField.setValue(myCamera.imageSize.x);
			sizePanel.add(sizeXField);
			sizeXField.addActionListener(al);
			sizeYField.setValue(myCamera.imageSize.y);
			sizePanel.add(sizeYField);
			sizeYField.addActionListener(al);
			
			//Planes Panel
			JPanel planePanel = new JPanel();
			planePanel.setLayout(new BoxLayout(planePanel, BoxLayout.X_AXIS));
			
			JLabel planePanelLabel = new JLabel("ZPlanes (f,b):");
			planePanelLabel.setAlignmentX(RIGHT_ALIGNMENT);
			planePanel.add(planePanelLabel);
			
			planeFField.setValue(myCamera.zPlanes.x);
			planePanel.add(planeFField);
			planeFField.addActionListener(al);
			planeBField.setValue(myCamera.zPlanes.y);
			planePanel.add(planeBField);
			planeBField.addActionListener(al);
			
			this.add(perspPanel);
			this.add(sizePanel);
			this.add(planePanel);
		}

		@Override
		public void updateValues() {
			if(!planeFField.isFocusOwner())
				planeFField.setValue(myCamera.zPlanes.x);
			if(!planeBField.isFocusOwner())
				planeBField.setValue(myCamera.zPlanes.y);
			if(!sizeXField.isFocusOwner())
				sizeXField.setValue(myCamera.imageSize.x);
			if(!sizeYField.isFocusOwner())
				sizeYField.setValue(myCamera.imageSize.y);
		}
	}
	
	@Override
	public void updateValues() {
		myCameraEditPanel.updateValues();
		matrixEditor.updateValues();
	}
}
