package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Controller implements Initializable {
    @FXML
    ListView todoList;

    @FXML
    TextField todoText;

    ObservableList<ToDoItem> todoItems = FXCollections.observableArrayList();

    private ArrayList<User> users = new ArrayList<User>();

    class User {
        private String userName;

//        private ArrayList<ToDoItem> toDoItems = new ArrayList<ToDoItem>();
//        ObservableList<ToDoItem> todoItems = FXCollections.observableArrayList();

        public User(String userName) {
            this.userName = userName;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public void addTodoItem(ToDoItem item) {
            todoItems.add(item);
        }

        public ObservableList<ToDoItem> getTodoItems() {
            return todoItems;
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File listOfUsers = new File("ListOfUsers.txt");
        if (listOfUsers.exists()) {
            try {
                Scanner listOfUsersFileReader = new Scanner(listOfUsers);
                String currentLine = listOfUsersFileReader.nextLine();
                String[] names = currentLine.split(",");
                for (String name : names) {
                    User returningUser = new User(name);

                    // get their to do list items
                    File userFile = new File(name + ".txt");
                    try {
                        Scanner userFileScanner = new Scanner(userFile);
                        String userCurrentLine;
                        String text;
                        boolean isDone;
                        while (userFileScanner.hasNext()) {
                            userCurrentLine = userFileScanner.nextLine();
                            text = userCurrentLine.split("=")[1];
                            userCurrentLine = userFileScanner.nextLine();
                            isDone = Boolean.valueOf(userCurrentLine.split("=")[1]);
                            ToDoItem myItem = new ToDoItem(text, isDone);
                            returningUser.addTodoItem(myItem);
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    // add them to list of all users!
                    users.add(returningUser);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        Scanner myScanner = new Scanner(System.in);
        String userName = askForUserName(myScanner);

        User myUser = null;
        // check if already on list of users
        boolean alreadyUser = false;
        for (User user : users) {
            if (userName.equals(user.getUserName())) {
                myUser = user;
                System.out.println("Welcome back, " + userName + "!");
                alreadyUser = true;
            }
        }
        if (!alreadyUser) {
            System.out.println("Welcome new user!");
            myUser = new User(userName);
            users.add(myUser);
        }
        todoList.setItems(todoItems);
    }

    public String askForUserName(Scanner myScanner) {
        System.out.print("What is your name? ");
        String userName = myScanner.nextLine();
        return userName;
    }

    public void handleEnterButton(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            addItem();
        }
    }

    public void addItem() {
        System.out.println("Adding item ...");
        todoItems.add(new ToDoItem(todoText.getText()));
        todoText.setText("");
        printToFile();
    }

    public void removeItem() {
        ToDoItem todoItem = (ToDoItem)todoList.getSelectionModel().getSelectedItem();
        System.out.println("Removing " + todoItem.text + " ...");
        todoItems.remove(todoItem);
        printToFile();
    }

    public void toggleItem() {
        System.out.println("Toggling item ...");
        ToDoItem todoItem = (ToDoItem)todoList.getSelectionModel().getSelectedItem();
        if (todoItem != null) {
            todoItem.isDone = !todoItem.isDone;
            todoList.setItems(null);
            todoList.setItems(todoItems);
        }
        printToFile();
    }

    public void markAllDone() {
        System.out.println("Marking all items as \"done\"");
        for (ToDoItem toDoItem : todoItems) {
            toDoItem.isDone = true;
        }
        todoList.setItems(null);
        todoList.setItems(todoItems);
        printToFile();
    }

    public void markAllNotDone() {
        System.out.println("Marking all items as \"not done\"");
        for (ToDoItem toDoItem : todoItems) {
            toDoItem.isDone = false;
        }
        todoList.setItems(null);
        todoList.setItems(todoItems);
        printToFile();
    }

    public void toggleAll() {
        System.out.println("Toggling all...");
        for (ToDoItem toDoItem : todoItems) {
            toDoItem.isDone = !toDoItem.isDone;
        }
        todoList.setItems(null);
        todoList.setItems(todoItems);
        printToFile();
    }

    public void printToFile() {
        try {
            File listOfUsers = new File("ListOfUsers.txt");
            FileWriter myFileWriter = new FileWriter(listOfUsers);
            for (User user : users) {
                //Write user's name in list of users file
                myFileWriter.write(user.getUserName() + ",");

                //AND print the user's todos in their own file
                File userFile = new File(user.getUserName() + ".txt");
                FileWriter myUserFileWriter = new FileWriter(userFile);
                for (ToDoItem item : user.getTodoItems()) {
                    myUserFileWriter.write("toDoItem.text=" + item.getText() + "\n");
                    myUserFileWriter.write("toDoItem.isDone=" + item.isDone() + "\n");
                }
                myUserFileWriter.close();
            }
            myFileWriter.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
