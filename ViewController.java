package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewController implements Initializable {

	//---------------------------CSV files------------------------------------------------------
	
	String[][] CsvIntoArray(String file){
		
		BufferedReader reader = null;
		String line="";
		List<String[]> rows = new ArrayList<String[]>();
		try {
		reader = new BufferedReader(new FileReader(file));
		while((line=reader.readLine()) != null) {
			rows.add(line.split(";"));
			}
		String[][] array = new String[rows.size()][0];
		rows.toArray(array);
		reader.close();
		return array;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String UserType (String username) {
		int i=1;
		while(i < users.length && users[i][0].equals(username)==false) {
			i++;
			}
		if (i < users.length) {
			if(Integer.parseInt(users[i][3])<100) {return "user";}
			else {return "admin";}
		}
		else { return "guest";}
	}

	String[][] users = CsvIntoArray("src\\Users.csv");
	String[][] questions = CsvIntoArray("src\\Questions.csv");
	String[][] answers = CsvIntoArray("src\\Answers.csv");
	
	int questionindex = 1;
	int answerindex = 1;
	//---------------------------switching between scenes--------------------------------------------
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void GoTo (String file,ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource(file));
		stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	
	public ViewController GoToAnother (String file,ActionEvent e) throws IOException {
	FXMLLoader loader = new FXMLLoader(getClass().getResource(file));
	root = loader.load();
	ViewController qvc = loader.getController();
	stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
	scene = new Scene(root);
	stage.setScene(scene);
	stage.show();
	return qvc;
	}
	
	public void GoToHomeGuest (ActionEvent e) throws IOException{GoTo("/HomeGuest.fxml",e);}
	public void GoToHomeUser (ActionEvent e) throws IOException{GoTo("/HomeUser.fxml",e);}
	
	public void GoToCategoriesGuest (ActionEvent e) throws IOException {GoTo("/CategoriesGuest.fxml",e);}
	public void GoToCategoriesUser (ActionEvent e) throws IOException {GoTo("/CategoriesUser.fxml",e);}
	
	public void GoToAboutUsGuest (ActionEvent e) throws IOException {GoTo("/AboutUsGuest.fxml",e);}
	public void GoToAboutUsUser (ActionEvent e) throws IOException {GoTo("/AboutUsUser.fxml",e);}
	
	public ViewController GoToViewQuestionGuest (ActionEvent e) throws IOException {
		ViewController qvc = GoToAnother("/ViewQuestionGuest.fxml",e);
		return qvc;}
	public ViewController GoToViewQuestionUser (ActionEvent e) throws IOException {
		ViewController qvc = GoToAnother("/ViewQuestionUser.fxml",e);
		return qvc;}

	public void GoToSearchResultGuest (ActionEvent e) throws IOException {GoTo("/SearchResultGuest.fxml",e);}
	public void GoToSearchResultUser (ActionEvent e) throws IOException {GoTo("/SearchResultUser.fxml",e);}
	
	public void GoToLogIn (ActionEvent e) throws IOException {GoTo("/LogIn.fxml",e);}
	public void GoToRegister (ActionEvent e) throws IOException {GoTo("/Register.fxml",e);}
	
	public void GoToMyAccount (ActionEvent e) throws IOException {GoTo("/MyAccount.fxml",e);}
	public void GoToEditProfile (ActionEvent e) throws IOException {GoTo("/EditProfile.fxml",e);}
	
	public void GoToPostQuestion (ActionEvent e) throws IOException {GoTo("/PostQuestion.fxml",e);}
	
	public void GoToLogOut (ActionEvent e) throws IOException {GoTo("/LogOut.fxml",e);}
	
	//----------------------------------------buttons----------------------------------------------
	
	@FXML
	TextArea answer;
	@FXML
	Label emptyanswer;
	
	public void PostAnswer(ActionEvent e) throws IOException {
		
		String a = answer.getText();
		
		if(a.equals("")) {emptyanswer.setVisible(true);}
		else {
		FileWriter file = new FileWriter("src\\Answers.csv",true);
	    BufferedWriter b = new BufferedWriter(file);
	    b.newLine();
	    b.write(questions.length-1+";"+a+";0");
	    b.close();
	    file.close();
	    SceneController.questionindex = questions.length-1;
	    ViewController qvc = GoToViewQuestionUser(e);
		}
	}
	
	public void UpVote() {
		int temp = Integer.parseInt(answers[answerindex][2]) ;
		temp++;
		answers[answerindex][2] = Integer.toString(temp);
	}
	public void DownVote() {
		int temp = Integer.parseInt(answers[answerindex][2]) ;
		temp--;
		answers[answerindex][2] = Integer.toString(temp);
	}
	public void DeleteQuestion() {
		//GoToHomeUser
	}
	public void DeleteAnswer() {
		//GoToViewQuestion
	}	
	public void EditProfile() {
		//GoToMyAccount
	}
	public void ChangePhoto() {
	}
	public void LogOut() {
		//GoToHomeGuest
	}
	
	//---------------Categories----------------------------------------
	
	public void usa() {}
	public void canada() {}
	public void uk() {}
	public void china() {}
	public void bachelor() {}
	public void master() {}
	public void phd() {}
	public void computerscience() {}
	public void biology() {}
	
	//-----------------------------------------------------------------
	
	@FXML
	VBox recentquestionsbox;
	
	//create a question pane
	void FillQuestion(int i) {
	    Pane question  = new Pane();
	    question.setPrefHeight(80);
	    Label a = new Label(questions[i][0]);
	    a.setPrefWidth(545);
	    a.setPrefHeight(61);
        a.setLayoutX(14);
        a.setLayoutY(9);
  	    Label b = new Label(Integer.toString(answerscounter(i)));
	    a.setPrefWidth(32);
	    a.setPrefHeight(41);
        a.setLayoutX(585);
        a.setLayoutY(13);
  	    Label c = new Label("answers");
	    a.setPrefWidth(57);
	    a.setPrefHeight(40);
        c.setLayoutX(573);
        c.setLayoutY(30);
        question.getChildren().add(a);
        question.getChildren().add(b);
        question.getChildren().add(c);
        recentquestionsbox.getChildren().add(answer);
	}
	
	//count answers in a question
	int answerscounter(int i) {
		int n=0;
		for(int j=1;j<answers.length;j++) {
		if(answers[j][0].equals(Integer.toString(i))) {n++;}
	}
		return n;}
	
	//remplir les questions dans home
	
	// popular --> most answers
	private void FillPopularQuestions(){
		int[] A= new int[questions.length];
		A[0]=-1;
		for(int i=1;i<A.length;i++) {A[i]=answerscounter(i);}
		Arrays.sort(A);
		for(int i=A.length-1;i>0;i--) {FillQuestion(i);}
	}
	
	private void FillRecentQuestions() {
		for(int i=questions.length-1;i>0;i--) {FillQuestion(i);}
	  }
	
	@FXML
	Label questionview;
	@FXML
	Label questiondetailsview;
	@FXML
	VBox answersbox;
	
	// remplir le titre les details et les reponses pour une question in ViewQuestion
	public void FillViewQuestionPage(int i) throws FileNotFoundException {
		
		questionview.setText(questions[i][0]);
		questiondetailsview.setText(questions[i][4]);
		
		int[] A= new int[answers.length];
		A[0]=-1;
		for(int k=1;k<A.length;k++) {A[k]=Integer.parseInt(answers[k][2]);}
		
		for(int j=1;j<answers.length;j++) {
		if(answers[j][0].equals(Integer.toString(i))) {
	    Pane answer  = new Pane();
	    answer.setPrefWidth(913);
	    answer.setPrefHeight(82);
	    answer.setStyle("-fx-background-color: #e6e6e6 ;");
	    Label a = new Label(answers[j][1]);
	    a.setStyle("-fx-font-size: 14px ;");
  	    Label b = new Label(answers[j][2]);
  	    b.setStyle("-fx-font-weight: bold ;-fx-font-size: 13px ;");
  	    Image image1 = new Image(getClass().getResourceAsStream("up-vote.png"));
        ImageView c = new ImageView();
        c.setImage(image1);
        c.setFitWidth(27);
        c.setFitHeight(27);
        c.setCursor(Cursor.HAND);
        Image image2 = new Image(getClass().getResourceAsStream("down-vote.png"));
        ImageView d = new ImageView();
        d.setImage(image2);
        d.setFitWidth(27);
        d.setFitHeight(27);
        d.setCursor(Cursor.HAND);
        answer.getChildren().add(a);
        a.setLayoutX(20);
        a.setLayoutY(25);
        answer.getChildren().add(b);
        b.setLayoutX(839);
        b.setLayoutY(33);
        answer.getChildren().add(c);
        c.setLayoutX(833);
        c.setLayoutY(10);
        c.setOnMouseClicked((MouseEvent e) -> {
        });
        answer.getChildren().add(d);
        d.setLayoutX(833);
        d.setLayoutY(51);
        d.setOnMouseClicked((MouseEvent e) -> {
        });
        d.setVisible(true);
        Pane separator = new Pane();
        separator.setPrefWidth(913);
        separator.setPrefHeight(14);
	    answersbox.getChildren().add(answer);
	    answersbox.getChildren().add(separator);
		}
	  }
	}
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			FillViewQuestionPage(SceneController.questionindex);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}