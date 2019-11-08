package splines.form;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.lwjgl.opengl.Display;

import mesh.MeshData;
import mesh.OBJParser;
import splines.BSpline;
import splines.CatmullRom;
import splines.SplineCurve;
import splines.RevolutionApp;
import egl.math.Vector2;

public class RevolutionControlFrame  extends JFrame {
	private static final long serialVersionUID = -5641957456264997948L;

	private RevolutionApp owner;

	private JPanel splinePanel;// one panel to rule them all

	// Tolerance sliders
	private JPanel tolerancePanel;// container for all sliders
	private ToleranceChangeListener toleranceListener;
	private ToleranceSliderPanel leftTolerance, centerTolerance, rightTolerance;

	// lwsb
	private JPanel load_savePanel;
	private JButton load, save;
	private SaveLoadListener lwsbListen;
	
	// configurations
	private JPanel configPanel;
	private OptionsListener opts;
	
	private JCheckBox displayNormals;
	public static boolean drawNormals= true;
	
	private JCheckBox displayTangents;
	public static boolean drawTangents =true;
	
	private JCheckBox closeLeft;
	public static boolean leftClosed = true;
	
	private JCheckBox enableResize;
	private boolean resizeEnabled= true;
	
	private JCheckBox realTimeRender;
	public static boolean REAL_TIME= true;
	
	private JCheckBox useBSpline;
	private boolean usingBSpline = false;

	public RevolutionControlFrame(String title, RevolutionApp owner) {
		super(title);
		this.owner= owner;
		init_display();
	}

