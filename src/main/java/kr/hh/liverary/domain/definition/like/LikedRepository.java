package kr.hh.liverary.domain.definition.like;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LikedRepository extends JpaRepository<Liked, Long> {
    public int countByDefinitionId(Long definitionId);
    public int countByDefinitionIdAndUserId(Long definitionId, Long userId);
}
