package org.terrence.testapp.envprocessor;

import java.util.Map;

import io.pivotal.cfenv.core.CfCredentials;
import io.pivotal.cfenv.core.CfService;
import io.pivotal.cfenv.spring.boot.CfEnvProcessor;
import io.pivotal.cfenv.spring.boot.CfEnvProcessorProperties;

public class WatsonVisualRecognitionCfEnvProcessor implements CfEnvProcessor {

    public WatsonVisualRecognitionCfEnvProcessor() {
        System.out.println("WatsonVisualRecognitionCfEnvProcessor built");
    }

    @Override
    public boolean accept(CfService service) {
        boolean match = service.existsByLabelStartsWith("watson_vision_combined");
        System.out.println("Match [" + match + "] to service " + service.toString());
        return match;
    }

    @Override
    public CfEnvProcessorProperties getProperties() {
        return CfEnvProcessorProperties.builder().propertyPrefixes("watson_vision_combined")
                .serviceName("Watson_Vision_Combined").build();
    }

    @Override
    public void process(CfCredentials cfCredentials, Map<String, Object> properties) {
        // set watsonVersion to date of the released watson spring boot starter
        // version 0.3.0 was released on 2018-06-22
        String watsonVersion = "2018-06-22";
        properties.put("watson.visual-recognition.url", cfCredentials.getUri("http"));
        properties.put("watson.visual-recognition.iam-api-key", cfCredentials.getString("apikey"));
        properties.put("watson.visual-recognition.versionDate", watsonVersion);

    }
}