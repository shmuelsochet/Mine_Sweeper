/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.awt.*;
import java.net.URL;
import javax.swing.*;

/**
 *
 * @author Shmuel
 */
public class GameButton extends JButton
{

    private boolean isMine;
    private int nearbyMines = 0;
    private boolean uncovered;

    public void uncover()
    {
        setBackground(Color.WHITE);
        setUncovered(true);

        if (isMine == true)
        {

            ImageIcon buttonIcon = null;
            URL imgURL = getClass().getClassLoader().getResource(("Resources/mine.png"));
            if (imgURL != null)
            {
                buttonIcon = new ImageIcon(imgURL);
                setIcon(buttonIcon);
            }
        } else if (nearbyMines != 0)
        {

            String nb = Integer.toString(nearbyMines);
            setText(nb);
            setFont(new Font("Comic Sans MS", Font.BOLD, 15));
            switch (nearbyMines)
            {
                case 1:

                    this.setForeground(Color.blue);
                    break;
                case 2:
                    this.setForeground(Color.green);
                    break;
                case 3:
                    this.setForeground(Color.red);
                    break;
                case 4:
                    this.setForeground(new Color(128, 0, 128));
                    break;
                case 5:
                    this.setForeground(new Color(128, 0, 0));
                    break;
                case 6:
                    this.setForeground(new Color(64, 224, 208));
                    break;
                case 7:
                    this.setForeground(Color.BLACK);
                    break;
                case 8:
                    this.setForeground(Color.GRAY);
                    break;
            }
            this.setOpaque(true);
        }
    }

    public boolean isIsMine()
    {
        return isMine;
    }

    /**
     * @param isMine the isMine to set
     */
    public void setIsMine(boolean isMine)
    {
        this.isMine = isMine;
    }

    /**
     * @return the nearbyMines
     */
    public int getNearbyMines()
    {
        return nearbyMines;
    }

    /**
     * @param nearbyMines the nearbyMines to set
     */
    public void setNearbyMines(int nearbyMines)
    {
        if (isMine == false)
        {
            this.nearbyMines = nearbyMines;
        }
    }

    public int incrementNearbyMines()
    {

        return ++nearbyMines;
    }

    /**
     * @return the uncovered
     */
    public boolean isUncovered()
    {
        return uncovered;
    }

    /**
     * @param uncovered the uncovered to set
     */
    public void setUncovered(boolean uncovered)
    {
        this.uncovered = uncovered;
    }

}
