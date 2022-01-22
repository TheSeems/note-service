package me.theseems.noteservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.BooleanSchema;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

import java.util.Objects;
import java.util.stream.Stream;

@Configuration
public class DocsConfig implements OperationCustomizer {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Note Service")
                .description("CRUD service to manage Notes")
                .version("v0.0.1")
                .license(new License()
                    .name("MIT")
                    .url("https://opensource.org/licenses/mit-license")));
    }

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        Stream<Content> failContent = operation.getResponses().keySet()
            .stream()
            .filter(code -> !Objects.equals(code, "200"))
            .map(code -> operation.getResponses().get(code).getContent());

        failContent.forEach(failure -> failure.keySet()
            .forEach(mediaTypeKey -> {
                MediaType mediaType = failure.get(mediaTypeKey);
                mediaType.schema(makeFailSchema(mediaType.getSchema()));
            }));

        Content success = operation.getResponses().get("200").getContent();
        if (success == null) {
            return operation;
        }

        // Wrap success schema
        success.keySet().forEach(mediaTypeKey -> {
            MediaType mediaType = success.get(mediaTypeKey);
            mediaType.schema(makeSuccessSchema(mediaType.getSchema()));
        });

        return operation;
    }

    /**
     * Wrap schema to feign success ApiResponse
     *
     * @param objSchema to wrap
     * @return wrapped schema
     */
    private Schema<?> makeSuccessSchema(final Schema<?> objSchema) {
        Schema<?> wrapperSchema = new Schema<>();
        wrapperSchema.addProperties("success", new BooleanSchema()._default(true));
        wrapperSchema.addProperties("message", new StringSchema()._default(null));
        wrapperSchema.addProperties("body", objSchema);

        return wrapperSchema;
    }

    /**
     * Wrap schema to feign fail ApiResponse
     *
     * @param objSchema to wrap
     * @return fail schema
     */
    private Schema<?> makeFailSchema(final Schema<?> objSchema) {
        Schema<?> wrapperSchema = new Schema<>();
        wrapperSchema.addProperties("success", new BooleanSchema()._default(false));
        wrapperSchema.addProperties("message", new StringSchema()._default(objSchema.getDescription()));
        wrapperSchema.addProperties("body", null);

        return wrapperSchema;
    }
}
