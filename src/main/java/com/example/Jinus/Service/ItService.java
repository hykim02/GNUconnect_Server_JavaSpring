package com.example.Jinus.Service;

import com.example.Jinus.ItNotice;
import com.example.Jinus.ItNoticeRepository;
import com.example.Jinus.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItService {

    @Autowired
    private ItNoticeRepository repository;

    public List<Response> getAllResponses() {
        List<ItNotice> notices = repository.findAll(); //모든 데이터를 조회하여 리스트로 반환
        return notices.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private Response mapToResponse(ItNotice notice) {
        Response response = new Response();

        response.setId((long) Math.toIntExact(notice.getId()));
        response.setCategory_id((long) Math.toIntExact(notice.getCategory_id()));
        response.setDepartment_id((long) Math.toIntExact(notice.getDepartment_id()));
        response.setTitle(notice.getTitle());

        return response;
    }
}
