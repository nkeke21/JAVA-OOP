import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;


 public class SudokuFrame extends JFrame{
	 private JTextArea textAreaPuz;
	 private JTextArea textAreaSol;
	 private String result;

	 public SudokuFrame() {
		super("Sudoku Solver");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout(4, 4));
		setLocationByPlatform(true);

		textAreaPuz = new JTextArea(15, 20);
		textAreaPuz.setBorder(new TitledBorder("Puzzle"));

		textAreaSol = new JTextArea(15, 20);
		textAreaSol.setBorder(new TitledBorder("Solution"));
		textAreaSol.setEditable(false);

		add(textAreaPuz, BorderLayout.CENTER);
		add(textAreaSol, BorderLayout.EAST);

		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));

		JButton check = new JButton("Check");
		check.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 solve();
			 }
		 });

		JCheckBox autoCheck = new JCheckBox("Auto Check");
		autoCheck.setSelected(true);

		controlPanel.add(check);
		controlPanel.add(autoCheck);
		add(controlPanel, BorderLayout.SOUTH);

		textAreaPuz.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				if(autoCheck.isSelected()) solve();
			}

			public void insertUpdate(DocumentEvent e) {
				if(autoCheck.isSelected()) solve();
			}

			public void removeUpdate(DocumentEvent e) {
				if(autoCheck.isSelected())	solve();
			}
		});

		pack();
		setVisible(true);
	}

	private void solve(){
		try {
			Sudoku sudoku = new Sudoku(Sudoku.textToGrid(textAreaPuz.getText()));
			int solCount = sudoku.solve();
			if (solCount != 0) {
				result = sudoku.getSolutionText() + "solutions:" + solCount + '\n' + "elapsed:" + sudoku.getElapsed() + "ms\n";
			}
		} catch (Exception ex) {
			result = "Parsing problem";
		}
		textAreaSol.setText(result);
	}


	public static void main(String[] args) {
		// GUI Look And Feel
		// Do this incantation at the start of main() to tell Swing
		// to use the GUI LookAndFeel of the native platform. It's ok
		// to ignore the exception.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) { }

		SudokuFrame frame = new SudokuFrame();
	}
 }
