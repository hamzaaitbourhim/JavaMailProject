package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LabeledTextField extends JPanel {

	private static final long serialVersionUID = 1L;

	public LabeledTextField(String label, int size) {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		add(new JLabel(label + " : "));
		add(new JTextField(size));
	}

	public LabeledTextField(String label, int size, int labelWidth) {
		this(label, size);
		((JLabel)this.getComponent(0)).setPreferredSize(new Dimension(labelWidth, 40));
	}

	public String getText() {
		return ((JTextField)this.getComponent(1)).getText();
	}
	
	public void setText(String text) {
		((JTextField)this.getComponent(1)).setText(text);
	}	
	
}
