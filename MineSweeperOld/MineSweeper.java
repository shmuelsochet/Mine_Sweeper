/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author samjays
 */
public class MineSweeper extends JFrame
{

    //for timer hw
    int seconds2;
    int minutes;
    volatile boolean gameOver = false;
    boolean timerCalled = false;
    //String timeStr = ("%1$tH" , seconds2);

    static int rows = 9;
    static int cols = 9;
    static int cellSize = 45;
    String level = "Easy";
    GameButton[][] gbArr = new GameButton[rows][cols];

    Timer time;
    boolean timeStarted = false;
    JLabel timerLabel = new JLabel("", SwingConstants.CENTER);

    //to track when player wins
    int winCounter = 0;
    static int mines = 10;

    //used for keeping track of flags
    int minesCounter = mines;

    //used so once mines are opened no clicking can take place
    int minesOpened;

    //for if hs were already read
    boolean hsRead = false;

    //use this for restart to get background color of button
    @Override
    public Color getBackground()
    {
        return super.getBackground();
    }

    //how many clicks in the game
    int clicks;
    HighScores hsc = new HighScores();

    ImageIcon flagIcon = null;
    URL imgURL = getClass().getClassLoader().getResource(("Resources/amFlag.png"));

    ImageIcon questionMarkIcon = null;
    URL imgURL1 = getClass().getClassLoader().getResource(("Resources/QuestionMark.png"));

    public void setLevels()
    {

        String[] choices =
        {
            "Easy", "Medium", "Hard", "Custom"
        };

        int choice = JOptionPane.showOptionDialog(this, "Select Level", "Difficulty", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);

        if (choice == JOptionPane.YES_OPTION)

        {
            dispose();
            rows = 9;
            cols = 9;
            cellSize = rows * cols / 2;
            mines = 10;
            level = "Easy";
            new MineSweeper(rows, cols, mines, level).setVisible(true);
        }
        if (choice == JOptionPane.NO_OPTION)

        {
            dispose();
            rows = 16;
            cols = 16;
            cellSize = rows * cols / 2;
            mines = 40;
            level = "Medium";
            new MineSweeper(rows, cols, mines, level).setVisible(true);
        }
        if (choice == JOptionPane.CANCEL_OPTION)

        {
            dispose();
            rows = 16;
            cols = 30;
            cellSize = rows * cols / 2;
            mines = 99;
            level = "Hard";
            new MineSweeper(rows, cols, mines, level).setVisible(true);

        }
        if (choice == 3)

        {

            JPanel finalPanel = new JPanel();

            JPanel widthPanel = new JPanel();
            JPanel widthPanel2 = new JPanel();

            JPanel heightPanel = new JPanel();
            JPanel heightPanel2 = new JPanel();

            JPanel minePanel = new JPanel();
            JPanel minePanel2 = new JPanel();

            JLabel widthLabelTxt = new JLabel("Width:", SwingConstants.CENTER);
            JLabel heightLabelTxt = new JLabel("Height:", SwingConstants.CENTER);
            JLabel mineLabelTxt = new JLabel("Mines:", SwingConstants.CENTER);

            JLabel widthLabel = new JLabel();
            JLabel heightLabel = new JLabel();
            JLabel mineLabel = new JLabel();

            finalPanel.setLayout(new BoxLayout(finalPanel, BoxLayout.Y_AXIS));

            int min = 9;
            int max = 30;
            int init = 15;
            JSlider width = new JSlider(JSlider.HORIZONTAL,
                    min, max, init);

            max = 24;

            JSlider height = new JSlider(JSlider.HORIZONTAL,
                    min, max, init);
            min = 10;
            init = 28;
            int maxMine = (width.getValue() - 1) * (height.getValue() - 1);
            JSlider mine = new JSlider(JSlider.HORIZONTAL,
                    min, maxMine, init);

            widthLabel.setText("" + width.getValue());

            width.addChangeListener(new ChangeListener()
            {
                @Override
                public void stateChanged(ChangeEvent e)
                {                                                                         
                    mine.setMaximum((width.getValue() - 1) * (height.getValue() - 1));
                    widthLabel.setText("" + width.getValue());                  
                }
            });

            heightLabel.setText("" + height.getValue());

            height.addChangeListener(new ChangeListener()
            {
                @Override
                public void stateChanged(ChangeEvent e)
                {

                    heightLabel.setText("" + height.getValue());
                    mine.setMaximum((width.getValue() - 1) * (height.getValue() - 1));

                }
            });

            mineLabel.setText("" + mine.getValue());
            mine.addChangeListener(new ChangeListener()
            {
                @Override
                public void stateChanged(ChangeEvent e)
                {
                    mineLabel.setText("" + mine.getValue());
                }
            });

            widthLabel.setHorizontalAlignment(SwingConstants.CENTER);
            heightLabel.setHorizontalAlignment(SwingConstants.CENTER);
            mineLabel.setHorizontalAlignment(SwingConstants.CENTER);
            widthLabel.setPreferredSize(new Dimension(32, 32));
            heightLabel.setPreferredSize(new Dimension(32, 32));
            mineLabel.setPreferredSize(new Dimension(32, 32));
            widthLabel.setBackground(Color.white);
            heightLabel.setBackground(Color.white);
            mineLabel.setBackground(Color.white);
            widthLabel.setOpaque(true);
            heightLabel.setOpaque(true);
            mineLabel.setOpaque(true);
            widthLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
            heightLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
            mineLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));

