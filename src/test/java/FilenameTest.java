import org.junit.jupiter.api.Test;

public class FilenameTest {

    @Test
    public void testFilename(){
        String filename = "demo.jpg";
        String suffix = filename.substring(filename.lastIndexOf("."));
        System.out.println(suffix);
        // .jpg
    }
}
