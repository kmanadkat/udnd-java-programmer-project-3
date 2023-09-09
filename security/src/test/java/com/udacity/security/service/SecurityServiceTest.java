package com.udacity.security.service;

import com.udacity.image.service.FakeImageService;
import com.udacity.security.application.StatusListener;
import com.udacity.security.data.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SecurityServiceTest {
    //======================= Arrange =======================
    private SecurityService securityService;
    private StatusListener statusListener;
    @Mock
    FakeImageService fakeImageService;
    @Mock
    SecurityRepository securityRepository;
    Set<Sensor> sensors = new HashSet<>();
    Sensor sensor_1 = new Sensor("Main Door", SensorType.DOOR);
    Sensor sensor_2 = new Sensor("Kitchen Door", SensorType.DOOR);

    //======================= Act =======================
    @BeforeEach
    void init() {
        securityService = new SecurityService(securityRepository, fakeImageService);
        sensors.add(sensor_1);
        sensors.add(sensor_2);
    }

    //======================= Assert =======================
    @ParameterizedTest
    @EnumSource(value = ArmingStatus.class, names = {"ARMED_HOME", "ARMED_AWAY"})
    @DisplayName("1. If alarm is armed and a sensor becomes activated, put the system into pending alarm status.")
    void alarmArmed_activateFirstSensor_checkPendingStatus(ArmingStatus armingStatus){
        // Initial Setup
        Mockito.when(securityRepository.getArmingStatus()).thenReturn(armingStatus);
        Mockito.when(securityRepository.getAlarmStatus()).thenReturn(AlarmStatus.NO_ALARM);
        // Activate Sensor
        securityService.changeSensorActivationStatus(sensor_1, true);
        // Verify
        Mockito.verify(securityRepository, Mockito.times(1)).setAlarmStatus(AlarmStatus.PENDING_ALARM);
    }

    @ParameterizedTest
    @EnumSource(value = ArmingStatus.class, names = {"ARMED_HOME", "ARMED_AWAY"})
    @DisplayName("2. If alarm is armed and a sensor becomes activated and the system is already pending alarm, set the alarm status to alarm.")
    void alarmArmed_activateSecondSensor_checkAlarmStatus(ArmingStatus armingStatus){
        // Initial Setup
        Mockito.when(securityRepository.getArmingStatus()).thenReturn(armingStatus);
        Mockito.when(securityRepository.getAlarmStatus()).thenReturn(AlarmStatus.PENDING_ALARM);
        // Activate Sensor
        securityService.changeSensorActivationStatus(sensor_2, true);
        // Verify
        Mockito.verify(securityRepository, Mockito.times(1)).setAlarmStatus(AlarmStatus.ALARM);
    }

    @Test
    @DisplayName("3. If pending alarm and all sensors are inactive, return to no alarm state.")
    void pendingStatus_deactivateSensor_checkNoAlarmStatus() {
        // Initial Setup
        Mockito.when(securityRepository.getAlarmStatus()).thenReturn(AlarmStatus.PENDING_ALARM);
        // Deactivate Sensor
        sensor_1.setActive(true);
        securityService.changeSensorActivationStatus(sensor_1, false);
        // Verify
        Mockito.verify(securityRepository, Mockito.atMostOnce()).setAlarmStatus(AlarmStatus.NO_ALARM);
    }

    @Test
    @DisplayName("4. If alarm is active, change in sensor state should not affect the alarm state.")
    void alarmStatus_deactivateSensors_checkAlarmStatus() {
        // Initial Setup
        Mockito.when(securityRepository.getAlarmStatus()).thenReturn(AlarmStatus.ALARM);
        // Deactivate Sensor
        sensor_1.setActive(true);
        sensor_2.setActive(true);
        securityService.changeSensorActivationStatus(sensor_1, false);
        securityService.changeSensorActivationStatus(sensor_2, false);
        // Verify - No Change in Alarm Status
        Mockito.verify(securityRepository, Mockito.never()).setAlarmStatus(Mockito.any(AlarmStatus.class));
    }

    @Test
    @DisplayName("5. If a sensor is activated while already active and the system is in pending state, change it to alarm state.")
    void pendingStatusSensor1Active_ActivateSensor2_checkAlarmStatus() {
        // Initial Setup
        Mockito.when(securityRepository.getAlarmStatus()).thenReturn(AlarmStatus.PENDING_ALARM);
        // Activate Sensor 2
        sensor_1.setActive(true);
        sensor_2.setActive(false);
        securityService.changeSensorActivationStatus(sensor_2, true);
        // Verify - Change in Alarm State
        Mockito.verify(securityRepository, Mockito.atMostOnce()).setAlarmStatus(AlarmStatus.ALARM);
    }

    @ParameterizedTest
    @EnumSource(value = AlarmStatus.class,names ={"ALARM", "NO_ALARM", "PENDING_ALARM"})
    @DisplayName("6. If a sensor is deactivated while already inactive, make no changes to the alarm state.")
    void Sensor1Inactive_DeactivateSensor1_alarmStatusNoChange(AlarmStatus alarmStatus) {
        // Initial Setup
        Mockito.when(securityRepository.getAlarmStatus()).thenReturn(alarmStatus);
        // Deactivate inactive Sensor
        sensor_1.setActive(false);
        securityService.changeSensorActivationStatus(sensor_1, false);
        // Verify - No Change in Alarm State
        Mockito.verify(securityRepository, Mockito.never()).setAlarmStatus(Mockito.any(AlarmStatus.class));
    }



    /*
     * 7. If the image service identifies an image containing a cat while the system is armed-home, put the system into alarm status.
     * 8. If the image service identifies an image that does not contain a cat, change the status to no alarm as long as the sensors are not active.
     * 9. If the system is disarmed, set the status to no alarm.
     * 10. If the system is armed, reset all sensors to inactive.
     * 11. If the system is armed-home while the camera shows a cat, set the alarm status to alarm.
     */

}