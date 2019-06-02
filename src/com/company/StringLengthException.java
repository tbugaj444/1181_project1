package com.company;

public class StringLengthException extends Exception {
    StringLengthException(){
        super("String is too long");
    }
}
