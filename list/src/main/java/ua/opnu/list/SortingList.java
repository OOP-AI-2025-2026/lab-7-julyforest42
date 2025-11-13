package ua.opnu.list;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Comparator;

public class SortingList extends Application {

    // Список студентів
    private ObservableList<Student> students;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Список студентів");

        students = populateList();

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(5));
        vbox.setAlignment(Pos.CENTER);

        final ListView<Student> listView = new ListView<>(students);
        listView.setPrefSize(400, 240);

        final HBox hbox = setButtons();

        vbox.getChildren().addAll(listView, hbox);

        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    // Заповнення списку студентами
    private ObservableList<Student> populateList() {
        Student student1 = new Student("Борис", "Іванов", 75);
        Student student2 = new Student("Петро", "Петренко", 92);
        Student student3 = new Student("Сергій", "Сергієнко", 61);
        Student student4 = new Student("Григорій", "Сковорода", 88);

        return FXCollections.observableArrayList(
                student1, student2, student3, student4
        );
    }

    // Налаштування кнопок з використанням лямбда-виразів
    private HBox setButtons() {
        final Button sortByNameButton = new Button("Сортувати за ім'ям");
        final Button sortByLastNameButton = new Button("Сортувати за прізвищем");
        final Button sortByMarkButton = new Button("Сортувати за оцінкою");

        HBox.setHgrow(sortByNameButton, Priority.ALWAYS);
        HBox.setHgrow(sortByLastNameButton, Priority.ALWAYS);
        HBox.setHgrow(sortByMarkButton, Priority.ALWAYS);
        sortByNameButton.setMaxWidth(Double.MAX_VALUE);
        sortByLastNameButton.setMaxWidth(Double.MAX_VALUE);
        sortByMarkButton.setMaxWidth(Double.MAX_VALUE);

        // Масив використовується, щоб зберігати порядок сортування (зростання / спадання)
        final boolean[] order = {true, true, true};

        // Лямбда для сортування за ім'ям
        sortByNameButton.setOnAction(event -> {
            Comparator<Student> comparator = Comparator.comparing(Student::getName);
            students.sort(order[0] ? comparator : comparator.reversed());
            order[0] = !order[0];
        });

        // Лямбда для сортування за прізвищем
        sortByLastNameButton.setOnAction(event -> {
            Comparator<Student> comparator = Comparator.comparing(Student::getLastName);
            students.sort(order[1] ? comparator : comparator.reversed());
            order[1] = !order[1];
        });

        // Лямбда для сортування за середнім балом
        sortByMarkButton.setOnAction(event -> {
            Comparator<Student> comparator = Comparator.comparingDouble(Student::getAvgMark);
            students.sort(order[2] ? comparator : comparator.reversed());
            order[2] = !order[2];
        });

        HBox hb = new HBox();
        hb.setSpacing(5);
        hb.getChildren().addAll(sortByNameButton, sortByLastNameButton, sortByMarkButton);
        hb.setAlignment(Pos.CENTER);

        return hb;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
