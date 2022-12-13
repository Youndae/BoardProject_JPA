package com.board.project.config.interceptor;

import com.board.project.domain.Comment;
import com.board.project.domain.HierarchicalBoard;
import com.board.project.repository.CommentRepository;
import com.board.project.repository.HierarchicalBoardRepository;
import com.board.project.repository.ImageBoardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

@Component
@Slf4j
public class CustomAuthInterceptor implements HandlerInterceptor {

    @Autowired(required = false)
    CommentRepository commentRepository;

    @Autowired(required = false)
    HierarchicalBoardRepository hierarchicalBoardRepository;

    @Autowired(required = false)
    ImageBoardRepository imageBoardRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /**
         * interceptor를 통해 사용자와 작성자를 검증하기 위해서는
         * request로 받은 정보와 작성자 데이터를 비교해봐야 하는데
         * 여기서 request를 getReader나 InputStream으로 열어보게 되면
         * 컨트롤러에서 열어볼 수 없는 문제점이 발생.
         *
         * 그래서 HttpServletRequestWrapper를 이용해 처리해야 한다.
         * request를 여러번 여는것은 톰캣에서 막고 있기 때문.
         *
         * 일단 JPA 기능구현이 먼저니까 JPA 기능구현 끝내고 나서 처리할것.
         */

        /*log.info("preHandle");

        String user = request.getUserPrincipal().getName();

        log.info("user : " + user);

        String writer = request.getParameter("userId");

        log.info("writer : " + writer);


        if(writer == null){
            JSONObject jObject = null;

            BufferedReader br = request.getReader();
            String input = null;
            while((input = br.readLine()) != null)
                jObject = new JSONObject(input);

            if(!jObject.isNull("boardNo")){
                log.info("boardNo is not null");
                HierarchicalBoard hierarchicalBoard = hierarchicalBoardRepository.findByBoardNo(Long.parseLong(jObject.get("boardNo").toString()));
                writer = hierarchicalBoard.getMember().getUserId();
            }else if(!jObject.isNull("commentNo")){
                log.info("commentNo is not null");
                Optional<Comment> comment = commentRepository.findById(Long.parseLong(jObject.get("commentNo").toString()));
                writer = comment.get().getMember().getUserId();
            }
        }



        log.info("PreHandle writer : " + writer);


        if(!user.equals(writer)){
            log.info("not equals");
            response.sendError(403);
            return false;
        }

        log.info("user and writer equals");
//        return HandlerInterceptor.super.preHandle(request, response, handler);*/
        return true;
    }
}
