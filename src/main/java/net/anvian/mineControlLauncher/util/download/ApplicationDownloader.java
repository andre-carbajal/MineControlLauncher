package net.anvian.mineControlLauncher.util.download;

import net.anvian.mineControlLauncher.Main;
import net.anvian.mineControlLauncher.util.Log;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class ApplicationDownloader implements Downloader{
    private static final OkHttpClient client = new OkHttpClient();

    @Override
    public void download() throws IOException {
        String user = "andre-carbajal";
        //TODO create and replace the repository name
        String repository_name = "McPackGenerator";

        String urlAPI = String.format("https://api.github.com/repos/%s/%s/releases/latest", user, repository_name);
        Request request = new Request.Builder().url(urlAPI).build();

        try(Response response = client.newCall(request).execute()){
            if (!response.isSuccessful()){
                Log.error("The request was not successful. Response code: " + response.code());
                return;
            }

            String answer = Objects.requireNonNull(response.body()).string();
            String downloadLink = getDownloadUrl(answer);

            Path dirPath = Paths.get(Main.USER_HOME, Main.MAIN_FOLDER);
            Path filePath = dirPath.resolve(getFileName(answer));

            downloadFile(downloadLink, filePath, "Application");
            Log.println("Application downloaded successfully");
        }
    }

    private static String getDownloadUrl(String answer) {
        String downloadLink = "";
        try {
            JSONObject jsonObject = new JSONObject(answer);
            JSONArray assets = jsonObject.getJSONArray("assets");
            if (!assets.isEmpty()) {
                JSONObject primerAsset = assets.getJSONObject(0);
                downloadLink = primerAsset.getString("browser_download_url");
            }

        }catch (JSONException e){
            Log.error("Error getting download link", e);
        }
        return downloadLink;
    }

    public String getFileName(String answer) {
        String fileName = "";
        try {
            JSONObject jsonObject = new JSONObject(answer);
            JSONArray assets = jsonObject.getJSONArray("assets");
            if (!assets.isEmpty()) {
                JSONObject primerAsset = assets.getJSONObject(0);
                fileName = primerAsset.getString("name");
            }
        }catch (JSONException e){
            Log.error("Error getting file name", e);
        }
        return fileName;
    }
}
