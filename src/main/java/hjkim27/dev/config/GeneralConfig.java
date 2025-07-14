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
     *     - generalConfig.properties 파일의 키와 일치하는 변수에 값 세팅
     * </pre>
     */
    public static void INIT() {
        log.info("INIT start =====");
        Properties prop = new Properties();
        // FileNotFoundException 발생으로 읽는 방식 변경
        try (InputStream fis = GeneralConfig.class.getClassLoader().getResourceAsStream("generalConfig.properties")) {
            if (fis != null) {
                prop.load(fis);
                for (String key : prop.stringPropertyNames()) {
                    String value = prop.getProperty(key);
                    try {
                        Field field = GeneralConfig.class.getDeclaredField(key);
                        if (Modifier.isStatic(field.getModifiers())) {
                            field.setAccessible(true);
                            Object convertedValue = convertValue(field.getType(), value);
                            if (convertedValue != null) {
                                field.set(null, convertedValue); // static 필드에 값 설정
                                log.debug("Set {} = {}", key, convertedValue);
                            }
                        }
                    } catch (NoSuchFieldException e) {
                        log.warn("Field '{}' not found in GeneralConfig class", key);
                    } catch (IllegalAccessException e) {
                        log.warn("Failed to access field {}: {}", key, e.getMessage());
                    }
                }
            } else {
                log.warn("property file not found in classpath");
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
     *     원시유형(int,long,boolean, double, float) 과 래퍼클래스 확인 후 해당 객체로 변환
     *     변환 실패 시 null 반환
     * </pre>
     *
     * @param type  변환 대상 클래스 타입
     * @param value 지정된 유형으로 변환할 값
     * @return 변환 성공 시 지정된 유형의 객체, 실패 시 null
     */
    private static Object convertValue(Class<?> type, String value) {
        try {
            if (type == String.class) {
                return value;
            }
            if (type == int.class || type == Integer.class) {
                return Integer.parseInt(value);
            }
            if (type == long.class || type == Long.class) {
                return Long.parseLong(value);
            }
            if (type == boolean.class || type == Boolean.class) {
                return Boolean.parseBoolean(value);
            }
            if (type == double.class || type == Double.class) {
                return Double.parseDouble(value);
            }
            if (type == float.class || type == Float.class) {
                return Float.parseFloat(value);
            }
        } catch (Exception e) {
            log.warn("Value '{}' could not be converted to {}", value, type.getSimpleName());
        }
        return null;
    }

    /**
     * <pre>
     *     print static parameter
     *     - 지정된 타입에 해당하는 정적 필드만 출력하도록 조건 추가
     * </pre>
     *
     * @param fields {class}.class.getDeclaredFields()
     */
    public static void PRINT_PARAMETER(Field[] fields) {

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
    private static boolean isSupportedConfigType(Class<?> type) {
        return type.isPrimitive()
                || type == String.class
                || type == int.class || type == Integer.class
                || type == long.class || type == Long.class
                || type == boolean.class || type == Boolean.class
                || type == double.class || type == Double.class
                || type == float.class || type == Float.class;
    }
}
