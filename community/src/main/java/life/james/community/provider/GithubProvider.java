package life.james.community.provider;


import com.alibaba.fastjson.JSON;
import life.james.community.dto.AccessTokenDTO;
import life.james.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) throws IOException {
        MediaType mediaType
                = okhttp3.MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            String token = str.split("&")[0].split("=")[1];
            return token;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken) throws IOException {
        OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://api.github.com/user?access_token="+accessToken)
                    .build();

            try{
                Response response = client.newCall(request).execute();
                String string  = response.body().string();
                GithubUser githubUser = JSON.parseObject(string,GithubUser.class);
                return  githubUser;
        }catch (IOException e){
            }
        return  null;
    }

}
