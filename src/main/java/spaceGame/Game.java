package games.spaceGame;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

class Fire {
    private int x;
    private int y;

    public Fire(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

public class Game extends JPanel implements KeyListener, ActionListener {

    Timer timer = new Timer(5, this);
    private int passingTime = 0;
    private int spentFire = 0;
    private BufferedImage image = new BufferedImage(926, 1000, 5);
    private ArrayList<Fire> atesler = new ArrayList<Fire>();
    private int atesDirY = 1;
    private int topX = 0;
    private int topDirX = 2;
    private int uzayGemisiX = 0;
    private int dirUzayX = 20;

    public boolean kontrolEt() {

        for (Fire ates : atesler) {

            if (new Rectangle(ates.getX(), ates.getY(), 10, 20).intersects(new Rectangle(topX, 0, 20, 20))) {
                return true;
            }
        }
        return false;
    }


    public Game() {

        try {
            image = ImageIO.read(new FileImageInputStream(new File("C:\\Users\\efqan\\OneDrive\\Desktop\\forGame.jpg")));
        }catch (IOException exception) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, exception);
        }
        setBackground(Color.BLACK);

        timer.start();
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        passingTime += 5;

        g.setColor(Color.red);
        g.fillOval(topX, 0, 20, 20);

        g.drawImage(image, uzayGemisiX, 490, image.getWidth() / 10, image.getHeight() / 10, this);

        for (Fire ates : atesler) {

            if (ates.getY() < 0) {

                atesler.remove(ates);
            }
        }
        g.setColor(Color.BLUE);

        for (Fire ates : atesler) {

            g.fillRect(ates.getX(), ates.getY(), 10, 20);
        }

        if (kontrolEt()) {

            timer.stop();
            String message = "kazandiniz...\n" +
                    "Harcanan Ates: " + spentFire +
                    "\nGecen sure: " + passingTime / 1000.0 + " saniye";

            JOptionPane.showMessageDialog(this, message);
            System.exit(0);
        }

    }



    @Override
    public void repaint() {
        super.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for (Fire ates : atesler) {

            ates.setY(ates.getY() - atesDirY);
        }

        topX += topDirX;

        if (topX >= 750) {
            topDirX = - topDirX;
        }

        if (topX <= 0) {
            topDirX = - topDirX;
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {



    }

    @Override
    public void keyPressed(KeyEvent e) {

        int c = e.getKeyCode();

        if (c == KeyEvent.VK_LEFT){
            if (uzayGemisiX <= 0) {

                uzayGemisiX = 0;
            }
            else {

                uzayGemisiX -= dirUzayX;
            }
        }
        else if (c == KeyEvent.VK_RIGHT) {

            if (uzayGemisiX >= 750) {

                uzayGemisiX = 750;
            }
            else {

                uzayGemisiX += dirUzayX;
            }
        } else if (c == KeyEvent.VK_CONTROL) {

            atesler.add(new Fire(uzayGemisiX + 15, 420));

            spentFire++;
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
