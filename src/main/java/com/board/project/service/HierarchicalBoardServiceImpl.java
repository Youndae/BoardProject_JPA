package com.board.project.service;

import com.board.project.domain.HierarchicalBoard;
import com.board.project.repository.HierarchicalBoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDate;

@Service
@Slf4j
public class HierarchicalBoardServiceImpl implements HierarchicalBoardService{

    @Autowired
    private HierarchicalBoardRepository hierarchicalBoardRepository;

    /**
     *
     * default로 넣어줘야 하는 데이터
     * boardTitle
     * boardContent
     * boardDate
     *
     * save도 공통.
     */

    @Override
    public void insertBoard(HierarchicalBoard hierarchicalBoard, HttpServletRequest request) throws Exception {
      log.info("service insert Board");
        /**
         * 글 제목
         * , 글 내용
         * , 작성자
         * , groupNo = max
         * , Indent = 0
         * , UpperNo = max
         */

        try{
            long maxNo = hierarchicalBoardRepository.maxBoardNo();
            /*hierarchicalBoard.setBoardTitle(request.getParameter("boardTitle"));
            hierarchicalBoard.setBoardContent(request.getParameter("boardContent"));
            hierarchicalBoard.setBoardDate(Date.valueOf(LocalDate.now()));*/
            hierarchicalBoard.setBoardIndent(0);
            hierarchicalBoard.setBoardGroupNo(maxNo);
            hierarchicalBoard.setBoardUpperNo(String.valueOf(maxNo));

//            hierarchicalBoardRepository.save(hierarchicalBoard);

            insertProc(hierarchicalBoard, request);
            log.info("Board insertion success");
        }catch (Exception e){
            log.info("Board insertion failure");
        }


    }

    @Override
    public void insertBoardReply(HierarchicalBoard hierarchicalBoard, HttpServletRequest request) throws Exception {
        log.info("insert Board Reply");
        long maxNo = hierarchicalBoardRepository.maxBoardNo();
        hierarchicalBoard.setBoardIndent(Integer.parseInt(request.getParameter("boardIndent")) + 1);
        hierarchicalBoard.setBoardGroupNo(Long.parseLong(request.getParameter("boardGroupNo")));
        hierarchicalBoard.setBoardUpperNo(request.getParameter("boardUpperNo") + "," + maxNo);

        insertProc(hierarchicalBoard, request);
    }


    @Override
    public void deleteBoard(HierarchicalBoard hierarchicalBoard) {
        log.info("delete board");
        try{
            hierarchicalBoardRepository.deleteById(hierarchicalBoard.getBoardNo());
            log.info("delete success");
        }catch (Exception e){
            log.info("delete failure");
        }
    }

    int insertProc(HierarchicalBoard hierarchicalBoard, HttpServletRequest request) throws Exception {
        try{
            hierarchicalBoard.setBoardTitle(request.getParameter("boardTitle"));
            hierarchicalBoard.setBoardContent(request.getParameter("boardContent"));
            hierarchicalBoard.setBoardDate(Date.valueOf(LocalDate.now()));

            hierarchicalBoardRepository.save(hierarchicalBoard);
            log.info("save success");
            return 1;
        }catch (Exception e){
            log.info("save failure");
            return 0;
        }
    }
}
