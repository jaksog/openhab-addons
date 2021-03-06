/**
 * Copyright (c) 2010-2020 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.neohub.internal;

import java.math.BigDecimal;
import java.util.List;

import org.eclipse.jdt.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;

/**
 * A wrapper around the JSON response to the JSON INFO request
 *
 * @author Sebastian Prehn - Initial contribution
 * @author Andrew Fiddian-Green - Refactoring for openHAB v2.x
 * 
 */
public class NeoHubInfoResponse {

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(NeohubBool.class, new NeohubBoolDeserializer()).create();

    @SerializedName("devices")
    private List<DeviceInfo> deviceInfos;

    static class StatMode {
        @SerializedName("MANUAL_OFF")
        private NeohubBool manualOff;
        @SerializedName("MANUAL_ON")
        private NeohubBool manualOn;

        private Boolean stateManualOn() {
            return (manualOn == null ? false : manualOn.value);
        }

        private Boolean stateManualOff() {
            return (manualOff == null ? false : manualOff.value);
        }
    }

    public static class DeviceInfo {

        @SerializedName("device")
        private String deviceName;
        @SerializedName("CURRENT_SET_TEMPERATURE")
        private BigDecimal currentSetTemperature;
        @SerializedName("CURRENT_TEMPERATURE")
        private BigDecimal currentTemperature;
        @SerializedName("CURRENT_FLOOR_TEMPERATURE")
        private BigDecimal currentFloorTemperature;
        @SerializedName("COOL_INP")
        private NeohubBool coolInput;
        @SerializedName("LOW_BATTERY")
        private NeohubBool batteryLow;
        @SerializedName("STANDBY")
        private NeohubBool standby;
        @SerializedName("HEATING")
        private NeohubBool heating;
        @SerializedName("PREHEAT")
        private NeohubBool preHeat;
        @SerializedName("TIMER")
        private NeohubBool timerOn;
        @SerializedName("DEVICE_TYPE")
        private BigDecimal deviceType;
        @SerializedName("OFFLINE")
        private NeohubBool offline;
        @SerializedName("STAT_MODE")
        private StatMode statMode = new StatMode();

        protected Boolean safeBoolean(NeohubBool value) {
            return (value == null ? false : value.value);
        }

        protected BigDecimal safeBigDecimal(BigDecimal value) {
            return value != null ? value : BigDecimal.ZERO;
        }

        public String getDeviceName() {
            return deviceName != null ? deviceName : "";
        }

        public BigDecimal getTargetTemperature() {
            return safeBigDecimal(currentSetTemperature);
        }

        public BigDecimal getRoomTemperature() {
            return safeBigDecimal(currentTemperature);
        }

        public BigDecimal getFloorTemperature() {
            return safeBigDecimal(currentFloorTemperature);
        }

        public BigDecimal getDeviceType() {
            return safeBigDecimal(deviceType);
        }

        public Boolean isStandby() {
            return safeBoolean(standby);
        }

        public Boolean isHeating() {
            return safeBoolean(heating);
        }

        public Boolean isPreHeating() {
            return safeBoolean(preHeat);
        }

        public Boolean isTimerOn() {
            return safeBoolean(timerOn);
        }

        public Boolean isOffline() {
            return safeBoolean(offline);
        }

        public Boolean stateManual() {
            return (statMode != null && statMode.stateManualOn());
        }

        public Boolean stateAuto() {
            return (statMode != null && statMode.stateManualOff());
        }

        public Boolean isCoolInputOn() {
            return safeBoolean(coolInput);
        }

        public Boolean isBatteryLow() {
            return safeBoolean(batteryLow);
        }
    }

    /**
     * Create wrapper around the JSON response
     * 
     * @param response the JSON INFO request
     * @return a NeoHubInfoResponse wrapper around the JSON response
     * @throws JsonSyntaxException
     * 
     */
    public static @Nullable NeoHubInfoResponse createInfoResponse(String response) throws JsonSyntaxException {
        return GSON.fromJson(response, NeoHubInfoResponse.class);
    }

    /*
     * returns the DeviceInfo corresponding to a given device name
     * 
     * @param deviceName the device name
     * 
     * @return its respective DeviceInfo
     */
    public DeviceInfo getDeviceInfo(String deviceName) {
        for (DeviceInfo d : deviceInfos) {
            if (deviceName.equals(d.getDeviceName())) {
                return d;
            }
        }
        return null;
    }

    /*
     * @return the full list of DeviceInfo objects
     */
    public List<DeviceInfo> getDevices() {
        return deviceInfos;
    }
}
