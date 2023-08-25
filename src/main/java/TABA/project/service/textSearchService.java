package TABA.project.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Service
public class textSearchService {
    //클라이언트에서 제품명 보내서 api 에 post 요청
    private final RestTemplate restTemplate;

    @Value("${api.key}")
    private String apiKey;

    @Value("${api.url}")
    private String apiUrl;
    @Autowired
    public textSearchService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public String searchText(String text) {
        try {

            StringBuilder urlBuilder = new StringBuilder(apiUrl);
            urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "="+apiKey);
            urlBuilder.append("&" + URLEncoder.encode("itemName","UTF-8") + "=" + URLEncoder.encode(text, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));

            // 파라미터를 url 에 추가함
            URL url = new URL(urlBuilder.toString());
            //실제 url 생성
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // HTTPURLConnection 생성
            conn.setRequestMethod("GET");

            conn.setRequestProperty("Content-type", "application/json");
            //json 타입 설정
            System.out.println("Response code: " + conn.getResponseCode());
            //응답코드 출력 : 200 이면 정상
            BufferedReader rd;
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }

            rd.close();
            conn.disconnect();
            System.out.println(sb.toString());
            return sb.toString();
            //응답 데이터를 문자열로 변환하여 반환
        } catch (RestClientException e) {
            // 네트워크 통신 오류 처리
            System.out.println("Network communication error: " + e.getMessage());
            return null;
        } catch (Exception e) {
            // 기타 예외 처리
            System.out.println("Unexpected error: " + e.getMessage());
            return null;
        }
    }


}
