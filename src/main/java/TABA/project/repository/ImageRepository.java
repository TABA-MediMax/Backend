package TABA.project.repository;

import TABA.project.domain.Image;

import java.util.Optional;

public interface ImageRepository {
    Image save(Image image);
    Optional<Image> findById(Long id);
    Optional<Image> findByPath(String path);
}
