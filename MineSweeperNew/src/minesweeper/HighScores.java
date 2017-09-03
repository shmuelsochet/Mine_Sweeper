/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Shmuel
 */
public class HighScores
{

    private ArrayList<HighScore> hs = new ArrayList();
    final int MAX_SCORES = 3;
    boolean sorted;

    public ArrayList<HighScore> getHs()
    {
        return hs;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hs.size(); i++)
        {
            sb.append((i + 1) + ". " + hs.get(i).toString());
            sb.append("\n");
        }

        return sb.toString();
    }

    void add(HighScore e)
    {

//        Collections.sort(hs, new Comparator<HighScore>()
//        {
//            public int compare(HighScore p1, HighScore p2)
//            {
//                return p1.getScore() - p2.getScore(); // Ascending
//            }
//        
//        });
        if (hs.size() < e.getMAX_GAMES())
        {
            JOptionPane.showMessageDialog(null, "Congrats you got a High Score!\n" + "Your score is: " + e.getScore());

            String name = JOptionPane.showInputDialog("Put in your name.");
            if (name != null)
            {
                e.setName(name);
            }

            hs.add(e);
            hs.sort(null);

            try
            {
                write();
            } catch (IOException ex)
            {

            }
        } else if (hs.size() >= e.getMAX_GAMES())
        {
            String eHS = "";
            for (int i = 0; i < e.getScore().length(); i++)
            {

                if (Character.isDigit(e.getScore().charAt(i)))
                {

                    eHS += "" + e.getScore().charAt(i);
                }
            }

            String lowestHS = "";
            for (int i = 0; i < hs.get(hs.size() - 1).getScore().length(); i++)
            {
                if (Character.isDigit(hs.get(hs.size() - 1).getScore().charAt(i)))
                {

                    lowestHS += "" + hs.get(hs.size() - 1).getScore().charAt(i);
                }
            }
            if (Integer.parseInt(eHS) < Integer.parseInt(lowestHS))
            {
                JOptionPane.showMessageDialog(null, "Congrats you got a High Score!\n" + "Your score is: " + e.getScore());
                String name = JOptionPane.showInputDialog("Put in your name.");
                if (name != null)
                {
                    e.setName(name);
                }

                hs.remove(hs.size() - 1);
                hs.add(e);
                hs.sort(null);

                try
                {
                    write();
                } catch (IOException ex)
                {

                }
            } else
            {
                JOptionPane.showMessageDialog(null, "Your score is: " + e.getScore() + "\nI'm sorry you did not get a HighScore.\nBetter Luck next time!");
            }
        }
    }

    public void write() throws IOException
    {
        File highscores = new File("C:\\Users\\Shmuel\\Documents\\MineSweepers\\HighScores.txt");
        File folder = new File("C:\\Users\\Shmuel\\Documents\\MineSweepers\\");
        folder.mkdir();

        if (!highscores.exists())
        {
            highscores.createNewFile();

        }

        FileOutputStream fw = new FileOutputStream(highscores);
        DataOutputStream dataOut = new DataOutputStream(fw);

        //ask zev if it earses the data and that's why you need to write the whole arraylist each time. if so
        //why not use append as per stackoverflow by filewriter. AKK said it's more expensive to append.
        for (HighScore h : hs)
        {
            dataOut.writeUTF(h.getLevel());
            dataOut.writeUTF(h.getName());
            dataOut.writeUTF(h.getScore());
        }

        dataOut.flush();
        dataOut.close();
    }

    public void read() throws IOException
    {
        File highscores = new File("C:\\Users\\Shmuel\\Documents\\MineSweepers\\HighScores.txt");
        File folder = new File("C:\\Users\\Shmuel\\Documents\\MineSweepers\\");
        folder.mkdir();

        if (!highscores.exists())
        {
            return;
        }

        DataInputStream dataIn = new DataInputStream(new FileInputStream(highscores));
        while (dataIn.available() > 0)
        {
            String level = dataIn.readUTF();
            String name = dataIn.readUTF();
            String score = dataIn.readUTF();
            HighScore h = new HighScore(level, name, score);
            hs.add(h);

            //System.out.println(name + "," + score);
            for (int i = 0; i < hs.size(); i++)
            {
                System.out.println("At slot: " + i);
                System.out.println("Level: " + hs.get(i).getLevel());
                System.out.println("Name: " + hs.get(i).getName());
                System.out.println("Score: " + hs.get(i).getScore());
            }
        }
        dataIn.close();
    }
}
