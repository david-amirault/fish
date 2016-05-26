import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;

/**
 * This class provides a GUI for solitaire games related to Elevens.
 */
public class FishGUI extends JFrame {

	/** Height of the game frame. */
	private static final int DEFAULT_HEIGHT = 332;
	/** Width of the game frame. */
	private static final int DEFAULT_WIDTH = 800;
	/** Width of a card. */
	private static final int CARD_WIDTH = 73;
	/** Height of a card. */
	private static final int CARD_HEIGHT = 97;
	/** Row (y coord) of the upper left corner of the first card. */
	private static final int LAYOUT_TOP = 30;
	/** Column (x coord) of the upper left corner of the first card. */
	private static final int LAYOUT_LEFT = 30;
	/** Distance between the upper left x coords of
	 *  two horizonally adjacent cards. */
	private static final int LAYOUT_WIDTH_INC = 15;
	/** Distance between the upper left y coords of
	 *  two vertically adjacent cards. */
	private static final int LAYOUT_HEIGHT_INC = 125;
    /** Height of the text box. */
    private static final int TEXTBOX_HEIGHT = 30;
    /** Height of one line of hand size messages. */
    private static final int TEXT_HEIGHT = 20;
	/** y coord of the "Replace" button. */
	private static final int BUTTON_TOP = 30;
	/** x coord of the "Replace" button. */
	private static final int BUTTON_LEFT = 570;
	/** Distance between the tops of the "Replace" and "Restart" buttons. */
	private static final int BUTTON_HEIGHT_INC = 50;
	/** y coord of the "n undealt cards remain" label. */
	private static final int LABEL_TOP = 160;
	/** x coord of the "n undealt cards remain" label. */
	private static final int LABEL_LEFT = 540;
	/** Distance between the tops of the "n undealt cards" and
	 *  the "You lose/win" labels. */
	private static final int LABEL_HEIGHT_INC = 35;

    /** The player (Player subclass). */
    private Player player;

    /** For new card inputs. */
    private JTextField textField;
	/** The main panel containing the game components. */
	private JPanel panel;
	/** The Replace button. */
	private JButton replaceButton;
	/** The Restart button. */
	private JButton restartButton;
	/** The "number of undealt cards remain" message. */
	private JLabel promptMsg;
	/** The "you've won n out of m games" message. */
	private JLabel totalsMsg;
	/** The win message. */
	private JLabel winMsg;
	/** The loss message. */
	private JLabel lossMsg;
    /** The tie message. */
    private JLabel tieMsg;
    /** Hand sizes. */
    private JLabel[] handMsg;

	/** The card displays. */
	private List<JLabel> displayCards;
	/** The coordinates of the card displays. */
	private List<Point> cardCoords;
	/** Contains k iff the user has selected card #k. */
	private List<Integer> selections;
    /** Ghetto synchronization. */
    private boolean entered;
    private boolean clicked;
    private boolean valid;


	/**
	 * Initialize the GUI.
	 * @param p is a <code>Player</code> subclass.
	 */
	public FishGUI(Player p) {
        player = p;
		displayCards = new ArrayList<JLabel>();
		cardCoords = new ArrayList<Point>();
        selections = new ArrayList<Integer>();

		initDisplay();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		repaint();
	}

