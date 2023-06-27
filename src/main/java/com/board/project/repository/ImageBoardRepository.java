package com.board.project.repository;

import com.board.project.domain.dto.ImageBoardDTO;
import com.board.project.domain.dto.ImageBoardModifyDTO;
import com.board.project.domain.entity.ImageBoard;
import com.board.project.domain.dto.ImageDTO;
import com.board.project.domain.entity.ImageData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface ImageBoardRepository extends JpaRepository<ImageBoard, Long> {

    //default ImageBoardList
    @Query(value = "SELECT new com.board.project.domain.dto.ImageBoardDTO(" +
            "ib.imageNo" +
            ", ib.imageTitle" +
            ", ib.member.userId" +
            ", ib.imageDate" +
            ", ib.imageContent" +
            ", id.imageName) " +
            "FROM ImageBoard  ib " +
            "INNER JOIN " +
            "ImageData id " +
            "ON ib.imageNo = id.imageBoard.imageNo " +
            "GROUP BY ib.imageNo"
    , countQuery = "SELECT c.contentCount " +
            "FROM CountTable c " +
            "WHERE c.boardName = 'imageBoard'")
    Page<ImageBoardDTO> getImageBoardList(Pageable pageable);


    //search ImageTitle List
    @Query(value = "SELECT new com.board.project.domain.dto.ImageBoardDTO(" +
            "ib.imageNo" +
            ", ib.imageTitle" +
            ", ib.member.userId" +
            ", ib.imageDate" +
            ", ib.imageContent" +
            ", id.imageName) " +
            "FROM ImageBoard  ib " +
            "INNER JOIN " +
            "ImageData id " +
            "ON ib.imageNo = id.imageBoard.imageNo " +
            "WHERE ib.imageTitle LIKE :keyword " +
            "GROUP BY ib.imageNo"
    , countQuery = "SELECT count(ib) " +
            "FROM ImageBoard ib " +
            "WHERE ib.imageTitle LIKE :keyword")
    Page<ImageBoardDTO> getImageBoardSearchTitle(@Param("keyword") String keyword, Pageable pageable);

    //search ImageContent List
    @Query(value = "SELECT new com.board.project.domain.dto.ImageBoardDTO(" +
            "ib.imageNo" +
            ", ib.imageTitle" +
            ", ib.member.userId" +
            ", ib.imageDate" +
            ", ib.imageContent" +
            ", id.imageName) " +
            "FROM ImageBoard  ib " +
            "INNER JOIN " +
            "ImageData id " +
            "ON ib.imageNo = id.imageBoard.imageNo " +
            "WHERE ib.imageContent LIKE :keyword " +
            "GROUP BY ib.imageNo"
    , countQuery = "SELECT count(ib) " +
            "FROM ImageBoard ib " +
            "WHERE ib.imageContent LIKE :keyword")
    Page<ImageBoardDTO> getImageBoardSearchContent(@Param("keyword") String keyword, Pageable pageable);

    //search writer List
    @Query(value = "SELECT new com.board.project.domain.dto.ImageBoardDTO(" +
            "ib.imageNo" +
            ", ib.imageTitle" +
            ", ib.member.userId" +
            ", ib.imageDate" +
            ", ib.imageContent" +
            ", id.imageName) " +
            "FROM ImageBoard  ib " +
            "INNER JOIN " +
            "ImageData id " +
            "ON ib.imageNo = id.imageBoard.imageNo " +
            "WHERE ib.member.userId LIKE :keyword " +
            "GROUP BY ib.imageNo"
    , countQuery = "SELECT count(ib) " +
            "FROM ImageBoard ib " +
            "WHERE ib.member.userId LIKE :keyword")
    Page<ImageBoardDTO> getImageBoardSearchWriter(@Param("keyword") String keyword, Pageable pageable);

    //search ImageTitle & content List
    @Query(value = "SELECT new com.board.project.domain.dto.ImageBoardDTO(" +
            "ib.imageNo" +
            ", ib.imageTitle" +
            ", ib.member.userId" +
            ", ib.imageDate" +
            ", ib.imageContent" +
            ", id.imageName) " +
            "FROM ImageBoard  ib " +
            "INNER JOIN " +
            "ImageData id " +
            "ON ib.imageNo = id.imageBoard.imageNo " +
            "WHERE ib.imageTitle LIKE :keyword " +
            "OR ib.imageContent LIKE :keyword " +
            "GROUP BY ib.imageNo"
    , countQuery = "SELECT count(ib) " +
            "FROM ImageBoard ib " +
            "WHERE ib.imageTitle LIKE :keyword " +
            "OR ib.imageContent LIKE :keyword")
    Page<ImageBoardDTO> getImageBoardSearchTitleAndContent(@Param("keyword") String keyword, Pageable pageable);


    @Query(value = "select new com.board.project.domain.dto.ImageDTO(" +
            "b.imageNo" +
            ", d.imageName" +
            ", b.imageContent" +
            ", b.imageDate" +
            ", b.imageTitle" +
            ", b.member.userId" +
            ", d.imageStep" +
            ", d.oldName) " +
            "FROM ImageBoard b " +
            "INNER JOIN ImageData d " +
            "ON b.imageNo = d.imageBoard.imageNo " +
            "WHERE b.imageNo = ?1 " +
            "order by d.imageStep asc")
    List<ImageDTO> imageDetailDTO(long imageNo);


    @Query(value = "SELECT new com.board.project.domain.dto.ImageBoardModifyDTO(" +
            "b.imageNo" +
            ", b.imageTitle" +
            ", b.imageDate" +
            ", b.imageContent) " +
            "FROM ImageBoard b " +
            "WHERE b.imageNo = ?1")
    ImageBoardModifyDTO modifyImageDetail(long imageNo);


    @Modifying
    @Transactional
    @Query(value = "UPDATE ImageBoard b " +
            "SET b.imageTitle = ?1" +
            ", b.imageContent = ?2" +
            ", b.imageDataSet = ?3 " +
            "WHERE b.imageNo = ?4")
    void imageBoardModify(String imageTitle, String imageContent, Set<ImageData> imageDataSet, long imageNo);


    @Query(value = "SELECT userId " +
            "FROM imageBoard " +
            "WHERE imageNo = ?1"
    , nativeQuery = true)
    String checkWriter(long boardNo);
}
