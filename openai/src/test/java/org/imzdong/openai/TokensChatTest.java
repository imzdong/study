package org.imzdong.openai;

import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author DongZhou
 * @since 2023/4/17 19:02
 */
public class TokensChatTest {
    private static final String API_KEY = "your-api-key-here";
    private static final int MAX_TOKENS = 4096;

    private static OkHttpClient client = new OkHttpClient();

    private static String startSession() throws IOException {
        // 创建请求体
        RequestBody requestBody = new FormBody.Builder()
                .add("model", "text-davinci-002")
                .build();

        // 创建请求
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/sessions")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + API_KEY)
                .post(requestBody)
                .build();

        // 发送请求并获取响应
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        // 解析响应中的会话 ID
        String sessionId = parseSessionId(responseBody);

        return sessionId;
    }

    private static String parseSessionId(String responseBody) {
        // 解析响应文本中的会话 ID
        // 例如：{"object":"session","id":"sess_XXXXXX","created":1630692261}
        int startIndex = responseBody.indexOf("\"id\":\"") + 6;
        int endIndex = responseBody.indexOf("\",", startIndex);
        String sessionId = responseBody.substring(startIndex, endIndex);
        return sessionId;
    }

    private static List<String> sendRequest(String sessionId, String prompt, String context) throws IOException {
        List<String> responses = new ArrayList<>();
        int tokens = 0;

        while (true) {
            // 创建请求体
            RequestBody requestBody = new FormBody.Builder()
                    .add("model", "text-davinci-002")
                    .add("prompt", prompt)
                    .add("temperature", "0.5")
                    .add("max_tokens", String.valueOf(MAX_TOKENS - tokens))
                    .add("n", "1")
                    .add("stream", "false")
                    .add("stop", "\n")
                    .add("context", context)
                    .build();

            // 创建请求
            Request request = new Request.Builder()
                    .url("https://api.openai.com/v1/chat/completions")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + API_KEY)
                    .addHeader("OpenAI-Session", sessionId)
                    .post(requestBody)
                    .build();

            // 发送请求并获取响应
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();

            // 解析响应中的文本和 tokens 数量
            String text = parseText(responseBody);
            int tokensCount = parseTokensCount(responseBody);

            // 将文本添加到响应列表中
            responses.add(text);

            // 更新 tokens 数量和上下文信息
            tokens += tokensCount;
            context += text;

            // 如果 tokens 数量达到最大值，则开启一个新的会话并重新开始对话
            if (tokens >= MAX_TOKENS) {
                System.out.println("Reached最大 tokens 数量，重新开始会话");
                sessionId = startSession();
                context = "";
                tokens = 0;
            }

            // 如果响应中包含 stop 信号，则停止对话
            if (text.contains("\n")) {
                break;
            }
        }

        return responses;
    }

    private static String parseText(String responseBody) {
        // 解析响应文本中的文本内容
        // 例如：{"id":"cmpl-XXXXXX","object":"text_completion","created":1630700473,"model":"text-davinci-002","choices":[{"text":"Response 1\n"}]}
        int startIndex = responseBody.indexOf("\"text\":\"") + 8;
        int endIndex = responseBody.indexOf("\"}", startIndex);
        String text = responseBody.substring(startIndex, endIndex);
        return text;
    }

    private static int parseTokensCount(String responseBody) {
        // 解析响应文本中的 tokens 数量
        // 例如：{"id":"cmpl-XXXXXX","object":"text_completion","created":1630700473,"model":"text-davinci-002","choices":[{"text":"Response 1\n","index":0,"logprobs":null,"finish_reason":"stop"}]}
        int startIndex = responseBody.indexOf("\"text\":\"") + 8;
        int endIndex = responseBody.indexOf("\"}],\"", startIndex);
        String choices = responseBody.substring(startIndex, endIndex);
        return choices.split(" ").length;
    }

    public static void main(String[] args) throws IOException {
        String sessionId = startSession();
        String context = "";
        String prompt = "Hello!";
        List<String> responses = sendRequest(sessionId, prompt, context);
        for (String response : responses) {
            System.out.println(response);
        }
    }

}
