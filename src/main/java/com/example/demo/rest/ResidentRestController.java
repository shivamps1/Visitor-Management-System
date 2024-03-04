package com.example.demo.rest;


import com.example.demo.domain.User;
import com.example.demo.model.VisitDTO;
import com.example.demo.model.VisitStatus;
import com.example.demo.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resident")
public class ResidentRestController
{

    @Autowired
    private VisitService visitService;

//    @PutMapping("/updateVisit/{id}/{status}")
//    public ResponseEntity<Void> updateVisit(@PathVariable(name = "id") final Long id,
//                                            @PathVariable(name ="status") final String status) {
//        //
//        return ResponseEntity.ok().build();
//    }


    @PutMapping("/approveVisit/{id}")
    public ResponseEntity<Void> approveVisit(@PathVariable(name = "id") final Long id) {
        visitService.updateStatus(id, VisitStatus.APPROVED);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/rejectVisit/{id}")
    public ResponseEntity<Void> rejectVisit(@PathVariable(name = "id") final Long id) {
        visitService.updateStatus(id, VisitStatus.REJECTED);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/visits/{status}")
    public ResponseEntity<List<VisitDTO>> getVisitsByStatus(@PathVariable(name = "status") final VisitStatus visitStatus){
        // we can get user from this, no need to pass in header
       User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
        List<VisitDTO> visits = visitService.findAllByStatusAndUserId(visitStatus, 4l);
        return ResponseEntity.ok(visits);
    }



}
