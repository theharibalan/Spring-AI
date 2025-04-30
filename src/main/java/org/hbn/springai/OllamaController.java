package org.hbn.springai;

import  org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OllamaController {

    private ChatClient chatClient;
//    private OpenAiChatModel openAiChatModel;

    public OllamaController(OllamaChatModel chatModel){
        this.chatClient = ChatClient.create(chatModel);
    }

//    public OllamaController(ChatClient.Builder builder){
//        this.chatClient = builder
//                .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
//                // for chat memory for followup prompt so that ai give the followup response relevant to the previous response
//                .build();
//    }

    // another way to get the response from the chat
    @GetMapping("/chat/{message}")
    public ResponseEntity<String> getAnswer2(@PathVariable String message) {
        ChatResponse chatResponse = chatClient
                .prompt(message)
                .call()
                .chatResponse();

        System.out.println(chatResponse.getMetadata().getModel());

        String response = chatResponse
                .getResult()
                .getOutput()
                .getText();

        return ResponseEntity.ok(response);
    }

}
