package com.board.project.service;

import com.board.project.domain.HierarchicalBoard;
import com.board.project.repository.HierarchicalBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class HierarchicalBoardServiceImpl implements HierarchicalBoardService{


    private final HierarchicalBoardRepository hierarchicalBoardRepository;

    private final PrincipalService principalService;

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
    public void insertBoard(HttpServletRequest request, Principal principal) throws Exception {
      log.info("service insert Board");
        /**
         * 글 제목
         * , 글 내용
         * , 작성자
         * , groupNo = max
         * , Indent = 0
         * , UpperNo = max
         *
         * save(...build().getId())를 사용하는 경우.
         * 1차적으로 save를 해서 boardNo를 알아야 하고.
         * 그다음 다른 데이터를 넣어 또 save를 해야 한다는 문제점.
         *
         * maxNo를 구해서 사용하는 경우
         * 동시에 요청이 들어오는 경우 원하지 않는 값이 들어올 가능성이 있음.
         *
         * 결과적으로 둘다 DB에 두번 접근하는것은 동일.
         * 1. getId()를 사용할 경우
         *      첫번째 save로 select - insert를 하게 될것이고
         *      두번째 save로 select - update를 하게 될것.
         * 2. maxNo를 사용할 경우
         *      maxBoardNo()로 count 쿼리 실행 후
         *      save로 select - insert를 실행.
         *
         * maxNo가 쿼리를 한번 덜 실행하긴 하지만 위험성이 존재.
         * getId()가 안전하지만 쿼리를 여러번 실행해야 한다는 단점이 존재.
         *
         * Transactional을 사용했을 때 겹쳐서 들어오는 요청에 대해 어떻게 처리가 되는지를 알아볼것.
         */

        try{
            long maxNo = hierarchicalBoardRepository.maxBoardNo();

            HierarchicalBoard hierarchicalBoard = HierarchicalBoard.builder()
                            .boardTitle(request.getParameter("boardTitle"))
                            .boardContent(request.getParameter("boardContent"))
                            .boardIndent(0)
                            .boardGroupNo(maxNo)
                            .boardUpperNo(String.valueOf(maxNo))
                            .boardDate(Date.valueOf(LocalDate.now()))
                            .member(principalService.checkPrincipal(principal))
                            .build();

            /*hierarchicalBoard.setBoardIndent(0);
            hierarchicalBoard.setBoardGroupNo(maxNo);
            hierarchicalBoard.setBoardUpperNo(String.valueOf(maxNo));

            insertProc(hierarchicalBoard, request);*/

            hierarchicalBoardRepository.save(hierarchicalBoard);
            log.info("Board insertion success");
        }catch (Exception e){
            log.info("Board insertion failure");
        }


    }

    @Override
    public void insertBoardReply(HttpServletRequest request, Principal principal) throws Exception {
        log.info("insert Board Reply");
        long maxNo = hierarchicalBoardRepository.maxBoardNo();

        HierarchicalBoard hierarchicalBoard = HierarchicalBoard.builder()
                        .boardTitle(request.getParameter("boardTitle"))
                        .boardContent(request.getParameter("boardContent"))
                        .boardIndent(Integer.parseInt(request.getParameter("boardIndent")) + 1)
                        .boardGroupNo(Long.parseLong(request.getParameter("boardGroupNo")))
                        .boardUpperNo(request.getParameter("boardUpperNo") + "," + maxNo)
                        .boardDate(Date.valueOf(LocalDate.now()))
                        .member(principalService.checkPrincipal(principal))
                        .build();

        hierarchicalBoardRepository.save(hierarchicalBoard);

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
