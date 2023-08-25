package TABA.project.service;

import TABA.project.domain.Image;
import TABA.project.repository.ImageRepository;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final ImageRepository imageRepository;
    private final AmazonS3 amazonS3;

    public ImageService(ImageRepository imageRepository, AmazonS3 amazonS3) {
        this.imageRepository = imageRepository;
        this.amazonS3 = amazonS3;
    }

    public String uploadFileToS3(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();

        /*
        //S3에 이미지 파일 업로드
        try {

            amazonS3.putObject(bucket, originalFilename, multipartFile.getInputStream(), getObjectMetadata(multipartFile));
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage()); //로그로 변경
            System.exit(1);
        }

        //DB저장
        saveToSqliteDB(amazonS3.getUrl(bucket, originalFilename).toString());
        */

        saveToSqliteDB("Hello!!DB~~~~");

        return amazonS3.getUrl(bucket, originalFilename).toString();
    }

    public void saveToSqliteDB(String path){
        Image image = new Image();
        image.setPath(path);
        imageRepository.save(image);
    }

    private ObjectMetadata getObjectMetadata(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());
        return objectMetadata;
    }
}



