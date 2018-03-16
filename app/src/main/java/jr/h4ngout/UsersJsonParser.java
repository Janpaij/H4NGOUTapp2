package jr.h4ngout;

/**
 * Created by jrfif on 19/02/2018.
 */

//import com.codingtrickshub.checkboxlistview.model.Category;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class UsersJsonParser {
    public static ArrayList<String> selectedCategories = new ArrayList<>();

    public ArrayList<User> getParsedCategories() {



        String JsonFavouriteCategories = "";
        ArrayList<User> MyArraylist = new ArrayList<>();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("http://well-prepared-socie.000webhostapp.com/getUsers.php");
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            JsonFavouriteCategories = EntityUtils.toString(httpResponse.getEntity());

            JSONArray jsonArray = new JSONArray(JsonFavouriteCategories);

            for (int i = 0; i < jsonArray.length(); i++) {
                User genres = new User();
                JSONObject MyJsonObject = jsonArray.getJSONObject(i);
                //genres.setCateogry_id(Integer.parseInt(MyJsonObject.getString("id")));
                genres.setCateogry_id(MyJsonObject.getString("id"));//new
                genres.setCategory_Name(MyJsonObject.getString("categoryName"));
                genres.setSelected(Boolean.parseBoolean(MyJsonObject.getString("selected")));
                MyArraylist.add(genres);
                if (MyJsonObject.getString("selected").equals("true")) {
                    selectedCategories.add(MyJsonObject.getString("id"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return MyArraylist;


    }

}
