///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.tartangatickets.views;
//
//import com.gluonhq.charm.glisten.mvc.View;
//import java.io.IOException;
//import javafx.fxml.FXMLLoader;
//
///**
// *
// * @author iker
// */
//public class MessageView {
//    
//    private final String name;
//
//    public MessageView(String name) {
//        this.name = name;
//    }
//    
//    public View getView() {
//        try {
//            View view = FXMLLoader.load(MessageView.class.getResource("mensajes.fxml"));
//            view.setName(name);
//            return view;
//        } catch (IOException e) {
//            System.out.println("IOException: " + e);
//            return new View(name);
//        }
//    }
//}
