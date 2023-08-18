package TABA.project.controller;

import TABA.project.service.ImageService;
import TABA.project.service.TritonService;
import TABA.project.service.InformationService;
import TABA.project.service.textSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class Controller {

    private final TritonService tritonService;
    private final InformationService informationService;
    private final textSearchService textSearchService;
    private final ImageService imageService;

    @Autowired
    public Controller(TritonService tritonService, InformationService informationService, textSearchService textSearchService, ImageService imageService) {
        this.tritonService = tritonService;
        this.informationService = informationService;
        this.textSearchService = textSearchService; // 주입해주세요
        this.imageService = imageService;
    }

    @PostMapping("/imageUpload")
    public String uploadImage(@RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        /*
        public String uploadImage(@RequestParam("base64Image") String base64Image){

         imageResponse=imageResponse.sendImageToTritonServer(base64Image)

         imageResponse.setType("json");

          }

         */

        //이미지 파일 S3에 업로드
        String url = imageService.uploadFileToS3(imageFile);
        System.out.println(url);

        ImageResponse imageResponse = new ImageResponse();
        imageResponse.setDl_company("경동제약(주)");
        imageResponse.setItem_seq("200902301");
        imageResponse.setDl_name("그날엔정");
        imageResponse.setType("json");
        // Triton 부분이 아직 미완성이라서 일단 임시로 이렇게 해놓음


        String jsonResponse = informationService.sendInformation(imageResponse);

        // JSON 응답을 그대로 반환
        return jsonResponse;
    }

    @PostMapping("/textSearch")
    public String textSearch(@RequestParam("searchText") String searchText) {
        // 검색어를 textSearchService로 전달하여 검색 요청을 보냅니다.
        String searchResponse = textSearchService.searchText(searchText);

        // 검색 결과를 그대로 반환
        return searchResponse;
    }
}