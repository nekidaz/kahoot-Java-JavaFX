package com.example.p2;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;




public class P2 extends Application {
    private File file;
    private Stage window;
    private final double W = 800, H = 650;
    private ArrayList<Questions> questons = new ArrayList<>();
    private static StackPane mainpane;
    private Label lbl;
    private BorderPane borderPane;
    //Radiobuttons
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;
    private RadioButton selectedBtn;
    private String[] answers = new String[5];
    private TextField textField;
    private int correct = 0;
    private int wrong = 0;
private Text text = new Text();

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        window = primaryStage;
        Quiz quiz = new Quiz();
        //mainpane
        mainpane = new StackPane();
        //Image
        ImageChooseFile();
        //Music
        Music();
        //FileChooser
        Button fileChooseButton = new Button("File Choose");
        mainpane.getChildren().add(fileChooseButton);
        fileChooseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();
                file = fileChooser.showOpenDialog(primaryStage);
                String link = file.getAbsolutePath();
                try {
                    questons = (ArrayList<Questions>) quiz.loadfromfile(link);
                    if (link.contains(".")) {
                        window.setScene(new Scene(Currentquestion(0), W, H));
                        window.show();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        window.setTitle("P2");
        window.setScene(new Scene(mainpane, W, H));
        window.show();

    }
    //Image
    public static void ImageChooseFile() throws FileNotFoundException {
        Image image = new Image(new FileInputStream("src\\background.jpg"));
        ImageView imageView = new ImageView(image);
        mainpane.getChildren().add(imageView);
    }

    //music
    public static void Music() {
        File sound = new File("src\\kahoot_music.mp3");
        Media media = new Media(sound.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    //Quiz
    public BorderPane Currentquestion(int ind) throws FileNotFoundException {

        borderPane = new BorderPane();
        String rimee="00:00";
        StackPane stackPane = new StackPane();
        textField = new TextField();

        String ans = questons.get(ind).getDescription();

        StackPane timer=new StackPane();
        timer.setAlignment(Pos.CENTER);


        lbl = new Label(ans);

        lbl.setWrapText(true);
        lbl.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 15));


        Image imagek = new Image(new FileInputStream("src\\k.png"));
        ImageView imageViewk = new ImageView(imagek);
        imageViewk.setFitHeight(25);
        imageViewk.setFitWidth(50);

        HBox hBox0 = new HBox(10);
        hBox0.getChildren().addAll(imageViewk, lbl);
        hBox0.setAlignment(Pos.CENTER);


        VBox vBox0 = new VBox(10.0);

        vBox0.getChildren().addAll(( hBox0) ,start());

        vBox0.setAlignment(Pos.CENTER);
        borderPane.setTop(vBox0);

        if (ans.contains("{blank}")) {

            lbl.setText(lbl.getText().replace("{blank}", "_____"));
            borderPane.setTop(vBox0);

            Image image = new Image(new FileInputStream("src\\fillin.png"));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(H / 3);
            imageView.setFitWidth(W / 2);
            borderPane.setCenter(imageView);

            textField.setMaxSize(400, 70);

            stackPane.getChildren().add(new StackPane(textField));
            borderPane.setBottom(stackPane);
            borderPane.setMargin(stackPane, new Insets(0, 0, 250, 0));
            textField.setOnKeyTyped(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    if (!textField.getText().isEmpty()) {
                        answers[ind] =textField.getText();
                    }
                }
            });
            if (answers[ind] != null) {
                textField.setText(answers[ind]);
            }

        } else {

            Image itest = new Image(new FileInputStream("src\\tst.png"));
            ImageView imageViewtest = new ImageView(itest);
            borderPane.setCenter(imageViewtest);

            Image image = new Image(new FileInputStream("src//sic.jpg"));
            ImageView imageView = new ImageView(image);
            imageView.toBack();
            imageView.setFitHeight(300);
            imageView.setFitWidth(800);
            imageView.setX(0);
            imageView.setY(400);
            borderPane.getChildren().add(imageView);

            String[] mama = questons.get(ind).toString().split("\n");

            radioButton1 = new RadioButton(mama[1]);
            radioButton2 = new RadioButton(mama[2]);
            radioButton3 = new RadioButton(mama[3]);
            radioButton4 = new RadioButton(mama[4]);

            VBox vBox = new VBox(80, radioButton1, radioButton2);
            VBox vBox1 = new VBox(80, radioButton3, radioButton4);

            HBox hBox = new HBox(250);

            hBox.getChildren().addAll(vBox, vBox1);
            hBox.setAlignment(Pos.CENTER);
            hBox.setMinHeight(150);
            ToggleGroup toggleGroup = new ToggleGroup();
            radioButton1.setToggleGroup(toggleGroup);
            radioButton2.setToggleGroup(toggleGroup);
            radioButton3.setToggleGroup(toggleGroup);
            radioButton4.setToggleGroup(toggleGroup);

            selectedBtn = null;

            stackPane.setMargin(hBox, new Insets(0, 150, 20, 0));

            toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                @Override
                public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                    if (toggleGroup.getSelectedToggle() != null) {
                        selectedBtn = (RadioButton) toggleGroup.getSelectedToggle();
                        answers[ind] = selectedBtn.getText();
                    }
                }
            });
            if (answers[ind] != null) {
                if (answers[ind].contains(radioButton1.getText())) {
                    radioButton1.setSelected(true);
                }
                if (answers[ind].contains(radioButton2.getText())) {
                    radioButton2.setSelected(true);
                }
                if (answers[ind].contains(radioButton3.getText())) {
                    radioButton3.setSelected(true);
                }
                if (answers[ind].contains(radioButton4.getText())) {
                    radioButton4.setSelected(true);
                }
            }

            BorderPane.setMargin(hBox, new Insets(200, 200, 200, 200));
            stackPane.getChildren().addAll(hBox);

            borderPane.setBottom(stackPane);
        }

        Button button = new Button("✓");
        Button buttonnext = new Button(">>");
        Button buttonprev = new Button("<<");

        button.setMinWidth(100.);
        button.setMaxHeight(50.);

        buttonprev.setMinWidth(100.);
        buttonprev.setMaxHeight(50.);

        buttonnext.setMinWidth(100.);
        buttonnext.setMaxHeight(50.);

        borderPane.setLeft(new StackPane(buttonprev));
        borderPane.setRight(new StackPane(buttonnext));

        if (ind == 0) {
            buttonprev.setVisible(false);
        }
        if (ind == questons.size() - 1) {
            buttonnext.setVisible(false);
            borderPane.setRight(new StackPane(button));
        }

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                BorderPane finish = new BorderPane();

                for (int i = 0; i < answers.length; i++) {
                    if (answers[i] != null) {
                        if (answers[i].contains(questons.get(i).getAnswer())) {
                            correct++;
                        } else wrong++;
                    }
                }
                int all = questons.size();

                Button buttonshowAns = kahootButton("Show answers", "BLUE");
                Button buttonexit = kahootButton("Exit", "RED");
                VBox vBoxy = new VBox(18, new Text("Pers: "+correct*100/all+" % " ), new Text("Ans : "+ correct + " / " + all), new Text("Time:  "), buttonshowAns, buttonexit);
                vBoxy.setAlignment(Pos.CENTER);
                finish.setCenter(vBoxy);

                buttonshowAns.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        BorderPane ashow=new BorderPane();
                        Label label=new Label("ANSWERS");
                        TextArea textArea=new TextArea("your answers: ");
                        TextArea textArea1=new TextArea("correct answers: ");

                        for (int i = 0; i < answers.length; i++) {
                            if (answers[i] != null) {

                                textArea.appendText(answers[i]+"\n");
                                textArea1.appendText(questons.get(i).getAnswer()+"\n");
                                HBox hBox=new HBox(10, textArea, textArea1);
                                textArea1.setWrapText(true);
                                textArea.setWrapText(true);

                                ashow.setCenter(new StackPane(hBox));
                               ashow.setTop(new StackPane(label));
                            }
                        }
                        window.setScene(new Scene(ashow, 600, 600));

                    }
                });

                buttonexit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.exit(0);
                    }
                });

                Image imagef = null;
                try {
                    imagef = new Image(new FileInputStream("src//result.jpg"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                ImageView imageViewf = new ImageView(imagef);
                imageViewf.setFitHeight(H / 2);
                imageViewf.setFitWidth(W / 2);
                finish.setBottom(new StackPane(imageViewf));

                Label restext = new Label("Results");
                restext.setAlignment(Pos.CENTER);
                restext.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
                finish.setTop(new StackPane(restext));

                window.setScene(new Scene(finish, W, H));
            }

        });

        buttonnext.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    window.setScene(new Scene(Currentquestion(ind + 1), W, H));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonprev.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    window.setScene(new Scene(Currentquestion(ind - 1), W, H));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        return borderPane;
    }

    public Button kahootButton(String text, String color) {
        Font font = Font.font("Times New Roman", FontWeight.BOLD,
                FontPosture.ITALIC, 18);
        Button button = new Button(text);
        button.setMinWidth(200);
        button.setMinHeight(50);
        button.setStyle("-fx-background-color: " + color);
        button.setTextFill(Color.WHITE);
        button.setFont(font);

        return button;
    }
   Timeline timeline=new Timeline();
    int min=0;
    int sec=0;
    Text taaaa=new Text();

    public  Text asda(){
        Text text=new Text("00:00");
        if(sec==60){
            min++;
            sec=0;
        }else sec++;
        text.setText((((min/10)==0)?"0"+min:min)+":"+(((sec/10)==0)?"0"+sec:sec) );
        return text;
    }
    public Text start() {
        taaaa.setText(asda().getText());
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e -> taaaa.setText(asda().getText())));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        return taaaa;
    }



}
