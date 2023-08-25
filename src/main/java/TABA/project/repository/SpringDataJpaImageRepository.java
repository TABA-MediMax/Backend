package TABA.project.repository;

import TABA.project.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaImageRepository extends JpaRepository<Image, Long>, ImageRepository{

    //select img from Image img where img.path = ?
    @Override
    Optional<Image> findByPath(String path);
}
