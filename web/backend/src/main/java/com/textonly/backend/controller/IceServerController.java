package com.textonly.backend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/ice")
public class IceServerController {

    @Value("${turn.server.url}")
    private String turnUrl;

    @Value("${turn.server.username}")
    private String turnUsername;

    @Value("${turn.server.password}")
    private String turnPassword;

    @Value("${stun.server.url}")
    private String stunUrl;

    @GetMapping
    public Map<String, Object> getIceServers() {
        Map<String, Object> response = new HashMap<>();
        List<Map<String, Object>> iceServers = new ArrayList<>();

        iceServers.add(Map.of("urls", stunUrl));
        iceServers.add(Map.of(
                "urls", turnUrl,
                "username", turnUsername,
                "credential", turnPassword
        ));

        response.put("iceServers", iceServers);
        return response;
    }
}
