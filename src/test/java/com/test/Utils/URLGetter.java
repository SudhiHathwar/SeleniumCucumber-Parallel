package com.test.Utils;

import com.test.Configuration.PropertyReader;

public class URLGetter {

    String toc_name;

    public String getURL ( String portalName ) {
        PropertyReader propertyReader = new PropertyReader();
        String url = propertyReader.readEnvSpecProperty(portalName);
        String environment = getEnvironment();
        toc_name = portalName;
        if (!environment.toLowerCase().contains("prod")) {
            return "https://" + environment + url;
        } else {
            System.out.println(url);
            return url;
        }
    }

    private String getEnvironment () {
        String environment = System.getenv("ENVIRONMENT");
        if (environment == null) {
            environment = new PropertyReader().readProperty("ENVIRONMENT");
        }
        return environment;
    }

}
