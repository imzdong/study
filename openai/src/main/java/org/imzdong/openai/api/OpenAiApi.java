package org.imzdong.openai.api;

import feign.RequestLine;
import org.imzdong.model.openai.OpenAiResponse;
import org.imzdong.model.openai.model.Model;

/**
 * @author DongZhou
 * @since 2023/3/29 13:31
 */
public interface OpenAiApi {

    @RequestLine("GET /v1/models")
    OpenAiResponse<Model> listModels();

}
