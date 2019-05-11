/**
 J'lin Rose
 * Assignment 7
 * Karen Whiting
 * cop 3330
 */
package databasejavafx;

//Imports
import dataModel.FilmDAO;
import inputOutput.ConnectionData;
import inputOutput.PostgreSQLConnect;
import inputOutput.XmlParser;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;



/**
 * 
 * @author jlinrose
 */

public class DatabaseJavaFx extends Application
{
   private static final Logger logger = Logger.getLogger(DatabaseJavaFx.class.getName());
   private ObservableList<FilmDAO> data = FXCollections.observableArrayList();
  
    public static void main(String[] args) {
       launch(args);
    }
    
   @Override
   public void start(Stage stage)
   {
       TableView tableView = new TableView();
       tableView.setEditable(false);
       final Label label = new Label("Files");
       label.setFont(new Font("Ariel", 20));
       
       TableColumn title = new TableColumn("Title");
       title.setMinWidth(200);
       
       title.setCellValueFactory(
        new PropertyValueFactory<FilmDAO, String>("filmName"));
       
       TableColumn description = new TableColumn("Description");
       description.setMinWidth(700);
       description.setCellValueFactory(
               new PropertyValueFactory<FilmDAO, String>("filmDescription"));
       
       TableColumn rate = new TableColumn("Rental Rate");
       rate.setMinWidth(100);
       rate.setCellValueFactory(
               new PropertyValueFactory<FilmDAO, Double>("filmPrice"));
       
       TableColumn rating = new TableColumn("Rating");
       rating.setMinWidth(100);
       rating.setCellValueFactory(
               new PropertyValueFactory<FilmDAO, String>("filmRating"));
       
       tableView.getColumns().addAll(title, description, rate, rating);
       
       final Button fetchData = new Button("Fetch films from database");
       
       fetchData.setOnAction(new EventHandler<ActionEvent>()
       {
           @Override public void handle(ActionEvent event)
           {
               fetchData(tableView);
           }
       });
       
       
       Scene scene = new Scene(new Group());
       
       final VBox vbox = new VBox();
       vbox.setPrefHeight(500);
       vbox.setStyle("-fx-background-color: cornsilk; -fx-padding: 50;");
       vbox.getChildren().addAll(label, tableView);
       ((Group) scene.getRoot()).getChildren().addAll(vbox, fetchData);
       
       stage.setTitle("Films for Rent");
       stage.setScene(scene);
       stage.show();
   }
   /**
    * 
    * @param tableView 
    */
   private void fetchData(TableView tableView)
   {
       try(Connection con = getConnection())
       {
           
           
           tableView.setItems(fetchFilms(con));
           
       }
       catch (SQLException | ClassNotFoundException ex)
       {
           logger.log(Level.SEVERE, null, ex);
       }
   }
    /**
     * 
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    private Connection getConnection() throws ClassNotFoundException, SQLException
    {
        logger.info("Getting a database connection");
        XmlParser xml = new XmlParser("inputOutput/properties.xml");
        ConnectionData data = xml.getConnectionData();
        
        PostgreSQLConnect connect = new PostgreSQLConnect(data);
        Connection dbConnect = connect.getConnection();
        
       return dbConnect;
    }
    
    /**
     * 
     * @param con
     * @return
     * @throws SQLException 
     */
    private ObservableList fetchFilms(Connection con) throws SQLException
    {
        logger.info("Fetching films from database");
        ObservableList<FilmDAO> films = FXCollections.observableArrayList();
        
        String select = "select title, rental_rate, rating, description " +
                "from film " +
                "order by title;";
        logger.info("Select statement " + select);
        
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(select);
        
        while(rs.next())
        {
           FilmDAO film = new FilmDAO();
           film.setFilmName(rs.getString("title"));
           film.setFilmRating(rs.getString("rating"));
           film.setFilmDescription(rs.getString("description"));
           film.setFilmPrice(rs.getDouble("rental_rate"));
           
           films.add(film);
        }
        logger.info("Found " + films.size() + " films");
        
        return films;
    }
       

}