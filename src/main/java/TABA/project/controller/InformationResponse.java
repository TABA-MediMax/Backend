package TABA.project.controller;

public class InformationResponse {
    private String resultCode; // 결과코드
    private String resultMsg; // 결과메시지
    private int numOfRows; // 한 페이지 결과 수
    private int pageNo; // 페이지 번호
    private int totalCount; // 전체 결과 수

    private String efcyQesitm; // 효능
    private String useMethodQesitm; // 사용법
    private String atpnWarnQesitm; // 이 약을 사용하기 전에 반드시 알아야 할 내용
    private String atpnQesitm; // 약의 사용상 주의사항
    private String intrcQesitm; // 약을 사용하는 동안 주의해야 할 약 또는 음식은
    private String seQesitm; // 이 약은 어떤 이상반응이 나타날 수 있습니까?
    private String depositMethodQesitm; // 보관법


    // Getter와 Setter 메서드들

    // ...

    public String getEfcyQesitm() {
        return efcyQesitm;
    }

    public void setEfcyQesitm(String efcyQesitm) {
        this.efcyQesitm = efcyQesitm;
    }

    public String getUseMethodQesitm() {
        return useMethodQesitm;
    }

    public void setUseMethodQesitm(String useMethodQesitm) {
        this.useMethodQesitm = useMethodQesitm;
    }

    public String getAtpnWarnQesitm() {
        return atpnWarnQesitm;
    }

    public void setAtpnWarnQesitm(String atpnWarnQesitm) {
        this.atpnWarnQesitm = atpnWarnQesitm;
    }

    public String getAtpnQesitm() {
        return atpnQesitm;
    }

    public void setAtpnQesitm(String atpnQesitm) {
        this.atpnQesitm = atpnQesitm;
    }

    public String getIntrcQesitm() {
        return intrcQesitm;
    }

    public void setIntrcQesitm(String intrcQesitm) {
        this.intrcQesitm = intrcQesitm;
    }

    public String getSeQesitm() {
        return seQesitm;
    }

    public void setSeQesitm(String seQesitm) {
        this.seQesitm = seQesitm;
    }

    public String getDepositMethodQesitm() {
        return depositMethodQesitm;
    }

    public void setDepositMethodQesitm(String depositMethodQesitm) {
        this.depositMethodQesitm = depositMethodQesitm;
    }

}
