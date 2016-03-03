package fr.iut_amiens.imagelist;

import android.net.Uri;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

public class ImageCache {

    private File imageCacheDirectory;

    public ImageCache(File imageCacheDirectory) {
        this.imageCacheDirectory = imageCacheDirectory;
    }

    public void download(AnimalImage image) throws IOException, InterruptedException {
        File localFile = new File(imageCacheDirectory, image.getFileName());
        if (localFile.exists()) {
            return;
        }
        HttpURLConnection connection = (HttpURLConnection) image.getImageUrl().toURL().openConnection();

        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new IOException("Server didn't reply correctly \n" + connection.getResponseMessage());
        }

        BufferedInputStream input = null;
        BufferedOutputStream output = null;
        try {
            input = new BufferedInputStream(connection.getInputStream());
            output = new BufferedOutputStream(new FileOutputStream(localFile));
            int data;
            while ((data = input.read()) != -1) {
                if (Thread.interrupted()) {
                    throw new InterruptedException();
                }
                output.write(data);
            }
            output.flush();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ignored) {
                }
            }
            if (output != null) {
                try {
                    output.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    public Uri getLocalData(AnimalImage image) {
        File localFile = new File(imageCacheDirectory, image.getFileName());
        return Uri.fromFile(localFile);
    }

    public boolean isImageDownloaded(AnimalImage image) {
        File localFile = new File(imageCacheDirectory, image.getFileName());
        return localFile.exists();
    }

    public void deleteCache() {
        for (File f : imageCacheDirectory.listFiles()) {
            f.delete();
        }
    }
}
