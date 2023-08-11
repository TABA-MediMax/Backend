package hello.servlet.Controller;

// Triton server 에 response 받을 때의 json 형태
public class ImageResponse {
    private String dl_name;
    private String dl_company;
    private String dl_mapping_code;

    public String getDl_name() {
        return dl_name;
    }

    public void setDl_name(String dl_name) {
        this.dl_name = dl_name;
    }

    public String getDl_company() {
        return dl_company;
    }

    public void setDl_company(String dl_company) {
        this.dl_company = dl_company;
    }

    public String getDl_mapping_code() {
        return dl_mapping_code;
    }

    public void setDl_mapping_code(String dl_mapping_code) {
        this.dl_mapping_code = dl_mapping_code;
    }
}
