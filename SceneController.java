package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SceneController implements Initializable {

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
			boolean t = users[i][0].equals(username);
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
	
	static int questionindex = 1;
	static int answerindex = 1;
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
	questions = CsvIntoArray("src\\Questions.csv");
	questionindex++;
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
	
	//------------------------buttons-----------------------------
	
	@FXML
	Label wrong ;
	@FXML
	TextField username ;
	@FXML
	PasswordField password ;
	
	public void LogIn(ActionEvent e) throws IOException {
		int i=1;
		String name = username.getText();
		String pswd = password.getText();
		String usertype = UserType(name);
		if(usertype=="guest") {
			while (i<users.length && users[i][1].equals(name)==false) {i++;}
			if(i<users.length) {
				i=1;
				while (users[i][1].equals(name)==false) {i++;}
				if(users[i][2].equals(pswd)==false) {wrong.setVisible(true);}
				else {GoToHomeUser(e);}
			}
			else {wrong.setVisible(true);}
		}
		else if (usertype=="user" || usertype=="admin") {
			i=1;
			while (users[i][0].equals(name)==false) {i++;}
			if(users[i][2].equals(pswd)==false) {wrong.setVisible(true);}
			else {GoToHomeUser(e);}
		}
	}
	
	ObservableList<String> degreeoptions = FXCollections.observableArrayList("Bachelor","Master's","PHD");
	
	@FXML
	TextField newusername;
	@FXML
	TextField newemail;
	@FXML
	PasswordField newpassword;
	@FXML
	PasswordField confirmpassword;
	@FXML
	DatePicker newbirthdate = new DatePicker(LocalDate.of(1998, 10, 8));
	@FXML
	TextField newnationality;
	@FXML
	ComboBox<String> newdegree = new ComboBox(degreeoptions);
	@FXML
	TextField newsubject;
	@FXML
	Label missing;
	
	public void Register(ActionEvent e) throws IOException {
		
		String x0,x1,x2,x4,x5,x6,x7;
		x0 = newusername.getText();
		x1 = newemail.getText();
		x2 = newpassword.getText();
		x4 = newbirthdate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		x5 = newnationality.getText();
		x6 = newdegree.getValue();
		x7 = newsubject.getText();
		if(x0.equals("") || x1.equals("") || x2.equals("") || confirmpassword.getText().equals("")) {
			missing.setVisible(true);
		}
		else {
		FileWriter file = new FileWriter("src\\Users.csv",true);
	    BufferedWriter b = new BufferedWriter(file);
	    b.newLine();
	    b.write(x0+";"+x1+";"+x2+";"+"0"+";"+x4+";"+x5+";"+x6+";"+x7+";"+"");
	    b.close();
	    file.close();
	    
	    GoToHomeUser(e);
		}
	}
	
	ObservableList<String> questionsubjectoptions = FXCollections.observableArrayList("Computer Science","Biology","Commerce");
	ObservableList<String> questioncountryoptions = FXCollections.observableArrayList("Canada","USA","UK","China"); 
	
	@FXML
	TextField questiontitle;
	@FXML
	ComboBox<String> questioncountry= new ComboBox(questioncountryoptions);
	@FXML
	ComboBox<String> questiondegree = new ComboBox(degreeoptions);
	@FXML
	ComboBox<String> questionsubject = new ComboBox(questionsubjectoptions);
	@FXML
	TextArea questiondetails; 
	@FXML
	Label emptyquestion;
	
	@FXML
	public void PostQuestion(ActionEvent e) throws IOException {
		
		String y0,y1,y2,y3,y4;
		y0 = questiontitle.getText();
		y1 = questioncountry.getValue();
		y2 = questiondegree.getValue();
		y3 = questionsubject.getValue();
		y4 = questiondetails.getText();
		
		if(y0.equals("")) {emptyquestion.setVisible(true);}
		else {
		FileWriter file = new FileWriter("src\\Questions.csv",true);
	    BufferedWriter b = new BufferedWriter(file);
	    b.newLine();
	    b.write(y0+";"+y1+";"+y2+";"+y3+";"+y4);
	    b.close();
	    file.close();
	    String[][] s = CsvIntoArray("src\\Questions.csv");
	    questionindex = s.length-1;
	    ViewController qvc = GoToViewQuestionUser(e);
		}
	}
	
	//-------------------------------------- trafic w kdoub ---------------------------------------
	
	public void view1(ActionEvent e) throws IOException {
		questionindex = 1;
	    ViewController qvc = GoToViewQuestionUser(e);
	}
	public void view2(ActionEvent e) throws IOException {
		questionindex = 2;
	    ViewController qvc = GoToViewQuestionUser(e);
	}
	public void view3(ActionEvent e) throws IOException {
		questionindex = 3;
	    ViewController qvc = GoToViewQuestionUser(e);
	}
	public void view4(ActionEvent e) throws IOException {
		questionindex = 4;
	    ViewController qvc = GoToViewQuestionUser(e);
	}
	public void view5(ActionEvent e) throws IOException {
		questionindex = 5;
	    ViewController qvc = GoToViewQuestionUser(e);
	}
	public void view1G(ActionEvent e) throws IOException {
		questionindex = 1;
	    ViewController qvc = GoToViewQuestionGuest(e);
	}
	public void view2G(ActionEvent e) throws IOException {
		questionindex = 2;
	    ViewController qvc = GoToViewQuestionGuest(e);
	}
	public void view3G(ActionEvent e) throws IOException {
		questionindex = 3;
	    ViewController qvc = GoToViewQuestionGuest(e);
	}
	public void view4G(ActionEvent e) throws IOException {
		questionindex = 4;
	    ViewController qvc = GoToViewQuestionGuest(e);
	}
	public void view5G(ActionEvent e) throws IOException {
		questionindex = 5;
	    ViewController qvc = GoToViewQuestionGuest(e);
	}
	
	//----------------------------------------------------------------------------------------------------

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
	
	//----------------------Categories---------------------------------
	
	public void usa() {}
	public void canada() {}
	public void uk() {}
	public void china() {}
	public void bachelor() {}
	public void master() {}
	public void phd() {}
	public void computerscience() {}
	public void biology() {}
	
	//----------------------------------------------------------

	@FXML
	Label questionview;
	@FXML
	Label questiondetailsview;
	@FXML
	VBox answersbox;
	@FXML
	VBox recentquestionsbox;
	
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
        recentquestionsbox.getChildren().add(question);
	}
	
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
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//FillPopularQuestions();
		//FillRecentQuestions();

		newbirthdate.setValue(LocalDate.now());
		newdegree.setItems(degreeoptions);
		questioncountry.setItems(questioncountryoptions);
		questiondegree.setItems(degreeoptions);
		questionsubject.setItems(questionsubjectoptions);
	}



}