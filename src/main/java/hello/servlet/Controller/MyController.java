package hello.servlet.Controller;// MyController.java
import hello.servlet.Controller.ImageResponse;
import hello.servlet.Service.TritonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
@RestController
public class MyController {

    //private final ImageService imageService;
    private final TritonService tritonService;

    @Autowired
    public MyController(TritonService tritonService) {
        // this.imageService = imageService;
        this.tritonService = tritonService;
    }

    @PostMapping("/사진 업로드")
    public ImageResponse uploadImage(@RequestParam("imageFile") MultipartFile imageFile) {
        /* String imagePath = imageService.saveImage(imageBytes);

        Long imageId = imageService.saveImagePathToDatabase(imagePath); */

        ImageResponse imageResponse = tritonService.sendImageToTritonServer(imageFile);

        return imageResponse;

    }


}




