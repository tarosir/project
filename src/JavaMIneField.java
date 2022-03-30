import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import MineField.Box;
import MineField.Coord;
import MineField.Game;
import MineField.Ranges;

public class JavaMIneField extends JFrame {

    private Game game;
    private JPanel panel;
    private JLabel label;
    private final int COLS = 9;
    private final int ROWS = 9;
    private final int BOMBS = 10;
    private final int IMAGESIZE = 50;


    public static void main(String[] args) {

        new JavaMIneField();

    }

    private JavaMIneField() {
        game = new Game(COLS, ROWS, BOMBS);
        game.start();
        initlabel();
        setImages();
        initPanel();
        initFrame();
    }

    private void initlabel() {
        label = new JLabel("WELCOME!");
        add(label, BorderLayout.SOUTH);
    }


    private void initPanel() {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                for (Coord coord : Ranges.getAllCoords()) {

                    g.drawImage((Image) game.getBox(coord).image, coord.x * IMAGESIZE, coord.y * IMAGESIZE, this);
                }
            }
        };
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGESIZE;
                int y = e.getY() / IMAGESIZE;
                Coord coord = new Coord(x, y);
                if (e.getButton() == MouseEvent.BUTTON1) game.pressLeftButton(coord);
                if (e.getButton() == MouseEvent.BUTTON2) game.start();
                if (e.getButton() == MouseEvent.BUTTON3) game.pressRightButton(coord);
                label.setText(getMessage());
                panel.repaint();
            }
        });
        panel.setPreferredSize(new Dimension(Ranges.getSize().x * IMAGESIZE, Ranges.getSize().y * IMAGESIZE));
        add(panel);

    }

    private String getMessage() {
        switch (game.getState()) {
            case PLAYED:
                return "Good Luck!";
            case WINNER:
                return "Congratulations!";

            case BOMBED:
                return "You Lose!";

            default:
                return "";
        }

    }

    private void initFrame() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Java Minefield");
        setResizable(false);
        setVisible(true);
        setIconImage(getImage("icon"));
        pack();
        setLocationRelativeTo(null);
    }


    private void setImages() {
        for (Box box : Box.values()) {
            box.image = getImage(box.name().toLowerCase());
        }
    }

    private Image getImage(String name) {
        String filename = "img/" + name + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();


    }
}
