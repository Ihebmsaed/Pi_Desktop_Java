package Controller;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



import tools.Myconnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.image.ImageView;

public class ReclamationStatController implements Initializable {
    @FXML
    private ImageView ImageP;
    @FXML
    private PieChart voy_stat;
    private Statement st;
    private ResultSet rs;
    private Connection cnx;
    ObservableList<PieChart.Data> data = FXCollections.observableArrayList();

    public ReclamationStatController() {
    }

    public void initialize(URL url, ResourceBundle rb) {
        this.cnx = Myconnection.getInstance().getCnx();
        this.stat();
    }

    private void stat() {
        try {
            String query = "select COUNT(*),`type` from pi GROUP BY `type`;";
            PreparedStatement PreparedStatement = this.cnx.prepareStatement(query);
            this.rs = PreparedStatement.executeQuery();

            while(this.rs.next()) {
                this.data.add(new PieChart.Data(this.rs.getString("type"), (double)this.rs.getInt("COUNT(*)")));
            }
        } catch (SQLException var3) {
            System.out.println(var3.getMessage());
        }

        this.voy_stat.setTitle("**Statistiques Des Reclamations par type **");
        this.voy_stat.setLegendSide(Side.LEFT);
        this.voy_stat.setData(this.data);
    }
}
