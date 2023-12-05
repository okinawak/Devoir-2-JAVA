package sio.devoir2graphiques;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import sio.devoir2graphiques.Tools.ConnexionBDD;
import sio.devoir2graphiques.Tools.GraphiqueController;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Devoir2GraphiquesController implements Initializable
{

    ConnexionBDD maCnx;
    GraphiqueController graphiqueController;
    @FXML
    private Button btnGraph1;
    @FXML
    private Button btnGraph2;
    @FXML
    private Button btnGraph3;
    @FXML
    private AnchorPane apGraph1;
    @FXML
    private LineChart graph1;
    @FXML
    private Label lblTitre;
    @FXML
    private AnchorPane apGraph2;
    @FXML
    private AnchorPane apGraph3;
    @FXML
    private PieChart graph3;
    @FXML
    private BarChart graph2;
    XYChart.Series<Integer,Double> serieGraph2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblTitre.setText("Devoir : Graphique n°1");
        apGraph1.toFront();

        // A vous de jouer
        try {
            maCnx = new ConnexionBDD();
            graphiqueController = new GraphiqueController();
    }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }}

    @FXML
    public void menuClicked(Event event) throws SQLException {
        if(event.getSource() == btnGraph1)
        {
            lblTitre.setText("Devoir : Graphique n°1");
            apGraph1.toFront();

            // A vous de jouer
            graph1.getData().clear();
        }
        else if(event.getSource() == btnGraph2)
        {
            lblTitre.setText("Devoir : Graphique n°2");
            apGraph2.toFront();

            // A vous de jouer
            graph2.getData().clear();

            HashMap<Integer, ArrayList> dataGraphique2 =  graphiqueController.GetDataGraphique2();
            for (Integer valeur : dataGraphique2.keySet())
            {
                serieGraph2 = new XYChart.Series<>();
                serieGraph2.setName(valeur.toString());
                for(int i = 0;i< dataGraphique2.get(valeur).size();i+=2)
                {
                    serieGraph2.getData().add(new XYChart.Data<>(dataGraphique2.get(valeur.toString()).get(i),Double.parseDouble(dataGraphique2.get(valeur).get(i+1))));
                }
                graph2.getData().add(serieGraph2);
            }
        }
        else
        {
            lblTitre.setText("Devoir : Graphique n°3");
            apGraph3.toFront();

            // A vous de jouer
            graph3.getData().clear();
            ObservableList<PieChart.Data> dataGraph3 = FXCollections.observableArrayList();
            HashMap<String, Double> datasGraphique3 =  graphiqueController.GetDataGraphique3();

            for (String valeur : datasGraphique3.keySet())
            {
                dataGraph3.add(new PieChart.Data(valeur,datasGraphique3.get(valeur) ));
            }
            graph2.setData(dataGraph3);
            for (PieChart.Data entry : graph3.getData()) {
                Tooltip t = new Tooltip(entry.getPieValue()+ " : "+entry.getName());
                t.setStyle("-fx-background-color:#3D9ADA");
                Tooltip.install(entry.getNode(), t);
            }
        }
    }
}