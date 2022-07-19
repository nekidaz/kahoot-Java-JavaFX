package com.example.p2;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class Quiz {
    private static int correct = 0;
    private static int all;
    private static int incorrect = 0;
    private String name;
    private final  ArrayList<Questions> questions = new ArrayList<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addQuestions(Questions question) {
        questions.add(question);
    }

    public ArrayList<Questions> loadfromfile(String a) throws FileNotFoundException {
        Quiz quiz = new Quiz();
        File file = new File(a);
        Scanner in = new Scanner(file);

        //File name
        String fileName = file.getName();
        int y = fileName.lastIndexOf('.');
        if (y != -1) {
            quiz.setName(fileName.substring(0, y));
        }
        //Main
        while (in.hasNext()) {
            String descriptoins = in.nextLine();
            if (descriptoins.contains("{blank}")) {
                Fillin fillin = new Fillin();
                fillin.setDescription(descriptoins);
                fillin.setAnswer(in.nextLine());
                questions.add(fillin);
            } else if (descriptoins.contains("?")) {
                TEST test = new TEST();
                test.setDescription(descriptoins);

                String[] options = new String[4];
                for (int i = 0; i < 4; i++) {
                    options[i] = in.nextLine();
                }
                test.setOptipons(options);
                test.setAnswer(options[0]);

                questions.add(test);
            }
            in.nextLine();
        }
        return questions;
    }

    @Override
    public String toString() {
        return "'" + getName() + "'";
    }

    public void start(){
        Scanner userin = new Scanner(System.in);
        int i = 0;
        try {
            while (i < 10) {
                System.out.println();
                if (questions.get(i) instanceof Fillin) {
                    System.out.println(questions.get(i).toString());
                    System.out.print("Type your answer: ");
                    String filluser = userin.nextLine();
                    if (filluser.equals(questions.get(i).getAnswer())) {
                        System.out.println("Correct");
                        correct++;
                    } else {
                        System.out.println("Incorrect");
                        incorrect++;
                    }
                    i++;
                } else if (questions.get(i) instanceof TEST) {
                    System.out.println(questions.get(i).toString());
                    do {
                        try {
                            System.out.print("Type your answer: ");
                            char userAns = userin.nextLine().charAt(0);
                            int ind = (userAns - 'A');
                            if (((TEST) questions.get(i)).getOptionsAt(ind).equals(questions.get(i).getAnswer())) {
                                System.out.println("Correct!");
                                correct++;
                                break;
                            } else {
                                System.out.println("Incorrect!");
                                incorrect++;
                                break;
                            }
                        } catch (ArrayIndexOutOfBoundsException ex) {
                            System.out.print("Invalid ! (Ex: A, B, C, D): ");
                        }
                    } while (true);
                    i++;
                }
            }
        } catch (IndexOutOfBoundsException ea) {
            all = correct + incorrect;
            System.out.println("Your score : " + correct + " / " + all);
            System.exit(0);
        }
    }
}