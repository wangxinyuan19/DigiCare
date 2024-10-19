package comp5216.sydney.edu.au.digicare.model.service;

import android.content.Context;
import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.BlockThreshold;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.ai.client.generativeai.type.HarmCategory;
import com.google.ai.client.generativeai.type.SafetySetting;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GeminiIntegrationService {
    private static final String GEMINI_API_KEY = "AIzaSyBGq8IOhC8dmJOdEzE6qf9jNmFXGGQPbnI";
    SafetySetting harassmentSafety = new SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.NONE);
    SafetySetting hateSpeechSafety = new SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.NONE);
    SafetySetting dangerousSafety = new SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.NONE);
    SafetySetting sexualSafety = new SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.NONE);
    public void sendToGemini(String inputText, Context context, GeminiCallback callback) {
        GenerativeModel gm = new GenerativeModel(
                "gemini-1.5-flash",
                GEMINI_API_KEY,
                null,
                Arrays.asList(harassmentSafety, hateSpeechSafety, dangerousSafety, sexualSafety)
                );
        GenerativeModelFutures model = GenerativeModelFutures.from(gm);
        Content content = new Content.Builder().addText(inputText).build();
        Executor executor = Executors.newSingleThreadExecutor();
        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);

        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                callback.onSuccess(result.getText());
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure("Analysis failed: " + t.getMessage());
            }
        }, executor);
    }

    public interface GeminiCallback {
        void onSuccess(String result);
        void onFailure(String error);
    }
}
