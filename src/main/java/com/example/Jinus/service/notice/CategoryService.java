package com.example.Jinus.service.notice;

import com.example.Jinus.entity.notice.*;
import com.example.Jinus.repository.notice.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);
    private final BizCategoryRepository bizCategoryRepository; // 경영대학
    private final CalsCategoryRepository calsCategoryRepository; // 농업생명과학대학
    private final CeCategoryRepository ceCategoryRepository; // 공과대학
    private final CnsCategoryRepository cnsCategoryRepository; // 자연과학대학
    private final CssCategoryRepository cssCategoryRepository; // 사회과학대학
    private final EtcCategoryRepository etcCategoryRepository; // 건설환경공과대학
    private final InmunCategoryRepository inmunCategoryRepository; // 인문대학
    private final ItCategoryRepository itCategoryRepository; // It 공과대학
    private final MarsciCategoryRepository marsciCategoryRepository; // 해양과학대학
    private final SadaeCategoryRepository sadaeCategoryRepository; // 사범대학

    public CategoryService(
            BizCategoryRepository bizCategoryRepository,
            CalsCategoryRepository calsCategoryRepository,
            CeCategoryRepository ceCategoryRepository,
            CnsCategoryRepository cnsCategoryRepository,
            CssCategoryRepository cssCategoryRepository,
            EtcCategoryRepository etcCategoryRepository,
            InmunCategoryRepository inmunCategoryRepository,
            ItCategoryRepository itCategoryRepository,
            MarsciCategoryRepository marsciCategoryRepository,
            SadaeCategoryRepository sadaeCategoryRepository
    ) {
        this.bizCategoryRepository = bizCategoryRepository;
        this.calsCategoryRepository = calsCategoryRepository;
        this.ceCategoryRepository = ceCategoryRepository;
        this.cnsCategoryRepository = cnsCategoryRepository;
        this.cssCategoryRepository = cssCategoryRepository;
        this.etcCategoryRepository = etcCategoryRepository;
        this.inmunCategoryRepository = inmunCategoryRepository;
        this.itCategoryRepository = itCategoryRepository;
        this.marsciCategoryRepository = marsciCategoryRepository;
        this.sadaeCategoryRepository = sadaeCategoryRepository;
//        logger.info("CategoryService 실행");
    }

    public Map<Integer, Map<String, String>> getCategory(int departmentId, String collegeEng) {
//        logger.info("getCategory 실행");
//        logger.info("collegeEng: {}", collegeEng);
        // 결과를 담을 해시맵 생성
        Map<Integer, Map<String, String>> categoryMap = new HashMap<>();

        switch(collegeEng) {
            case "biz" -> {
                List<BizCategoryEntity> bizCategoryEntities = bizCategoryRepository.findByDepartmentId(departmentId);
//                logger.info("biz: {}", bizCategoryEntities.toString());

                for (BizCategoryEntity entity : bizCategoryEntities) {
                    Map<String, String> map = new HashMap<>();
//                    logger.info("entity.getId():{}", entity.getId());
//                    logger.info("entity.getCategory():{}", entity.getCategory());
//                    logger.info("entity.getMi():{}", entity.getMi());
//                    logger.info("entity.getBbsId():{}", entity.getBbsId());
                    map.put("category", entity.getCategory());
                    map.put("mi", String.valueOf(entity.getMi()));
                    map.put("bbs_id", String.valueOf(entity.getBbsId()));
                    categoryMap.put(entity.getId(), map);
                }
            }
            case "cals" -> {
                List<CalsCategoryEntity> calsCategoryEntities = calsCategoryRepository.findByDepartmentId(departmentId);
//                logger.info("cals: {}", calsCategoryEntities.toString());

                for (CalsCategoryEntity entity : calsCategoryEntities) {
                    Map<String, String> map = new HashMap<>();
//                    logger.info("entity.getId():{}", entity.getId());
//                    logger.info("entity.getCategory():{}", entity.getCategory());
//                    logger.info("entity.getMi():{}", entity.getMi());
//                    logger.info("entity.getBbsId():{}", entity.getBbsId());
                    map.put("category", entity.getCategory());
                    map.put("mi", String.valueOf(entity.getMi()));
                    map.put("bbs_id", String.valueOf(entity.getBbsId()));
                    categoryMap.put(entity.getId(), map);
                }
            }
            case "ce" -> {
                List<CeCategoryEntity> ceCategoryEntities = ceCategoryRepository.findByDepartmentId(departmentId);
//                logger.info("ce: {}", ceCategoryEntities.toString());

                for (CeCategoryEntity entity : ceCategoryEntities) {
                    Map<String, String> map = new HashMap<>();
//                    logger.info("entity.getId():{}", entity.getId());
//                    logger.info("entity.getCategory():{}", entity.getCategory());
//                    logger.info("entity.getMi():{}", entity.getMi());
//                    logger.info("entity.getBbsId():{}", entity.getBbsId());
                    map.put("category", entity.getCategory());
                    map.put("mi", String.valueOf(entity.getMi()));
                    map.put("bbs_id", String.valueOf(entity.getBbsId()));
                    categoryMap.put(entity.getId(), map);
                }
            }
            case "cns" -> {
                List<CnsCategoryEntity> cnsCategoryEntities = cnsCategoryRepository.findByDepartmentId(departmentId);
//                logger.info("cns: {}", cnsCategoryEntities.toString());

                for (CnsCategoryEntity entity : cnsCategoryEntities) {
                    Map<String, String> map = new HashMap<>();
//                    logger.info("entity.getId():{}", entity.getId());
//                    logger.info("entity.getCategory():{}", entity.getCategory());
//                    logger.info("entity.getMi():{}", entity.getMi());
//                    logger.info("entity.getBbsId():{}", entity.getBbsId());
                    map.put("category", entity.getCategory());
                    map.put("mi", String.valueOf(entity.getMi()));
                    map.put("bbs_id", String.valueOf(entity.getBbsId()));
                    categoryMap.put(entity.getId(), map);
                }
            }
            case "css" -> {
                List<CssCategoryEntity> cssCategoryEntities = cssCategoryRepository.findByDepartmentId(departmentId);
//                logger.info("css: {}", cssCategoryEntities.toString());

                for (CssCategoryEntity entity : cssCategoryEntities) {
                    Map<String, String> map = new HashMap<>();
//                    logger.info("entity.getId():{}", entity.getId());
//                    logger.info("entity.getCategory():{}", entity.getCategory());
//                    logger.info("entity.getMi():{}", entity.getMi());
//                    logger.info("entity.getBbsId():{}", entity.getBbsId());
                    map.put("category", entity.getCategory());
                    map.put("mi", String.valueOf(entity.getMi()));
                    map.put("bbs_id", String.valueOf(entity.getBbsId()));
                    categoryMap.put(entity.getId(), map);
                }
            }
            case "etc" -> {
                List<EtcCategoryEntity> etcCategoryEntities = etcCategoryRepository.findByDepartmentId(departmentId);
//                logger.info("etc: {}", etcCategoryEntities.toString());

                for (EtcCategoryEntity entity : etcCategoryEntities) {
                    Map<String, String> map = new HashMap<>();
//                    logger.info("entity.getId():{}", entity.getId());
//                    logger.info("entity.getCategory():{}", entity.getCategory());
//                    logger.info("entity.getMi():{}", entity.getMi());
//                    logger.info("entity.getBbsId():{}", entity.getBbsId());
                    map.put("category", entity.getCategory());
                    map.put("mi", String.valueOf(entity.getMi()));
                    map.put("bbs_id", String.valueOf(entity.getBbsId()));
                    categoryMap.put(entity.getId(), map);
                }
            }
            case "inmun" -> {
                List<InmunCategoryEntity> inmunCategoryEntities = inmunCategoryRepository.findByDepartmentId(departmentId);
//                logger.info("inmun: {}", inmunCategoryEntities.toString());

                for (InmunCategoryEntity entity : inmunCategoryEntities) {
                    Map<String, String> map = new HashMap<>();
//                    logger.info("entity.getId():{}", entity.getId());
//                    logger.info("entity.getCategory():{}", entity.getCategory());
//                    logger.info("entity.getMi():{}", entity.getMi());
//                    logger.info("entity.getBbsId():{}", entity.getBbsId());
                    map.put("category", entity.getCategory());
                    map.put("mi", String.valueOf(entity.getMi()));
                    map.put("bbs_id", String.valueOf(entity.getBbsId()));
                    categoryMap.put(entity.getId(), map);
                }
            }
            case "it" -> {
                List<ItCategoryEntity> itCategoryEntities = itCategoryRepository.findByDepartmentId(departmentId);
//                logger.info("it: {}", itCategoryEntities.toString());

                for (ItCategoryEntity entity : itCategoryEntities) {
                    Map<String, String> map = new HashMap<>();
//                    logger.info("entity.getId():{}", entity.getId());
//                    logger.info("entity.getCategory():{}", entity.getCategory());
//                    logger.info("entity.getMi():{}", entity.getMi());
//                    logger.info("entity.getBbsId():{}", entity.getBbsId());
                    map.put("category", entity.getCategory());
                    map.put("mi", String.valueOf(entity.getMi()));
                    map.put("bbs_id", String.valueOf(entity.getBbsId()));
                    categoryMap.put(entity.getId(), map);
                }
            }
            case "marsci" -> {
                List<MarsciCategoryEntity> marsciCategoryEntities = marsciCategoryRepository.findByDepartmentId(departmentId);
//                logger.info("marsci: {}", marsciCategoryEntities.toString());

                for (MarsciCategoryEntity entity : marsciCategoryEntities) {
                    Map<String, String> map = new HashMap<>();
//                    logger.info("entity.getId():{}", entity.getId());
//                    logger.info("entity.getCategory():{}", entity.getCategory());
//                    logger.info("entity.getMi():{}", entity.getMi());
//                    logger.info("entity.getBbsId():{}", entity.getBbsId());
                    map.put("category", entity.getCategory());
                    map.put("mi", String.valueOf(entity.getMi()));
                    map.put("bbs_id", String.valueOf(entity.getBbsId()));
                    categoryMap.put(entity.getId(), map);
                }
            }
            case "sadae" -> {
                List<SadaeCategoryEntity> sadaeCategoryEntities = sadaeCategoryRepository.findByDepartmentId(departmentId);
//                logger.info("sadae: {}", sadaeCategoryEntities.toString());

                for (SadaeCategoryEntity entity : sadaeCategoryEntities) {
                    Map<String, String> map = new HashMap<>();
//                    logger.info("entity.getId():{}", entity.getId());
//                    logger.info("entity.getCategory():{}", entity.getCategory());
//                    logger.info("entity.getMi():{}", entity.getMi());
//                    logger.info("entity.getBbsId():{}", entity.getBbsId());
                    map.put("category", entity.getCategory());
                    map.put("mi", String.valueOf(entity.getMi()));
                    map.put("bbs_id", String.valueOf(entity.getBbsId()));
                    categoryMap.put(entity.getId(), map);
                }
            }
            default -> logger.debug("CategoryService: category를 찾을 수 없습니다.");
        }

//        logger.info("categoryMap: {}", categoryMap);
        // 해시맵 반환
        return sortCategories(categoryMap);
    }

    // 공지 카테고리 가나다순 정렬
    public Map<Integer, Map<String, String>> sortCategories(Map<Integer, Map<String, String>> categoryMap) {
        // 엔트리셋을 리스트로 변환
        List<Map.Entry<Integer, Map<String, String>>> entryList = new ArrayList<>(categoryMap.entrySet());

        // 정렬
        Collections.sort(entryList, Comparator.comparing(e -> e.getValue().get("category")));

        // 정렬된 결과를 담을 맵 생성
        Map<Integer, Map<String, String>> sortedCategories = new LinkedHashMap<>();

        // 정렬된 결과를 맵에 추가
        for (Map.Entry<Integer, Map<String, String>> entry : entryList) {
            sortedCategories.put(entry.getKey(), entry.getValue());
        }

//        logger.info("sortedCategories: {}", sortedCategories);
        // 정렬된 맵 반환
        return sortedCategories;
    }
}