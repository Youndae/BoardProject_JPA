package com.board.project.repository;

import com.board.project.domain.HierarchicalBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HierarchicalBoardRepository extends JpaRepository<HierarchicalBoard, Long> {


}
