package com.jt.www.service.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
//url = "http://192.144.153.26:36103",
@FeignClient(value = "idg-svc")
public interface IdgClient {

    @GetMapping(value="/api/v1/idg")
    long nextId();

    @GetMapping(value="api/v1/resub_token")
    String resubToken();
}
