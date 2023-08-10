package hello.servlet.Service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TritonService {

    private final String TRITON_URL = "http://triton-server-url/api"; // 실제 Triton 서버 URL로 대체

    @Autowired
    private RestTemplate restTemplate;

    // 이미지 바이너리 데이터를 Triton 서버에 보내는 메서드
    public void sendImageToTritonServer(byte[] imageBytes) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            // MULITPART_FORM_DATA 에 대해서 좀 더 공부해야될듯
            HttpEntity<byte[]> requestEntity = new HttpEntity<>(imageBytes, headers);

            //response 는 json ? 
            ResponseEntity<String> response = restTemplate.postForEntity(TRITON_URL, requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                String responseBody = response.getBody();
                //이부분 마무리
                
            } else {
                // 에러 처리 -> 파일 없음 출력
            }
        } catch (Exception e) {
            // 예외 처리
        }
    }
}
