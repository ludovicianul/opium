package com.insidecoding.opium.rest.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

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

import com.insidecoding.opium.entity.Device;
import com.insidecoding.opium.persistence.DeviceStore;

@Controller
public class DeviceStoreController {

  private static final Logger LOG = LoggerFactory.getLogger(DeviceStoreController.class);

  @Autowired
  private DeviceStore deviceStore;

  @CrossOrigin
  @RequestMapping(value = "/device", method = RequestMethod.GET, produces = "application/json")
  public ResponseEntity<List<Device>> getAll() {
    return new ResponseEntity<List<Device>>(deviceStore.all(), HttpStatus.OK);
  }

  @CrossOrigin
  @RequestMapping(value = "/device", method = RequestMethod.PUT, consumes = "application/json")
  public ResponseEntity<String> storeTest(@RequestBody List<Device> devices) {
    LOG.info("on PUT ... Persisting devices ..." + devices);

    try {
      String ip = "";
      if (devices.size() > 0) {
        ip = devices.get(0).getIp();
      }
      deviceStore.store(ip, devices);

    } catch (ConstraintViolationException e) {
      Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
      List<String> props = new ArrayList<String>();

      for (ConstraintViolation<?> violation : violations) {
        props.add(violation.getPropertyPath().toString());
      }

      return new ResponseEntity<String>("The following properties cannot be empty: " + props,
          HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<String>("OK", HttpStatus.OK);
  }

}
