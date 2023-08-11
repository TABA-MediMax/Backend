package hello.servlet.Service;

import hello.servlet.Controller.ImageResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TritonService {

    private final String TRITON_URL = "http://triton-server-url/api";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public ImageResponse sendImageToTritonServer(MultipartFile imageFile) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            // 이미지 파일을 바이트 배열로 변환
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
                imageResponse.setDl_mapping_code(jsonResponse.get("images").get(0).get("dl_mapping_code").asText());

                return imageResponse;
            } else {
                // 에러 처리
            }
        } catch (Exception e) {
            // 예외 처리
        }
        return null;
    }
}
