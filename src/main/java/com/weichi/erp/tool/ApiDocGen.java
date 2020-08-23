package com.weichi.erp.tool;

import io.github.swagger2markup.GroupBy;
import io.github.swagger2markup.Language;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

/**
 * Created by Wewon on 2020-08-23.
 */
public class ApiDocGen {
    public static void main(String[] args) throws MalformedURLException {
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();

        Swagger2MarkupConverter.from(new URL("http://localhost:8087/v2/api-docs"))
                .withConfig(config)
                .build()
                .toFolder(Paths.get("./doc/markdown/generated"));
    }
}
