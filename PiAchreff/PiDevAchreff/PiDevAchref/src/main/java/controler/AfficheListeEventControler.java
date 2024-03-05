package controler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import entities.Event;
import javafx.scene.control.cell.PropertyValueFactory;
import utils.MyConnection;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AfficheListeEventControler {

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
    private TableColumn<Event, Image> imageColumn;

    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        ticketPriceColumn.setCellValueFactory(new PropertyValueFactory<>("ticketPrice"));
        ticketCountColumn.setCellValueFactory(new PropertyValueFactory<>("ticketCount"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("affiche"));

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
                event.setAffiche(resultSet.getString("affiche"));
                event.setTicketCount(resultSet.getInt("ticketCount"));

                /*byte[] imageBytes = resultSet.getBytes("affiche");
                if (imageBytes != null) {
                    Image image = new Image(new ByteArrayInputStream(imageBytes));
                    event.setAffiche(String.valueOf(image));
                }*/

                //File file = new File(event.getAffiche());
                //Image image = new Image(file.toURI().toString());
                //imagePreview.setImage(image);

                eventData.add(event);
            }

            eventsTableView.getItems().clear();
            eventsTableView.getItems().addAll(eventData);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void reserverEvent(ActionEvent event) {
        Event selectedEvent = eventsTableView.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            ouvrirInterfaceReservation(selectedEvent);
        }
    }

    public Event EvenementSelected(){
        Event selectedEvent = eventsTableView.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            ouvrirInterfaceReservation(selectedEvent);
            return selectedEvent;
        } else {
            return null;
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
    private void handleEventDoubleClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
            Event selectedEvent = eventsTableView.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                ouvrirInterfaceReservation(selectedEvent);
            }
        }
    }

    @FXML
    void Home(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(""));
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
    }





