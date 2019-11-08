package scene.form;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import common.BasicType;

public class SimpleMeshWindow extends JDialog {
	/**
	 * UID
	 */
	private static final long serialVersionUID = 6139910861111146138L;
	private static final int DEFAULT_M = 10;
	private static final int DEFAULT_N = 10;

	private JComboBox<String> cmbMeshType;
	private JTextField mField;
	private JTextField nField;
	private JButton btnOK;

	boolean createdMesh;

	public class Result {
		public BasicType meshType;
		public int m;
		public int n;
		public String filename;
	}
	
	public SimpleMeshWindow(JFrame parent) {
		super(parent, "Simple Mesh", JDialog.ModalityType.DOCUMENT_MODAL);
		
		setSize(300, 600);
		setLayout(new BorderLayout());
		
		cmbMeshType = new JComboBox<>(new String[] {
				"Sphere",
				"Cube",
				"Cylinder"
		});
		
		mField = new JTextField(); nField = new JTextField();
		mField.setText(Integer.toString(DEFAULT_M));
		nField.setText(Integer.toString(DEFAULT_N));

		btnOK = new JButton("OK");
		btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				createdMesh = true;
				setVisible(false);
			}
		});
		

		JPanel pnlOptions = new JPanel();
		pnlOptions.setLayout(new GridLayout(3, 2));
		pnlOptions.add(new JLabel("m"));
		pnlOptions.add(mField);
		pnlOptions.add(new JLabel("n"));
		pnlOptions.add(nField);
		pnlOptions.add(new JLabel("Type"));
		pnlOptions.add(cmbMeshType);
		
		add(pnlOptions, BorderLayout.CENTER);
		add(btnOK, BorderLayout.SOUTH);

		pack();
		setResizable(false);
	}
	public Result getInput() {
		createdMesh = true;
		
		// Run The Window
		setVisible(true);
		
		if(!createdMesh) return null;
		Result res = new Result();
		switch((String)cmbMeshType.getSelectedItem()) {
		case "Sphere":
			res.meshType = BasicType.Sphere;
			break;
		case "Cube":
			res.meshType = BasicType.Cube;
			break;
		case "Cylinder":
			res.meshType = BasicType.Cylinder;
			break;
		default:
			return null;
		}
		try {
			res.m = Integer.parseInt(mField.getText());
			res.n = Integer.parseInt(nField.getText());
		}
		catch(NumberFormatException e) {
			mField.setText(Integer.toString(DEFAULT_M));
			nField.setText(Integer.toString(DEFAULT_N));
			return null;
		}

		return res;
	}
}
