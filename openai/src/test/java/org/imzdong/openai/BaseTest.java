package org.imzdong.openai;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.imzdong.openai.chatgpt.ChatGpt;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * @author DongZhou
 * @since 2023/4/6 10:32
 */
public class BaseTest {

    private String proxyHost = "127.0.0.1";
    private int proxyPort = 7890;
    private String token = OpenAiConstant.TOKEN;
    protected ObjectMapper objectMapper;

    protected ChatGpt chatGpt;

    protected void initChat(boolean userProxy){
        objectMapper = new ObjectMapper();
        ChatGpt.ChatGptBuilder builder = ChatGpt.builder()
                .apiKey(token)
                .baseUrl(OpenAiConstant.BASE_URL);
        if(userProxy){
            builder.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort)));
        }
        chatGpt = builder
                .build();
    }

}
