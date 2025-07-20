package hjkim27.dev.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * <pre>
 *     GeneralConfigInit
 *     - application.properties에 정의된 general.* 프로퍼티를 읽어와 GeneralConfig 클래스의 정적 필드에 설정
 *     - 초기화 시점에 프로퍼티 값을 로깅
 * </pre>
 */
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "general")
@PropertySource("classpath:generalConfig.properties")
@Getter
@Setter
public class GeneralConfigInit {

    private Boolean debugMode;

    private final Boolean DEFAULT_BOOLEAN = false;

    @PostConstruct
    public void INIT() {
        GeneralConfig.DEBUG_MODE = (debugMode != null) ? debugMode : DEFAULT_BOOLEAN;
        log.info("INIT start =====");
        PRINT_PARAMETER(GeneralConfig.class.getDeclaredFields());   // 클래스 내 선언된 모든 필드 가져오기
        log.info("INIT END =======");
    }


    /**
     * <pre>
     *     print static parameter
     *     - 지정된 타입에 해당하는 정적 필드만 출력하도록 조건 추가
     * </pre>
     *
     * @param fields {class}.class.getDeclaredFields()
     */
    private void PRINT_PARAMETER(Field[] fields) {

        for (Field field : fields) {
            // static 필드만 출력
            if (Modifier.isStatic(field.getModifiers()) && isSupportedConfigType(field.getType())) {
                try {
                    String key = field.getName();
                    Object value = field.get(null); // static 필드는 인스턴스 없이 접근 가능
                    log.info("\t{} = {}", key, value);
                } catch (IllegalAccessException e) {
                    log.error("{} || ", e.getMessage(), e);
                }
            }
        }
    }

    /**
     * <pre>
     *     지원되는 유형의 원시 유형, 래퍼클래스, 문자열 클래스인지 확인
     * </pre>
     *
     * @param type 변환 대상 클래스 타입
     * @return 지원 유형일 경우 true, 아닐 경우 false 반환
     */
    private boolean isSupportedConfigType(Class<?> type) {
        return type.isPrimitive()
                || type == String.class
                || type == int.class || type == Integer.class
                || type == long.class || type == Long.class
                || type == boolean.class || type == Boolean.class
                || type == double.class || type == Double.class
                || type == float.class || type == Float.class;
    }
}
