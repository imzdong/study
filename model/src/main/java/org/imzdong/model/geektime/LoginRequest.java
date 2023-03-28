package org.imzdong.model.geektime;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author DongZhou
 * @since 2023/3/27 17:28
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginRequest {

    private String country;
    private String cellphone;
    private String password;
    @JsonProperty("learn_status")
    private String learnStatus;
    private String captcha;
    private String remember;
    private String platform;
    private String appid;

}