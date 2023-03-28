package org.imzdong.model.geektime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author DongZhou
 * @since 2023/3/27 18:45
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginResponse {

    private Long uid;
    private String nickname;
    private String ticket;
    private Long ttl;
}

