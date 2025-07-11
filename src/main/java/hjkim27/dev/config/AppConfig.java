package hjkim27.dev.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.stereotype.Component;

/**
 * <pre>
 *     별도 생성된 bean 관리용 class
 * </pre>
 *
 * @since 2025.06
 */
@Component
public class AppConfig {

    public static ObjectMapper objectMapper = new ObjectMapper();

    static {
        // json snake_case 형식을 java camelCase 형식으로 변환
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        // null 값을 제외하고 직렬화
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 알 수 없는 속성 무시 (null 필드 제외)
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
