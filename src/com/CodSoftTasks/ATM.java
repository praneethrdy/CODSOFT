package com.internship;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;

public class ATM extends JFrame {
    private BankAccount account;
    private JTextField amountField;
    private JLabel messageLabel;
    private JLabel balanceLabel;

    public ATM() {
        // Initialize a bank account with a default balance
        account = new BankAccount(0);  // Initial balance of ₹10,000
        createATMInterface();
    }

    private void createATMInterface() {
       
        setTitle("ATM Interface");
        setSize(600, 500);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout());  

       
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("ATM Machine");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36)); 
        titleLabel.setForeground(Color.WHITE);
        titlePanel.setBackground(new Color(25, 118, 210)); 
        titlePanel.add(titleLabel);

    
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2, 10, 10));  
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));  
        inputPanel.setBackground(Color.WHITE);

        JLabel enterAmountLabel = new JLabel("Enter Amount (₹):");
        enterAmountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        amountField = new JTextField(10);
        amountField.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        JButton depositButton = createCustomButton("Deposit", new Color(76, 175, 80));
        JButton withdrawButton = createCustomButton("Withdraw", new Color(244, 67, 54));
        JButton checkBalanceButton = createCustomButton("Check Balance", new Color(33, 150, 243));

        inputPanel.add(enterAmountLabel);
        inputPanel.add(amountField);
        inputPanel.add(depositButton);
        inputPanel.add(withdrawButton);
        inputPanel.add(checkBalanceButton);

    
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout(10, 10));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        infoPanel.setBackground(new Color(238, 238, 238));  

        messageLabel = new JLabel("Welcome to the ATM!");
        messageLabel.setFont(new Font("Segoe UI", Font.ITALIC, 18));
        messageLabel.setForeground(new Color(0, 128, 128)); 
        
        balanceLabel = new JLabel("Current Balance: ₹" + formatCurrency(account.getBalance()));
        balanceLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        balanceLabel.setForeground(new Color(0, 128, 128)); 

        infoPanel.add(messageLabel, BorderLayout.NORTH);
        infoPanel.add(balanceLabel, BorderLayout.SOUTH);

    
        add(titlePanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);

        // Button Listeners
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDeposit();
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleWithdraw();
            }
        });

        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBalance();
            }
        });
    }


    private JButton createCustomButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));  // Change cursor to hand
        return button;
    }

   
    private void handleDeposit() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            account.deposit(amount);
            JOptionPane.showMessageDialog(this, "Successfully Deposited: ₹" + formatCurrency(amount));
            balanceLabel.setText("Current Balance: ₹" + formatCurrency(account.getBalance()));
            amountField.setText(""); 
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void handleWithdraw() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (account.withdraw(amount)) {
                JOptionPane.showMessageDialog(this, "Successfully Withdrew: ₹" + formatCurrency(amount));
            } else {
                JOptionPane.showMessageDialog(this, "Insufficient balance. Transaction failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            balanceLabel.setText("Current Balance: ₹" + formatCurrency(account.getBalance()));
            amountField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

  
    private void checkBalance() {
        JOptionPane.showMessageDialog(this, "Your current balance is: ₹" + formatCurrency(account.getBalance()));
        messageLabel.setText("Balance checked.");
    }


    private String formatCurrency(double amount) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        return formatter.format(amount);
    }

    public static void main(String[] args) {
      
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ATM().setVisible(true);
            }
        });
    }
}
