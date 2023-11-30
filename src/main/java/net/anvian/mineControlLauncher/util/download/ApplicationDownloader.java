package net.anvian.mineControlLauncher.util.download;

import net.anvian.mineControlLauncher.Main;
import net.anvian.mineControlLauncher.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class ApplicationDownloader implements Downloader{
    @Override
    public void download() throws IOException {
        String user = "andre-carbajal";
        //TODO create and replace the repository name
        String repository_name = "McPackGenerator";

        String urlAPI = String.format("https://api.github.com/repos/%s/%s/releases/latest", user, repository_name);
        URL url = new URL(urlAPI);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.connect();

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = connection.getInputStream();
            String respuesta = convertirInputStreamAString(inputStream);

            String enlaceDescarga = getDownloadUrl(respuesta);

            Path dirPath = Paths.get(Main.USER_HOME, Main.MAIN_FOLDER);
            Path filePath = dirPath.resolve("MineControlFx_"+ getTagName(respuesta) +".jar");

            downloadFile(enlaceDescarga, filePath, "Application");
            Log.println("¡Descarga exitosa!");
        } else {
            Log.error("La solicitud no fue exitosa. Código de respuesta: " + responseCode);
        }

        connection.disconnect();
    }
    private static String convertirInputStreamAString(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }

    private static String getDownloadUrl(String respuesta) {
        String downloadLink = "";
        try {
            JSONObject jsonObject = new JSONObject(respuesta);

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

    private static String getTagName(String respuesta) {
        String tagName = "";
        try {
            JSONObject jsonObject = new JSONObject(respuesta);
            tagName = jsonObject.getString("tag_name");
        }catch (JSONException e){
            Log.error("Error getting tag name", e);
        }
        return tagName;
    }
}
