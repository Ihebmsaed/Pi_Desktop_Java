package controler;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.EventServices;
import utils.MyConnection;
import entities.Event;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ListeEventControler {

    @FXML
    private TableView<Event> eventsTableView;

    @FXML
    private TableColumn<Event, String> titleColumn;

    @FXML
    private TableColumn<Event, String> typeColumn;

    @FXML
    private TableColumn<Event, java.sql.Date> startDateColumn;

    @FXML
    private TableColumn<Event, java.sql.Date> endDateColumn;

    @FXML
    private TableColumn<Event, Float> ticketPriceColumn;

    @FXML
    private TableColumn<Event, Integer> ticketCountColumn;

    @FXML
    private DatePicker datePicker;

    public void initialize() {
        eventsTableView.setOnMouseClicked(this::handleEventDoubleClick);

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        ticketPriceColumn.setCellValueFactory(new PropertyValueFactory<>("ticketPrice"));
        ticketCountColumn.setCellValueFactory(new PropertyValueFactory<>("ticketCount"));

        afficherEvent();
    }

    private void afficherEvent() {
        try {
            MyConnection connection = MyConnection.getInstance();
            if (connection == null || connection.getCnx() == null) {
                System.out.println("La connexion à la base de données a échoué.");
                return;
            }

            Statement statement = connection.getCnx().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM event");

            List<Event> eventData = new ArrayList<>();
            while (resultSet.next()) {
                Event event = new Event();
                event.setTitle(resultSet.getString("title"));
                event.setType(resultSet.getString("type"));
                event.setStartDate(resultSet.getDate("startdate"));
                event.setEndDate(resultSet.getDate("enddate"));
                event.setTicketPrice(resultSet.getFloat("ticketPrice"));
                event.setTicketCount(resultSet.getInt("ticketCount"));
                eventData.add(event);
            }

            eventsTableView.getItems().clear();
            eventsTableView.getItems().addAll(eventData);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void supprimerEvent() {
        Event selectedEvent = eventsTableView.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            EventServices eventServices = new EventServices();
            if (eventServices.supprimerEvent(selectedEvent)) { // Supprimer l'événement de la base de données
                eventsTableView.getItems().remove(selectedEvent); // Supprimer l'événement de la TableView
                System.out.println("L'événement a été supprimé avec succès.");
            } else {
                System.out.println("Erreur lors de la suppression de l'événement dans la base de données.");
            }

            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Dialog information");
            alert.setContentText("Evénement supprimé avec succès !");
            alert.show();


        }
    }


    @FXML
    private void rechercherParDate() {
        LocalDate selectedDate = datePicker.getValue();
        ObservableList<Event> filteredEvents = eventsTableView.getItems().filtered(event -> event.getStartDate().toLocalDate().equals(selectedDate));
        eventsTableView.setItems(filteredEvents);
    }

    @FXML
    private void handleEventDoubleClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
            Event selectedEvent = eventsTableView.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                ouvrirInterfaceReservation(selectedEvent);
            }
        }
    }

    private void ouvrirInterfaceReservation(Event selectedEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reservation.fxml"));
            Parent root = loader.load();

            ReservationControler controller = loader.getController();
            controller.initData(selectedEvent);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void HomeButtonMeth(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterEvent.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void modifierEvent(ActionEvent actionEvent) {
        Event selectedEvent = eventsTableView.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            ouvrirInterfaceModifier(selectedEvent);
        }
    }

    private void ouvrirInterfaceModifier(Event selectedEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierEvent.fxml"));
            Parent root = loader.load();



            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Home(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(""));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
