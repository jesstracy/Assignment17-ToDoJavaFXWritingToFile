package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.security.Key;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    ListView todoList;

    @FXML
    TextField todoText;

    ObservableList<ToDoItem> todoItems = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        todoList.setItems(todoItems);
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
    }

    public void removeItem() {
        ToDoItem todoItem = (ToDoItem)todoList.getSelectionModel().getSelectedItem();
        System.out.println("Removing " + todoItem.text + " ...");
        todoItems.remove(todoItem);
    }

    public void toggleItem() {
        System.out.println("Toggling item ...");
        ToDoItem todoItem = (ToDoItem)todoList.getSelectionModel().getSelectedItem();
        if (todoItem != null) {
            todoItem.isDone = !todoItem.isDone;
            todoList.setItems(null);
            todoList.setItems(todoItems);
        }
    }

    public void markAllDone() {
        System.out.println("Marking all items as \"done\"");
        for (ToDoItem toDoItem : todoItems) {
            toDoItem.isDone = true;
        }
        todoList.setItems(null);
        todoList.setItems(todoItems);
    }

    public void markAllNotDone() {
        System.out.println("Marking all items as \"not done\"");
        for (ToDoItem toDoItem : todoItems) {
            toDoItem.isDone = false;
        }
        todoList.setItems(null);
        todoList.setItems(todoItems);
    }

    public void toggleAll() {
        System.out.println("Toggling all...");
        for (ToDoItem toDoItem : todoItems) {
            toDoItem.isDone = !toDoItem.isDone;
        }
        todoList.setItems(null);
        todoList.setItems(todoItems);
    }
}
