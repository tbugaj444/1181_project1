package com.company;


public class Food {
    private String name;
    private String expDate;
    private double weight;
    private int quant;
    private int calories;

    static final int sizeOfString = 16;
    static final int sizeInBytes = (sizeOfString*2)+ (2*Double.BYTES)+Integer.BYTES;

    public Food(String name, String expDate, double weight, int quant, int calories) throws Exception{
        setName(name);
        setExpDate(expDate);
        setWeight(weight);
        setQuant(quant);
        setCalories(calories);


    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception{
        name = String.format("%1$"+sizeOfString+"s",name);
        if(name.length()!=sizeOfString){
            throw new StringLengthException();
        }else{
            this.name = name;
        }
    }

    public String getExpDate() {

        return expDate;
    }

    public void setExpDate(String expDate) throws Exception{
        expDate = String.format("%1$"+sizeOfString+"s",expDate);
        if(expDate.length()!=sizeOfString){
            throw new StringLengthException();
        }else{
            this.expDate = expDate;
        }

    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "Food{" +
                "name='" + name + '\'' +
                ", expDate='" + expDate + '\'' +
                ", weight=" + weight +
                ", quant=" + quant +
                ", calories=" + calories +
                '}';
    }
}
