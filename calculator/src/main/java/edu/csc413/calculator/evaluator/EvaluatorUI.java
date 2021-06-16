package edu.csc413.calculator.evaluator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EvaluatorUI extends JFrame implements ActionListener {

    private TextField expressionTextField = new TextField();
    private Panel buttonPanel = new Panel();
    Evaluator ExecuteEval = new Evaluator();

    // total of 20 buttons on the calculator,
    // numbered from left to right, top to bottom
    // bText[] array contains the text for corresponding buttons
    private static final String[] buttonText = {
        "7", "8", "9", "+", "4", "5", "6", "- ", "1", "2", "3",
        "*", "0", "^", "=", "/", "(", ")", "C", "CE"
    };
    public String removeLast(String str) {
        if (str != null && str.length() > 0) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    /**
     * C  is for clear, clears entire expression
     * CE is for clear expression, clears last entry up until the last operator.
     */
    private Button[] buttons = new Button[buttonText.length];

    public static void main(String argv[]) {
        new EvaluatorUI();
    }

    public EvaluatorUI() {
        setLayout(new BorderLayout());
        this.expressionTextField.setPreferredSize(new Dimension(600, 50));
        this.expressionTextField.setFont(new Font("Courier", Font.BOLD, 28));

        add(expressionTextField, BorderLayout.NORTH);
        expressionTextField.setEditable(false);

        add(buttonPanel, BorderLayout.CENTER);
        buttonPanel.setLayout(new GridLayout(5, 4));

        //create 20 buttons with corresponding text in bText[] array
        Button tempButtonReference;
        for (int i = 0; i < EvaluatorUI.buttonText.length; i++) {
            tempButtonReference = new Button(buttonText[i]);
            tempButtonReference.setFont(new Font("Courier", Font.BOLD, 28));
            buttons[i] = tempButtonReference;
        }

        //add buttons to button panel
        for (int i = 0; i < EvaluatorUI.buttonText.length; i++) {
            buttonPanel.add(buttons[i]);
        }

        //set up buttons to listen for mouse input
        for (int i = 0; i < EvaluatorUI.buttonText.length; i++) {
            buttons[i].addActionListener(this);
        }

        setTitle("Calculator");
        setSize(400, 400);
        setLocationByPlatform(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * This function is triggered anytime a button is pressed
     * on our Calculator GUI.
     * @param actionEventObject Event object generated when a
     *                    button is pressed.
     */
    public void actionPerformed(ActionEvent actionEventObject) {
            //checks if button is not = , c , or ce
        if (!actionEventObject.getSource().equals(buttons[14]) && !actionEventObject.getSource().equals(buttons[18]) && !actionEventObject.getSource().equals(buttons[19])) {
            int buttonCount = 0;
            while (buttonCount < 20) {
                if (actionEventObject.getSource().equals(buttons[buttonCount])) {
                    expressionTextField.setText(expressionTextField.getText() + buttonText[buttonCount]);
                    System.out.println(expressionTextField.getText());

                }
                buttonCount++;
            }
           // checks if button is ce then clears last opperand
        } else if (actionEventObject.getSource().equals(buttons[19])) {
            String lastText = expressionTextField.getText();
            if (!lastText.equals("")) {
                expressionTextField.setText( removeLast( expressionTextField.getText() ) );

            }
            // checks if button is c then clears everthing
        } else if (actionEventObject.getSource().equals(buttons[18])) {
            expressionTextField.setText("");

            // checks if button is = then evaluates expression
        } else if (actionEventObject.getSource().equals(buttons[14])) {
            try {
                expressionTextField.setText(Integer.toString(ExecuteEval.evaluateExpression(expressionTextField.getText())));
            } catch (Exception e) {
                expressionTextField.setText("error");

            }


        }

    }


}

