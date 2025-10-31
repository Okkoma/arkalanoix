module com.okkomastudio.arkalanoix.desktop {

    requires transitive com.okkomastudio.arkalanoix.core; 
    requires transitive java.desktop;
    requires transitive javafx.controls;
    requires transitive javafx.fxml;

    opens com.okkomastudio.arkalanoix.desktop to javafx.fxml;
    exports com.okkomastudio.arkalanoix.desktop;
}
