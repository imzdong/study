package org.imzdong.openai;

import feign.Feign;
import org.imzdong.model.openai.OpenAiResponse;
import org.imzdong.model.openai.model.Model;
import org.imzdong.openai.api.OpenAiApi;

/**
 * @author DongZhou
 * @since 2023/3/29 13:30
 */
public class Main {

    private static final String BASE_URL = "https://api.openai.com/";

    public static void main(String[] args) {
        OpenAiApi api = Feign.builder()
                //.client()
                .target(OpenAiApi.class, BASE_URL);
        OpenAiResponse<Model> modelOpenAiResponse = api.listModels();
        System.out.println(modelOpenAiResponse);
    }

}
