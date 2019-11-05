package com.insidecoding.opium.persistence;

import com.insidecoding.opium.entity.Device;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.Map.Entry;

@Component
public class DeviceStore {
    private Map<String, List<Device>> devices = Collections
            .synchronizedMap(new HashMap<>());

    public void store(String key, List<Device> devices) {
        this.devices.put(key, devices);
    }

    public void removeAgent(String ip) {
        this.devices.remove(ip);
    }

    public List<String> agents() {
        Map<String, List<Device>> copy = Collections.unmodifiableMap(devices);

        List<String> agents = new ArrayList<String>();
        for (Entry<String, List<Device>> entry : copy.entrySet()) {
            agents.add(entry.getKey());
        }

        return agents;
    }

    public List<Device> all() {
        List<Device> all = new ArrayList<>();
        for (Entry<String, List<Device>> entry : this.devices.entrySet()) {
            all.addAll(entry.getValue());
        }

        return all;
    }
}
