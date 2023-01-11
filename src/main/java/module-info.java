module strengthdetailscalculator {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires static lombok;
    requires templ4docx;


    opens strengthdetailscalculator to javafx.fxml;
    exports strengthdetailscalculator;
    exports strengthdetailscalculator.controller;
    opens strengthdetailscalculator.controller to javafx.fxml;
}