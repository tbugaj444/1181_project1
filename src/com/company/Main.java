package com.company;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {
    static Scanner in = new Scanner(System.in);
    static long sizeOfFile;


    public static void main(String[] args) {

        RandomAccessFile file = ifFileExists();


        boolean exit = true;
//
//        addItem(file);
//        addItem(file);
//        addItem(file);

        deleteItem(file, 1);


//        while(exit){
//            System.out.println("Please enter the number of the option you wish to perform\n" +
//                    "1. Add Item\n" +
//                    "2. Delete Item\n" +
//                    "3. Search Item\n" +
//                    "4. Stats\n" +
//                    "5. View Items\n" +
//                    "6. Exit\n");
//            int choice = getOption();
//
//        }

        //loop menu
        try {
            System.out.println(file.length());
            System.out.println(sizeOfFile);
            System.out.println(Food.sizeInBytes);
            System.out.println(file.readUTF());
            deleteItem(file, 1);
            System.out.println(file.readUTF());

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static int getOption() {
        int temp = 0;
        int choice = 0;

        while (choice == 0) {
            try {
                temp = Integer.parseInt(in.nextLine());
            } catch (Exception e) {
                System.out.println("invalid input");
                temp = 0;
            }
            if (temp <= 0 || temp >= 7) {
                System.out.println("Please enter the number of the option you wish to perform");
            } else {
                choice = temp;
            }
        }
        return choice;

    }

    private static RandomAccessFile ifFileExists() {
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile("data.dat", "rw");
            file.seek(0);
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            sizeOfFile = file.length() / Food.sizeInBytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

//}
    private static void addItem(RandomAccessFile file){
        System.out.println("Add Item:\n" +
                "Enter the Name of the Food Item");
        String name = in.nextLine();
        System.out.println("Enter the Expiration date(01/01/2000)");
        String date = in.nextLine();

        System.out.println("Enter the Item Weight:");
        double weight =0;
        do{
            try{
                weight = Double.parseDouble(in.nextLine());
            }catch (Exception e){
                System.out.println("Please enter a number");
                weight =0;
            }
            if(weight<0){
                System.out.println("Enter a positive number");
                weight =0;
            }
        }while(weight==0);

        System.out.println("How many do you have?");
        int quant =0;
        do{
            try{
                quant = Integer.parseInt(in.nextLine());
            }catch (Exception e){
                System.out.println("Please enter a number");
                quant =0;
            }
            if(quant<0){
                System.out.println("Enter a positive number");
                quant =0;
            }
        }while(quant==0);

        System.out.println("How many Calories are they each?");
        int cal =0;
        do{
            try{
                cal = Integer.parseInt(in.nextLine());
            }catch (InputMismatchException e){
                System.out.println("Please enter a number");
                cal =0;

            }
            if(cal<0){
                System.out.println("Enter a positive number");
                cal =0;
            }
        }while(cal==0);


        Food f =null;
        try{
            f = new Food(name, date, weight, quant, cal);
        }catch(Exception e){
            System.out.println("something wrong");
        }
        try{
            file = ifFileExists();
            file.seek(file.length());
            file.writeUTF(f.toString());

        }catch (IOException e){
            System.out.println("Failed to read/write");
        }finally {
            try{
                file.close();
            }catch (Exception e){
                System.out.println("things are bad");
            }
        }

    }
    public static void deleteItem(RandomAccessFile file, int index){
       // int remaining = sizeOfFile -index;
        try{
            file = ifFileExists();
            file.seek((long) index*Food.sizeInBytes);
            String temp = file.readLine();
            file.setLength((long) index*Food.sizeInBytes-1*Food.sizeInBytes);
            file.writeBytes(temp);
        } catch (IOException e) {
            System.out.println("file is not open");
            e.printStackTrace();
        }

    }
    //Function to Delete
    //Function to Search
    //Function Stats
}
