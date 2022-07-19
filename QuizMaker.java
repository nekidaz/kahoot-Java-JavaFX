package com.example.p2;


import java.io.FileNotFoundException;


public class QuizMaker {
    public static void main(String[] args) throws  FileNotFoundException {
     Quiz quiz=new Quiz();
        quiz.loadfromfile("F:\\JAVAjust\\src\\Dimas\\Example1.txt");
        quiz.start();
    }
}

