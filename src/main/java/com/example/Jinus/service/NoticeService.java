package com.example.Jinus.service;

import com.example.Jinus.entity.*;
import com.example.Jinus.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NoticeService {
    private static final Logger logger = LoggerFactory.getLogger(NoticeService.class);
    private final BizNoticeRepository bizNoticeRepository; // 경영대학
    private final CalsNoticeRepository calsNoticeRepository; // 농업생명과학대학
    private final CeNoticeRepository ceNoticeRepository; // 공과대학
    private final CnsNoticeRepository cnsNoticeRepository; // 자연과학대학
    private final CssNoticeRepository cssNoticeRepository; // 사회과학대학
    private final EtcNoticeRepository etcNoticeRepository; // 건설환경공과대학
    private final InmunNoticeRepository inmunNoticeRepository; // 인문대학
    private final ItNoticeRepository itNoticeRepository; // It 공과대학
    private final MarsciNoticeRepository marsciNoticeRepository; // 해양과학대학
    private final SadaeNoticeRepository sadaeNoticeRepository; // 사범대학

    public NoticeService(
            BizNoticeRepository bizNoticeRepository,
            CalsNoticeRepository calsNoticeRepository,
            CeNoticeRepository ceNoticeRepository,
            CnsNoticeRepository cnsNoticeRepository,
            CssNoticeRepository cssNoticeRepository,
            EtcNoticeRepository etcNoticeRepository,
            InmunNoticeRepository inmunNoticeRepository,
            ItNoticeRepository itNoticeRepository,
            MarsciNoticeRepository marsciNoticeRepository,
            SadaeNoticeRepository sadaeNoticeRepository
    ) {
        this.bizNoticeRepository = bizNoticeRepository;
        this.calsNoticeRepository = calsNoticeRepository;
        this.ceNoticeRepository = ceNoticeRepository;
        this.cnsNoticeRepository = cnsNoticeRepository;
        this.cssNoticeRepository = cssNoticeRepository;
        this.etcNoticeRepository = etcNoticeRepository;
        this.inmunNoticeRepository = inmunNoticeRepository;
        this.itNoticeRepository = itNoticeRepository;
        this.marsciNoticeRepository = marsciNoticeRepository;
        this.sadaeNoticeRepository = sadaeNoticeRepository;
        logger.info("NoticeService 실행");
    }

    // 공지 가져오기
    public Map<Integer, Map<String, String>> getNotice(int departmentId, int categoryId, String collegeEng) {
        logger.info("getNotice 실행");
        logger.info(collegeEng);
        Map<Integer, Map<String, String>> noticesMap = new HashMap<>();

        switch(collegeEng) {
            case "biz" -> {
                // categoryId와 departmentId가 모두 일치하는 공지를 조회
                List<BizNoticeEntity> notices = bizNoticeRepository.findByDepartmentIdAndCategoryId(departmentId, categoryId);

                // 조회된 공지들을 해시맵에 저장
                for (BizNoticeEntity notice : notices) {
                    Map<String, String> noticeInfo = new HashMap<>();
                    noticeInfo.put("title", notice.getTitle());
                    noticeInfo.put("created_at", notice.getCreatedAt());
                    noticesMap.put(notice.getCategoryId(), noticeInfo);
                }
                logger.info(notices.toString());
            }
            case "cals" -> {
                // categoryId와 departmentId가 모두 일치하는 공지를 조회
                List<CalsNoticeEntity> notices = calsNoticeRepository.findByDepartmentIdAndCategoryId(departmentId, categoryId);

                // 조회된 공지들을 해시맵에 저장
                for (CalsNoticeEntity notice : notices) {
                    Map<String, String> noticeInfo = new HashMap<>();
                    noticeInfo.put("title", notice.getTitle());
                    noticeInfo.put("created_at", notice.getCreatedAt());
                    noticesMap.put(notice.getCategoryId(), noticeInfo);
                }
                logger.info(notices.toString());
            }
            case "ce" -> {
                // categoryId와 departmentId가 모두 일치하는 공지를 조회
                List<CeNoticeEntity> notices = ceNoticeRepository.findByDepartmentIdAndCategoryId(departmentId, categoryId);

                // 조회된 공지들을 해시맵에 저장
                for (CeNoticeEntity notice : notices) {
                    Map<String, String> noticeInfo = new HashMap<>();
                    noticeInfo.put("title", notice.getTitle());
                    noticeInfo.put("created_at", notice.getCreatedAt());
                    noticesMap.put(notice.getCategoryId(), noticeInfo);
                }
                logger.info(notices.toString());
            }
            case "cns" -> {
                // categoryId와 departmentId가 모두 일치하는 공지를 조회
                List<CnsNoticeEntity> notices = cnsNoticeRepository.findByDepartmentIdAndCategoryId(departmentId, categoryId);

                // 조회된 공지들을 해시맵에 저장
                for (CnsNoticeEntity notice : notices) {
                    Map<String, String> noticeInfo = new HashMap<>();
                    noticeInfo.put("title", notice.getTitle());
                    noticeInfo.put("created_at", notice.getCreatedAt());
                    noticesMap.put(notice.getCategoryId(), noticeInfo);
                }
                logger.info(notices.toString());
            }
            case "css" -> {
                // categoryId와 departmentId가 모두 일치하는 공지를 조회
                List<CssNoticeEntity> notices = cssNoticeRepository.findByDepartmentIdAndCategoryId(departmentId, categoryId);

                // 조회된 공지들을 해시맵에 저장
                for (CssNoticeEntity notice : notices) {
                    Map<String, String> noticeInfo = new HashMap<>();
                    noticeInfo.put("title", notice.getTitle());
                    noticeInfo.put("created_at", notice.getCreatedAt());
                    noticesMap.put(notice.getCategoryId(), noticeInfo);
                }
                logger.info(notices.toString());
            }
            case "etc" -> {
                // categoryId와 departmentId가 모두 일치하는 공지를 조회
                List<EtcNoticeEntity> notices = etcNoticeRepository.findByDepartmentIdAndCategoryId(departmentId, categoryId);

                // 조회된 공지들을 해시맵에 저장
                for (EtcNoticeEntity notice : notices) {
                    Map<String, String> noticeInfo = new HashMap<>();
                    noticeInfo.put("title", notice.getTitle());
                    noticeInfo.put("created_at", notice.getCreatedAt());
                    noticesMap.put(notice.getCategoryId(), noticeInfo);
                }
                logger.info(notices.toString());
            }
            case "inmun" -> {
                // categoryId와 departmentId가 모두 일치하는 공지를 조회
                List<InmunNoticeEntity> notices = inmunNoticeRepository.findByDepartmentIdAndCategoryId(departmentId, categoryId);

                // 조회된 공지들을 해시맵에 저장
                for (InmunNoticeEntity notice : notices) {
                    Map<String, String> noticeInfo = new HashMap<>();
                    noticeInfo.put("title", notice.getTitle());
                    noticeInfo.put("created_at", notice.getCreatedAt());
                    noticesMap.put(notice.getCategoryId(), noticeInfo);
                }
                logger.info(notices.toString());
            }
            case "it" -> {
                // categoryId와 departmentId가 모두 일치하는 공지를 조회
                List<ItNoticeEntity> notices = itNoticeRepository.findByDepartmentIdAndCategoryId(departmentId, categoryId);

                // 조회된 공지들을 해시맵에 저장
                for (ItNoticeEntity notice : notices) {
                    Map<String, String> noticeInfo = new HashMap<>();
                    noticeInfo.put("title", notice.getTitle());
                    noticeInfo.put("created_at", notice.getCreatedAt());
                    noticesMap.put(notice.getCategoryId(), noticeInfo);
                }
                logger.info(notices.toString());
            }
            case "marsci" -> {
                // categoryId와 departmentId가 모두 일치하는 공지를 조회
                List<MarsciNoticeEntity> notices = marsciNoticeRepository.findByDepartmentIdAndCategoryId(departmentId, categoryId);

                // 조회된 공지들을 해시맵에 저장
                for (MarsciNoticeEntity notice : notices) {
                    Map<String, String> noticeInfo = new HashMap<>();
                    noticeInfo.put("title", notice.getTitle());
                    noticeInfo.put("created_at", notice.getCreatedAt());
                    noticesMap.put(notice.getCategoryId(), noticeInfo);
                }
                logger.info(notices.toString());
            }
            case "sadae" -> {
                // categoryId와 departmentId가 모두 일치하는 공지를 조회
                List<SadaeNoticeEntity> notices = sadaeNoticeRepository.findByDepartmentIdAndCategoryId(departmentId, categoryId);

                // 조회된 공지들을 해시맵에 저장
                for (SadaeNoticeEntity notice : notices) {
                    Map<String, String> noticeInfo = new HashMap<>();
                    noticeInfo.put("title", notice.getTitle());
                    noticeInfo.put("created_at", notice.getCreatedAt());
                    noticesMap.put(notice.getCategoryId(), noticeInfo);
                }
                logger.info(notices.toString());
            }
            default -> logger.error("NoticeService: 공지를 찾을 수 없습니다.");
        }
        logger.info(noticesMap.toString());
        return noticesMap;
    }
}