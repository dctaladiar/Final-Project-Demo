package com.finalproject.final_project.Exceptions;

public class CardNullException extends Exception{
    public CardNullException () {
        super("Card number does not exist in the system.");
    }
}
