package kr.hh.liverary.service;

import kr.hh.liverary.domain.definition.Definition;
import kr.hh.liverary.domain.definition.like.Liked;
import kr.hh.liverary.domain.definition.like.LikedRepository;
import kr.hh.liverary.domain.exception.RequestedItemIsNotFoundException;
import kr.hh.liverary.domain.exception.document.InvalidParameterException;
import kr.hh.liverary.dto.LikedRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class LikedService {

    private LikedRepository repository;
    private DefinitionService definitionService;

    @Autowired
    public LikedService(LikedRepository repository, DefinitionService definitionService) {
        this.definitionService = definitionService;
        this.repository = repository;
    }

    @Transactional
    public void create(LikedRequestDto dto) throws Exception{
        if(dto.getDefinition().getId() == null) throw new InvalidParameterException();
        Definition definition = definitionService.findById(dto.getDefinition().getId()).get();

        if(definition == null) throw new RequestedItemIsNotFoundException();

        repository.save(dto.toEntity());
    }

    @Transactional
    public void cancel(LikedRequestDto dto) throws Exception{
        Definition definition = dto.getDefinition();
        if(definition == null) throw new RequestedItemIsNotFoundException();
        Liked liked = repository.findByDefinitionAndWriter(definition, dto.getWriter());
        if(liked == null) throw new RequestedItemIsNotFoundException();
        repository.deleteById(liked.getId());
    }

    @Transactional
    public boolean haveUserReactionOnDefinition(LikedRequestDto dto) throws Exception{
        Definition definition = dto.getDefinition();

        if(definition == null) throw new RequestedItemIsNotFoundException();
        int count = repository.countByDefinitionIdAndWriter(definition.getId(), dto.getWriter());

        if(count == 0) return false;
        else return true;
    }

    @Transactional
    public int getReactionsByDefinition(Long definitionId) throws Exception{
        return repository.countByDefinitionIdAndIsLiked(definitionId, true) - repository.countByDefinitionIdAndIsLiked(definitionId, false);
    }
}