	/**
	 * Run the game.
	 */
	public void displayGame() {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				setVisible(true);
			}
		});
	}

    /**
     * Inform the user of the most recent game event.
     */
    public void status(String msg, String title) {
        Popup.infoBox(msg, title);
    }

    /**
     * Update the promptMsg JLabel.
     */
    private void prompt(String msg) {
        promptMsg.setText(msg);
		promptMsg.setVisible(true);
        pack();
        panel.repaint();
    }

	/**
	 * Draw the display (cards and messages).
	 */
	public void repaint() {
        for (int i = 0; i < 6; i++)
            handMsg[i].setText("Player " + i + ":    " + player.handsizes()[i] + " cards");

		cardCoords.clear();
		int x = LAYOUT_LEFT;
		int y = LAYOUT_TOP;
		for (int i = 0; i < player.size(); i++) {
			cardCoords.add(new Point(x, y));
            x += LAYOUT_WIDTH_INC;
		}

        for (int i = 0; i < displayCards.size(); i++)
            panel.remove(displayCards.get(i));

		displayCards.clear();
		for (int k = player.size() - 1; k >= 0; k--) {
			displayCards.add(new JLabel());
            int b = displayCards.size() - 1;
			panel.add(displayCards.get(b));
			displayCards.get(b).setBounds(cardCoords.get(k).x, cardCoords.get(k).y, CARD_WIDTH, CARD_HEIGHT);
			displayCards.get(b).addMouseListener(new MyMouseListener());
		}

		for (int k = 0; k < player.size(); k++) {
			String cardImageFileName = imageFileName(player.hand().get(k), selections.contains(new Integer(k)));
			URL imageURL = getClass().getResource(cardImageFileName);
			if (imageURL != null) {
				ImageIcon icon = new ImageIcon(imageURL);
				displayCards.get(k).setIcon(icon);
				displayCards.get(k).setVisible(true);
			} else {
				throw new RuntimeException("Card image not found: \"" + cardImageFileName + "\"");
			}
		}

		totalsMsg.setText("Score:    " + player.score()[0] + " to " + player.score()[1]);
		totalsMsg.setVisible(true);
        if (player.score()[0] + player.score()[1] == 8)
        {
            if (player.score()[player.id() % 2] > player.score()[(player.id() + 1) % 2])
                winMsg.setVisible(true);
            else if (player.score()[player.id() % 2] < player.score()[(player.id() + 1) % 2])
                lossMsg.setVisible(true);
            else
                tieMsg.setVisible(true);
        }
		pack();
		panel.repaint();
	}

	/**
	 * Initialize the display.
	 */
	private void initDisplay()	{
		panel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
			}
		};

        setTitle("Fish Player " + player.id());

		this.setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(DEFAULT_WIDTH - 20, DEFAULT_HEIGHT - 20));

		replaceButton = new JButton();
		replaceButton.setText("Ask");
        replaceButton.setEnabled(false);
		panel.add(replaceButton);
		replaceButton.setBounds(BUTTON_LEFT, BUTTON_TOP, 100, 30);

		restartButton = new JButton();
		restartButton.setText("Declare");
        restartButton.setEnabled(false);
		panel.add(restartButton);
		restartButton.setBounds(BUTTON_LEFT, BUTTON_TOP + BUTTON_HEIGHT_INC, 100, 30);

        handMsg = new JLabel[6];
        for (int k = 0; k < 6; k++) {
            handMsg[k] = new JLabel();
            panel.add(handMsg[k]);
            handMsg[k].setBounds(LAYOUT_LEFT, LABEL_TOP + k * TEXT_HEIGHT, 250, 30);
        }

		promptMsg = new JLabel("Game started.");
		panel.add(promptMsg);
		promptMsg.setBounds(LABEL_LEFT, LABEL_TOP, 250, 30);

        textField = new JTextField(20);
        panel.add(textField);
        textField.setBounds(LABEL_LEFT, LABEL_TOP + TEXTBOX_HEIGHT, 160, 20);

		winMsg = new JLabel();
		winMsg.setBounds(LABEL_LEFT, LABEL_TOP + TEXTBOX_HEIGHT + LABEL_HEIGHT_INC, 200, 30);
		winMsg.setFont(new Font("SansSerif", Font.BOLD, 25));
		winMsg.setForeground(Color.GREEN);
		winMsg.setText("You win!");
		panel.add(winMsg);
		winMsg.setVisible(false);

		lossMsg = new JLabel();
		lossMsg.setBounds(LABEL_LEFT, LABEL_TOP + TEXTBOX_HEIGHT + LABEL_HEIGHT_INC, 200, 30);
		lossMsg.setFont(new Font("SanSerif", Font.BOLD, 25));
		lossMsg.setForeground(Color.RED);
		lossMsg.setText("Sorry, you lose.");
		panel.add(lossMsg);
		lossMsg.setVisible(false);

        tieMsg = new JLabel();
        tieMsg.setBounds(LABEL_LEFT, LABEL_TOP + TEXTBOX_HEIGHT + LABEL_HEIGHT_INC, 200, 30);
        tieMsg.setFont(new Font("SanSerif", Font.BOLD, 25));
        tieMsg.setForeground(Color.BLUE);
        tieMsg.setText("It's a tie!");
        panel.add(tieMsg);
        tieMsg.setVisible(false);

        totalsMsg = new JLabel();
		totalsMsg.setBounds(LABEL_LEFT, LABEL_TOP + TEXTBOX_HEIGHT + 2 * LABEL_HEIGHT_INC, 250, 30);
		panel.add(totalsMsg);

		pack();
		getContentPane().add(panel);
		getRootPane().setDefaultButton(replaceButton);
		panel.setVisible(true);
	}

	/**
	 * Deal with the user clicking on something other than a button or a card.
	 */
	private void signalError() {
		Toolkit t = panel.getToolkit();
		t.beep();
	}

	/**
	 * Returns the image that corresponds to the input card.
	 * Image names have the format "[Rank][Suit].GIF" or "[Rank][Suit]S.GIF",
	 * for example "aceclubs.GIF" or "8heartsS.GIF". The "S" indicates that
	 * the card is selected.
	 *
	 * @param c Card to get the image for
	 * @param isSelected flag that indicates if the card is selected
	 * @return String representation of the image
	 */
	private String imageFileName(Card c, boolean isSelected) {
		String str = "cards/";
		if (c == null) {
			return "cards/back1.GIF";
		}
		str += c.rank() + c.suit();
		if (isSelected) {
			str += "S";
		}
		str += ".GIF";
		return str;
	}

	/**
	 * Respond to a button click (on either the "Replace" button
	 * or the "Restart" button).
	 * @param e the button click action event
	 */
    private String getText(String promptMsg) {
        prompt(promptMsg);
        entered = false;
        for (ActionListener al : textField.getActionListeners())
            textField.removeActionListener(al);

        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textField.selectAll();
                entered = true;
            }
        });
        while (!entered) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        prompt("");
        return textField.getText();
    }

    public Declaration declare(boolean must) {
        Declaration dec = new Declaration();
        valid = true;
        clicked = false;
        if (!must)
        {
            replaceButton.setEnabled(true);
            for (ActionListener al : replaceButton.getActionListeners())
                replaceButton.removeActionListener(al);

            replaceButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    valid = false;
                    clicked = true;
                }
            });
        }

        restartButton.setEnabled(true);
        for (ActionListener al : restartButton.getActionListeners())
            restartButton.removeActionListener(al);

        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < selections.size(); i++)
                {
                    dec.addQuestion(new Question(player.id(), player.id(), player.hand().get(selections.get(i).intValue())));
                    if (i > 0 && dec.getQuestion(i).card().code() != dec.getQuestion(i - 1).card().code()) {
                        valid = false;
                        break;
                    }
                }
                if (dec.size() > 6)
                    valid = false;

                if (!valid) {
                    selections.clear();
                    repaint();
                }
                clicked = true;
            }
        });
        while (!clicked) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        if (valid) {
            String s;
            if (dec.size() == 0)
                s = getText("Enter declared suit:");
            else
                s = dec.getQuestion(0).card().suit();

            while (dec.size() < 6) {
                int t = Integer.parseInt(getText("Enter teammate id:"));
                String r = getText("Enter card rank:");
                dec.addQuestion(new Question(player.id(), t, new Card(r, s)));
                if (dec.size() > 1 && dec.getQuestion(dec.size() - 1).card().code() != dec.getQuestion(dec.size() - 2).card().code()) {
                    break;
                }
            }
            selections.clear();
            repaint();
        }
        replaceButton.setEnabled(false);
        restartButton.setEnabled(false);
        return dec;
    }

    public Question ask() {
        clicked = true;
        if (selections.size() != 1) {
            clicked = false;
            selections.clear();
            repaint();
        }
        replaceButton.setEnabled(true);
        for (ActionListener al : replaceButton.getActionListeners())
            replaceButton.removeActionListener(al);

        replaceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selections.size() != 1) {
                    selections.clear();
                    repaint();
                } else {
                    clicked = true;
                }
            }
        });
        while (!clicked) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        replaceButton.setEnabled(false);
        int t = Integer.parseInt(getText("Enter enemy id:"));
        String r = getText("Enter card rank:");
        Question q = new Question(player.id(), t, new Card(r, player.hand().get(selections.get(0).intValue()).suit()));
        selections.clear();
        repaint();
        return q;
    }

	/**
	 * Receives and handles mouse clicks.  Other mouse events are ignored.
	 */
	private class MyMouseListener implements MouseListener {

		/**
		 * Handle a mouse click on a card by toggling its "selected" property.
		 * Each card is represented as a label.
		 * @param e the mouse event.
		 */
		public void mouseClicked(MouseEvent e) {
			for (int k = 0; k < player.size(); k++) {
				if (e.getSource().equals(displayCards.get(k))) {
                    if (selections.contains(new Integer(k)))
                        selections.remove(new Integer(k));
                    else
                        selections.add(new Integer(k));

					repaint();
					return;
				}
			}
			signalError();
		}

		/**
		 * Ignore a mouse exited event.
		 * @param e the mouse event.
		 */
		public void mouseExited(MouseEvent e) {
		}

		/**
		 * Ignore a mouse released event.
		 * @param e the mouse event.
		 */
		public void mouseReleased(MouseEvent e) {
		}

		/**
		 * Ignore a mouse entered event.
		 * @param e the mouse event.
		 */
		public void mouseEntered(MouseEvent e) {
		}

		/**
		 * Ignore a mouse pressed event.
		 * @param e the mouse event.
		 */
		public void mousePressed(MouseEvent e) {
		}
	}

    private static class Popup {
        public static void infoBox(String infoMessage, String titleBar) {
            JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
