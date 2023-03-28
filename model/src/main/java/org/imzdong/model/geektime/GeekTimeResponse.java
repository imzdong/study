package org.imzdong.model.geektime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author DongZhou
 * @since 2023/3/27 18:50
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GeekTimeResponse<T> {

        private int code;
        private String error;
        private String extra;
        private T data;
}
/**
 *
 * {"code":0,"data":{"uid":1183644,"ucode":"122FBA4296836C","uid_str":"1183644",
 * "type":1,"cellphone":"185****9025","country":"86","nickname":"Winter","avatar":"https://static001.geekbang.org/account/avatar/00/12/0f/9c/4e7a76a4.jpg","gender":"","birthday":"","graduation":"",
 * "profession":"","industry":"","description":"","overdue":0,"cert":1,"province":"","city":"","mail":"","wechat":"",
 * "github_name":"","github_email":"","company":"","post":"","expirence_years":"","my_position":"","work_year":"","direction_interest":"","learn_goal":"","department_name":"",
 * "team_size":"","internal_training":"","school":"","real_name":"","openid":"","euid":"0","subtype":1,"role":-1,"name":"","address":"","mobile":"","contact":"","position":"",
 * "passworded":true,"create_time":1532003584,"join_infoq":"","actives":{"athena":false},"is_student":0,"student_expire_time":0,"device_id":"500d10e-74fee9d-886b4e5-21196a7",
 * "ticket":"BgQEAI0nAAUEAAAAAAwBAQkBAQcEytyIXQ0BAQIEkzAhZAMEkzAhZAoEAAAAAAEInA8SAAAAAAAGBITsLxwIAQMLAgYA","ttl":2592000,"oss_token":"c7cb10b2824ec9a3","is_white":2},"
 * error":{},"extra":{"cost":0.16766715049743652,"request-id":"7178ba79f5fa767c4a1a09a986aa0464@3@account"}}
 *
 */