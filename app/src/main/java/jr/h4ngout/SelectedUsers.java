package jr.h4ngout;

/**
 * Created by jrfif on 19/02/2018.
 */

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.util.ArrayList;

public class SelectedUsers {
    public static String insertUpdateCall(String categoriesCsv, String function, String idToken, String groupName) {

        String response = "";

        if(function.equals("NewGroup")) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://well-prepared-socie.000webhostapp.com/insertGroup.php");
            try {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("groupName", groupName));
                nameValuePairs.add(new BasicNameValuePair("favouriteCategories", categoriesCsv));
                nameValuePairs.add(new BasicNameValuePair("idToken", idToken));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                response = EntityUtils.toString(httpResponse.getEntity());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(function.equals("Availability")){

        }

        return response;

    }
}
