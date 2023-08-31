import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JBrainTetris extends JTetris{
    /**
     * Creates a new JTetris where each tetris square
     * is drawn with the given number of pixels.
     *
     * @param pixels
     */
    private static DefaultBrain brain;
    private Brain.Move move;

    //    boolean
    private Piece brainPiece;
    private int brainX;
    private int brainY;
    private JCheckBox brainMode;
    private JCheckBox animate;
    private JSlider adversary;
    private JLabel ok;
    private int countP; // This variable will help us to detect when to call bestMove
    JBrainTetris(int pixels) {
        super(pixels);
        countP = 0;
        brainX = 0;
        brainY = 0;
        brainPiece = null;
        move = new Brain.Move();
        brain = new DefaultBrain();

    }
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }

        JBrainTetris tetris = new JBrainTetris(16);
        JFrame frame = JBrainTetris.createFrame(tetris);
        frame.setVisible(true);
    }

    @Override
    public JComponent createControlPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // COUNT
        countLabel = new JLabel("0");
        panel.add(countLabel);

        // SCORE
        scoreLabel = new JLabel("0");
        panel.add(scoreLabel);

        // TIME
        timeLabel = new JLabel(" ");
        panel.add(timeLabel);

        panel.add(Box.createVerticalStrut(12));

        // START button
        startButton = new JButton("Start");
        panel.add(startButton);
        startButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        // STOP button
        stopButton = new JButton("Stop");
        panel.add(stopButton);
        stopButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopGame();
            }
        });

        enableButtons();

        JPanel row = new JPanel();

        // SPEED slider
        panel.add(Box.createVerticalStrut(12));
        row.add(new JLabel("Speed:"));
        speed = new JSlider(0, 200, 75);	// min, max, current
        speed.setPreferredSize(new Dimension(100, 15));

        updateTimer();
        row.add(speed);

        panel.add(row);
        speed.addChangeListener( new ChangeListener() {
            // when the slider changes, sync the timer to its value
            public void stateChanged(ChangeEvent e) {
                updateTimer();
            }
        });

        testButton = new JCheckBox("Test sequence");
        panel.add(testButton);

        brainMode = new JCheckBox("Brain");
        panel.add(brainMode);

        animate = new JCheckBox("Animate Falling");
        animate.setSelected(true);
        panel.add(animate);

        panel.add(new JLabel("Adversary:"));
        adversary = new JSlider(0, 100, 0); // min, max, current
        adversary.setPreferredSize(new Dimension(100,15));
        panel.add(adversary);

        ok = new JLabel("ok");
        panel.add(ok);

        return panel;
    }

    private void enableButtons() {
        startButton.setEnabled(!gameOn);
        stopButton.setEnabled(gameOn);
    }

    /* Algorithm for adversary how to choose piece*/
    private Piece advChoose(){
        Piece result = null;
        double score = 0;
        Brain.Move moveAD = new Brain.Move();
        for (int i = 0; i < pieces.length; i ++){
            Piece tmp = pieces[i];
            moveAD = brain.bestMove(board, tmp, getHeight() - TOP_SPACE, moveAD);
            if(moveAD != null){
                if(moveAD.score > score){
                    score = moveAD.score;
                    result = pieces[i];
                }
            }
        }
        return result;
    }

    /* Picking next Piece*/
    @Override
    public Piece pickNextPiece() {
        int x = random.nextInt(99)+1;
        if(x >= adversary.getValue()){
            ok.setText("ok");
            return super.pickNextPiece();
        }
        ok.setText("*ok*");
        return advChoose();
    }

    /* Overriding tick method, because in this situation computer plays game, not us */
    @Override
    public void tick(int verb) {
        if (verb == DOWN && brainMode.isSelected()) {
            /* This method tells us if we need to call new bestMove */
            if (countP != super.count) {
                board.undo();
                countP = super.count;
                move = brain.bestMove(board, super.currentPiece, board.getHeight() - TOP_SPACE, move);
            }

            if (move != null) {
                /* Going through the pieces to reach dest */
                if (!move.piece.equals(currentPiece))
                    super.tick(ROTATE);
                /* moving part */
                moveX();
            }
        }
            super.tick(verb);
    }

    /* Detects X coordinate and do DROP operation if necessary */
    private void moveX() {
        if (move.x > currentX)
            super.tick(RIGHT);
        if (move.x < currentX) {
            super.tick(LEFT);
        } else if (!animate.isSelected()) {
            if (move.x == currentX && currentY > move.y)
                super.tick(DROP);
        }
    }

}
