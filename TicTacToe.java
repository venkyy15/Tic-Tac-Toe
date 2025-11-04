import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToe extends JFrame implements ActionListener {

    private JButton[] buttons = new JButton[9];
    private boolean playerXTurn = true; 
    private JLabel statusLabel;
    private JButton resetButton;

    public TicTacToe() {
        setTitle("Tic-Tac-Toe Game 🎮");
        setSize(400, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        statusLabel = new JLabel("Player X's Turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(statusLabel, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));
        Font btnFont = new Font("Arial", Font.BOLD, 60);

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(btnFont);
            buttons[i].setFocusPainted(false);
            buttons[i].addActionListener(this);
            panel.add(buttons[i]);
        }

        add(panel, BorderLayout.CENTER);

        resetButton = new JButton("Restart");
        resetButton.setFont(new Font("Arial", Font.BOLD, 18));
        resetButton.addActionListener(e -> resetGame());
        add(resetButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton buttonClicked = (JButton) e.getSource();

        if (!buttonClicked.getText().equals("")) {
            return; 
        }

        buttonClicked.setText(playerXTurn ? "X" : "O");
        buttonClicked.setForeground(playerXTurn ? Color.BLUE : Color.RED);

        if (checkWinner()) {
            statusLabel.setText("Player " + (playerXTurn ? "X" : "O") + " Wins!");
            disableButtons();
        } else if (isDraw()) {
            statusLabel.setText("It's a Draw!");
        } else {
            playerXTurn = !playerXTurn;
            statusLabel.setText("Player " + (playerXTurn ? "X" : "O") + "'s Turn");
        }
    }

    private boolean checkWinner() {
        int[][] winConditions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, 
            {0, 4, 8}, {2, 4, 6}              
        };

        for (int[] wc : winConditions) {
            String s1 = buttons[wc[0]].getText();
            String s2 = buttons[wc[1]].getText();
            String s3 = buttons[wc[2]].getText();

            if (!s1.equals("") && s1.equals(s2) && s2.equals(s3)) {
                buttons[wc[0]].setBackground(Color.GREEN);
                buttons[wc[1]].setBackground(Color.GREEN);
                buttons[wc[2]].setBackground(Color.GREEN);
                return true;
            }
        }
        return false;
    }

    private boolean isDraw() {
        for (JButton b : buttons) {
            if (b.getText().equals("")) return false;
        }
        return true;
    }

    private void disableButtons() {
        for (JButton b : buttons) {
            b.setEnabled(false);
        }
    }

    private void resetGame() {
        for (JButton b : buttons) {
            b.setText("");
            b.setBackground(null);
            b.setEnabled(true);
        }
        playerXTurn = true;
        statusLabel.setText("Player X's Turn");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicTacToe());
    }
}

