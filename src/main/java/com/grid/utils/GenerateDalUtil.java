package com.grid.utils;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import javax.xml.soap.Text;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GenerateDalUtil {

    public static void main(String[] args) {

        try {
            List<String> warnings = new ArrayList<String>();
            boolean overwrite = true;
            String genCfg = "/generatorConfig.xml";
            File configFile = new File(Text.class.getResource(genCfg).getFile());
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = null;
            config = cp.parseConfiguration(configFile);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);

            myBatisGenerator.generate(null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
