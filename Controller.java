package hello.servlet.Controller;

import hello.servlet.Service.TritonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    private final ImageService imageService;
    private final TritonService tritonService;

    @Autowired
    public YourController(ImageService imageService, TritonService tritonService) {
        this.imageService = imageService;
        this.tritonService = tritonService;
    }

    @PostMapping("/사진 업로드")
    public void uploadImage(@RequestBody byte[] imageBytes) {
        String imagePath = imageService.saveImage(imageBytes);
        Long imageId = imageService.saveImagePathToDatabase(imagePath);
        tritonService.sendImageToTritonServer(imageBytes); // tritonservice 에 이미지 올리기.
    }
}
