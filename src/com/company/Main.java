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



        while(exit){
            System.out.println("Please enter the number of the option you wish to perform\n" +
                    "1. Add Item\n" +
                    "2. Delete Item\n" +
                    "3. Search Item\n" +
                    "4. Stats\n" +
                    "5. View Items\n" +
                    "6. Exit\n");
            int choice = getOption();
            switch (choice){
                case(1):{
                    addItem(file);
                }break;
                case(2):{
                    System.out.println("Which number item do you want to delete?");
                    int i = Integer.parseInt(in.nextLine());
                    deleteItem(file, i);
                }break;
                case(3):{
                    System.out.println("Enter the name of the food item you are looking for");
                    String s = in.nextLine();
                    System.out.println(searchItem(file, s));
                }break;
                case(4):{
                    stats(file);
                }break;
                case(5):{
                    printItems(file);
                }break;
                case(6):{
                    exit =false;
                }break;
                default:{
                    System.out.println("INVALID");
                    exit = false;
                }
            }

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
            file.writeBytes(f.getName());
            file.writeBytes(f.getExpDate());
            file.writeDouble(f.getWeight());
            file.writeInt(f.getQuant());
            file.writeInt(f.getCalories());

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
        try{
            file = ifFileExists();
            file.seek((long) index*Food.sizeInBytes);
            String temp = file.readLine();
            file.setLength((long) index*Food.sizeInBytes-1*Food.sizeInBytes);
            file.writeBytes(temp);
        } catch (IOException e) {
            System.out.println("file is not open");
            e.printStackTrace();
        }finally{
            try{
                file.close();
            }catch (Exception e){
                System.out.println("things are bad");
            }
        }

    }
    public static String searchItem(RandomAccessFile file, String name){
        String temp = null;
        byte arr[] = new byte[Food.sizeInBytes];
        try{
            file = ifFileExists();
            for(int i = 0;i<sizeOfFile;i++){
                try {
                    file.read(arr, 0, Food.sizeOfString);
                    temp = new String(arr);
                    file.seek((i+1)*Food.sizeInBytes);


                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(temp.substring(0, Food.sizeOfString).trim().equals(name)){
                    file.seek(i*Food.sizeInBytes);
                    file.read(arr, 0, Food.sizeInBytes);
                    temp = new String(arr);
                    return "Item found at Index "+(i+1)+" "+temp;
                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try{
                file.close();
            }catch (Exception e){
                System.out.println("things are bad");
            }
        }
        return "Food not Found";
    }
    public static void stats(RandomAccessFile file){
        String temp = null;
        double weight[] = new double[(int)sizeOfFile];
        int quant[] = new int[(int)sizeOfFile];
        int cal[] = new int[(int)sizeOfFile];

        try{
            file = ifFileExists();
            for(int i = 0;i<sizeOfFile;i++){
                file.seek(i*(Food.sizeInBytes)+(2*Food.sizeOfString));
                System.out.println(file.getFilePointer());
                weight[i] = file.readDouble();
                quant[i] = file.readInt();
                cal[i] = file.readInt();
            }
        }catch (Exception e){
            e.printStackTrace();
            try {
                System.out.println(file.length());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }finally {
            try{
                file.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        double ttlweight = 0, avgweight =0,
         avgcal =0, ttlquant =0;

        for(int i =0;i<weight.length;i++){
            ttlweight+=(weight[i]*quant[i]);
            avgweight+=weight[i];
            avgcal+=cal[i];
            ttlquant+=quant[i];
        }
        avgcal/=cal.length;
        avgweight/=weight.length;

        System.out.printf("Data.dat Stats:\n" +
                "Total weight of all food items: %.2f\n" +
                "Average weight: %.2f\n" +
                "Total amount of food items: %.1f\n" +
                "Average Calorie count: %.1f\n", ttlweight, avgweight, ttlquant, avgcal);

    }
    public static void printItems(RandomAccessFile file){
        try{
            file = ifFileExists();

            for(int i =0;i<sizeOfFile;i++){
                file.seek(i*Food.sizeInBytes);
                byte [] arr = new byte[Food.sizeOfString];
                file.read(arr, 0, Food.sizeOfString);
                String name = new String(arr);
                file.read(arr, 0, Food.sizeOfString);
                String exp = new String(arr);
                System.out.printf("Name: %s\texp Date: %s\tWeight: %.2f\tQuantity: %d\tCalories: %d\n",
                        name.trim(), exp.trim(), file.readDouble(), file.readInt(), file.readInt());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
