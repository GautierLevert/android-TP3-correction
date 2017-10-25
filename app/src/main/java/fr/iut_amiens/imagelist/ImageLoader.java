package fr.iut_amiens.imagelist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

public class ImageLoader {

    private File imageCacheDirectory;

    public ImageLoader(File imageCacheDirectory) {
        this.imageCacheDirectory = imageCacheDirectory;
    }

    private File getLocalFile(AnimalImage image) {
        return new File(imageCacheDirectory, image.getFileName());
    }

    public Bitmap load(AnimalImage image) throws IOException, InterruptedException {
        File localFile = getLocalFile(image);
        if (localFile.exists()) {
            return getLocalData(image);
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

        return getLocalData(image);
    }

    public Bitmap getLocalData(AnimalImage image) throws IOException {
        File localFile = getLocalFile(image);
        return BitmapFactory.decodeFile(localFile.getCanonicalPath());
    }

    public boolean isImageDownloaded(AnimalImage image) {
        File localFile = getLocalFile(image);
        return localFile.exists();
    }

    public void clearCache() {
        for (File f : imageCacheDirectory.listFiles()) {
            f.delete();
        }
    }
}
