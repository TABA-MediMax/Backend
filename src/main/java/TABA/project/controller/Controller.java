package TABA.project.controller;

import TABA.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;


@RestController
public class Controller {
    private final OAuthService oAuthService;
    private final TritonService tritonService;
    private final InformationService informationService;
    private final textSearchService textSearchService;
    private final ImageService imageService;

    private final MemberService memberService;

    private final item_seqSearchService itemseqSearchService;
    @Autowired
    public Controller(OAuthService oAuthService, OAuthService oAuthService1, TritonService tritonService, InformationService informationService, textSearchService textSearchService, ImageService imageService, MemberService memberService, item_seqSearchService itemseqSearchService) {
        this.oAuthService = oAuthService1;
        this.tritonService = tritonService;
        this.informationService = informationService;
        this.textSearchService = textSearchService;
        this.imageService = imageService;
        this.memberService = memberService;
        this.itemseqSearchService = itemseqSearchService;
    }

    @PostMapping("/imageUpload")
    public String uploadImage(@RequestParam("Image") MultipartFile imageFile) {

        if (!imageFile.isEmpty()) {
            try {
                String userLocalPath = "C:/python/image/"; //사진 저장 경로
                File userImageFile = new File(userLocalPath+imageFile.getOriginalFilename());
                System.out.println(imageFile.getOriginalFilename());
                if (!userImageFile.exists()) {
                    userImageFile.mkdirs(); // 디렉토리가 없으면 생성
                }
                //사진저장
                imageFile.transferTo(userImageFile);
                System.out.println("Saving image");
                // 파이썬 스크립트 호출

                String pythonScriptPath = "C:/python/model_script.py";
                String imageAbsolutePath = userImageFile.getAbsolutePath();
                System.out.println("Calling Python");

                ProcessBuilder processBuilder = new ProcessBuilder("python", pythonScriptPath, imageAbsolutePath);

                Process process = processBuilder.start(); //스크립트 실행


                 InputStream inputStream = process.getInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                 String line;
                 StringBuilder output = new StringBuilder();
                 while ((line = reader.readLine()) != null) {
                     output.append(line);
                 }

                // 프로세스 종료까지 대기
                int exitCode = process.waitFor();

                if (exitCode == 0) {
                    String response=itemseqSearchService.searchText(output.toString());
                    return response;
                } else {
                    // 파이썬 스크립트 실행 실패
                    return "Error executing Python script.";
                }

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                return "Error uploading image or executing Python script.";
            }
        } else {
            return "No image selected for upload.";
        }
        //이미지 파일 S3에 업로드
        // String url = imageService.uploadFileToS3(imageFile);
        // System.out.println(url);
    }

    @PostMapping("/textSearch")
    public String textSearch(@RequestParam("searchText") String searchText) {
        // 검색어를 textSearchService로 전달하여 검색 요청을 보냅니다.
        String searchResponse = textSearchService.searchText(searchText);

        // 검색 결과를 그대로 반환
        return searchResponse;
    }


    @PostMapping("/kakao")
    public String kakaoCallback(@RequestParam String code) {
        String accessToken = oAuthService.getKakaoAccessToken(code);

        // 사용자 정보를 가져옴
        HashMap<String, Object> userInfo = oAuthService.getUserInfo(accessToken);

        String email = (String) userInfo.get("email");
        String nickName = (String) userInfo.get("nickname");
        System.out.println("닉네임 : " + nickName+" 이메일 : " + email);
        // Member 리포지토리에서 사용자가 존재하는지 확인
        boolean isMemberExists = memberService.isMemberExists(email);

        if (isMemberExists) {
            // 로그인
            return("로그인");
        } else {
            // 회원가입
            memberService.signUp(nickName, email);
            System.out.println("로그인 후 회원가입");
        }

        return("완료");
    }
}

