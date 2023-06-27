package com.board.project.service;

import com.board.project.domain.dto.HierarchicalBoardDTO;
import com.board.project.domain.dto.HierarchicalBoardModifyDTO;
import com.board.project.domain.entity.Criteria;
import com.board.project.domain.entity.HierarchicalBoard;
import com.board.project.domain.entity.Member;
import com.board.project.repository.HierarchicalBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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


    @Override
    public Page<HierarchicalBoardDTO> getHierarchicalBoardList(Criteria cri) {

        Page<HierarchicalBoardDTO> dto = null;

        String keyword = null;

        if(cri.getKeyword() != null)
            keyword = "%" + cri.getKeyword() + "%";


        if (cri.getKeyword() == null || cri.getKeyword() == "") {// default List
            dto =
                    hierarchicalBoardRepository.hierarchicalBoardList(
                            PageRequest.of(cri.getPageNum() - 1
                                    , cri.getBoardAmount()
                                    , Sort.by("boardGroupNo").descending()
                                            .and(Sort.by("boardUpperNo").ascending())));
        } else if (cri.getSearchType().equals("t")) {//title 검색시 사용
            dto =
                    hierarchicalBoardRepository.hierarchicalBoardListSearchTitle(
                            keyword
                            , PageRequest.of(cri.getPageNum() - 1
                                    , cri.getBoardAmount()
                                    , Sort.by("boardGroupNo").descending()
                                            .and(Sort.by("boardUpperNo").ascending())));
        } else if (cri.getSearchType().equals("c")) {//content 검색시 사용
            dto =
                    hierarchicalBoardRepository.hierarchicalBoardListSearchContent(
                            keyword
                            , PageRequest.of(cri.getPageNum() - 1
                                    , cri.getBoardAmount()
                                    , Sort.by("boardGroupNo").descending()
                                            .and(Sort.by("boardUpperNo").ascending())));
        } else if (cri.getSearchType().equals("u")) {// user 검색 시 사용
            dto =
                    hierarchicalBoardRepository.hierarchicalBoardListSearchUser(
                            keyword
                            , PageRequest.of(cri.getPageNum() - 1
                                    , cri.getBoardAmount()
                                    , Sort.by("boardGroupNo").descending()
                                            .and(Sort.by("boardUpperNo").ascending())));
        } else if (cri.getKeyword().equals("tc")) {// title and content 검색시 사용
            dto =
                    hierarchicalBoardRepository.hierarchicalBoardListSearchTitleOrContent(
                            keyword
                            , PageRequest.of(cri.getPageNum() - 1
                                    , cri.getBoardAmount()
                                    , Sort.by("boardGroupNo").descending()
                                            .and(Sort.by("boardUpperNo").ascending())));
        }

        return dto;
    }

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
    public long insertBoard(HttpServletRequest request, Principal principal) throws Exception {
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
//            long maxNo = hierarchicalBoardRepository.maxBoardNo();

            Member member = principalService.checkPrincipal(principal);

            long boardNo = hierarchicalBoardRepository.save(
                                    HierarchicalBoard.builder()
                                            .boardTitle(request.getParameter("boardTitle"))
                                            .member(member)
                                            .boardDate(Date.valueOf(LocalDate.now()))
                                            .build()
            ).getBoardNo();

            request.setAttribute("boardNo", boardNo);
            request.setAttribute("boardGroupNo", boardNo);
            request.setAttribute("boardIndent", 0);
            request.setAttribute("boardUpperNo", boardNo);

            /*HierarchicalBoard hierarchicalBoard = HierarchicalBoard.builder()
                            .boardTitle(request.getParameter("boardTitle"))
                            .boardContent(request.getParameter("boardContent"))
                            .boardIndent(0)
                            .boardGroupNo(maxNo)
                            .boardUpperNo(String.valueOf(maxNo))
                            .boardDate(Date.valueOf(LocalDate.now()))
                            .member(principalService.checkPrincipal(principal))
                            .build();*/

            /*hierarchicalBoard.setBoardIndent(0);
            hierarchicalBoard.setBoardGroupNo(maxNo);
            hierarchicalBoard.setBoardUpperNo(String.valueOf(maxNo));

            insertProc(hierarchicalBoard, request);*/



            insertModifyHierarchicalBoard(request);

//            hierarchicalBoardRepository.save(hierarchicalBoard);
            log.info("Board insertion success");
            return boardNo;
        }catch (Exception e){
            log.info("Board insertion failure");
            return -1;
        }


    }

    //수정 데이터 get
    @Override
    public HierarchicalBoardModifyDTO getModifyData(long boardNo, Principal principal) {
        HierarchicalBoardModifyDTO dto;

        if(principal == null || checkWriter(boardNo, principal))
            dto = null;
        else{
            dto = hierarchicalBoardRepository.getModifyData(boardNo);
        }

        return dto;
    }

    //insert 후 modify 처리
    public void insertModifyHierarchicalBoard(HttpServletRequest request){
        log.info("modifyBoard");

        hierarchicalBoardRepository.boardInsertModify(request.getParameter("boardContent")
                                    , Integer.parseInt(request.getAttribute("boardIndent").toString())
                                    , Long.parseLong(request.getAttribute("boardGroupNo").toString())
                                    , request.getAttribute("boardUpperNo").toString()
                                    , Long.parseLong(request.getAttribute("boardNo").toString())
                         );

    }

    @Override
    public void insertBoardReply(HttpServletRequest request, Principal principal) throws Exception {
        log.info("insert Board Reply");

        long boardNo = hierarchicalBoardRepository.save(
                HierarchicalBoard.builder()
                        .boardTitle(request.getParameter("boardTitle"))
                        .boardDate(Date.valueOf(LocalDate.now()))
                        .member(principalService.checkPrincipal(principal))
                        .build()
        ).getBoardNo();

        request.setAttribute("boardNo", boardNo);
        request.setAttribute("boardGroupNo", request.getParameter("boardGroupNo"));
        request.setAttribute("boardIndent", request.getParameter("boardIndent"));
        request.setAttribute("boardUpperNo", request.getParameter("boardUpperNo") + "," + boardNo);



        /*HierarchicalBoard hierarchicalBoard = HierarchicalBoard.builder()
                        .boardTitle(request.getParameter("boardTitle"))
                        .boardContent(request.getParameter("boardContent"))
                        .boardIndent(Integer.parseInt(request.getParameter("boardIndent")) + 1)
                        .boardGroupNo(Long.parseLong(request.getParameter("boardGroupNo")))
                        .boardUpperNo(request.getParameter("boardUpperNo") + "," + maxNo)
                        .boardDate(Date.valueOf(LocalDate.now()))
                        .member(principalService.checkPrincipal(principal))
                        .build();*/

        insertModifyHierarchicalBoard(request);

    }


    @Override
    public long deleteBoard(long boardNo, Principal principal) {
        log.info("delete board");

        //principal == null이거나
        // checkWriter에서 false가 오거나.
        if(principal == null || !checkWriter(boardNo, principal)) {
            log.info("return 0");
            log.info("principal is null : " + (principal == null));
            log.info("checkWriter : {}", checkWriter(boardNo, principal));
            return 0;
        }

        try{
            hierarchicalBoardRepository.deleteById(boardNo);
            log.info("delete success");
            return 1;
        }catch (Exception e){
            log.info("delete failure");
            return -1;
        }
    }

    //boardModify
    @Override
    public long boardModify(HttpServletRequest request){

        hierarchicalBoardRepository.boardModify(
                request.getParameter("boardTitle")
                , request.getParameter("boardContent")
                , Long.parseLong(request.getParameter("boardNo"))
        );

        return Long.parseLong(request.getParameter("boardNo"));
    }


    boolean checkWriter(long boardNo, Principal principal) {

        if(principal.getName().equals(hierarchicalBoardRepository.checkWriter(boardNo)))
            return true;

        return false;
    }
}
