package com.company;

public class ConsoleApp {
    public static void main(String[] args) {
	// write your code here
        if(args.length==2){
            MyXml.AverageCheck(args[0], args[1]);
            //MyXml.AverageCheck("F:\\study\\java\\java_ee_lw7\\src\\lw7.xml", "F:\\study\\java\\java_ee_lw7\\src\\lw7_output.xml");
        }
        else {
            System.out.println("Invalid number of arguments");
        }
    }
}
