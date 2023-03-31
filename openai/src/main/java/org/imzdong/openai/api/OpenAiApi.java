package org.imzdong.openai.api;

import feign.Body;
import feign.Param;
import feign.RequestLine;
import okhttp3.RequestBody;
import org.imzdong.model.openai.DeleteResult;
import org.imzdong.model.openai.OpenAiResponse;
import org.imzdong.model.openai.completion.CompletionRequest;
import org.imzdong.model.openai.completion.CompletionResult;
import org.imzdong.model.openai.completion.chat.ChatCompletionRequest;
import org.imzdong.model.openai.completion.chat.ChatCompletionResult;
import org.imzdong.model.openai.edit.EditRequest;
import org.imzdong.model.openai.edit.EditResult;
import org.imzdong.model.openai.embedding.EmbeddingRequest;
import org.imzdong.model.openai.embedding.EmbeddingResult;
import org.imzdong.model.openai.file.File;
import org.imzdong.model.openai.finetune.FineTuneEvent;
import org.imzdong.model.openai.finetune.FineTuneRequest;
import org.imzdong.model.openai.finetune.FineTuneResult;
import org.imzdong.model.openai.image.CreateImageRequest;
import org.imzdong.model.openai.image.ImageResult;
import org.imzdong.model.openai.model.Model;
import org.imzdong.model.openai.moderation.ModerationRequest;
import org.imzdong.model.openai.moderation.ModerationResult;

/**
 * @author DongZhou
 * @since 2023/3/29 13:31
 */
public interface OpenAiApi {

    @RequestLine("GET /v1/models")
    OpenAiResponse<Model> listModels();

    @RequestLine("GET /v1/models/{model_id}")
    Model getModel(@Param("model_id") String modelId);

    @RequestLine("POST /v1/completions")
    CompletionResult createCompletion(CompletionRequest request);

    @RequestLine("POST /v1/chat/completions")
    ChatCompletionResult createChatCompletion(ChatCompletionRequest request);

    @RequestLine("POST /v1/edits")
    EditResult createEdit(EditRequest request);

    @RequestLine("POST /v1/embeddings")
    @Body("{request}")
    EmbeddingResult createEmbeddings(@Param("request") EmbeddingRequest request);

    @RequestLine("GET /v1/files")
    OpenAiResponse<File> listFiles();


    @RequestLine("POST /v1/fine-tunes")
    @Body("{request}")
    FineTuneResult createFineTune(@Param("request") FineTuneRequest request);

    @RequestLine("POST /v1/completions")
    @Body("{request}")
    CompletionResult createFineTuneCompletion(@Param("request")CompletionRequest request);

    @RequestLine("GET /v1/fine-tunes")
    OpenAiResponse<FineTuneResult> listFineTunes();

    @RequestLine("GET /v1/fine-tunes/{fine_tune_id}")
    FineTuneResult retrieveFineTune(@Param("fine_tune_id") String fineTuneId);

    @RequestLine("POST /v1/fine-tunes/{fine_tune_id}/cancel")
    FineTuneResult cancelFineTune(@Param("fine_tune_id") String fineTuneId);

    @RequestLine("GET /v1/fine-tunes/{fine_tune_id}/events")
    OpenAiResponse<FineTuneEvent> listFineTuneEvents(@Param("fine_tune_id") String fineTuneId);

    @RequestLine("DELETE v1/models/{fine_tune_id}")
    DeleteResult deleteFineTune(@Param("fine_tune_id") String fineTuneId);

    @RequestLine("POST /v1/images/generations")
    @Body("{request}")
    ImageResult createImage(@Param("request") CreateImageRequest request);

    @RequestLine("POST /v1/images/edits")
    @Body("{request}")
    ImageResult createImageEdit(@Param("request")RequestBody requestBody);

    @RequestLine("POST /v1/images/variations")
    @Body("{request}")
    ImageResult createImageVariation(@Param("request")RequestBody requestBody);

    @RequestLine("POST /v1/moderations")
    @Body("{request}")
    ModerationResult createModeration(@Param("request") ModerationRequest request);

}
