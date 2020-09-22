package life;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GameOfLife extends JFrame {
    private static final String WINDOW_TITLE = "Game of life";
    private static final String GENERATION_LABEL_NAME = "GenerationLabel";
    private static final String ALIVE_LABEL_NAME = "AliveLabel";
    private static final String PLAY_TOGGLE_BUTTON_NAME = "PlayToggleButton";
    private static final String RESET_BUTTON_NAME = "ResetButton";
    private static final String GENERATION_LABEL_PREFIX = "Generation #";
    private static final String ALIVE_COUNT_LABEL_PREFIX = "Alive : ";
    private static final String PAUSE_BUTTON_TEXT = "Pause";
    private static final String RESUME_BUTTON_TEXT = "Resume";
    private static final String RESET_BUTTON_TEXT = "Reset";
    private JButton pauseToggleButton;
    private JButton resetButton;
    private JLabel generationLabel;
    private JLabel aliveCountLabel;
    private DrawPanel drawPanel;
    private Model model = new Model(Universe.generate(20));

    public GameOfLife() {
        super(WINDOW_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(300, 300);
        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        final JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        final JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        pauseToggleButton = new JButton(PAUSE_BUTTON_TEXT);
        pauseToggleButton.setName(PLAY_TOGGLE_BUTTON_NAME);
        buttonsPanel.add(pauseToggleButton);
        resetButton = new JButton(RESET_BUTTON_TEXT);
        resetButton.setName(RESET_BUTTON_NAME);
        buttonsPanel.add(resetButton);
        settingsPanel.add(buttonsPanel);
        generationLabel = new JLabel();
        generationLabel.setName(GENERATION_LABEL_NAME);
        settingsPanel.add(generationLabel);
        aliveCountLabel = new JLabel();
        aliveCountLabel.setVerticalAlignment(SwingConstants.TOP);
        aliveCountLabel.setName(ALIVE_LABEL_NAME);
        settingsPanel.add(aliveCountLabel);
        drawPanel = new DrawPanel();
        mainPanel.add(settingsPanel);
        mainPanel.add(drawPanel);
        add(mainPanel);
        updateComponents();
        setVisible(true);
    }

    public void addPauseToggleButtonListener(ActionListener listener) {
        pauseToggleButton.addActionListener(listener);
    }

    public void addResetButtonListener(ActionListener listener) {
        resetButton.addActionListener(listener);
    }

    private static class DrawPanel extends JPanel {
        private Universe universe;

        public void setUniverse(Universe universe) {
            this.universe = universe;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            doPaint(g);
        }

        private void doPaint(Graphics g) {
            final Graphics2D g2d = (Graphics2D) g;
            final Dimension size = getSize();
            final Insets insets = getInsets();
            final int w = size.width - insets.left - insets.right;
            final int h = size.height - insets.top - insets.bottom;

            final int universeSize = universe.getSize();
            final int margin = 5;
            final int stepX = (w - margin * 2) / universeSize;
            final int stepY = (h - margin * 2) / universeSize;
            final int startX = margin;
            final int endX = startX + stepX * universeSize;
            final int startY = margin;
            final int endY = startY + stepY * universeSize;
            final FieldState[][] fields = universe.getFields();
            if (stepX <= 0 || stepY <= 0) {
                return;
            }
            g2d.setColor(Color.black);
            for (int y = startY, i = 0; y < endY && i < universeSize; y += stepY, i++) {
                for (int x = startX, j = 0; x < endX && j < universeSize; x += stepX, j++) {
                    final FieldState fieldState = fields[i][j];
                    g2d.drawRect(x, y, stepX, stepY);
                    if (fieldState == FieldState.ALIVE) {
                        g2d.fillRect(x, y, stepX, stepY);
                    } else {
                        g2d.clearRect(x, y, stepX, stepY);
                    }
                }
            }
        }
    }

    public void togglePauseButton() {
        setIgnoreRepaint(true);
        if (pauseToggleButton.getText().equals(PAUSE_BUTTON_TEXT)) {
            pauseToggleButton.setText(RESUME_BUTTON_TEXT);
        } else {
            pauseToggleButton.setText(PAUSE_BUTTON_TEXT);
        }
        setIgnoreRepaint(false);
        pauseToggleButton.repaint();
    }

    public void update(Model model) {
        setIgnoreRepaint(true);
        this.model = model;
        updateComponents();
        setIgnoreRepaint(false);
        repaint();
    }

    private void updateComponents() {
        generationLabel.setText(GENERATION_LABEL_PREFIX + model.getGeneration());
        aliveCountLabel.setText(ALIVE_COUNT_LABEL_PREFIX + model.getUniverse().getAliveCount());
        drawPanel.setUniverse(model.getUniverse());
    }

}
