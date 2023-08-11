package hello.servlet.Service;

import hello.servlet.Controller.ImageResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper; // jackson-databind 라이브러리를 추가해야 함

@Service
public class TritonService {

    private final String TRITON_URL = "http://triton-server-url/api";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper; // ObjectMapper를 주입하여 JSON 파싱 사용

    public ImageResponse sendImageToTritonServer(byte[] imageBytes) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<byte[]> requestEntity = new HttpEntity<>(imageBytes, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(TRITON_URL, requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                String responseBody = response.getBody();
                // JSON 응답 파싱
                JsonNode jsonResponse = objectMapper.readTree(responseBody);

                // 필요한 데이터 추출
                JsonNode dlCompany = jsonResponse.get("images").get(0).get("dl_company");
                JsonNode dlName = jsonResponse.get("images").get(0).get("dl_name");
                JsonNode itemSeq = jsonResponse.get("images").get(0).get("item_seq");

                // ImageResponse 객체 생성 및 데이터 설정
                ImageResponse imageResponse = new ImageResponse();
                imageResponse.setDl_company(dlCompany.asText());
                imageResponse.setDl_name(dlName.asText());
                imageResponse.setDl_mapping_code(itemSeq.asText());

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
