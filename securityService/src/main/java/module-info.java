module com.udacity.securityService {
    requires java.desktop;
    requires com.google.common;

    requires com.udacity.imageService;

    exports com.udacity.securityService.data;
    exports com.udacity.securityService.application;
}