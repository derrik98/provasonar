package com.daniele.ispw.calculator;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Calculator extends Application {

    public Button number_0 = new Button();
    public Button number_1 = new Button();
    public Button number_2 = new Button();
    public Button number_3 = new Button();
    public Button number_4 = new Button();
    public Button number_5 = new Button();
    public Button number_6 = new Button();
    public Button number_7 = new Button();
    public Button number_8 = new Button();
    public Button number_9 = new Button();
    private String OPERATION = "";
    private String OP1 = "";
    private final String ERROR = "Error";
    private String SYMBOL = "";
    private final ArrayList<String> list = new ArrayList<>();
    Button b = new Button();


    @FXML
    private TextField display = new TextField("");
    @FXML
    private TextField displayRes = new TextField("");

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Calculator.class.getResource("calculator-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 500);
        stage.setTitle("Calculator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @FXML

    public void selectNumber(MouseEvent mouseEvent) {
        b = (Button) mouseEvent.getSource();
        if (OPERATION.equals(ERROR)) {
            list.clear();
            displayRes.setText("");
            OPERATION = "";
            OP1 = b.getText();
            list.add(OP1);
            SYMBOL = "";
            OPERATION = b.getText();
            display.setText(OPERATION);
        } else {
            OP1 += b.getText();
            OPERATION += b.getText();
            if (OP1.length() > 1) {
                list.set(list.size() - 1, OP1);
                calculateResult(list);
            } else {
                list.add(OP1);
                calculateResult(list);
            }
            display.setText(OPERATION);
        }
    }

    public void selectSymbol(MouseEvent mouseEvent) {
        b = (Button) mouseEvent.getSource();
        if (!SYMBOL.equals("")) {
            OP1 = displayRes.getText();
        }
        OPERATION += b.getText();
        SYMBOL = b.getText();
        OP1 = "";
        display.setText(OPERATION);
        list.add(SYMBOL);
    }

    public void calculateResult(ArrayList<String> als) {

        ArrayList<String> alsCopy = new ArrayList<>(als);
        if(alsCopy.get(0).equals("+") || alsCopy.get(0).equals("-") || alsCopy.get(0).equals("*") || alsCopy.get(0).equals("/") || alsCopy.get(0).equals(".") || alsCopy.get(0).equals("^")){
            OPERATION = ERROR;
            displayRes.setText(ERROR);
            return;
        }
        while ((!(alsCopy.size() == 1))) {
            for (int i = 0; i <= alsCopy.size() - 1; i++) {
                if (alsCopy.get(i).equals("^") || alsCopy.get(i).equals("/") || alsCopy.get(i).equals("*")) {
                    String o1Copy = alsCopy.get(i - 1);
                    String o2Copy = alsCopy.get(i + 1);
                    String symbolCopy = alsCopy.get(i);
                    alsCopy.remove(i + 1);
                    alsCopy.remove(i);
                    alsCopy.remove(i - 1);
                    alsCopy.add(i - 1, displayResult(o1Copy, o2Copy, symbolCopy));
                }
            }
            for (int i = 0; i <= alsCopy.size() - 1; i++) {
                if ((alsCopy.get(i).equals("-") || alsCopy.get(i).equals("+"))) {
                    String o1Copy = alsCopy.get(i - 1);
                    String o2Copy = alsCopy.get(i + 1);
                    String symbolCopy = alsCopy.get(i);
                    alsCopy.remove(i + 1);
                    alsCopy.remove(i);
                    alsCopy.remove(i - 1);
                    String result = displayResult(o1Copy, o2Copy, symbolCopy);
                    alsCopy.add(i - 1, result);
                }
            }

        }
    }

    public String displayResult(String op1, String op2, String symbol) {
        if (!op1.equals("") && !op2.equals("") && !symbol.equals("")) {
            switch (symbol) {
                case "+" -> {
                    displayRes.setText(sum(Double.parseDouble(op1), Double.parseDouble(op2)));
                    return sum(Double.parseDouble(op1), Double.parseDouble(op2));
                }
                case "-" -> {
                    displayRes.setText(sub(Double.valueOf(op1), Double.valueOf(op2)));
                    return sub(Double.valueOf(op1), Double.valueOf(op2));
                }
                case "*" -> {
                    displayRes.setText(mul(Double.valueOf(op1), Double.valueOf(op2)));
                    return mul(Double.valueOf(op1), Double.valueOf(op2));
                }
                case "/" -> {
                    displayRes.setText(div(Double.valueOf(op1), Double.valueOf(op2)));
                    return div(Double.valueOf(op1), Double.valueOf(op2));
                }
                case "^" -> {
                    displayRes.setText(exp(Double.valueOf(op1), Double.valueOf(op2)));
                    return exp(Double.valueOf(op1), Double.valueOf(op2));
                }
            }
        }
        if (op1.equals("") && !op2.equals("") && symbol.equals("√")) {
            displayRes.setText(radq(Double.valueOf(op2)));
            OPERATION = display.getText();
            return radq(Double.valueOf(op2));
        } else if (op2.equals("") && symbol.equals("")) {
            displayRes.setText("");
            return "";
        } else {
            displayRes.setText(ERROR);
            return  ERROR;
        }

    }

    //Implementazione tasto somma
    private String sum(double num1, double num2) {
        if ((num1 + num2) == (int) (num1 + num2)) {
            return String.valueOf((int) (num1 + num2));
        } else {
            return String.valueOf(num1 + num2);
        }
    }

    //Implementazione tasto radice quadrata
    protected String radq(Double num1) {
        if (Math.sqrt(num1) == (int) Math.sqrt(num1)) {
            return String.valueOf((int) Math.sqrt(num1));
        } else {
            return String.valueOf(Math.sqrt(num1));
        }
    }

    //Implementazione tasto elevazione di potenza
    protected String exp(Double num1, Double num2) {
        if (Math.pow(num1, num2) == (int) Math.pow(num1, num2)) {
            return String.valueOf((int) Math.pow(num1, num2));
        } else {
            return String.valueOf(Math.pow(num1, num2));
        }
    }

    //Implementazione tasto di divisione
    protected String div(Double num1, Double num2) {
        if (num2 == 0) {
            return ERROR;
        } else if ((num1 / num2) == (int) (num1 / num2)) {
            return String.valueOf((int) (num1 / num2));
        } else {
            return String.valueOf(num1 / num2);
        }
    }

    //Implementazione tasto di moltiplicazione
    protected String mul(Double num1, Double num2) {
        if ((num1 * num2) == (int) (num1 * num2)) {
            return String.valueOf((int) (num1 * num2));
        } else {
            return String.valueOf(num1 * num2);
        }
    }

    //Implementazione tasto di sottrazione
    protected String sub(Double num1, Double num2) {
        if ((num1 - num2) == (int) (num1 - num2)) {
            return String.valueOf((int) (num1 - num2));
        } else {
            return String.valueOf(num1 - num2);
        }
    }


    //Implementazione tasto C di calcellazione generale
    public void delete() {
        OPERATION = "";
        SYMBOL = "";
        OP1 = "";
        display.setText("");
        displayRes.setText("");
        list.clear();
    }

    //Implementazione tasto "uguale"
    public void getResult() {
        OP1 = displayRes.getText();
        SYMBOL = "";
        OPERATION = OP1;
        display.setText(OPERATION);
        displayRes.clear();
        list.clear();
        list.add(OP1);
    }



}