package com.shy.chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shy.chat.common.Result;
import com.shy.chat.dto.ChatMessageDTO;
import com.theokanning.openai.OpenAiApi;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.service.OpenAiService;
import okhttp3.OkHttpClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Retrofit;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


/**
 * @author shihaoyan
 * @date 2023/5/7 6:27
 * @since 1.0.0
 */
@RestController
public class ChatGptController {

    private static final String TOKEN = "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";

    private static final OpenAiService service;

    static {
        ObjectMapper mapper = OpenAiService.defaultObjectMapper();
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890));
        OkHttpClient client = OpenAiService.defaultClient(TOKEN, Duration.ofSeconds(10000)).newBuilder().proxy(proxy).build();
        Retrofit retrofit = OpenAiService.defaultRetrofit(client, mapper);
        OpenAiApi api = retrofit.create(OpenAiApi.class);
        service = new OpenAiService(api);
    }


    @GetMapping("/chat/message")
    public Result<ChatMessageDTO> chatGpt(String msg) {

        CompletionRequest completionRequest = CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt(msg)
                .temperature(0.5)
                .maxTokens(4000)
                .topP(1D)
                .frequencyPenalty(0D)
                .presencePenalty(0D)
                .build();
        CompletionResult completion = service.createCompletion(completionRequest);
        List<CompletionChoice> choices = completion.getChoices();
        List<String> strings = new ArrayList<>();
        for (CompletionChoice choice : choices) {
            String text = choice.getText();
            strings.add(text);
        }
        ChatMessageDTO chatMessageDTO = new ChatMessageDTO();
        chatMessageDTO.setText(strings);
        return Result.success(chatMessageDTO);
    }

}
