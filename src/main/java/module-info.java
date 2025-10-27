
module com.okkoma.arkalanoix {

    requires transitive java.desktop;
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive javafx.graphics;
	requires com.fasterxml.jackson.databind;

    opens com.okkoma.arkalanoix to javafx.fxml;
    exports com.okkoma.arkalanoix;
}
