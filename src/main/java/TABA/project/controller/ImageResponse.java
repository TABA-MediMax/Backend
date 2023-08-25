package TABA.project.controller;

// Triton server 에 response 받을 때의 json 형태
public class ImageResponse {
    private String dl_name;
    private String dl_company;
    private String item_seq;



    private String type;

    public void setType(String type){
        this.type=type;
    }
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

    public String getItem_seq() {
        return item_seq;
    }

    public void setItem_seq(String item_seq) {
        this.item_seq = item_seq;
    }
}
