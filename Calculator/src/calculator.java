/*
 * Brian Johnson
 * Assignment 2
 * Infix Calculator
 * Updated with GUI
 */
import java.awt.Color;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class calculator implements ActionListener{
	static JFrame f;
	static JTextField l;
	//creates the stack for values
	static Stack<Double> vals = new Stack<Double>();
	//creates the stack for operators
	static Stack<Character> opers = new Stack<Character>();
	
	//number buttons
	public static JButton zero = new JButton("0");
	public static JButton one = new JButton("1");
	public static JButton two = new JButton("2");
	public static JButton three = new JButton("3");
	public static JButton four = new JButton("4");
	public static JButton five = new JButton("5");
	public static JButton six = new JButton("6");
	public static JButton seven = new JButton("7");
	public static JButton eight = new JButton("8");
	public static JButton nine = new JButton("9");
	
	//operator buttons
	public static JButton add = new JButton("+");
	public static JButton sub = new JButton("-");
	public static JButton mul = new JButton("*");
	public static JButton div = new JButton("/");
	public static JButton dec = new JButton(".");
	public static JButton clear = new JButton("C");
	public static JButton equal = new JButton("=");

	String op, val1, val2;
	
	calculator() {
		op = val1 = val2 = "";
	}
	
	public static void main(String[] args) {
		f = new JFrame("Infix Calculator");
		l = new JTextField(16);
		l.setEditable(false);
		JPanel p = new JPanel();
		p.setBackground(Color.gray);
		f.add(p);
		f.setSize(300,300);
		f.show();
		calculator c = new calculator();
		
		p.add(l);
		p.add(zero);
		p.add(one);
		p.add(two);
		p.add(three);
		p.add(four);
		p.add(five);
		p.add(six);
		p.add(seven);
		p.add(eight);
		p.add(nine);
		p.add(add);
		p.add(sub);
		p.add(mul);
		p.add(div);
		p.add(equal);
		p.add(clear);
		p.add(dec);
		
		one.addActionListener(c);
		two.addActionListener(c);
		three.addActionListener(c);
		four.addActionListener(c);
		five.addActionListener(c);
		six.addActionListener(c);
		seven.addActionListener(c);
		eight.addActionListener(c);
		nine.addActionListener(c);
		add.addActionListener(c);
		sub.addActionListener(c);
		mul.addActionListener(c);
		div.addActionListener(c);
		clear.addActionListener(c);
		dec.addActionListener(c);
		equal.addActionListener(c);
		
	}
	//this method is to see the precedence of two operators
	//it resturns true if oper2 is higher or same as oper1, else it is false
public static boolean precedence(char oper1, char oper2) {
		
		if(oper2 == '(' || oper1 == ')') {
			return false;
		}
		if((oper1 == '-' || oper1 == '+') && (oper2 == '*' || oper2 == '/')) {
			return false;
		} else {
			return true;
		}
	}


public static double calc1(char[] ops) {
	/*=============================================
	* OPERANDS (this section deals with values)
	=============================================*/
	//if the element is a space, it skips it
	for(int i = 0; i<ops.length;i++) {
		if(ops[i] == ' ') {
			continue;
		}
		//checks to see if the element is a number, then adds it to the "vals" stack
		if(ops[i] >= '0' && ops[i] <= '9' || ops[i] == '.') {
			//creates a StringBuffer to be able to manipulate the characters
			StringBuffer sb = new StringBuffer();
			//checks if the number has multiple digits
			while(i<ops.length && ops[i] >= '0' && ops[i] <= '9') {
				sb.append(ops[i++]); //appends the next element
				//converts the element to a string, and then to an integer, then it is added to the vals stack
				vals.push(Double.parseDouble(sb.toString()));
			}
			i--; //since we increased the spot of i, we have to put it back
		
		/*=================================================
		 * OPERATORS and CALCULATION (this section deals with the calculation)
		 =================================================*/
		} else if (ops[i] == '(') { //if element is open parentheses, add to opers stack
			opers.push(ops[i]); //adds to opers stack
		} else if (ops[i] == ')') { //if element is close parentheses, solve whats in it
			while(opers.peek() != '(' ) {
				vals.push(calc2(opers.pop(), vals.pop(),vals.pop()));
			}
			opers.pop(); //pops parentheses out of stack
		} else if (ops[i] == '*' || ops[i] == '/' || ops[i] == '+' || ops[i] == '-') {
			while(!opers.empty() && precedence(ops[i], opers.peek())) {
				//calls the "calc" menthod with given parameters
				//then what is returned from the method is pushed into the vals stack
				vals.push(calc2(opers.pop(),vals.pop(),vals.pop()));
			}
			opers.push(ops[i]); //pushes the current element to the opers stack
		}
	}
	while(!opers.empty()) {
		//calls the "calc" menthod with given parameters
		//then what is returned from the method is pushed into the vals stack
		vals.push(calc2(opers.pop(),vals.pop(),vals.pop()));
	}
	//returns the answer for the calculation
	return vals.pop();

}
//this method calculates the result between an operand and two values and returns it
public static double calc2(char op, double val1, double val2) {
	//in try catch block to handle a divide by zero case
	try {
	//switch case to determine which operand to use
	switch(op) {
	case '*':
		return val1 * val2;
	case '/':
		return val1 / val2;
	case '+':
		return val1 + val2;
	case '-':
		return val1 - val2;
	}
	//prints error
	}catch (Exception e) {
		System.out.println("Error: Cannot divide by zero");
	}
	return 0;
}

	@Override
	//this method is for button actions
	public void actionPerformed(ActionEvent e) {
		String exp = e.getActionCommand();
		
		if ((exp.charAt(0) >= '0' && exp.charAt(0) <= '9') || exp.charAt(0) == '.' || exp.charAt(0) == '+'|| exp.charAt(0) == '-'|| exp.charAt(0) == '*'|| exp.charAt(0) == '/') {
            
            if (!val1.equals(""))
                val2 = val2 + exp;
            else
                op = op + exp;
 
            
            l.setText(op + val1 + val2);
        }
        else if (exp.charAt(0) == 'C') {
            
            op = val1 = val2 = "";
 
            
            l.setText(op + val1 + val2);
        } else if (exp.charAt(0) == '=') {
        	String screen = l.getText();
        	char[] ops = screen.toCharArray();
        	double result = calc1(ops);
        	String result2 = Double.toString(result);
        	l.setText(result2);
        }
		
	}

		
	}


