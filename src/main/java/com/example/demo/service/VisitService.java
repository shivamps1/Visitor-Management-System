package com.example.demo.service;


import com.example.demo.domain.Flat;
import com.example.demo.domain.User;
import com.example.demo.domain.Visit;
import com.example.demo.domain.Visitor;
import com.example.demo.model.UserStatus;
import com.example.demo.model.VisitDTO;
import com.example.demo.model.VisitStatus;
import com.example.demo.repos.FlatRepository;
import com.example.demo.repos.UserRepository;
import com.example.demo.repos.VisitRepository;
import com.example.demo.repos.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.demo.util.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class VisitService {

    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private VisitorRepository visitorRepository;
    @Autowired
    private FlatRepository flatRepository;
    @Autowired
    private UserRepository userRepository;



    public List<VisitDTO> findAll() {
        final List<Visit> visits = visitRepository.findAll(Sort.by("id"));
        return visits.stream()
                .map((visit) -> mapToDTO(visit, new VisitDTO()))
                .collect(Collectors.toList());
    }

    public List<VisitDTO> findAll(Pageable pageable) {
        final List<Visit> visits = visitRepository.findAll(pageable).toList();
        return visits.stream()
                .map((visit) -> mapToDTO(visit, new VisitDTO()))
                .collect(Collectors.toList());
    }

    public VisitDTO get(final Long id) {
        return visitRepository.findById(id)
                .map(visit -> mapToDTO(visit, new VisitDTO()))
                .orElseThrow(NotFoundException::new);

    }

    public Long create(final VisitDTO visitDTO) {
        final Visit visit = new Visit();
        mapToEntity(visitDTO, visit);
        return visitRepository.save(visit).getId();
    }

    public void update(final Long id, final VisitDTO visitDTO) {
        final Visit visit = visitRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(visitDTO, visit);
        visitRepository.save(visit);
    }

    public void updateStatus(final Long id, final VisitStatus status) {
        final Visit visit = visitRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        visit.setStatus(status);
        visitRepository.save(visit);
    }
    public List<VisitDTO> findAllByStatusAndUserId(VisitStatus visitStatus, Long userId) {
        User user = userRepository.findById(userId).get();
        Flat flat = user.getFlat();
        final List<Visit> visits = visitRepository.findByStatusAndFlat(VisitStatus.WAITING,flat);
        return visits.stream()
                .map((visit) -> mapToDTO(visit, new VisitDTO()))
                .collect(Collectors.toList());
    }


    public void delete(final Long id) {
        visitRepository.deleteById(id);
    }

    private VisitDTO mapToDTO(final Visit visit, final VisitDTO visitDTO) {
        visitDTO.setId(visit.getId());
        visitDTO.setStatus(visit.getStatus());
        visitDTO.setInTime(visit.getInTime());
        visitDTO.setOutTime(visit.getOutTime());
        visitDTO.setPurpose(visit.getPurpose());
        visitDTO.setImageUrl(visit.getImageUrl());
        visitDTO.setNoOfPeople(visit.getNoOfPeople());
        visitDTO.setVisitor(visit.getVisitor() == null ? null : visit.getVisitor().getId());
        visitDTO.setFlat(visit.getFlat() == null ? null : visit.getFlat().getId());
        return visitDTO;
    }

    private Visit mapToEntity(final VisitDTO visitDTO, final Visit visit) {
        visit.setStatus(visitDTO.getStatus());
        visit.setInTime(visitDTO.getInTime());
        visit.setOutTime(visitDTO.getOutTime());
        visit.setPurpose(visitDTO.getPurpose());
        visit.setImageUrl(visitDTO.getImageUrl());
        visit.setNoOfPeople(visitDTO.getNoOfPeople());
        final Visitor visitor = visitDTO.getVisitor() == null ? null : visitorRepository.findById(visitDTO.getVisitor())
                .orElseThrow(() -> new NotFoundException("visitor not found"));
        visit.setVisitor(visitor);
        final Flat flat = visitDTO.getFlat() == null ? null : flatRepository.findById(visitDTO.getFlat())
                .orElseThrow(() -> new NotFoundException("flat not found"));
        visit.setFlat(flat);
        return visit;
    }

}
