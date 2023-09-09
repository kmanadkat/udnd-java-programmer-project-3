module com.udacity.security {
    requires miglayout;
    requires java.desktop;
    requires com.google.common;
    requires com.google.gson;
    requires java.prefs;

    requires com.udacity.image;
    opens com.udacity.security.data to com.google.gson;
}