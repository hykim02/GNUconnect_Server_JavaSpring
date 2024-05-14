package com.example.Jinus.service.notice;

import com.example.Jinus.entity.notice.*;
import com.example.Jinus.repository.notice.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

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
    public List<Map<String, String>> getNotice(int categoryId, String collegeEng) throws ParseException {
        logger.info("getNotice 실행");
        logger.info("collegeEng:{}", collegeEng);
        List<Map<String, String>> noticeList = new ArrayList<>(); // 공지 리스트

        switch(collegeEng) {
            case "biz" -> {
                // categoryId 일치하는 공지를 조회
                List<BizNoticeEntity> notices = bizNoticeRepository.findByCategoryId(categoryId);
                logger.info("categoryId:{}", categoryId);
                logger.info("biz-notices:{}", notices);
                logger.info("biz-notices 개수:{}", notices.size());

                int i = 0;
                // 조회된 공지들을 해시맵에 저장
                for (BizNoticeEntity notice : notices) {
                    ++i;
                    logger.info("notice:{}", i);
                    logger.info("biz-notice:{}", notice);
                    Map<String, String> noticeInfo = new HashMap<>(); // 공지 객체 하나
                    noticeInfo.put("title", notice.getTitle());
                    noticeInfo.put("created_at", notice.getCreatedAt());
                    noticeInfo.put("ntt_sn", String.valueOf(notice.getNttSn()));
                    noticeList.add(noticeInfo);
                    logger.info("biz-noticeInfo:{}", noticeInfo);
                }
                logger.info("biz-noticeList:{}", noticeList);
            }
            case "cals" -> {
                // categoryId 일치하는 공지를 조회
                List<CalsNoticeEntity> notices = calsNoticeRepository.findByCategoryId(categoryId);
                logger.info("categoryId:{}", categoryId);
                logger.info("cals-notices:{}", notices);
                logger.info("cals-notices 개수:{}", notices.size());

                int i = 0;
                // 조회된 공지들을 해시맵에 저장
                for (CalsNoticeEntity notice : notices) {
                    ++i;
                    logger.info("notice:{}", i);
                    logger.info("cals-notice:{}", notice);
                    Map<String, String> noticeInfo = new HashMap<>(); // 공지 객체 하나
                    noticeInfo.put("title", notice.getTitle());
                    noticeInfo.put("created_at", notice.getCreatedAt());
                    noticeInfo.put("ntt_sn", String.valueOf(notice.getNttSn()));
                    noticeList.add(noticeInfo);
                    logger.info("cals-noticeInfo:{}", noticeInfo);
                }
                logger.info("cals-noticeList:{}", noticeList);
            }
            case "ce" -> {
                // categoryId 일치하는 공지를 조회
                List<CeNoticeEntity> notices = ceNoticeRepository.findByCategoryId(categoryId);
                logger.info("categoryId:{}", categoryId);
                logger.info("ce-notices:{}", notices);
                logger.info("ce-notices 개수:{}", notices.size());

                int i = 0;
                // 조회된 공지들을 해시맵에 저장
                for (CeNoticeEntity notice : notices) {
                    ++i;
                    logger.info("notice:{}", i);
                    logger.info("ce-notice:{}", notice);
                    Map<String, String> noticeInfo = new HashMap<>(); // 공지 객체 하나
                    noticeInfo.put("title", notice.getTitle());
                    noticeInfo.put("created_at", notice.getCreatedAt());
                    noticeInfo.put("ntt_sn", String.valueOf(notice.getNttSn()));
                    noticeList.add(noticeInfo);
                    logger.info("ce-noticeInfo:{}", noticeInfo);
                }
                logger.info("ce-noticeList:{}", noticeList);
            }
            case "cns" -> {
                // categoryId 일치하는 공지를 조회
                List<CnsNoticeEntity> notices = cnsNoticeRepository.findByCategoryId(categoryId);
                logger.info("categoryId:{}", categoryId);
                logger.info("cns-notices:{}", notices);
                logger.info("cns-notices 개수:{}", notices.size());

                int i = 0;
                // 조회된 공지들을 해시맵에 저장
                for (CnsNoticeEntity notice : notices) {
                    ++i;
                    logger.info("notice:{}", i);
                    logger.info("cns-notice:{}", notice);
                    Map<String, String> noticeInfo = new HashMap<>(); // 공지 객체 하나
                    noticeInfo.put("title", notice.getTitle());
                    noticeInfo.put("created_at", notice.getCreatedAt());
                    noticeInfo.put("ntt_sn", String.valueOf(notice.getNttSn()));
                    noticeList.add(noticeInfo);
                    logger.info("cns-noticeInfo:{}", noticeInfo);
                }
                logger.info("cns-noticeList:{}", noticeList);
            }
            case "css" -> {
                // categoryId 일치하는 공지를 조회
                List<CssNoticeEntity> notices = cssNoticeRepository.findByCategoryId(categoryId);
                logger.info("categoryId:{}", categoryId);
                logger.info("css-notices:{}", notices);
                logger.info("css-notices 개수:{}", notices.size());

                int i = 0;
                // 조회된 공지들을 해시맵에 저장
                for (CssNoticeEntity notice : notices) {
                    ++i;
                    logger.info("notice:{}", i);
                    logger.info("css-notice:{}", notice);
                    Map<String, String> noticeInfo = new HashMap<>(); // 공지 객체 하나
                    noticeInfo.put("title", notice.getTitle());
                    noticeInfo.put("created_at", notice.getCreatedAt());
                    noticeInfo.put("ntt_sn", String.valueOf(notice.getNttSn()));
                    noticeList.add(noticeInfo);
                    logger.info("css-noticeInfo:{}", noticeInfo);
                }
                logger.info("css-noticeList:{}", noticeList);
            }
            case "etc" -> {
                // categoryId 일치하는 공지를 조회
                List<EtcNoticeEntity> notices = etcNoticeRepository.findByCategoryId(categoryId);
                logger.info("categoryId:{}", categoryId);
                logger.info("etc-notices:{}", notices);
                logger.info("etc-notices 개수:{}", notices.size());

                int i = 0;
                // 조회된 공지들을 해시맵에 저장
                for (EtcNoticeEntity notice : notices) {
                    ++i;
                    logger.info("notice:{}", i);
                    logger.info("etc-notice:{}", notice);
                    Map<String, String> noticeInfo = new HashMap<>(); // 공지 객체 하나
                    noticeInfo.put("title", notice.getTitle());
                    noticeInfo.put("created_at", notice.getCreatedAt());
                    noticeInfo.put("ntt_sn", String.valueOf(notice.getNttSn()));
                    noticeList.add(noticeInfo);
                    logger.info("etc-noticeInfo:{}", noticeInfo);
                }
                logger.info("etc-noticeList:{}", noticeList);
            }
            case "inmun" -> {
                // categoryId 일치하는 공지를 조회
                List<InmunNoticeEntity> notices = inmunNoticeRepository.findByCategoryId(categoryId);
                logger.info("categoryId:{}", categoryId);
                logger.info("inmun-notices:{}", notices);
                logger.info("inmun-notices 개수:{}", notices.size());

                int i = 0;
                // 조회된 공지들을 해시맵에 저장
                for (InmunNoticeEntity notice : notices) {
                    ++i;
                    logger.info("notice:{}", i);
                    logger.info("inmun-notice:{}", notice);
                    Map<String, String> noticeInfo = new HashMap<>(); // 공지 객체 하나
                    noticeInfo.put("title", notice.getTitle());
                    noticeInfo.put("created_at", notice.getCreatedAt());
                    noticeInfo.put("ntt_sn", String.valueOf(notice.getNttSn()));
                    noticeList.add(noticeInfo);
                    logger.info("inmun-noticeInfo:{}", noticeInfo);
                }
                logger.info("inmun-noticeList:{}", noticeList);
            }
            case "it" -> {
                // categoryId 일치하는 공지를 조회
                List<ItNoticeEntity> notices = itNoticeRepository.findByCategoryId(categoryId);
                logger.info("categoryId:{}", categoryId);
                logger.info("it-notices:{}", notices);
                logger.info("it-notices 개수:{}", notices.size());

                int i = 0;
                // 조회된 공지들을 해시맵에 저장
                for (ItNoticeEntity notice : notices) {
                    ++i;
                    logger.info("notice:{}", i);
                    logger.info("it-notice:{}", notice);
                    Map<String, String> noticeInfo = new HashMap<>(); // 공지 객체 하나
                    noticeInfo.put("title", notice.getTitle());
                    noticeInfo.put("created_at", notice.getCreatedAt());
                    noticeInfo.put("ntt_sn", String.valueOf(notice.getNttSn()));
                    noticeList.add(noticeInfo);
                    logger.info("it-noticeInfo:{}", noticeInfo);
                }
                logger.info("it-noticeList:{}", noticeList);
            }
            case "marsci" -> {
                // categoryId 일치하는 공지를 조회
                List<MarsciNoticeEntity> notices = marsciNoticeRepository.findByCategoryId(categoryId);
                logger.info("categoryId:{}", categoryId);
                logger.info("marsci-notices:{}", notices);
                logger.info("marsci-notices 개수:{}", notices.size());

                int i = 0;
                // 조회된 공지들을 해시맵에 저장
                for (MarsciNoticeEntity notice : notices) {
                    ++i;
                    logger.info("notice:{}", i);
                    logger.info("marsci-notice:{}", notice);
                    Map<String, String> noticeInfo = new HashMap<>(); // 공지 객체 하나
                    noticeInfo.put("title", notice.getTitle());
                    noticeInfo.put("created_at", notice.getCreatedAt());
                    noticeInfo.put("ntt_sn", String.valueOf(notice.getNttSn()));
                    noticeList.add(noticeInfo);
                    logger.info("marsci-noticeInfo:{}", noticeInfo);
                }
                logger.info("marsci-noticeList:{}", noticeList);
            }
            case "sadae" -> {
                // categoryId 일치하는 공지를 조회
                List<SadaeNoticeEntity> notices = sadaeNoticeRepository.findByCategoryId(categoryId);
                logger.info("categoryId:{}", categoryId);
                logger.info("sadae-notices:{}", notices);
                logger.info("sadae-notices 개수:{}", notices.size());

                int i = 0;
                // 조회된 공지들을 해시맵에 저장
                for (SadaeNoticeEntity notice : notices) {
                    ++i;
                    logger.info("notice:{}", i);
                    logger.info("sadae-notice:{}", notice);
                    Map<String, String> noticeInfo = new HashMap<>(); // 공지 객체 하나
                    noticeInfo.put("title", notice.getTitle());
                    noticeInfo.put("created_at", notice.getCreatedAt());
                    noticeInfo.put("ntt_sn", String.valueOf(notice.getNttSn()));
                    noticeList.add(noticeInfo);
                    logger.info("sadae-noticeInfo:{}", noticeInfo);
                }
                logger.info("sadae-noticeList:{}", noticeList);
            }
            default -> logger.error("NoticeService: 공지를 찾을 수 없습니다.");
        }
        return sortDates(noticeList);
    }

    public static List<Map<String, String>> sortDates(List<Map<String, String>> noticeList) {
        // ntt_sn 값을 큰 값부터 작은 값 순으로 데이터를 정렬
        noticeList.sort(Comparator.comparingInt((Map<String, String> o) -> Integer.parseInt(o.get("ntt_sn"))).reversed());
        return noticeList;
    }
}