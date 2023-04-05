package org.imzdong.openai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.imzdong.model.openai.completion.CompletionRequest;
import org.imzdong.model.openai.completion.CompletionResult;
import org.imzdong.model.openai.edit.EditChoice;
import org.imzdong.model.openai.edit.EditRequest;
import org.imzdong.model.openai.edit.EditResult;
import org.imzdong.openai.api.OpenAiApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * @author DongZhou
 * @since 2023/3/31 16:12
 */
public class CodeTest {

    private static final String token = OpenAiConstant.TOKEN;
    private ObjectMapper objectMapper;
    private OpenAiApi openAiApi;

    @BeforeEach
    public void init(){
        objectMapper = new ObjectMapper();
        openAiApi = FeignClientBuilder.build(OpenAiConstant.BASE_URL, OpenAiApi.class, token, true);
    }

    @Test
    public void testCodeEdit(){
        //get resource file path
        String codeText = readText();
        System.out.println(codeText);
        //text-davinci-003 gpt-3.5-turbo-0301
        EditRequest build = EditRequest.builder().model("code-davinci-edit-001")
                .input(codeText)
                .temperature(1.0)
                .instruction("优化代码")
                .build();
        EditResult edit = openAiApi.createEdit(build);
        List<EditChoice> choices = edit.getChoices();

    }

    @Test
    public void writeText(){
        try {
            String fileName = "code-01-result.txt";
            String tt = "haha点点的kk丫丫";
            URL resource = this.getClass().getClassLoader().getResource(fileName);
            //app.getResource -> Platform.getResource -> BootLoader.getResource -> platform.findResource -> app.findResource
            //-> findResourceOnClassPath -> classPath
            //jdk.internal.loader.ClassLoaders$AppClassLoader
            //jdk.internal.loader.ClassLoaders$PlatformClassLoader
            //jdk.internal.loader.BootLoader.findResource(java.lang.String)

            // jdk.internal.loader.BootLoader.findResource(java.lang.String)
            // jdk.internal.loader.BuiltinClassLoader.findResourceOnClassPath
            // java.lang.ClassLoader.findResource(java.lang.String)
            // jdk.internal.loader.BuiltinClassLoader.findResourceOnClassPath
            if(resource == null){
                Path path = Paths.get(this.getClass().getClassLoader().getResource("").toURI());
                Files.createFile(path.resolve(fileName));
                resource = this.getClass().getClassLoader().getResource(fileName);
            }
            Path path = Paths.get(resource.toURI());
            List<String> lines = List.of(tt);
            Files.write(path, lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private String readText(){
        try {
            URL resource = this.getClass().getClassLoader().getResource("code-01.txt");
            Path realPath = Paths.get(resource.toURI());
            return new String(Files.readAllBytes(realPath));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
