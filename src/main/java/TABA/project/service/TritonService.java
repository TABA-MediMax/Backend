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


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

@Service
public class TritonService {
    private final String TRITON_URL = "http://115.23.193.50:8000/v2/models/ensemble_pill_pipeline/versions/1/infer"; // triton server url
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    public ImageResponse sendImageToTritonServer(String base64Image) {
        try {
            Map<String, Object> inputData = new HashMap<>();
            inputData.put("name", "input_image");
            inputData.put("datatype", "BYTES");
            inputData.put("shape", new int[]{1,1});

            List<String> base64List = new ArrayList<>();
            base64List.add(base64Image);
            inputData.put("data", base64List);

            //Map<String, Object> parameterMap = new HashMap<>();
            //parameterMap.put("data", base64Image);
            //inputData.put("parameters", parameterMap);

            List<Map<String, Object>> inputs = new ArrayList<>();
            inputs.add(inputData);

            Map<String, Object> inputMap = new HashMap<>();
            inputMap.put("inputs", Collections.singletonList(inputData));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(inputMap, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(TRITON_URL, requestEntity, String.class);


            if (response.getStatusCode().is2xxSuccessful()) {
                String responseBody = response.getBody();
                JsonNode jsonResponse = objectMapper.readTree(responseBody);
                JsonNode inputsNode = jsonResponse.get("inputs");
                if (inputsNode != null && inputsNode.isArray() && inputsNode.size() > 0) {
                    JsonNode firstInputNode = inputsNode.get(0);
                    JsonNode shapeNode = firstInputNode.get("shape");

                    if (shapeNode != null && shapeNode.isArray() && shapeNode.size() == 2) {
                        int[] shape = new int[] { shapeNode.get(0).asInt(), shapeNode.get(1).asInt() };
                        // 이제 'shape' 배열을 코드에서 사용할 수 있습니다
                    }
                }
                ImageResponse imageResponse = new ImageResponse();
                imageResponse.setDl_company(jsonResponse.get("dl_company").asText());
                imageResponse.setDl_name(jsonResponse.get("dl_name").asText());
                imageResponse.setItem_seq(jsonResponse.get("itemSeq").asText());
                imageResponse.setType("json");
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
