package hjkim27.dev.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/**
 * <pre>
 *     프로젝트 변수 설정 config
 *     - 단순 변수만 갖고있도록 함. 실제 변수 설정은 {@link GeneralConfigInit}
 * </pre>
 *
 * @since 2025.03.31
 */
@Slf4j
@Configuration
public class GeneralConfig {

    // debug 로그 출력여부
    public static Boolean DEBUG_MODE = false;

}