            widthPanel.setLayout(new BorderLayout());
            heightPanel.setLayout(new BorderLayout());
            minePanel.setLayout(new BorderLayout());

            widthPanel.add(widthLabelTxt, BorderLayout.LINE_START);
            heightPanel.add(heightLabelTxt, BorderLayout.LINE_START);
            minePanel.add(mineLabelTxt, BorderLayout.LINE_START);

            widthPanel2.add(width);
            widthPanel2.add(widthLabel);
            heightPanel2.add(height);
            heightPanel2.add(heightLabel);
            minePanel2.add(mine);
            minePanel2.add(mineLabel);
            finalPanel.setBackground(Color.black);
            finalPanel.setOpaque(true);
            finalPanel.add(widthPanel);
            finalPanel.add(widthPanel2);
            finalPanel.add(heightPanel);
            finalPanel.add(heightPanel2);
            finalPanel.add(minePanel);
            finalPanel.add(minePanel2);

            JOptionPane.showMessageDialog(null, finalPanel);
            rows = height.getValue();
            cols = width.getValue();
            mines = mine.getValue();
            cellSize = rows * cols / 2;

            level = "Custom";
            System.out.println("rows: " + rows + "cols: " + cols + "mines " + mines + "level " + level);

            new MineSweeper(rows, cols, mines, level).setVisible(true);
            dispose();

        }

    }

    public MineSweeper(int rows, int cols, int mines, String level)
    {

        flagIcon = new ImageIcon(imgURL);
        questionMarkIcon = new ImageIcon(imgURL1);
        this.setTitle("Mine Sweeper");

        JPanel board = new JPanel();

        JPanel mineCounter = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        mineCounter.setBackground(Color.white);

        String disTime = String.format("%02d:%02d ", minutes, seconds2);
        timerLabel.setText(disTime);
        timerLabel.setBackground(Color.black);
        timerLabel.setForeground(Color.white);
        timerLabel.setPreferredSize(new Dimension(64, 32));
        timerLabel.setOpaque(true);

        ImageIcon clockIcon = null;
        URL imgURL2 = getClass().getClassLoader().getResource(("Resources/clock.png"));
        clockIcon = new ImageIcon(imgURL2);
        JLabel clockLabel = new JLabel();

        clockLabel.setIcon(clockIcon);

        ImageIcon mineIcon = null;
        URL imgURL3 = getClass().getClassLoader().getResource(("Resources/mine.png"));
        mineIcon = new ImageIcon(imgURL3);
        JLabel mineLabel = new JLabel();
        mineLabel.setIcon(mineIcon);

        JLabel minesFlagged = new JLabel("", SwingConstants.CENTER);
        minesFlagged.setText("" + minesCounter);
        minesFlagged.setPreferredSize(new Dimension(32, 32));
        minesFlagged.setBackground(Color.black);
        minesFlagged.setForeground(Color.white);
        minesFlagged.setOpaque(true);

        mineCounter.add(clockLabel);
        mineCounter.add(timerLabel);
        mineCounter.add(mineLabel);
        mineCounter.add(minesFlagged);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton quit = new JButton("Quit");
        JButton restart = new JButton("Restart");
        JButton difficulty = new JButton("Difficulty");
        JButton highScores = new JButton("High Scores");

        buttons.add(quit);
        buttons.add(restart);
        buttons.add(difficulty);
        buttons.add(highScores);

        restart.addActionListener(new ActionListener()
        {
            @Override

            public void actionPerformed(ActionEvent e)
            {
                //for timer thread
                gameOver = true;
                seconds2 = 0;

               
                timerLabel.setText(disTime);
                timeStarted = false;
              
                winCounter = 0;
                minesCounter = 10;
                minesOpened = 0;
             
                minesFlagged.setText("" + minesCounter);

                for (int i = 0; i < gbArr.length; i++)
                {
                    for (int j = 0; j < gbArr[i].length; j++)
                    {

                        gbArr[i][j].setBackground(getBackground());
                        gbArr[i][j].setIcon(null);
                        gbArr[i][j].setText("");
                        gbArr[i][j].setIsMine(false);
                        gbArr[i][j].setUncovered(false);
                        gbArr[i][j].setNearbyMines(0);

                    }
                }

                setUpGame();

            }

        });

        quit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //dispose();
                System.exit(0);
            }
        });

        difficulty.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setLevels();
            }
        });
        highScores.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                if (!hsRead)
                {
                    try
                    {

                        hsc.read();
                        hsRead = true;

                    } catch (IOException ex)
                    {
                        Logger.getLogger(MineSweeper.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
                JOptionPane.showMessageDialog(MineSweeper.this, hsc.toString(), "High Scores", JOptionPane.PLAIN_MESSAGE);
            }
        });

        add(buttons, BorderLayout.PAGE_END);
        add(mineCounter, BorderLayout.PAGE_START);

        board.setLayout(new GridLayout(rows, cols));

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                GameButton gb = new GameButton();
                gb.putClientProperty("firstIndex", new Integer(i));
                gb.putClientProperty("secondIndex", new Integer(j));

                gbArr[i][j] = gb;
                board.add(gb);
                add(board, BorderLayout.CENTER);

                //I used this to see the placement of each square
                // String a = Integer.toString(i, j);
                //gbArr[i][j].setText(i + ", " + j);
                gb.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {

                        if (timeStarted == false)
                        {
                            timer();
                        }
                        timeStarted = true;

                        GameButton gb = (GameButton) e.getSource();

                        Integer firstIndex = (Integer) gb.getClientProperty("firstIndex");
                        Integer secondIndex = (Integer) gb.getClientProperty("secondIndex");

                        ImageIcon imgURL = new ImageIcon(getClass().getClassLoader().getResource(("Resources/amFlag.png")));
                        if (gb.getIcon() == flagIcon || gb.getIcon() == questionMarkIcon
                                || gb.isUncovered() == true || winCounter == rows * cols - mines || gb.isIsMine() == true)
                        {
                            if (gb.getIcon() != flagIcon && gb.getIcon() != questionMarkIcon
                                    && winCounter != rows * cols - mines && gb.isIsMine() == true)
                            {
                                gameOver = true;

                                for (int i = 0; i < gbArr.length; i++)
                                {
                                    for (int j = 0; j < gbArr[i].length; j++)
                                    {

                                        gbArr[i][j].setUncovered(true);
                                        if (gbArr[i][j].isIsMine())
                                        {
                                            gbArr[i][j].uncover();
				            gbArr[i][j].setBackground(Color.red);
                                            minesOpened++;

                                        }

                                    }

                                }
                                if (minesOpened == mines)
                                {
                                    String[] loser =
                                    {
                                        "Do it again, but uh better!", "You lost!", "Better luck next time!", "Not cool!", "\"Mine\"d your own business!"
                                    };
                                    int choice = (int) (Math.random() * loser.length);
                                    JOptionPane.showMessageDialog(null, loser[choice]);
                                }
                            }
                        } else
                        {
                            uncoverCell(firstIndex, secondIndex, level);
                        }                         
                    }

                });
                gb.addMouseListener(new MouseAdapter()
                {

                    @Override
                    public void mousePressed(MouseEvent e)
                    {

                        GameButton gb = (GameButton) e.getSource();
                        if (e.getButton() == MouseEvent.BUTTON3)
                        {

                            if (imgURL != null && gb.getIcon() == null && minesOpened != mines && gb.isUncovered() == false)
                            {

                                gb.setIcon(flagIcon);
                                //if flagged sets mine number down one
                                minesCounter--;

                                //sets text to new minescounter number
                                minesFlagged.setText("" + minesCounter);

                            } else if (imgURL1 != null && gb.getIcon() == flagIcon && minesOpened != mines)
                            {
                                gb.setIcon(questionMarkIcon);
                                //if unflagged sets mine number up one
                                minesCounter++;

                                //sets text to new minescounter number
                                minesFlagged.setText("" + minesCounter);

                            } else if (gb.getIcon() == questionMarkIcon && minesOpened != mines)
                            {
                                gb.setIcon(null);

                            }

                        }
                    }

                }
                );
            }
        }
        setUpGame();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        if (level == "Hard")
        {
            setExtendedState(JFrame.MAXIMIZED_BOTH);

        } else
        {
            setSize(cols * cellSize, cols * cellSize);
        }

        setLocationRelativeTo(null);
     
    }

    public void setUpGame()
    {

        //placing minesFlagged
        for (int i = 1; i <= mines; i++)
        {
            int mineJ;
            int mineK;
            while (true)
            {

                mineJ = (int) (Math.random() * rows);
                mineK = (int) (Math.random() * cols);

                if (gbArr[mineJ][mineK].isIsMine() == false)
                {
                    gbArr[mineJ][mineK].setIsMine(true);
                    break;
                }

            }

            //incrementing int nearbymines
            for (int j = -1; j < 2; j++)
            {

                for (int k = -1; k < 2; k++)
                {

                    if (mineJ + j < 0 || mineJ + j > rows - 1 || mineK + k < 0 || mineK + k > cols - 1)
                    {

                    } else if (gbArr[mineJ + j][mineK + k].isIsMine() == false)

                    {
                      
                        gbArr[mineJ + j][mineK + k].setNearbyMines(gbArr[mineJ + j][mineK + k].incrementNearbyMines());
                     
                    }

                }
            }

        }

    }

    public Thread timer()
    {
        Thread t = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                gameOver = false;

                //a way to stop a run method is have the run method come to an
                //end
                while (!gameOver)
                {

                    try
                    {

                        Thread.sleep(1000);
                        seconds2++;
                   
                    } catch (InterruptedException ex)
                    {

                    }
                    //you need swing utilities bec. multi threading is a real
                    //art and you need it to be safe, a race condition
                    SwingUtilities.invokeLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                           
                            //zev's way. Like this you'll get seconds and minutes
                            //min are /60 and what ever remains %60 is the leftover
                            //seconds
                            String disTime = String.format("%02d:%02d ", seconds2 / 60, seconds2 % 60);
                            timerLabel.setText(disTime);
                        }
                    });
                }
            }
        });
        t.start();
        return t;
    }

    //passing through level so can access it for highscore
    public void uncoverCell(int r, int c, String level)
    {

        //when you come back around you don't want to automatically uncover

        if (gbArr[r][c].isIsMine() == false && gbArr[r][c].getIcon() == null)
        {
            winCounter++;
        }
        //this is so that if flagged it won't uncover
        if (gbArr[r][c].getIcon() == null)
        {
            gbArr[r][c].uncover();
        }

        winner(level);

        if (gbArr[r][c].getNearbyMines() > 0 || gbArr[r][c].isIsMine() == true)
        {
            return;
        }

        for (int i = -1; i < 2; i++)
        {
            for (int j = -1; j < 2; j++)
            {
                if (r + i < 0 || c + j < 0 || r + i > rows - 1 || c + j > cols - 1 || (i == 0 && j == 0) /*|| (r + i == 0 && c + j == 0)*/)
                {

                } else if (gbArr[r + i][c + j].isIsMine() == true || gbArr[r + i][c + j].isUncovered() == true || gbArr[r][c].getIcon() != null)
                {

                } //this was not to check diagnals but the game does check
                //                    else if (i + j == 2 || i + j == 0 || i + j == -2)
                //                {
                //
                //                }
                else
                {

                    uncoverCell(r + i, c + j, level);

                }
            }
        }
    }

    //passing through level so can access it for highscore
    public void winner(String level)
    {

        if (winCounter == (rows * cols) - mines)
        {
            gameOver = true;

            JOptionPane.showMessageDialog(null, "You Won!");

            for (int i = 0; i < gbArr.length; i++)
            {
                for (int j = 0; j < gbArr[i].length; j++)
                {

                    if (gbArr[i][j].isIsMine())
                    {
                        gbArr[i][j].uncover();
                        gbArr[i][j].setBackground(Color.green);

                    }

                }

            }
            HighScore hs = new HighScore(level, " ", timerLabel.getText());
            hsc.add(hs);
            hsRead = true;
            JOptionPane.showMessageDialog(null, hsc.toString());
        }
    }

    public static void main(String[] args)
    {
        new MineSweeper(9, 9, 10, "Easy").setVisible(true);
    }
}
