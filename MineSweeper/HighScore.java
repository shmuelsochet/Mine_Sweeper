/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.util.ArrayList;

/**
 *
 * @author samjays
 */
public class HighScore implements Comparable<HighScore>
{

    /**
     * @return the MAX_GAMES
     */
    public static int getMAX_GAMES()
    {
        return MAX_GAMES;
    }

    //instructor made this as an inner class in class HighScores
    private String level;
    private String name;
    private String score;

    private static final int MAX_GAMES = 15;

    public HighScore(String level, String name, String score)
    {
        this.level = level;
        this.name = name;
        this.score = score;
//
//        if (level == "Easy")
//        {
//            levelNum = 2;
//        } else if (level == "Medium")
//        {
//            levelNum = 1;
//        } else if (level == "Hard")
//        {
//            levelNum = 0;
//        }
//        else 
//        {
//            levels = 2;
//        }

    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the score
     */
    public String getScore()
    {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(String score)
    {
        this.score = score;
    }

    @Override
    //if you're just a comparable for highscores than you need to pass a highscore
    //that's what the diamond <highscore> means that you're only a comparable 
    //for highscores
    public int compareTo(HighScore hs)
    {
              
//        if (levelNum < hs.levelNum)
//        {
//            return 1;
//        } else if (levelNum > hs.levelNum)
//        {
//            return -1;
//        } else

        {
            String sHS = "";
            for (int i = 0; i < hs.getScore().length(); i++)
            {

                if (Character.isDigit(hs.getScore().charAt(i)))
                {

                    sHS += "" + hs.getScore().charAt(i);
                }

            }

            String s = "";
            for (int i = 0; i < score.length(); i++)
            {

                if (Character.isDigit(score.charAt(i)))
                {

                    s += "" + score.charAt(i);
                }

            }

            return Integer.compare(Integer.parseInt(s), Integer.parseInt(sHS));
        }
      
   }

    public String toString()
    {
        return level + " . " + name + " . " + score;
    }

    /**
     * @return the level
     */
    public String getLevel()
    {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(String level)
    {
        this.level = level;
    }

}
