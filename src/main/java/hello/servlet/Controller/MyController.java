package hello.servlet.Controller;// MyController.java
import hello.servlet.Controller.ImageResponse;
import hello.servlet.Service.TritonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ImageResponse uploadImage(@RequestBody byte[] imageBytes) {
       /* String imagePath = imageService.saveImage(imageBytes);
        Long imageId = imageService.saveImagePathToDatabase(imagePath); */

        ImageResponse Image = tritonService.sendImageToTritonServer(imageBytes);
        /*
     triton server 에 imageBytes 를 보낸 후 Image 라는 이름으로 json 데이터 값을 반환 받음

     String dl_name;
     String dl_company;
     String dl_mapping_code;
      */

        // 이약은요 api 에 rest api 요청

        return Image; // return 값 json 형태 다시 바꿔야됨
    }
}
