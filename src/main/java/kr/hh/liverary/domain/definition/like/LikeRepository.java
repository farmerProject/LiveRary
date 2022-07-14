package kr.hh.liverary.domain.definition.like;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
//    public int countByDefinitionId(Long definitionId);
}
