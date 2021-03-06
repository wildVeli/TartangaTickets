/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartangatickets.views;


import com.gluonhq.charm.glisten.animation.FadeInLeftBigTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.CharmListView;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.tartangatickets.TartangaTickets;
import static com.tartangatickets.TartangaTickets.NEWUSER_VIEW;
import com.tartangatickets.entities.User;
import com.tartangatickets.exceptions.NoUserException;
import com.tartangatickets.logic.LogicInterface;
import com.tartangatickets.utils.DialogHelper;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Shows a list of users registered in the application and shows a option to 
 * create new users
 *
 *  <ul>
 *      <li><strong>logic:</strong> Get the logic of the program from TartangaTickets</li>
 *  </ul>
 *  @author Sergio López, Iker Jon Mediavilla, Ionut Savin, Jon Zaballa
 *  @version 1.0, Feb 21 2018
 */
public class UsersListController {
    
    private static final String GENERAL_ERROR = "Error inesperado.";

    @FXML
    private Button newUser;
    @FXML
    private View usuarios_charmlist;
    @FXML
    private CharmListView<User, String> charmUsuarios;
    private final LogicInterface logic = TartangaTickets.LOGIC; 
    private List <User> users;
    
     /**
     * First actions when initialize the window
     * -Set up the AppBar
     * -fill users list
     */
    public void initialize() {
        usuarios_charmlist.setShowTransitionFactory(v -> new FadeInLeftBigTransition(v));
        charmUsuarios.setCellFactory(p -> new UserCell());
        usuarios_charmlist.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setTitleText("Usuarios");
                Button back = MaterialDesignIcon.ARROW_BACK.button();
                back.setOnAction(event -> 
                    MobileApplication.getInstance().switchView(TartangaTickets.MAINMENU_VIEW)
                );
                appBar.setNavIcon(back);
                fillList();
            }
        });
    }
    
    /**
     * Creates and load  NEWUSER_VIEW
     */
    @FXML
    private void handleButtonNewUser(){
        MobileApplication
                .getInstance()
                .addViewFactory(NEWUSER_VIEW, () -> new NewUserView(NEWUSER_VIEW).getView());
        MobileApplication.getInstance().switchView(NEWUSER_VIEW);
    }

    /**
     * fills the table with data of users 'name, last name and department code
     */
    private void fillList() {
        try {
            users = logic.findAllUsers();
            charmUsuarios.setItems(FXCollections.observableArrayList(users));
        } catch (NoUserException ex) {
            DialogHelper.newInstance("INFO", ex.getMessage());
        } catch (Exception ex) {
            DialogHelper.newInstance("ERROR", GENERAL_ERROR);
        }
    }
}
