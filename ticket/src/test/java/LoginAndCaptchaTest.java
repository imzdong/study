import com.alibaba.fastjson.JSONObject;
import org.apache.http.impl.client.CloseableHttpClient;
import org.imzdong.study.ticket.core.Captcha;
import org.imzdong.study.ticket.core.Login;
import org.imzdong.study.ticket.dto.CaptchaResult;
import org.imzdong.study.ticket.dto.LoginResult;
import org.imzdong.study.ticket.util.HttpUtil;

/**
 * @description: 图片下载到本地，登录验证码测试
 * @author: Winter
 * @time: 2020/1/11
 */
public class LoginAndCaptchaTest {

    private static String userName = "xx";
    private static String passWord = "xx";
    private static String imagePath = "xx";//D:\WorkSpace\result\20191221\random.png

    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpUtil.getHttpClient(30000);
        Login login = new Login(httpClient);
        LoginResult loginResult = login.initLogin();
        if(loginResult.isSuccess()){
            Captcha captcha = new Captcha(httpClient);
            String timeValue = System.currentTimeMillis()+"";
            String callback = captcha.getCallback(timeValue);
            CaptchaResult code = captcha.getCode(callback, timeValue);
            if(code.isSuccess()){
                boolean image2Local = captcha.image2Local(code.getValue()
                        ,imagePath);
                if(image2Local) {
                    String input = "";
                    String image2Code = captcha.image2Code(input);
                    CaptchaResult captchaResult = captcha.checkCode(image2Code, callback, timeValue);
                    if (captchaResult.isSuccess()) {
                        LoginResult loginResultW = login.webLogin(userName, passWord, image2Code);
                        System.out.println(JSONObject.toJSONString(loginResultW));
                    }
                }
            }
        }
    }
}
