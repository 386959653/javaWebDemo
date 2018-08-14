package com.weichi.erp;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Wewon on 2018/8/14.
 */
@org.springframework.context.annotation.Configuration
public class FreemarkerConfig {

    private static Logger log = LoggerFactory.getLogger(FreemarkerConfig.class);
    @Autowired
    private FreeMarkerProperties properties;

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer(@Value("${auto_import}") String autoImport) throws Exception {
        FreeMarkerConfigurer config = new FreeMarkerConfigurer();
        writerProperties(config);
        Configuration configuration = null;
        try {
            configuration = config.createConfiguration();
        } catch (IOException e) {
            throw new Exception("freemarker配置bean，IO异常", e);
        } catch (TemplateException e) {
            throw new Exception("freemarker配置bean异常", e);
        }
        setAutoImport(autoImport, configuration);
        config.setConfiguration(configuration);
        return config;
    }

    private void writerProperties(FreeMarkerConfigurer config) {
        config.setTemplateLoaderPaths(this.properties.getTemplateLoaderPath());
        config.setPreferFileSystemAccess(this.properties.isPreferFileSystemAccess());
        config.setDefaultEncoding(this.properties.getCharsetName());
        Properties settings = new Properties();
        settings.putAll(this.properties.getSettings());
        config.setFreemarkerSettings(settings);
    }

    private void setAutoImport(String autoImport, Configuration configuration) throws Exception {
        if ("_".equals(autoImport.trim())) {
            return;
        }
        String[] imports = autoImport.split(";");
        Map<String, String> importMap = new HashMap<String, String>(imports.length);
        for (String s : imports) {
            String[] keyValue = s.split("as");
            if (keyValue.length != 2) {
                log.error("freemarker配置auto_import格式不正确 ");
                throw new Exception("freemarker配置auto_import格式不正确");
            }
            importMap.put(keyValue[1].trim(), keyValue[0].trim());
        }
        configuration.setAutoImports(importMap);
    }

}