	private void init_display() {
		///////////////////////////////////////////////////////////////////////////////////////////////////////
		// setup tolerance sliders and panel
		///////////////////////////////////////////////////////////////////////////////////////////////////////
		toleranceListener= new ToleranceChangeListener();
		leftTolerance= new ToleranceSliderPanel(-1.95f, -0.25f, toleranceListener);
		centerTolerance = new ToleranceSliderPanel(-1.35f, -0.25f, toleranceListener);
		rightTolerance= new ToleranceSliderPanel(-1.35f, -0.05f, toleranceListener);
		toleranceListener.setPanels(leftTolerance, centerTolerance, rightTolerance);
		
		leftTolerance.setMinimumSize(RevolutionApp.tolSlideDim);
		rightTolerance.setMaximumSize(RevolutionApp.tolSlideDim);
		
		leftTolerance.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(new Color(150,0,150),
						                       1,
						                       true),
						                       "Left"));
		centerTolerance.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(new Color(150,0,150),
						                       1,
						                       true),
						                       "Right"));
		rightTolerance.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(new Color(150,0,150),
						                       1,
						                       true),
						                       "Scale"));
		
		tolerancePanel= new JPanel(new GridLayout(1,3));
		tolerancePanel.setPreferredSize(RevolutionApp.tolPanelDim);
		tolerancePanel.setMinimumSize(RevolutionApp.tolPanelDim);
		tolerancePanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(new Color(150,0,150),
						                       1,
						                       true),
						                       "Tolerance"));
		
		RevolutionSplineScreen.tol1= leftTolerance.getTolerance();
		
		tolerancePanel.add(leftTolerance);
		tolerancePanel.add(centerTolerance);
		tolerancePanel.add(rightTolerance);
		///////////////////////////////////////////////////////////////////////////////////////////////////////
		// setup lwsb panel
		///////////////////////////////////////////////////////////////////////////////////////////////////////
		load_savePanel= new JPanel(new GridLayout(2,1));
		load_savePanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(new Color(150,0,150),
						                       1,
						                       true),
						                       "Load/Save"));
		load= new JButton("Load");
		save= new JButton("Save Sweep Mesh");
		lwsbListen= new SaveLoadListener(this);
		load.addActionListener(lwsbListen);
		save.addActionListener(lwsbListen);
		load_savePanel.add(load);
		load_savePanel.add(save);
		load_savePanel.setPreferredSize(RevolutionApp.lwsbDim);
		load_savePanel.setMinimumSize(RevolutionApp.lwsbDim);
		///////////////////////////////////////////////////////////////////////////////////////////////////////
		// setup configurations panel
		///////////////////////////////////////////////////////////////////////////////////////////////////////
		configPanel= new JPanel(new GridLayout(6,1));
		configPanel.setPreferredSize(RevolutionApp.configDim);
		configPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(new Color(150,0,150),
						                       1,
						                       true),
						                       "Configurations..."));
		
		opts= new OptionsListener(this);
		
		displayNormals= new JCheckBox();
		displayNormals.setText("Display Normals");
		displayNormals.setSelected(drawNormals);
		displayNormals.addActionListener(opts);
		
		displayTangents= new JCheckBox();
		displayTangents.setText("Display Tangents");
		displayTangents.setSelected(drawTangents);
		displayTangents.addActionListener(opts);
		
		closeLeft= new JCheckBox();
		closeLeft.setText("Close Spline");
		closeLeft.setSelected(leftClosed);
		closeLeft.addActionListener(opts);
		
		enableResize= new JCheckBox();
		enableResize.setText("Enable Display Resize");
		enableResize.setSelected(resizeEnabled);
		enableResize.addActionListener(opts);
		
		realTimeRender= new JCheckBox();
		realTimeRender.setText("Render in Real Time");
		realTimeRender.setSelected(REAL_TIME);
		realTimeRender.addActionListener(opts);
		
		useBSpline = new JCheckBox();
		useBSpline.setText("Use BSpline");
		useBSpline.setSelected(usingBSpline);
		useBSpline.addActionListener(opts);
		
		configPanel.add(displayNormals);
		configPanel.add(displayTangents);
		configPanel.add(closeLeft);
		configPanel.add(enableResize);
		configPanel.add(realTimeRender);
		configPanel.add(useBSpline);
		///////////////////////////////////////////////////////////////////////////////////////////////////////
		// setup spline panel
		///////////////////////////////////////////////////////////////////////////////////////////////////////
		splinePanel= new JPanel();
		splinePanel.setLayout(new BoxLayout(splinePanel, BoxLayout.Y_AXIS));
		splinePanel.setPreferredSize(RevolutionApp.optionsDim);
		splinePanel.setMinimumSize(RevolutionApp.optionsDim);
		
		splinePanel.add(tolerancePanel);
		splinePanel.add(load_savePanel);
		splinePanel.add(configPanel);
		///////////////////////////////////////////////////////////////////////////////////////////////////////
		// add components to Frame and modify display
		///////////////////////////////////////////////////////////////////////////////////////////////////////
		this.setPreferredSize(RevolutionApp.optionsDim);
		this.setSize(RevolutionApp.optionsDim);
		this.setMinimumSize(RevolutionApp.optionsDim);
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.add(splinePanel);
		
		Display.setLocation(RevolutionApp.optionsDim.width, 0);
		Display.setResizable(true);
		
		this.pack();
		this.setVisible(true);
	}
	
	public float getSliceTolerance() {
		return centerTolerance.getTolerance();
	}
	
	public float getScale() {
		return rightTolerance.getTolerance();
	}

	private class ToleranceChangeListener implements ChangeListener {
		private ToleranceSliderPanel left, center, right;
		private boolean initialized= false;

		private void setPanels(ToleranceSliderPanel left,
				ToleranceSliderPanel center,
				ToleranceSliderPanel right) {
			this.left= left;
			this.center = center;
			this.right= right;
			// if any of them are null, none will update.  use this class only as specified.
			initialized= left != null && right != null;
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			if(!initialized) return;
			JSlider src= (JSlider)e.getSource();
			if (!src.getValueIsAdjusting()) {
				if(src.getParent() == left) {
					float newVal = left.getTolerance();
					RevolutionSplineScreen.tol1 = newVal;
					((RevolutionTwoDimSplinePanel) RevolutionSplineScreen.panels[0]).spline.modifyEpsilon(newVal);
					if(REAL_TIME)
						owner.scrView.newRevolution();
				}
				else if(src.getParent() == center) {
					owner.scrView.generator.setSliceTolerance(center.getTolerance());
					if(REAL_TIME)
						owner.scrView.newRevolution();
				}
				else if(src.getParent() == right){
					owner.scrView.generator.setScale(right.getTolerance());
					if(REAL_TIME)
						owner.scrView.newRevolution();
				}
			}
		}

	}
	
	private class SaveLoadListener implements ActionListener {
		private RevolutionControlFrame owner;
		
		public SaveLoadListener(RevolutionControlFrame owner) {
			this.owner= owner;
		}
		
		@SuppressWarnings("resource")
		@Override
		public void actionPerformed(ActionEvent e) {
			if(owner == null) return;
			Object src= e.getSource();
			if(!(src instanceof JButton)) return;
			if(src == load) {
				try {
					FileDialog fd = new FileDialog(owner);
					fd.setVisible(true);
					for(File f : fd.getFiles()) {
						String file = f.getAbsolutePath();
						BufferedReader br = new BufferedReader(new FileReader(file));
						String line = null;
						
						ArrayList<Vector2> splineCP = new ArrayList<Vector2>();
						boolean splineClosed= true;
						float splineEpsilon= 0.0f;
						
						float scale= 0.0f;
						float sliceEpsilon = 0.0f;
						boolean isBSpline = true;
						
						boolean next_line_configs= false;
						boolean configs_done= false;
						boolean next_lines_cross_section= false;
						/* finished is  fail-safe:
						 *   - 0 for none
						 *   - 1 for configs parsed
						 *   - 2 for configs and cross-section
						 */
						int finished= 0;// fail-safe: 0 for none, 1 for configs, 
						while ((line = br.readLine()) != null) {
							if(line.charAt(0) != '#') {
								finished++;
								break;// done parsing all comments
							}
							if(next_line_configs) {
								String[] configs= line.split(" ");
								splineClosed = configs[1].equals("true");
								try {
									splineEpsilon= Float.parseFloat(configs[2]);
								} catch(Exception nf) {
									System.err.println("Could not parse cross-section epsilon value.");
									return;
								}
								isBSpline = configs[3].equals("true");
								try {
									scale= Float.parseFloat(configs[4]);
								} catch(Exception nf) {
									System.err.println("Could not parse the scale of the cross-section.");
									return;
								}
								try {
									sliceEpsilon = Float.parseFloat(configs[5]);
								} catch(Exception nf) {
									System.err.println("Could not parse slice epsilon value.");
									return;
								}
								next_line_configs= false;
								configs_done= true;
								finished++;
								continue;
							}

							if(line.contains("Configs") && !next_line_configs) {
								next_line_configs= true;
								continue;
							}
							
							if(line.contains("Cross-section") && configs_done) {
								next_lines_cross_section= true;
								continue;
							}
						   if(line.length() < 3 && next_lines_cross_section) {
							   next_lines_cross_section= false;
							   finished++;
							   continue;
						   }
						   
						   // control points!
						   if(next_lines_cross_section) {
							   Vector2 curPoint = new Vector2();
							   String[] tokens = line.substring(2).split(" ");
							   try {
								   curPoint.x = Float.parseFloat(tokens[0]);
								   curPoint.y = Float.parseFloat(tokens[1]);
							   } catch(Exception nf) {
								   System.err.println("Could not parse a control point; line that was a problem: "+line);
								   return;
							   }
							   if(next_lines_cross_section)
								   splineCP.add(curPoint);
						   }
						}
						
						if(finished == 2) {
							owner.leftTolerance.setTolerance(splineEpsilon);
							RevolutionSplineScreen.tol1= splineEpsilon;
							
							owner.centerTolerance.setTolerance(sliceEpsilon);
							owner.owner.scrView.generator.setSliceTolerance(sliceEpsilon);
							
							owner.rightTolerance.setTolerance(scale);
							owner.owner.scrView.generator.setScale(scale);
							
							leftClosed= splineClosed;
							owner.closeLeft.setSelected(splineClosed);
							
							owner.useBSpline.setSelected(isBSpline);
							
							owner.owner.leftPoints = splineCP;
							if (isBSpline) {
								((RevolutionTwoDimSplinePanel)RevolutionSplineScreen.panels[0]).spline= new BSpline(splineCP, splineClosed, splineEpsilon);
							} else {
								((RevolutionTwoDimSplinePanel)RevolutionSplineScreen.panels[0]).spline= new CatmullRom(splineCP, splineClosed, splineEpsilon);
							}
							
							owner.owner.scrView.generator.setSplineToRevolve(((RevolutionTwoDimSplinePanel)RevolutionSplineScreen.panels[0]).spline);
							owner.owner.scrView.newRevolution();
						} else {
							System.err.println("Could not load all data, please make sure you are loading a mesh generated with this app and try again.");
						}
						
					}
				}
				catch (Exception e1) {
					e1.printStackTrace();
					System.err.println("Could not load all data, please make sure you are loading a mesh generated with this app and try again.");
				}
			}
			else if(src == save) {
				JFileChooser fileChooser = new JFileChooser();
				if (fileChooser.showSaveDialog(owner) == JFileChooser.APPROVE_OPTION) {
				  File file = fileChooser.getSelectedFile();
				  if (!file.exists()) {
						try {
							file.createNewFile();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
				  }
				  FileWriter fw = null;
				try {
					fw = new FileWriter(file.getAbsoluteFile());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				  PrintWriter pw = new PrintWriter(fw);
				  try {
					  MeshData myData = new MeshData();
					  owner.owner.scrView.generator.generate(myData, null);
					  // preliminary information
					  pw.write("# This mesh is a Surface of Revolution: the comments below describe a Spline (cross-section) swept along\n");
					  pw.write("# an axis that generated this mesh.\n");
					  pw.write("#\n");
					  pw.write("# Configs: closed, epsilon, isBSpline, Scale, sliceTolerance\n");
					  pw.write("# "+((RevolutionTwoDimSplinePanel) RevolutionSplineScreen.panels[0]).spline.isClosed() + " " + 
						  		((RevolutionTwoDimSplinePanel) RevolutionSplineScreen.panels[0]).spline.getEpsilon() + " " +
						  		(((RevolutionTwoDimSplinePanel) RevolutionSplineScreen.panels[0]).spline instanceof BSpline) + " " + 
						  		owner.rightTolerance.getTolerance() + " " + 
						  		owner.getSliceTolerance() + "\n");
					  
					  // cross-section
					  pw.write("#\n");
					  pw.write("# Cross-section Spline control points: \n");
					  for(Vector2 v : ((RevolutionTwoDimSplinePanel) RevolutionSplineScreen.panels[0]).spline.getControlPoints())
					  {
						  pw.write("# " + v.x + " " + v.y + "\n");
					  }
					  
					  OBJParser.write(pw, OBJParser.convert(myData));
					  
					  pw.close();
					  
					  
				  }
				  catch(Exception e1) {
					  e1.printStackTrace();
				  }
				}
			}
		}
		
	}


	private class OptionsListener implements ActionListener {
		private RevolutionControlFrame owner;
		
		public OptionsListener(RevolutionControlFrame owner) {
			this.owner= owner;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Object src= e.getSource();
			if(!(src instanceof JCheckBox)) return;
			
			JCheckBox jb= (JCheckBox) src;
			
			if(jb == displayNormals) {
				drawNormals= jb.isSelected();
			} else if(jb == displayTangents) {
				drawTangents= jb.isSelected();
			} else if(jb == closeLeft) {
				leftClosed= jb.isSelected();
				boolean success= ((RevolutionTwoDimSplinePanel) RevolutionSplineScreen.panels[0]).spline.setClosed(leftClosed);
				if(!success) {
					jb.setSelected(!leftClosed);
					leftClosed= !leftClosed;
				} else {
					owner.owner.scrView.newRevolution();
				}
			} else if(jb == enableResize) {
				resizeEnabled= jb.isSelected();
				Display.setResizable(resizeEnabled);
			} else if(jb == realTimeRender) {
				REAL_TIME= realTimeRender.isSelected();
				if(!REAL_TIME)
					JOptionPane.showMessageDialog(owner, "Hit the P key to render the sweep mesh.");
			} else if (jb == useBSpline) {
				usingBSpline = useBSpline.isSelected();
				if (usingBSpline) {
					SplineCurve oldSpline = ((RevolutionTwoDimSplinePanel) RevolutionSplineScreen.panels[0]).spline;
					BSpline newSpline = new BSpline(oldSpline.getControlPoints(), oldSpline.isClosed(), oldSpline.getEpsilon());
					((RevolutionTwoDimSplinePanel) RevolutionSplineScreen.panels[0]).spline = newSpline;
				} else {
					SplineCurve oldSpline = ((RevolutionTwoDimSplinePanel) RevolutionSplineScreen.panels[0]).spline;
					CatmullRom newSpline = new CatmullRom(oldSpline.getControlPoints(), oldSpline.isClosed(), oldSpline.getEpsilon());
					((RevolutionTwoDimSplinePanel) RevolutionSplineScreen.panels[0]).spline = newSpline;
				}
				owner.owner.scrView.generator.setSplineToRevolve(((RevolutionTwoDimSplinePanel)RevolutionSplineScreen.panels[0]).spline);
				owner.owner.scrView.newRevolution();
			}
		}
	}
}
