import org.apache.commons.dbcp2.BasicDataSource;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.SimpleTimeZone;

public class SwingVeiw  extends JFrame {
    private final int SENTINEL = 5;
    private final int COMBOW = 100;
    private final int COMBOH = 50;
    private final int WIDTH = 500;
    private final int HEIGHT = 300;
    private final String POP_GEQ = "Population Greater Than or Equal To";
    private final String POP_S = "Population Smaller Than";
    private final String eMatch = "Exact Match";
    private final String pMatch = "Partial Match";
    private dataTableModel model;

    public SwingVeiw(BasicDataSource dataSource) {
        model = new dataTableModel(dataSource);
        JTable table = new JTable(model);

        /* Next Line are for GUI design */
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);
        tablePanel.add(table, BorderLayout.CENTER);

        /* Table should be scrollable, because it may have much information */
        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        add(scrollpane, BorderLayout.CENTER);

        JPanel createPanel = new JPanel();
        createPanel.setLayout(new BoxLayout(createPanel, BoxLayout.X_AXIS));
        add(createPanel, BorderLayout.NORTH);

        /* TextFields */
        JLabel label1 = new JLabel("Metropolis:");
        JTextField metropolis = new JTextField("");
        JLabel label2 = new JLabel("Continent:");
        JTextField continent = new JTextField("");
        JLabel label3 = new JLabel("Population:");
        JTextField population = new JTextField("");

        createPanel.add(label1);
        createPanel.add(metropolis);
        createPanel.add(label2);
        createPanel.add(continent);
        createPanel.add(label3);
        createPanel.add(population);

        // Set main panel as content pane

        // Set window size and center it
        JPanel east = new JPanel(new BorderLayout());
        add(east, BorderLayout.EAST);

        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        east.add(buttonPanel, BorderLayout.NORTH);

        JButton add = new JButton("Add     ");
        add.setAlignmentX(Component.CENTER_ALIGNMENT);
        add.setAlignmentY(Component.CENTER_ALIGNMENT);

        /* Adding Buttons to buttonPannel */
        JButton search = new JButton("Search");
        search.setAlignmentX(Component.CENTER_ALIGNMENT);
        search.setAlignmentY(Component.CENTER_ALIGNMENT);
        buttonPanel.add(add);
        buttonPanel.add(Box.createVerticalStrut(SENTINEL));
        buttonPanel.add(search);

        /* Adding comboBoxes */
        JPanel searchP = new JPanel(new BorderLayout());
        searchP.setBorder(new TitledBorder("Search Options"));
        searchP.setLayout(new BoxLayout(searchP, BoxLayout.Y_AXIS));
        east.add(searchP, BorderLayout.CENTER);

        JComboBox<String> pPulldown = new JComboBox(new String[]{POP_GEQ, POP_S});
        JComboBox<String> mPulldown = new JComboBox(new String[]{eMatch, pMatch});
        pPulldown.setMaximumSize(new Dimension(COMBOW, COMBOH));
        mPulldown.setMaximumSize(new Dimension(COMBOW, COMBOH));

        searchP.add(pPulldown);
        searchP.add(mPulldown);

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Add");
                myData newData = new myData();
                newData.setMetropolis(metropolis.getText());
                newData.setContinent(continent.getText());
                newData.setPopulation(Integer.parseInt(population.getText()));
                model.setData(model.addData(newData));
            }
        });

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String met = metropolis.getText();
                String con = continent.getText();
                String pop = population.getText();
                boolean smaller = pPulldown.getSelectedItem().equals(POP_S);
                boolean exact = mPulldown.getSelectedItem().equals(eMatch);
                model.setData(model.search(met, con, pop, smaller, exact));
            }
        });

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public static void main(String[] args){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/metropolises");
        dataSource.setUsername("root");
        dataSource.setPassword("******");
        SwingVeiw view = new SwingVeiw(dataSource);
    }
}