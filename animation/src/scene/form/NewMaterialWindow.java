package scene.form;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import common.Material;
import common.Scene.NameBindMaterial;
import egl.math.Color;


public class NewMaterialWindow extends JDialog {
	/**
	 * UID
	 */
	private static final long serialVersionUID = 6287518700753033832L;

	boolean createdMaterial = false;
	private JButton btnOK = new JButton("OK");
	private JTextField fieldName = new JTextField();
	private JTextField fieldType = new JTextField();
	private InputProviderPanel[] fieldInputs = new InputProviderPanel[3];
	
	public NewMaterialWindow(JFrame parent) {
		super(parent, "New Material", JDialog.ModalityType.DOCUMENT_MODAL);
		setSize(400, 280);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		add(new JLabel("Name:"));
		add(fieldName);
		fieldName.setText("Material");
		add(new JLabel("Type:"));
		add(fieldType);
		fieldType.setText(Material.T_AMBIENT);
		add(new JLabel("Diffuse Input:"));
		Material m = new Material();
		add(fieldInputs[0] = new InputProviderPanel(m.inputDiffuse.color));
		add(new JLabel("Normal Input:"));
		add(fieldInputs[1] = new InputProviderPanel(m.inputNormal.color));
		add(new JLabel("Specular Input:"));
		add(fieldInputs[2] = new InputProviderPanel(m.inputSpecular.color));
		add(btnOK);
		btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				createdMaterial = true;
				setVisible(false);
			}
		});
		
		setResizable(false);
	}

	
	public NameBindMaterial getInput() {
		createdMaterial = false;

		// Run The Window
		setVisible(true);
		
		
		if(!createdMaterial) return null;
		
		Material m = new Material();
		m.setType(fieldType.getText());
		m.setDiffuse(fieldInputs[0].getData());
		m.setNormal(fieldInputs[1].getData());
		m.setSpecular(fieldInputs[2].getData());
		
		NameBindMaterial res = new NameBindMaterial();
		res.setName(fieldName.getText());
		res.setData(m);
		return res;
	}
	
	public class InputProviderPanel extends JPanel {
		/**
		 * UID
		 */
		private static final long serialVersionUID = 4930157124852647614L;

		private JFormattedTextField[] colorFields = new JFormattedTextField[4];
		private JTextField textureField = new JTextField();
		private JPanel dataPanel = new JPanel();
		private CardLayout cl = new CardLayout();
		private JCheckBox checkUseTexture = new JCheckBox();
		
		public InputProviderPanel(Color def) {
			checkUseTexture.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					cl.show(dataPanel, checkUseTexture.isSelected() ? "Texture" : "Color");
				}
			});
			
			JPanel colorPanel = new JPanel();
			colorPanel.setLayout(new BoxLayout(colorPanel, BoxLayout.X_AXIS));
			for(int i = 0;i < 4;i++) {
				colorFields[i] = new JFormattedTextField(NumberFormat.getIntegerInstance(getDefaultLocale()));
				colorPanel.add(colorFields[i]);
			}
			colorFields[0].setText(Integer.toString(def.r()));
			colorFields[1].setText(Integer.toString(def.g()));
			colorFields[2].setText(Integer.toString(def.r()));
			colorFields[3].setText(Integer.toString(def.a()));
			textureField.setText("Checker Board");
			
			dataPanel.setLayout(cl);
			dataPanel.add(textureField, "Texture");
			dataPanel.add(colorPanel, "Color");
			cl.show(dataPanel, "Color");
			
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			add(new JLabel("Use Texture:"));
			add(checkUseTexture);
			add(dataPanel);
		}
		
		public Material.InputProvider getData() {
			Material.InputProvider ip = new Material.InputProvider();
			if(checkUseTexture.isSelected()) {
				ip.setTexture(textureField.getText());
			}
			else {
				ip.setColor(new Color(
						new Integer(colorFields[0].getText()),
						new Integer(colorFields[1].getText()),
						new Integer(colorFields[2].getText()),
						new Integer(colorFields[3].getText())
						));
			}
			return ip;
		}
	}

}
