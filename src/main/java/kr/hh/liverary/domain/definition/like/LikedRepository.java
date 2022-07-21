package kr.hh.liverary.domain.definition.like;

import kr.hh.liverary.domain.definition.Definition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikedRepository extends JpaRepository<Liked, Long> {
    public int countByDefinitionIdAndIsLiked(Long definitionId, boolean isLiked);
    public int countByDefinitionIdAndWriter(Long definitionId, String writer);
    public Liked findByDefinitionAndWriter(Definition definition, String writer);
}
