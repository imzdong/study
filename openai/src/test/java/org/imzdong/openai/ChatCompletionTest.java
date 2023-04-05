package org.imzdong.openai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.imzdong.model.openai.completion.chat.ChatCompletionRequest;
import org.imzdong.model.openai.completion.chat.ChatCompletionResult;
import org.imzdong.model.openai.completion.chat.ChatMessage;
import org.imzdong.model.openai.completion.chat.ChatMessageRole;
import org.imzdong.openai.api.OpenAiApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author DongZhou
 * @since 2023/3/31 13:39
 */
public class ChatCompletionTest {

    private static final String token = OpenAiConstant.TOKEN;
    private ObjectMapper objectMapper;
    private OpenAiApi openAiApi;

    @BeforeEach
    public void init(){
        objectMapper = new ObjectMapper();
        openAiApi = FeignClientBuilder.build(OpenAiConstant.BASE_URL_CF, OpenAiApi.class, token, false);
    }

    @Test
    public void testCompletion(){
        //text-davinci-003 gpt-3.5-turbo-0301
        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), "You are a dog and will speak as such.");
        messages.add(systemMessage);

        ChatCompletionRequest build = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .n(5)
                .maxTokens(50)
                .logitBias(new HashMap<>())
                .build();
        ChatCompletionResult completion = openAiApi.createChatCompletion(build);
        String s = null;
        try {
            s = objectMapper.writeValueAsString(completion);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(s);
    }

}
