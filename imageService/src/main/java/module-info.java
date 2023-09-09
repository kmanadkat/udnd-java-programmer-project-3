module com.udacity.imageService {
    requires software.amazon.awssdk.auth;
    requires software.amazon.awssdk.core;
    requires software.amazon.awssdk.regions;
    requires software.amazon.awssdk.services.rekognition;
    requires org.slf4j;
    requires java.desktop;

    exports com.udacity.imageService.service;
}