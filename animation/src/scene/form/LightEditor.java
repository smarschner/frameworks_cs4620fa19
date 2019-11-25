package scene.form;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import common.Scene;
import common.SceneLight;


/*
 * Light editor is a JPanel that supports editing of parameters for lights, which includes the
 * object's transformation matrix and color
 */
public class LightEditor extends ObjectEditor {
	/**
	 * UID
	 */
	private static final long serialVersionUID = -6996814537432621062L;

	SceneLight myLight;
	
	LightEditPanel myLightEditPanel;
	
	//This will be a boxlayout of JPanels
	//The first panel supports matrix editing
	//The second panel supports mesh/material string editing
	public LightEditor(Scene s, SceneLight lightIn) {
		super(s, lightIn);
		myLight = lightIn;
		myLightEditPanel = new LightEditPanel();
		add(myLightEditPanel);
	}
	
	//Light editing panel
	class LightEditPanel extends JPanel implements ValueUpdatable {
		/**
		 * UID
		 */
		private static final long serialVersionUID = -534967362076424185L;

		JFormattedTextField[] lightColorFields = new JFormattedTextField[3];

		public LightEditPanel() {
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			JLabel panelLabel = new JLabel("Light Editing");
			panelLabel.setAlignmentX(CENTER_ALIGNMENT);
			this.add(panelLabel);
			
			//LIGHT UPDATE PANEL
			JPanel lightUpdatePanel = new JPanel();
			lightUpdatePanel.setLayout(new BoxLayout(lightUpdatePanel, BoxLayout.X_AXIS));
			
			ActionListener al = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					for(int colorIndex = 0; colorIndex < 3; colorIndex++) {
						double l = ((Number)lightColorFields[colorIndex].getValue()).doubleValue();
						myLight.intensity.set(colorIndex, l);
					}
				}
			};
			
			for(int colorIndex = 0; colorIndex < 3; ++colorIndex) {
				JFormattedTextField curField = new JFormattedTextField(decimalFormat);
				curField.setColumns(6);
				curField.setValue(myLight.intensity.get(colorIndex));
				curField.addActionListener(al);
				lightColorFields[colorIndex] = curField;
				lightUpdatePanel.add(curField);
			}
			
			this.add(lightUpdatePanel);
		}

		@Override
		public void updateValues() {
			for(int colorIndex = 0; colorIndex < 3; ++colorIndex) {
				JFormattedTextField curField = lightColorFields[colorIndex];
				if(!curField.isFocusOwner())
					curField.setValue(myLight.intensity.get(colorIndex));
			}
		}
	}

	@Override
	public void updateValues() {
		super.updateValues();
		myLightEditPanel.updateValues();
	}
}
