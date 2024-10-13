package comp5216.sydney.edu.au.digicare.model.service;

import android.content.Context;
import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GeminiIntegrationService {
    private static final String GEMINI_API_KEY = "AIzaSyBGq8IOhC8dmJOdEzE6qf9jNmFXGGQPbnI";

    public void sendToGemini(String inputText, Context context, GeminiCallback callback) {
        GenerativeModel gm = new GenerativeModel("gemini-1.5-flash", GEMINI_API_KEY);
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
