package com.example.alexisapp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.StringJoiner;

@Entity
public class BackhaulData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;
    private String gps_signal;
    private int criticallity_level;
    private LocalDateTime timestamp;

    @Override
    public String toString() {
        return new StringJoiner(", ", BackhaulData.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("address='" + address + "'")
                .add("gps_signal='" + gps_signal + "'")
                .add("criticallity_level=" + criticallity_level)
                .add("timestamp=" + timestamp)
                .toString();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGps_signal() {
        return gps_signal;
    }

    public void setGps_signal(String gps_signal) {
        this.gps_signal = gps_signal;
    }

    public int getCriticallity_level() {
        return criticallity_level;
    }

    public void setCriticallity_level(int criticallity_level) {
        this.criticallity_level = criticallity_level;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}

