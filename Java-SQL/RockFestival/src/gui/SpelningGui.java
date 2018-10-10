package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by patriklarsson on 12/05/16.
 */
public class SpelningGui extends JLabel {
    private final Color TEXT_COLOR = Color.BLACK;
    private final Color RESERVERD_COLOR = Color.RED;
    private final Color FREE_COLOR = Color.GREEN;
    private final Dimension SIZE = new Dimension(300, 50);
    // TODO : Change font?

    String band;
    String scen;

    public SpelningGui(Boolean free, String band, String scen) {
        super();
        setOpaque(true);
        setPreferredSize(SIZE);
        if (free == true) {
            setBackground(FREE_COLOR);
        } else {
            setBackground(RESERVERD_COLOR);
        }
        this.band = band;
        this.scen = scen;
        setVerticalAlignment(SwingConstants.CENTER);
        setText("<html>" +band + "<br>" + scen +"<html>");
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame1 = new JFrame();
                frame1.add(new SpelningGui(false, "Pattenp", "Jesus"));
                frame1.pack();
                frame1.setResizable(false);
                frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame1.setVisible(true);

                JFrame frame2 = new JFrame();
                frame2.add(new SpelningGui(true, "OSCAR", "Jesus"));
                frame2.pack();
                frame2.setResizable(false);
                frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame2.setVisible(true);

            }
        });
    }
}
