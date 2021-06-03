package application;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Window;

public class AddBookControlor implements Initializable{

	@FXML
	private TextField tfLastName;
	@FXML
	private TextField tfFirstName;
	@FXML
	private TextField tfEmail;
	@FXML
	private Button addBtn;
	@FXML
	private Button exportBtn;
	@FXML
	private Button importBtn;
	@FXML
	private Button removeBtn;
	@FXML
	private Button quitBtn;
	@FXML
	private TableView<Person> table;
	@FXML
	private TableColumn<Person, String> emailCol;
	@FXML
	private TableColumn<Person, String> firstNameCol;
	@FXML
	private TableColumn<Person, String> lastNameCol;
	private DataClass data;
	//A Completer

	
	
	
	final ObservableList<Person> data2 =FXCollections.observableArrayList(
			new Person("sami","BenAli","sami@exemple.com"),
			new Person("Alia","BenSalah","alia@exemple.com"),
			new Person("Ali","BenSalah","ali@exemple.com")
			);
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
	
	
		lastNameCol.setCellValueFactory(new PropertyValueFactory<Person,String>("nom"));
		lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		lastNameCol.setOnEditCommit(new EventHandler<CellEditEvent<Person,String>>() {

			@Override
			public void handle(CellEditEvent<Person, String> event) {
				Person person =event.getRowValue();
				person.setNom(event.getNewValue());
			}
			
		});
		
		firstNameCol.setCellValueFactory(new PropertyValueFactory<Person,String>("prenom"));
		firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		firstNameCol.setOnEditCommit(new EventHandler<CellEditEvent<Person,String>>() {

			@Override
			public void handle(CellEditEvent<Person, String> event) {
				Person person =event.getRowValue();
				person.setPrenom(event.getNewValue());
			}
			
		});
		
		emailCol.setCellValueFactory(new PropertyValueFactory<Person,String>("email"));
		emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
		emailCol.setOnEditCommit(new EventHandler<CellEditEvent<Person,String>>() {

			@Override
			public void handle(CellEditEvent<Person, String> event) {
				Person person =event.getRowValue();
				if(isEmailAdress(person.getEmail()))
				{
					person.setEmail(event.getNewValue());
				}
				else
				{
					Window owner = addBtn.getScene().getWindow();
					Alert alert = new Alert(AlertType.ERROR);
					 alert.setTitle("Email Incorrect!");
					 alert.setHeaderText(null);
					 alert.setContentText(person.getEmail()+" : Email Incorrect!");
					 alert.initOwner(owner);
					 alert.show();
				}
			}
			
		});
		//for the integer age for exemple
		//	ageColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		table.setEditable(true);
		table.setItems(data2);
		
		//vérifier si les inputs sont vides ou non
		Window owner = addBtn.getScene().getWindow();
		
	if(lastNameCol.getText().isEmpty() || firstNameCol.getText().isEmpty() || emailCol.getText().isEmpty()) {
		Alert alert = new Alert(AlertType.ERROR);
		 alert.setTitle("Form Error!");
		 alert.setHeaderText(null);
		 alert.setContentText("Remplir tout les champs !");
		 alert.initOwner(owner);
		 alert.show();
		}
		
	}
	public void addPerson()
	{
		Window owner = addBtn.getScene().getWindow();
		
		 String nom=tfLastName.getText();
			String prenom=tfFirstName.getText();
			String email=tfEmail.getText();
			
			if(isEmailAdress(email)) {
				Person newperson =new Person(nom,prenom,email);
				table.getItems().add(newperson);
			}
			else 
			{
				Alert alert = new Alert(AlertType.ERROR);
				 alert.setTitle("Email Incorrect!");
				 alert.setHeaderText(null);
				 alert.setContentText(email+" : Email Incorrect!");
				 alert.initOwner(owner);
				 alert.show();
			}
		
	}

	public void removePerson()
	{
		
		ObservableList<Person> personSelected, allPerson;
		allPerson=table.getItems();
		personSelected= table.getSelectionModel().getSelectedItems();
		personSelected.forEach(allPerson::remove);
		
	}
 /*   public void quitList()
    { 
    	System.exit(0);

    }*/
    public static boolean isEmailAdress(String email){
    	Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$" );
    	Matcher m = p.matcher(email.toUpperCase());
    	return m.matches();
    	}


	}

