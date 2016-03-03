package fr.iut_amiens.imagelist;

import java.io.Serializable;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

public class AnimalImage implements Serializable {

    public static final List<AnimalImage> ANIMAL_IMAGE_LIST = Arrays.asList(
            new AnimalImage("Baby Giraffe Face", URI.create("http://www.publicdomainpictures.net/pictures/110000/velka/baby-giraffe-face.jpg")),
            new AnimalImage("Sleeping Polar Bear", URI.create("http://www.publicdomainpictures.net/pictures/110000/velka/sleeping-polar-bear.jpg")),
            new AnimalImage("Koala Bear In Tree", URI.create("http://www.publicdomainpictures.net/pictures/110000/velka/koala-bear-in-tree.jpg")),
            new AnimalImage("Rhinoceros", URI.create("http://www.publicdomainpictures.net/pictures/110000/velka/rhinoceros-14202086623vq.jpg")),
            new AnimalImage("Alligator On Dock", URI.create("http://www.publicdomainpictures.net/pictures/110000/velka/alligator-on-dock.jpg")),
            new AnimalImage("Baby Lamb", URI.create("http://www.publicdomainpictures.net/pictures/20000/velka/baby-lamb.jpg")),
            new AnimalImage("Meerkat", URI.create("http://www.publicdomainpictures.net/pictures/10000/velka/2364-126824238033E9.jpg")),
            new AnimalImage("Cat With Green Eyes", URI.create("http://www.publicdomainpictures.net/pictures/20000/velka/cat-with-green-eyes-871298226869aN0.jpg")),
            new AnimalImage("Black Cow", URI.create("http://www.publicdomainpictures.net/pictures/10000/velka/1-1192354093.jpg")),
            new AnimalImage("Mia", URI.create("http://www.publicdomainpictures.net/pictures/30000/velka/mia-1330985708p4N.jpg"))
    );

    private String title;

    private String fileName;

    private URI imageUrl;

    public AnimalImage(String title, URI imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
        String path = imageUrl.getPath();
        fileName = path.substring(path.lastIndexOf('/') + 1, path.length());
    }

    public String getTitle() {
        return title;
    }

    public URI getImageUrl() {
        return imageUrl;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnimalImage)) return false;

        AnimalImage that = (AnimalImage) o;

        return !(imageUrl != null ? !imageUrl.equals(that.imageUrl) : that.imageUrl != null);

    }

    @Override
    public int hashCode() {
        return imageUrl != null ? imageUrl.hashCode() : 0;
    }
}
