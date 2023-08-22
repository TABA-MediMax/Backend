package TABA.project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import TABA.project.controller.ImageResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TritonService {

    private final String TRITON_URL = "http://triton-server-url/api"; //주소 설정?

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public ImageResponse sendImageToTritonServer(MultipartFile imageFile) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);


            byte[] imageBytes = imageFile.getBytes();

            // MultipartFile을 바로 HttpEntity로 사용 가능
            HttpEntity<byte[]> requestEntity = new HttpEntity<>(imageBytes, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(TRITON_URL, requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                String responseBody = response.getBody();
                JsonNode jsonResponse = objectMapper.readTree(responseBody);

                ImageResponse imageResponse = new ImageResponse();
                imageResponse.setDl_company(jsonResponse.get("images").get(0).get("dl_company").asText());
                imageResponse.setDl_name(jsonResponse.get("images").get(0).get("dl_name").asText());
                imageResponse.setItem_seq(jsonResponse.get("images").get(0).get("dl_mapping_code").asText());

                return imageResponse;
            } else {
                System.out.println("API request failed : " + response.getStatusCodeValue());
                return null;
            }
        } catch (RestClientException e) {
            // 네트워크 통신 오류 처리
            System.out.println("Network communication error: " + e.getMessage());
            return null;
        } catch (JsonProcessingException e) {
            // JSON 파싱 오류 처리
            System.out.println("JSON processing error: " + e.getMessage());
            return null;
        } catch (Exception e) {
            // 기타 예외 처리
            System.out.println("Unexpected error: " + e.getMessage());
            return null;
        }

    }
}
//@Service
//public class TritonService {
//
//    private final String TRITON_URL = "http://triton-server-url/api"; // Triton 서버의 URL로 변경
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    public ImageResponse sendImageToTritonServer(String base64Image) {
//        try {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//
//            // JSON 요청 바디 생성
//            Map<String, String> requestBody = new HashMap<>();
//            requestBody.put("base64Image", base64Image);
//
//            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
//
//            ResponseEntity<String> response = restTemplate.postForEntity(TRITON_URL, requestEntity, String.class);
//
//            if (response.getStatusCode().is2xxSuccessful()) {
//                String responseBody = response.getBody();
//                JsonNode jsonResponse = objectMapper.readTree(responseBody);
//
//                ImageResponse imageResponse = new ImageResponse();
//                imageResponse.setDl_company(jsonResponse.get("dl_company").asText());
//                imageResponse.setDl_name(jsonResponse.get("dl_name").asText());
//                imageResponse.setItem_seq(jsonResponse.get("dl_mapping_code").asText());
//
//                return imageResponse;
//            } else {
//                System.out.println("API request failed : " + response.getStatusCodeValue());
//                return null;
//            }
//        } catch (RestClientException e) {
//            // 네트워크 통신 오류 처리
//            System.out.println("Network communication error: " + e.getMessage());
//            return null;
//        } catch (JsonProcessingException e) {
//            // JSON 파싱 오류 처리
//            System.out.println("JSON processing error: " + e.getMessage());
//            return null;
//        } catch (Exception e) {
//            // 기타 예외 처리
//            System.out.println("Unexpected error: " + e.getMessage());
//            return null;
//        }
//    }
//}
// ... (이전 코드 생략)
//
//@Service
//public class TritonService {
//
//    private final String TRITON_URL = "http://triton-server-url/api"; // Triton 서버의 URL로 변경
//    private final String API_KEY = "your-api-key"; // 실제 API 키로 변경해야 합니다
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    public ImageResponse sendImageToTritonServer(String base64Image) {
//        try {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            headers.set("Authorization", "Bearer " + API_KEY); // API 키를 헤더에 추가
//
//            // JSON 요청 바디 생성
//            Map<String, String> requestBody = new HashMap<>();
//            requestBody.put("base64Image", base64Image);
//
//            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
//
//            ResponseEntity<String> response = restTemplate.postForEntity(TRITON_URL, requestEntity, String.class);
//
//            // ... (이후 코드는 이전과 동일)
//
//        } catch (RestClientException e) {
//            // 네트워크 통신 오류 처리
//            System.out.println("Network communication error: " + e.getMessage());
//            return null;
//        } catch (JsonProcessingException e) {
//            // JSON 파싱 오류 처리
//            System.out.println("JSON processing error: " + e.getMessage());
//            return null;
//        } catch (Exception e) {
//            // 기타 예외 처리
//            System.out.println("Unexpected error: " + e.getMessage());
//            return null;
//        }
//    }
//}
