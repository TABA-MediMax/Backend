
public interface ImageService {
    String saveImage(byte[] imageBytes); //  imagePath 리턴
    Long saveImagePathToDatabase(String imagePath); // 경로 저장한 imageId 리턴
}
