module com.okkomastudio.arkalanoix {

    requires transitive java.desktop;
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive javafx.graphics;
	requires transitive com.fasterxml.jackson.databind;
    
    opens com.okkomastudio.arkalanoix.desktop to javafx.fxml;
    exports com.okkomastudio.arkalanoix.core;
    exports com.okkomastudio.arkalanoix.desktop;
}
