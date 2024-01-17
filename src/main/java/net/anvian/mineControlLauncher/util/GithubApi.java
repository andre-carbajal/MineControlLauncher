package net.anvian.mineControlLauncher.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

public class GithubApi {
    private static final OkHttpClient client = new OkHttpClient();
    private final String repository_name;

    public GithubApi(String repository_name) {
        this.repository_name = repository_name;
    }

    public String getDownloadUrl() throws JSONException, IOException {
        return getAssetProperty("browser_download_url");
    }

    public String getFileName() throws JSONException, IOException {
        return getAssetProperty("name");
    }

    public String getTag() {
        String tag = "";
        try {
            JSONObject jsonObject = new JSONObject(answer());
            tag = jsonObject.getString("tag_name");
        }catch (JSONException | IOException e){
            Log.error("Error getting tag name", e);
        }
        return tag;
    }

    private String answer() throws IOException {
        String user = "andre-carbajal";
        String urlAPI = String.format("https://api.github.com/repos/%s/%s/releases/latest", user, repository_name);
        Request request = new Request.Builder().url(urlAPI).build();

        try(Response response = client.newCall(request).execute()){
            if (!response.isSuccessful()){
                Log.error("The request was not successful. Response code: " + response.code());
                return urlAPI;
            }
            return Objects.requireNonNull(response.body()).string();
        }
    }

    private String getAssetProperty(String propertyName) throws JSONException, IOException {
        JSONObject jsonObject = new JSONObject(answer());
        JSONArray assets = jsonObject.getJSONArray("assets");
        if (!assets.isEmpty()) {
            JSONObject primerAsset = assets.getJSONObject(0);
            return primerAsset.getString(propertyName);
        }
        return "";
    }
}