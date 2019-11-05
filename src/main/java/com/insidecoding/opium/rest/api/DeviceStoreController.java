package com.insidecoding.opium.rest.api;

import com.insidecoding.opium.entity.Device;
import com.insidecoding.opium.persistence.DeviceStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class DeviceStoreController {

    private static final Logger LOG = LoggerFactory.getLogger(DeviceStoreController.class);

    @Autowired
    private DeviceStore deviceStore;

    @CrossOrigin
    @RequestMapping(value = "/device", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Device>> getAll() {
        return new ResponseEntity<>(deviceStore.all(), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/device", method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<String> storeDevices(HttpServletRequest request, @RequestBody List<Device> devices) {
        LOG.info("on PUT ... Persisting devices ..." + devices);

        try {
            if (devices.size() > 0) {
                String ip = devices.get(0).getIp();
                deviceStore.store(ip, devices);
            } else {
                String ip = request.getRemoteAddr();
                deviceStore.removeAgent(ip);
                LOG.info("received ip = " + ip);
            }

        } catch (ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            List<String> props = new ArrayList<>();

            for (ConstraintViolation<?> violation : violations) {
                props.add(violation.getPropertyPath().toString());
            }

            return new ResponseEntity<>("The following properties cannot be empty: " + props,
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

}
