package com.CodSoftTasks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

public class StudentGradeCalculator extends JFrame {
    private JTextField[] markFields;
    private JTextField numSubjectsField;
    private JLabel totalMarksLabel, averagePercentageLabel, gradeLabel;
    private JButton calculateButton;
    private float titleAlpha = 0.0f; 

    public StudentGradeCalculator() {
       
        setTitle("Marks Calculator");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        getContentPane().setLayout(new BorderLayout());

        
        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color color1 = new Color(0, 153, 204); 
                Color color2 = new Color(0, 51, 102); 
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };
        gradientPanel.setLayout(new GridLayout(7, 2, 10, 10));
        gradientPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        
        JLabel titleLabel = new JLabel("Marks Calculator", JLabel.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, titleAlpha)); 
                super.paintComponent(g);
            }
        };
        titleLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 30));
        titleLabel.setForeground(new Color(102, 51, 255)); 
        getContentPane().add(titleLabel, BorderLayout.NORTH);

        // Input for number of subjects
        JLabel subjectLabel = new JLabel("Number of Subjects:");
        subjectLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        subjectLabel.setForeground(Color.WHITE); 
        gradientPanel.add(subjectLabel);

        numSubjectsField = new JTextField();
        gradientPanel.add(numSubjectsField);

  
        totalMarksLabel = new JLabel("Total Marks: ");
        totalMarksLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        totalMarksLabel.setForeground(Color.WHITE);
        gradientPanel.add(totalMarksLabel);

        averagePercentageLabel = new JLabel("Average Percentage: ");
        averagePercentageLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        averagePercentageLabel.setForeground(Color.WHITE);
        gradientPanel.add(averagePercentageLabel);

        gradeLabel = new JLabel("Grade: ");
        gradeLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        gradeLabel.setForeground(Color.WHITE);
        gradientPanel.add(gradeLabel);

       
        getContentPane().add(gradientPanel, BorderLayout.CENTER);

   
        calculateButton = new JButton("Enter Marks and Calculate") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
            }
        };
        calculateButton.setBackground(new Color(255, 255, 255)); 
        calculateButton.setForeground(new Color(102, 51, 255)); 
        calculateButton.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
        calculateButton.setFocusPainted(false);
        calculateButton.setBorder(new EmptyBorder(16, 16, 15, 16));
        calculateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));


      
        calculateButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                new Timer(10, new ActionListener() {
                    float brightness = 1.0f;
                    public void actionPerformed(ActionEvent e) {
                        brightness -= 0.05f;
                        if (brightness < 0.85f) {
                            ((Timer) e.getSource()).stop();
                        }
                        calculateButton.setBackground(new Color((int) (70 * brightness), (int) (130 * brightness), (int) (180 * brightness)));
                    }
                }).start();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                new Timer(10, new ActionListener() {
                    float brightness = 0.85f;
                    public void actionPerformed(ActionEvent e) {
                        brightness += 0.05f;
                        if (brightness >= 1.0f) {
                            ((Timer) e.getSource()).stop();
                        }
                        calculateButton.setBackground(new Color((int) (70 * brightness), (int) (130 * brightness), (int) (180 * brightness)));
                    }
                }).start();
            }
        });

        getContentPane().add(calculateButton, BorderLayout.SOUTH);


        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numSubjects = Integer.parseInt(numSubjectsField.getText());
                markFields = new JTextField[numSubjects];

             
                JPanel marksPanel = new JPanel(new GridLayout(numSubjects, 2, 10, 10));
                for (int i = 0; i < numSubjects; i++) {
                    marksPanel.add(new JLabel("Marks in Subject " + (i + 1) + ":"));
                    markFields[i] = new JTextField();
                    marksPanel.add(markFields[i]);
                }

         
                int result = JOptionPane.showConfirmDialog(null, marksPanel, "Enter Marks", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    calculateResults(numSubjects);
                }
            }
        });

        new Timer(30, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                titleAlpha += 0.05f;
                if (titleAlpha >= 1.0f) {
                    titleAlpha = 1.0f;
                    ((Timer) e.getSource()).stop();
                }
                titleLabel.repaint(); 
            }
        }).start();
    }

   
    private void calculateResults(int numSubjects) {
        int totalMarks = 0;

      
        for (int i = 0; i < numSubjects; i++) {
            int marks = Integer.parseInt(markFields[i].getText());
            totalMarks += marks;
        }

        double averagePercentage = (double) totalMarks / numSubjects;

      
        String grade;
        if (averagePercentage >= 90) {
            grade = "A";
        } else if (averagePercentage >= 80) {
            grade = "B";
        } else if (averagePercentage >= 70) {
            grade = "C";
        } else if (averagePercentage >= 60) {
            grade = "D";
        } else {
            grade = "F";
        }

        totalMarksLabel.setText("Total Marks: " + totalMarks);
        averagePercentageLabel.setText("Average Percentage: " + String.format("%.2f", averagePercentage) + "%");
        gradeLabel.setText("Grade: " + grade);
    }

    public static void main(String[] args) {
      
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StudentGradeCalculator().setVisible(true);
            }
        });
    }
}
