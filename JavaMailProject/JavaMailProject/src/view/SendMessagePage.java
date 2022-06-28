package view;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import controller.SendMessageAction;

public class SendMessagePage extends JFrame {

	private static final long serialVersionUID = 1L;

	public SendMessagePage() {

		setVisible(true);
		setTitle("Send Email");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 721, 493);
		setResizable(false);
		setLocationRelativeTo(null);
		pack();

		
		JPanel p = new JPanel();
		p.setBorder(new EmptyBorder(5, 5, 5, 5));
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.setBorder(new EmptyBorder(20, 40, 20, 40));

		JPanel radio = new JPanel();
		JRadioButton r1 = new JRadioButton(" Email addresses");
		JRadioButton r2 = new JRadioButton(" Mailing List");

		ButtonGroup bg = new ButtonGroup();
		bg.add(r1);
		bg.add(r2);
		radio.add(r1);
		radio.add(r2);
		p.add(radio);

		LabeledTextField to = new LabeledTextField("Send to", 30, 150);
		p.add(to);
		LabeledTextField sujet = new LabeledTextField("Sujet", 30, 150);
		p.add(sujet);
		

		JTextArea text = new JTextArea(10, 40);
		p.add(text);
		
		p.add(Box.createVerticalStrut(30));

		JButton send = new JButton("Send");
		send.addActionListener(new SendMessageAction(r1, r2, sujet, to, text));
		p.add(send);

		setContentPane(p);
		pack();
	}

}
