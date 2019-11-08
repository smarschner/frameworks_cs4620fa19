package splines.form;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ToleranceSliderPanel extends JPanel implements ChangeListener
{
	private static final long serialVersionUID = 3977294404597855026L;

	/** The GUI components */
	public JSlider slider;
	private JTextField toleranceTextField;
	private DecimalFormat df1 = new DecimalFormat("0.000");
	private float tolerance;

	// Configuration of the slider
	int numTicks = 500;
	float initialValue = 0.5f;
	float minValue = -1.5f;
	float maxValue = -0.25f;
	boolean log = true;

	public ToleranceSliderPanel(ChangeListener changeListener) {
		this(0.5f, -1.5f, -0.25f, changeListener);
	}
	
	public ToleranceSliderPanel(float minVal, float maxVal, ChangeListener changeListener) {
		this(0.5f, minVal, maxVal, changeListener);
	}
	
	public ToleranceSliderPanel(float initialVal, float minVal, float maxVal, ChangeListener changeListener) {
		super();
		
		this.initialValue = initialVal;
		this.minValue = minVal;
		this.maxValue = maxVal;

		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

		slider = new JSlider(JSlider.VERTICAL);
		slider.setMinorTickSpacing(10);
		slider.setMaximum(numTicks);
		slider.setMinimum(0);
		slider.setPaintTicks(true);
		slider.setValue((int)(numTicks * initialValue));
		slider.addChangeListener(this);
		slider.addChangeListener(changeListener);
		this.add(slider, BorderLayout.CENTER);

		toleranceTextField = new JTextField();
		toleranceTextField.setEditable(false);
		toleranceTextField.setText(df1.format(tolerance));
		toleranceTextField.setHorizontalAlignment(JTextField.RIGHT);

		JPanel southPanel = new JPanel(new GridLayout(1,1));
		southPanel.add(toleranceTextField);
		this.add(southPanel, BorderLayout.SOUTH);

		stateChanged(null);
	}

	public float getValue(JSlider source) {

		float value;
		value = source.getValue() / (float) numTicks;
		value = minValue + value * (maxValue - minValue);
		if (log)
			value = (float) Math.pow(10, value);
		return value;

	}

	public void stateChanged(ChangeEvent e) {
		tolerance = getValue(slider);
		toleranceTextField.setText(df1.format(tolerance));
		repaint();
	}

	public float getTolerance() {
		return tolerance;
	}
	
	/** DO NOT USE THIS FUNCTION.  Only for loading splines in the ControlFrame.
	 *  Behavior unchecked elsewhere!
	 * 
	 * @param newTol
	 */
	public void setTolerance(float value) {
		
		if (log)
			value = (float) Math.log10(value);
		value = (value - minValue) / (maxValue - minValue);
		slider.setValue((int) (value * numTicks));
		stateChanged(null);
	}

	public JSlider getSlider() {
		return slider;
	}
}
