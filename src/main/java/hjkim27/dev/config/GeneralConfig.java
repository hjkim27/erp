package hjkim27.dev.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Properties;

/**
 * <pre>
 *     프로젝트 변수 설정 config
 * </pre>
 *
 * @since 2025.03.31
 */
@Slf4j
@Configuration
public class GeneralConfig {

    // debug 로그 출력여부
    public static Boolean DEBUG_MODE = false;

    static {
        // 파일 로드 후 변수를 설정하도록 추가
        INIT();
    }

    /**
     * <pre>
     *     setting parameter
     * </pre>
     */
    public static void INIT() {
        log.info("INIT start =====");
        Properties prop = new Properties();
        // FileNotFoundException 발생으로 읽는 방식 변경
        try (InputStream fis = GeneralConfig.class.getClassLoader().getResourceAsStream("generalConfig.properties")) {
            if (fis != null) {
                prop.load(fis);
                prop.keySet().forEach(key -> {
                    String keyStr = key.toString();
                    switch (keyStr) {
                        case "LOG_LEVEL":
                            DEBUG_MODE = prop.getProperty((String) key).equals("DEBUG");
                            break;
                        case "test":
                            break;
                    }
                });
            } else {
                throw new FileNotFoundException("property file not found in classpath");
            }

        } catch (FileNotFoundException e) {
            log.warn("{} || ", e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PRINT_PARAMETER(GeneralConfig.class.getDeclaredFields());   // 클래스 내 선언된 모든 필드 가져오기
        log.info("INIT end =====");
    }

    /**
     * <pre>
     *     print static parameter
     * </pre>
     *
     * @param fields {class}.class.getDeclaredFields()
     */
    public static void PRINT_PARAMETER(Field[] fields) {

        for (Field field : fields) {
            // static 필드만 출력
            if (Modifier.isStatic(field.getModifiers())) {
                try {
                    String key = field.getName();
                    Object value = field.get(null); // static 필드는 인스턴스 없이 접근 가능
                    log.debug("\t{} = {}", key, value);
                } catch (IllegalAccessException e) {
                    log.error("{} || ", e.getMessage(), e);
                }
            }
        }
    }
}
