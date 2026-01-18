package com.textonly.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/app")
@CrossOrigin(origins = "*")
public class AppController {

    private static final int CURRENT_VERSION_CODE = 1;
    private static final String CURRENT_VERSION_NAME = "1.0.0";
    private static final String DOWNLOAD_URL = "https://example.com/textonly-latest.apk";

    /**
     * Get app version information
     */
    @GetMapping("/version")
    public ResponseEntity<Map<String, Object>> getVersion() {
        Map<String, Object> versionInfo = new HashMap<>();
        versionInfo.put("versionCode", CURRENT_VERSION_CODE);
        versionInfo.put("versionName", CURRENT_VERSION_NAME);
        versionInfo.put("url", DOWNLOAD_URL);
        versionInfo.put("updateAvailable", false); // Will be calculated based on client's version

        return ResponseEntity.ok(versionInfo);
    }

    /**
     * Check if update is available
     * Request param: currentVersion (version code from client)
     */
    @GetMapping("/version/check")
    public ResponseEntity<Map<String, Object>> checkUpdate(@RequestParam int currentVersion) {
        Map<String, Object> updateInfo = new HashMap<>();
        boolean updateAvailable = currentVersion < CURRENT_VERSION_CODE;

        updateInfo.put("updateAvailable", updateAvailable);
        updateInfo.put("latestVersion", CURRENT_VERSION_CODE);
        updateInfo.put("versionName", CURRENT_VERSION_NAME);
        
        if (updateAvailable) {
            updateInfo.put("url", DOWNLOAD_URL);
            updateInfo.put("message", "O nouă versiune este disponibilă!");
        }

        return ResponseEntity.ok(updateInfo);
    }
}
