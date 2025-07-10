module checklist {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.junit.jupiter.api;
    requires junit;

    exports view;

    opens test to junit;
}