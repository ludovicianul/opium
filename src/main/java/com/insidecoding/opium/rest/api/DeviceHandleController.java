package com.insidecoding.opium.rest.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.insidecoding.opium.entity.OpiumCallResponse;
import com.insidecoding.opium.rest.api.service.RemoteExecutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.constraints.Pattern;
import java.io.IOException;

@Controller
public class DeviceHandleController {
    private static final Logger LOG = LoggerFactory.getLogger(DeviceHandleController.class);

    @Autowired
    private RemoteExecutionService remoteExec;

    @CrossOrigin
    @RequestMapping(value = "/execute", method = RequestMethod.DELETE, consumes = "application/json")
    public ResponseEntity<String> stop(@RequestBody @Validated OpiumCommand opiumCmd) {
        LOG.info("Got command: " + opiumCmd);

        ResponseEntity<String> response = null;
        try {
            OpiumCallResponse opiumResp = this.remoteExec.stop(opiumCmd.ip, opiumCmd.command);
            response = new ResponseEntity<>(opiumResp.getResponseBody(), opiumResp.getStatusCode());
        } catch (IOException e) {
            LOG.error("Command not executed: " + opiumCmd, e);
            response = new ResponseEntity<>("Error while stopping appium: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    @CrossOrigin
    @RequestMapping(value = "/execute", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<String> execute(@RequestBody @Validated OpiumCommand opiumCmd) {
        LOG.info("Got command: " + opiumCmd);

        ResponseEntity<String> response = null;
        try {
            OpiumCallResponse opiumResp = this.remoteExec.start(opiumCmd.ip, opiumCmd.command);
            response = new ResponseEntity<>(opiumResp.getResponseBody(), opiumResp.getStatusCode());
        } catch (IOException e) {
            LOG.error("Command not executed: " + opiumCmd, e);
            response = new ResponseEntity<>("Error while starting appium: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    static class OpiumCommand {

        @Pattern(regexp = "[\\w.]+")
        private String ip;

        @Pattern(regexp = "[\\w]+")
        private String deviceHash;

        @Pattern(regexp = "[\\w\\-\\w ]+")
        private String command;

        @JsonCreator
        public OpiumCommand(@JsonProperty("ip") String ip,
                            @JsonProperty("deviceHash") String deviceHash, @JsonProperty("command") String command) {
            this.ip = ip;
            this.deviceHash = deviceHash;
            this.command = command;
        }

        @Override
        public String toString() {
            return "OpiumCommand [ip=" + ip + ", deviceHash=" + deviceHash + ", command=" + command + "]";
        }

    }

}
