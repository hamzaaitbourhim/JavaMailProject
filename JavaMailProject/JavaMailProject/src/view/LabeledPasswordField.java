package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;


public class LabeledPasswordField extends JPanel {

	
	private static final long serialVersionUID = 1L;

	public LabeledPasswordField(String label, int size) {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		add(new JLabel(label + " : "));
		add(new JPasswordField(size));

	}

	public LabeledPasswordField(String label, int size, int labelWidth) {
		this(label, size);
		((JLabel)this.getComponent(0)).setPreferredSize(new Dimension(labelWidth, 40));
	}

	public String getText() {
		return new String(((JPasswordField)this.getComponent(1)).getPassword());
		
	}
	
}
