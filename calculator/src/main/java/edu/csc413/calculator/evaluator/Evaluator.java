package edu.csc413.calculator.evaluator;



import edu.csc413.calculator.exceptions.InvalidTokenException;
import edu.csc413.calculator.operators.*;

import java.util.Stack;
import java.util.StringTokenizer;

public class Evaluator {

  private Stack<Operand> operandStack;
  private Stack<Operator> operatorStack;
  private StringTokenizer expressionTokenizer;
  private final String delimiters = " +/*-^()";

  public Evaluator() {
    operandStack = new Stack<>();
    operatorStack = new Stack<>();
  }

  public int evaluateExpression(String expression) throws InvalidTokenException {
    String expressionToken;

    // The 3rd argument is true to indicate that the delimiters should be used
    // as tokens, too. But, we'll need to remember to filter out spaces.
    this.expressionTokenizer = new StringTokenizer(expression, this.delimiters, true);

    // initialize operator stack - necessary with operator priority schema
    // the priority of any operator in the operator stack other than
    // the usual mathematical operators - "+-*/" - should be less than the priority
    // of the usual operators


    while (this.expressionTokenizer.hasMoreTokens()) {
      // filter out spaces
      if (!(expressionToken = this.expressionTokenizer.nextToken()).equals(" ")) {
        // check if token is an operand
        if (Operand.check(expressionToken)) {
          operandStack.push(new Operand(expressionToken));
        } else {
          if (!Operator.check(expressionToken)) {
            throw new InvalidTokenException(expressionToken);
            // if expressionToken is ")" is in map

          } else if (")".equals(expressionToken)) {
            // perform the operations until you reach "("
            while (operatorStack.peek().priority() != 0) {
              Operator operatorFromStack = operatorStack.pop();
              Operand operandOne = operandStack.pop();
              Operand operandTwo = operandStack.pop();
              Operand result = operatorFromStack.execute(operandOne, operandTwo);
              operandStack.push(result);

            }
            //When done performing operations pop the "(" off the Operator Stack
            operatorStack.pop();

            // If "(" is expressionToken push it to Operator Stack
          } else if ("(".equals(expressionToken)) {
            operatorStack.push(new OpenParOperator());

          } else {
            Operator newOperator = Operator.getOperator(expressionToken);

            // If operator Stack is not  empty
            if (!operatorStack.empty()) {

              //while the last operator in the stack has greater priority then the new one and both stacks are not empty evaluate
              while ((operatorStack.peek().priority() >= newOperator.priority() && (!operatorStack.isEmpty()) && (operandStack.size() >= 1))) {
                Operator operatorFromStack = operatorStack.pop();
                Operand operandTwo = operandStack.pop();
                Operand operandOne = operandStack.pop();
                Operand result = operatorFromStack.execute(operandOne, operandTwo);
                operandStack.push(result);
              }
            }

            operatorStack.push(newOperator);
          }


        }

      }
    }


    // When operator stack is not empty perform the operation
    while (!operatorStack.empty()) {
      Operator operatorFromStack = operatorStack.pop();
      Operand operandTwo = operandStack.pop();
      Operand operandOne = operandStack.pop();
      Operand result = operatorFromStack.execute(operandOne, operandTwo);
      operandStack.push(result);
    }

    return (operandStack.pop().getValue());
  }

}
