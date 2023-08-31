// JCount.java

/*
 Basic GUI/Threading exercise.
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JCount extends JPanel {
	private Worker worker;
	private static final int MAX_NUMBER = 100000000;
	private static final int NUMBER_TO_PRINT = 10000;

	public JCount() {
		// Set the JCount to use Box layout
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JTextField field = new JTextField(MAX_NUMBER + "");
		add(field);
		JLabel label = new JLabel("0");
		add(label);
		JButton start = new JButton("Start");
		JButton stop = new JButton("Stop");
		add(start);
		add(stop);

		add(Box.createRigidArea(new Dimension(0,40)));

		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!field.getText().matches("\\d+")) {
					throw new IllegalArgumentException("String does not contain only numbers");
				} else {
					if(worker != null) worker.interrupt();

					int destNum = Integer.parseInt(field.getText());
					worker = new Worker(destNum, label);
					worker.start();
				}
			}
		});

		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(worker != null){
					worker.interrupt();
					worker = null;
				}
			}
		});
	}

	public static class Worker extends Thread {
		int destNum;
		JLabel label;

		Worker(int destNum, JLabel label){
			this.destNum = destNum;
			this.label = label;
		}

		@Override
		public void run(){
			for (int i = 0; i <= destNum; i ++){
				if(i % NUMBER_TO_PRINT == 0){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						break;
					}
					if(label != null) {
						label.setText(i + "");
					}
				}
			}
		}
	}

	private static void createAndShowGUI() {
		JFrame frame = new JFrame("The Count");
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

		frame.add(new JCount());
		frame.add(new JCount());
		frame.add(new JCount());
		frame.add(new JCount());

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}

