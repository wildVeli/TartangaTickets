/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tartangatickets.views;

import com.gluonhq.charm.glisten.animation.FadeInLeftBigTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.DropdownButton;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.tartangatickets.TartangaTickets;
import com.tartangatickets.entities.Credential;
import com.tartangatickets.entities.Department;
import com.tartangatickets.entities.Technician;
import com.tartangatickets.entities.User;
import com.tartangatickets.exceptions.NoDepartmentException;
import com.tartangatickets.logic.LogicInterface;
import com.tartangatickets.utils.DialogHelper;
import com.tartangatickets.utils.Reader;
import java.util.List;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import org.apache.commons.mail.EmailException;


/**
 * Creates a new user on the application with the inserted data
 * @author Sergio
 */
public class NewUserController  {
    
    private static final String GENERAL_ERROR = "Error inesperado.";
    private static final String EMAIL_ERROR = "Error al enviar email al usuario.";
    private static final String EMAIL_VALID_ERROR = "Error en el formato de email.";
    private static final String EMAIL_REPEATED_ERROR = "Ya existe un usuario con ese email.";
    
    @FXML
    private View nuevo_usuario;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfLastname1;
    @FXML
    private TextField tfLastname2;
    @FXML
    private TextField tfEmail;
    @FXML
    private DropdownButton dbDepartment;
    @FXML
    private CheckBox cbTechnician;
    private User user;
    private List<Department> departments;
    private final LogicInterface logic = TartangaTickets.LOGIC; 
    
    public void initialize() {
        nuevo_usuario.setShowTransitionFactory(v -> new FadeInLeftBigTransition(v));
        nuevo_usuario.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                Button back = MaterialDesignIcon.ARROW_BACK.button();
                back.setOnAction(event -> 
                    MobileApplication.getInstance().switchToPreviousView()
                );
                appBar.setNavIcon(back);
            }
        });
                 
        try {
            departments = logic.findAllDepartments();
        } catch (NoDepartmentException ex) {
            DialogHelper.newInstance("ERROR", ex.getMessage());
        } catch (Exception ex) {
            DialogHelper.newInstance("ERROR", GENERAL_ERROR);
        }
        
        for (Department department : departments){
            dbDepartment.getItems().add(new MenuItem(department.getName()));
        }
    }
    
    @FXML
    private void handleButtonCreateUser(){
        Credential credential = new Credential();
        Department department = new Department();

        //fields not empty
        if(!tfName.getText().trim().isEmpty()&& !tfLastname1.getText().trim().isEmpty()&&!tfEmail.getText().trim().isEmpty()){
            if(cbTechnician.isSelected()){
                user = new Technician();
            }else{
                user = new User();
            }
            departments=  departments.stream().filter(c -> c.getName().equals(dbDepartment.getSelectedItem().getText())).collect(Collectors.toList());
        
            department = departments.get(0);
            //email  
            String email = tfEmail.getText();
            if (!Reader.checkValidEmail(email)) {
                DialogHelper.newInstance("ERROR", EMAIL_VALID_ERROR);
                return;
            }
            if (!emailExists(email)) {
                DialogHelper.newInstance("ERROR", EMAIL_REPEATED_ERROR);
                return;
            }
            credential.setLogin(email);
            
            user.setName(tfName.getText());
            user.setLastName1(tfLastname1.getText());
            user.setLastName2(tfLastname2.getText());
            user.setDepartment(department);
            user.setCredential(credential);
            try {  
                logic.createUser(user);
                tfName.setText("");
                tfLastname1.setText("");
                tfLastname2.setText("");
                tfEmail.setText("");
                cbTechnician.setSelected(false);
                
            } catch (EmailException ex) {
                DialogHelper.newInstance("ERROR", EMAIL_ERROR);
            } catch (Exception ex) {
                DialogHelper.newInstance("ERROR", GENERAL_ERROR);
            }
        }else{
            DialogHelper.newInstance("ERROR", "Rellene los campos");
        }
    }
    
    private boolean emailExists(String email) {
        boolean exists = false;
        try {
            exists = logic.findUserByLogin(email).size() < 0;
        } catch (Exception e) {
            DialogHelper.newInstance("ERROR", GENERAL_ERROR);
        }
        return exists;
    }
}
