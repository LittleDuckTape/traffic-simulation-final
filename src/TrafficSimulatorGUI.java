import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI class for the traffic simulation.
 * Users can:
 * - Input number of vehicles
 * - Select simulation mode (queue, stack, priority)
 * - Run the simulation and view results
 */
public class TrafficSimulatorGUI extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JTextField inputField;
    private JComboBox<String> modeBox;
    private JTextArea resultArea;

    // Store previous settings for "Run Again" button
    private String lastMode = "queue";
    private int lastInput = 100;

    public TrafficSimulatorGUI() {
        setTitle("Traffic Simulator");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen

        // Main panel that switches between input, loading, result screens
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add three screens
        mainPanel.add(createInputPanel(), "input");
        mainPanel.add(createLoadingPanel(), "loading");
        mainPanel.add(createResultPanel(), "result");

        add(mainPanel);
        cardLayout.show(mainPanel, "input");
    }

    /**
     * First screen: user enters number of vehicles and selects simulation mode.
     */
    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel label1 = new JLabel("Enter number of vehicles:");
        inputField = new JTextField("100");

        JLabel label2 = new JLabel("Select simulation mode:");
        modeBox = new JComboBox<>(new String[]{"queue", "stack", "priority"});

        JButton runButton = new JButton("Run Simulation");

        // Start simulation when user clicks the button
        runButton.addActionListener(e -> {
            try {
                int input = Integer.parseInt(inputField.getText());
                String mode = (String) modeBox.getSelectedItem();

                lastMode = mode;
                lastInput = input;

                cardLayout.show(mainPanel, "loading");

                // Run simulation with a slight delay to simulate loading
                new Timer(1000, new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        String result = SimulationRunner.runSimulation(mode, input);
                        resultArea.setText(result);
                        cardLayout.show(mainPanel, "result");
                        ((Timer) evt.getSource()).stop();
                    }
                }).start();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number.");
            }
        });

        panel.add(label1);
        panel.add(inputField);
        panel.add(label2);
        panel.add(modeBox);
        panel.add(runButton);

        return panel;
    }

    /**
     * Second screen: displays a "loading..." message while simulation runs.
     */
    private JPanel createLoadingPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel loadingLabel = new JLabel("Simulating traffic... please wait", SwingConstants.CENTER);
        panel.add(loadingLabel, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Third screen: displays simulation results and options to retry.
     */
    private JPanel createResultPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        panel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton retrySame = new JButton("Run Again (Same Settings)");
        JButton back = new JButton("Back to Main Menu");

        retrySame.addActionListener(e -> {
            cardLayout.show(mainPanel, "loading");

            new Timer(1000, new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    String result = SimulationRunner.runSimulation(lastMode, lastInput);
                    resultArea.setText(result);
                    cardLayout.show(mainPanel, "result");
                    ((Timer) evt.getSource()).stop();
                }
            }).start();
        });

        back.addActionListener(e -> cardLayout.show(mainPanel, "input"));

        buttonPanel.add(retrySame);
        buttonPanel.add(back);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Main method to run GUI from Main.java.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TrafficSimulatorGUI().setVisible(true);
        });
    }
}
