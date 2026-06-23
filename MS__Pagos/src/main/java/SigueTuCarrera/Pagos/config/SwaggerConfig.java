package SigueTuCarrera.Pagos.config;


import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.converter.ModelConverterContext;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.media.Schema;
import jakarta.persistence.GeneratedValue;
import java.lang.reflect.Field;
import java.util.Iterator;



@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI pagosOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Pagos (MS_Pagos)")
                        .description("Microservicio para la gestión de pagos del sistema académico")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Duoc UC")
                                .email("contacto@duocuc.cl")));
    }

    @Bean
    public ModelConverter generatedValueReadOnlyConverter() {
        return new ModelConverter() {
            @Override
            public Schema resolve(AnnotatedType type, ModelConverterContext context, Iterator<ModelConverter> chain) {
                
                Schema<?> schema = (chain.hasNext()) ? chain.next().resolve(type, context, chain) : null;
                
                if (schema != null && schema.getProperties() != null && type.getType() instanceof Class<?>) {
                    Class<?> clazz = (Class<?>) type.getType();
                    

                    for (Field field : clazz.getDeclaredFields()) {
                        if (field.isAnnotationPresent(GeneratedValue.class)) {
                            Schema<?> propertySchema = (Schema<?>) schema.getProperties().get(field.getName());
                            if (propertySchema != null) {
                                // Forzamos el atributo a ser ReadOnly en la documentación
                                propertySchema.setReadOnly(true);
                            }
                        }
                    }
                }
                return schema;
            }
        };
    }
}
