module com.okkomastudio.arkalanoix.desktop {

    requires transitive com.okkomastudio.arkalanoix.core; 
    
    requires java.desktop;

    requires org.apache.logging.log4j;

    requires transitive javafx.controls;
    requires javafx.fxml;

    opens com.okkomastudio.arkalanoix.desktop to javafx.fxml;
    exports com.okkomastudio.arkalanoix.desktop;
}
