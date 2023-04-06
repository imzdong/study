package org.imzdong.openai.chatgpt;

import lombok.Builder;
import org.imzdong.model.openai.completion.CompletionRequest;
import org.imzdong.model.openai.completion.CompletionResult;
import org.imzdong.model.openai.completion.chat.ChatCompletionRequest;
import org.imzdong.model.openai.completion.chat.ChatCompletionResult;
import org.imzdong.openai.builder.FeignClientBuilder;
import org.imzdong.openai.api.OpenAiApi;

import java.net.Proxy;

/**
 * @author DongZhou
 * @since 2023/4/6 9:57
 */
public class ChatGpt {

    private String apiKey;
    private String baseUrl;
    private Proxy proxy;
    private OpenAiApi openAiApi;

    public ChatGpt(){}

    @Builder
    public ChatGpt(String apiKey, String baseUrl, Proxy proxy){
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        this.proxy = proxy;
        openAiApi = FeignClientBuilder.build(baseUrl, OpenAiApi.class, apiKey, proxy);
    }

    public ChatCompletionResult chat(ChatCompletionRequest request){
        return openAiApi.createChatCompletion(request);
    }

    public CompletionResult completion(CompletionRequest build) {
        return openAiApi.createCompletion(build);
    }

}
