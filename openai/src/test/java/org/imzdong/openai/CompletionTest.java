package org.imzdong.openai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.imzdong.model.openai.completion.CompletionRequest;
import org.imzdong.model.openai.completion.CompletionResult;
import org.imzdong.openai.api.OpenAiApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author DongZhou
 * @since 2023/3/31 13:39
 */
public class CompletionTest {

    private static final String BASE_URL = "https://api.openai.com/";

    private static final String token = TestStr.token;
    private ObjectMapper objectMapper;
    private OpenAiApi openAiApi;

    @BeforeEach
    public void init(){
        objectMapper = new ObjectMapper();
        openAiApi = FeignClientBuilder.build(BASE_URL, OpenAiApi.class, token);
    }

    @Test
    public void testCompletion(){
        //text-davinci-003 gpt-3.5-turbo-0301
        CompletionRequest build = CompletionRequest.builder().model("text-davinci-003")
                .prompt("给我将一个故事吧")
                .maxTokens(20).build();
        CompletionResult completion = openAiApi.createCompletion(build);
        String s = null;
        try {
            s = objectMapper.writeValueAsString(completion);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(s);
    }

}
