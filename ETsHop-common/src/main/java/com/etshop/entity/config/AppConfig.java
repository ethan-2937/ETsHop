package com.etshop.entity.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("appConfig")

public class AppConfig {
    @Value("${admin.account:}")
    private String adminAccount;
    @Value("${admin.password:}")
    private String adminPassword;
    @Value("${project.folder:}")
    private String projectFolder;

    public String getAdminAccount() {
        return adminAccount;
    }
    public String getAdminPassword() {
        return adminPassword;
    }
    public String getProjectFolder() {
        return projectFolder;
    }
}
