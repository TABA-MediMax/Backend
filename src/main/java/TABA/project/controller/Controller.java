package TABA.project.controller;

import TABA.project.domain.Member;
import TABA.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class Controller {
    private final OAuthService oAuthService;
    private final TritonService tritonService;
    private final InformationService informationService;
    private final textSearchService textSearchService;
    private final ImageService imageService;

    private final MemberService memberService;

    @Autowired
    public Controller(OAuthService oAuthService, OAuthService oAuthService1, TritonService tritonService, InformationService informationService, textSearchService textSearchService, ImageService imageService, MemberService memberService) {
        this.oAuthService = oAuthService1;
        this.tritonService = tritonService;
        this.informationService = informationService;
        this.textSearchService = textSearchService;
        this.imageService = imageService;
        this.memberService = memberService;
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
      // String url = imageService.uploadFileToS3(imageFile);
       // System.out.println(url);

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
    @PostMapping("/kakao")
    public void kakaoCallback(@RequestParam String code) {

        String accessToken = oAuthService.getKakaoAccessToken(code);

        // 액세스 토큰을 사용하여 사용자 정보 조회
        try {
            oAuthService.createKakaoUser(accessToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody Member request) {
        if (request.getNickName() == null || request.getEmail() == null) {
            return ResponseEntity.badRequest().body("닉네임과 이메일은 필수 정보입니다.");
        }

        if (memberService.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 등록된 이메일입니다.");
        }

        Member newMember = memberService.signUp(request.getNickName(), request.getEmail());
        return ResponseEntity.ok("회원 등록이 완료되었습니다.");
    }




}