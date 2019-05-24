package org.springcloudbox.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 网关启动类
 *
 * @author chm141593@alibaba-inc.com
 * @date 2019-05-24 14:59
 */

@SpringCloudApplication
public class GateWayApplication {
  public static void main(String[] args) {
    SpringApplication.run(GateWayApplication.class, args);
  }
}
