package scene.form;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import common.Scene;

public abstract class ResourcePanel extends JPanel {
	/**
	 * UID
	 */
	private static final long serialVersionUID = -6994264258114347674L;

	public static final int MAX_NAME_COPIES = 100;
	
	protected DefaultListModel<String> dataModel;
	protected JFrame frame;
	protected JList<String> data;
	protected JScrollPane listScroller;
	protected JPanel pnlButtons;
	protected JButton btnLoad, btnDelete;
	protected JPanel southPanel;

	protected Scene scene;
	
	public ResourcePanel(JFrame parentFrame) {
		frame = parentFrame;
		
		initComponents();
	}
	
	public void initComponents() {
		setLayout(new BorderLayout());
		
		dataModel = new DefaultListModel<>();
		
		data = new JList<String>(dataModel);
		data.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		data.setLayoutOrientation(JList.VERTICAL);
		data.setVisibleRowCount(-1);

		listScroller = new JScrollPane(data, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		listScroller.setPreferredSize(new Dimension(200, 80));
		listScroller.setEnabled(true);
		
		add(listScroller, BorderLayout.CENTER);
		
		southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
		
		pnlButtons = new JPanel();
		pnlButtons.setLayout(new GridLayout(3, 2));
		
		btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				FileDialog dialog = new FileDialog(frame);
				dialog.setVisible(true);
				for(File f : dialog.getFiles()) {
					String file = f.getAbsolutePath();
					if(file != null) onFileLoad(file);					
				}
			}
		});
		pnlButtons.add(btnLoad);

		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<String> l = data.getSelectedValuesList();
				if(l == null || l.size() < 1) return;
				for(String elem : l) {
					onDeletion(elem);
				}
			}
		});
		pnlButtons.add(btnDelete);
		
		southPanel.add(pnlButtons);
		add(southPanel, BorderLayout.SOUTH);
	}

	public void setScene(Scene s) {
		removeAll();
		scene = s;
	}

	public String getUniqueName(String name) {
		// Check For Existing Data
		if(dataModel.contains(name)) {
			// Attempt To Make A Name
			String newName;
			int i = 1;
			while(i <= MAX_NAME_COPIES) {
				newName = name + " (" + i + ")";
				if(!dataModel.contains(newName)) {
					name = newName;
					break;
				}
				i++;
			}
			// Couldn't Find A Suitable Name
			if(i > MAX_NAME_COPIES) return null;
		}
		return name;
	}
	
	public void addData(String name) {
		dataModel.addElement(name);
		data.repaint();
		listScroller.repaint();
	}
	public boolean tryAddUniqueData(String name) {
		name = getUniqueName(name);
		if(name == null) return false;
		addData(name);
		return true;
	}
	public void removeData(String name) {
		dataModel.removeElement(name);
		data.repaint();
		listScroller.repaint();
	}
	public void removeAll() {
		dataModel.removeAllElements();
		data.repaint();
		listScroller.repaint();
	}
	
	public abstract void onDeletion(String name);
	public abstract void onFileLoad(String file);
}
