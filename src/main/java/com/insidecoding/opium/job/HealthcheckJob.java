package com.insidecoding.opium.job;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.insidecoding.opium.persistence.DeviceStore;
import com.insidecoding.opium.rest.api.service.RemoteExecutionService;

@Component
public class HealthcheckJob {
  private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(HealthcheckJob.class);
  private static final int RETRY = 3;

  @Autowired
  private DeviceStore deviceStore;

  @Autowired
  private RemoteExecutionService remoteExec;

  @Scheduled(cron = "0/10 * * * * ?")
  public void run() {
    List<String> agents = deviceStore.agents();
    for (String ip : agents) {
      boolean healthy = remoteExec.healthy(ip, RETRY);

      if (!healthy) {
        LOG.info(ip + " is down. Removing...");
        deviceStore.removeAgent(ip);
      }
    }
  }
}
