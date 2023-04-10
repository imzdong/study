package org.imzdong.openai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.imzdong.model.openai.completion.chat.ChatCompletionRequest;
import org.imzdong.model.openai.completion.chat.ChatCompletionResult;
import org.imzdong.model.openai.completion.chat.ChatMessage;
import org.imzdong.model.openai.completion.chat.ChatMessageRole;
import org.imzdong.openai.api.OpenAiApi;
import org.imzdong.openai.builder.FeignClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @author DongZhou
 * @since 2023/3/31 13:39
 */
public class ChatCompletionTest extends BaseTest {

    @BeforeEach
    public void init(){
        initChat(true);
    }

    @Test
    public void testCompletion(){
        doChat();
    }

    public static void main(String[] args) {
        ChatCompletionTest chatCompletionTest = new ChatCompletionTest();
        chatCompletionTest.init();
        chatCompletionTest.doChat();
    }

    private void doChat() {
        //text-davinci-003 gpt-3.5-turbo-0301
        final List<ChatMessage> messages = new ArrayList<>();
        ChatCompletionResult completion;
        ChatCompletionRequest build;

        // 创建 Scanner 对象以获取用户输入
        Scanner scanner = new Scanner(System.in);

        while (true){
            // 提示用户输入消息
            System.out.println("请输入消息：");
            // 从用户输入中获取字符串消息
            String message = scanner.nextLine();
            // 输出用户输入的消息
            System.out.println("您输入的消息是：" + message);
            ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), message);
            messages.add(systemMessage);
            build = ChatCompletionRequest
                    .builder()
                    .model("gpt-3.5-turbo")
                    .messages(messages)
                    .n(1)
                    .maxTokens(4000)
                    .logitBias(new HashMap<>())
                    .build();
            completion = chatGpt.chat(build);
            String s = completion.getChoices().get(0).getMessage().getContent();
            System.out.println("ChatGpt回复的消息是：" + s);
            if(Objects.equals(message, "bye")){
                System.out.println("ChatGpt结束");
                break;
            }
        }
        // 关闭 Scanner 对象
        scanner.close();
    }

}
