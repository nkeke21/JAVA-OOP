import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.Semaphore;


public class WebFrame extends JFrame {
    private static final int MAX_WIDTH_FIELD = 50;
    private static final int MAX_HEIGHT_FIELD = 20;
    private static final int SINGLE_WORKER = 1;
    private Vector<String> urls;
    private Vector<WebWorker> workers;
    private launcher myLauncher;
    private DefaultTableModel model;
    private int run;
    private int compl;
    private long startTime;
    private JTable table;
    private JScrollPane scroll;
    private String Running = "Running :";
    private String Completed = "Completed :";
    private String Elapsed = "Elapsed :";

    private JLabel running;
    private JLabel completed;
    private JLabel elapsed;
    private JProgressBar progressBar;
    private JButton Single_Thread_Fetch;
    private JButton Concurrent_Fetch;
    private JButton stop;
    private Semaphore semaphore;
    private static final String pattern = "\\d+";

    public WebFrame(){
        super("WebLoader");
        String fileName = "links.txt";
        urls = new Vector<>();
        workers = new Vector<>();
        run = 0;
        compl = 0;

        model = new DefaultTableModel(new String[] { "url", "status"}, 0);
        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(600,300));
        panel.add(scroll);
        add(panel);

        try {
            readingFile(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JPanel fetches = new JPanel(new BorderLayout());
        fetches.setLayout(new BoxLayout(fetches, BoxLayout.Y_AXIS));

        Single_Thread_Fetch = new JButton("Single Thread Fetch");
        Concurrent_Fetch = new JButton("Concurrent Fetch");

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;

        fetches.add(Single_Thread_Fetch, constraints);
        fetches.add(Concurrent_Fetch, constraints);
        panel.add(fetches);

        JPanel fields_labels = new JPanel(new BorderLayout());
        fields_labels.setLayout(new BoxLayout(fields_labels, BoxLayout.Y_AXIS));
        JTextField textField = new JTextField();
        textField.setMaximumSize(new Dimension(MAX_WIDTH_FIELD, MAX_HEIGHT_FIELD));

        running = new JLabel(Running + "0");
        completed = new JLabel(Completed + "0");
        elapsed = new JLabel(Elapsed + "0");
        fields_labels.add(textField);
        fields_labels.add(running);
        fields_labels.add(completed);
        fields_labels.add(elapsed);
        panel.add(fields_labels);

        JPanel bar_Button = new JPanel();
        bar_Button.setLayout(new BoxLayout(bar_Button, BoxLayout.Y_AXIS));
        stop = new JButton("Stop");
        stop.setEnabled(false);
        progressBar = new JProgressBar(0, urls.size());
        progressBar.setValue(0);
        bar_Button.add(progressBar);
        bar_Button.add(stop);
        panel.add(bar_Button);

        Single_Thread_Fetch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
                run = compl = 0;
                updateButtonsLaunch();
                myLauncher = new launcher(SINGLE_WORKER);
                startTime = System.currentTimeMillis();
                myLauncher.start();
            }
        });

        Concurrent_Fetch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textField.getText().matches(pattern) && textField.getText().length() != 0) {
                    reset();
                    run = compl = 0;
                    updateButtonsLaunch();
                    int numThreads = Integer.parseInt(textField.getText());
                    myLauncher = new launcher(numThreads);
                    startTime = System.currentTimeMillis();
                    myLauncher.start();
                }
            }
        });

        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(myLauncher != null){
                    myLauncher.interrupt();
                    for(int i = 0; i < workers.size(); i ++){
                        workers.get(i).interrupt();
                    }
                    reset();
                    updateButtonsStop();
                }
            }
        });


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
    private void updateButtonsLaunch(){
        Single_Thread_Fetch.setEnabled(false);
        Concurrent_Fetch.setEnabled(false);
        stop.setEnabled(true);
        for (int i = 0; i < urls.size(); i ++){
            model.setValueAt("", i, 1);
        }
    }
    private void updateButtonsStop(){
        Single_Thread_Fetch.setEnabled(true);
        Concurrent_Fetch.setEnabled(true);
        stop.setEnabled(false);
    }

    private void reset(){
        running.setText(Running + "0");
        completed.setText(Completed + "0");
        elapsed.setText(Elapsed + "0");
        workers.clear();
        progressBar.setValue(0);
    }
    public synchronized void incrementRun(){
        run++;
    }
    public synchronized void decrementRun(){
        run--;
    }
    public synchronized void incrementCompl(){
        compl++;
    }
    private void readingFile(String fileName) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(fileName));
        String[] dataArray = new String[2];
        String line;

        while ((line = bf.readLine()) != null){
            dataArray[0] = line;
            dataArray[1] = "";
            model.addRow(dataArray);
            urls.add(line);
        }

    }
    public void stopWorking(int index, String stat){
        model.setValueAt(stat, index, 1);
        decrementRun();
        incrementCompl();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                running.setText(Running + run);
                completed.setText(Completed + compl);
                progressBar.setValue(compl);
            }
        });

        checkForFinish();

        semaphore.release();
    }

    private void checkForFinish(){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (run == 0) {
                    long timeElapsed = System.currentTimeMillis() - startTime;
                    updateButtonsStop();
                    elapsed.setText("Elapsed:" + timeElapsed);
                }
            }
        });
    }

    public class launcher extends Thread {
        private WebFrame frame;
        private int numThreads;

        public launcher(int numThreads) {
            this.frame = WebFrame.this;
            this.numThreads = numThreads;
            run = 1;
        }

        @Override
        public void run() {
            semaphore = new Semaphore(numThreads);
            for (int i = 0; i < urls.size(); i++) {

                try {
                    semaphore.acquire();
                    if (isInterrupted()) throw new InterruptedException();
                    WebWorker worker = new WebWorker(urls.get(i), i, frame, semaphore);
                    workers.add(worker);
                    worker.start();

                } catch (InterruptedException e) {
                    for (int j = 0; j < workers.size(); j++) workers.get(j).interrupt();
                    break;
                }

            }
            decrementRun();
            checkForFinish();
        }
    }

    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        new WebFrame();
    }
}
