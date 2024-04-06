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
        logger.info("CategoryService 실행");
    }

    public Map<Integer, String> getCategory(int departmentId, String collegeEng) {
        logger.info("getCategory 실행");
        logger.info(collegeEng);
        // 결과를 담을 해시맵 생성
        Map<Integer, String> categoryMap = new HashMap<>();

        switch(collegeEng) {
            case "biz" -> {
                // bizCategoryRepository에서 departmentId에 해당하는 모든 행을 조회
                List<BizCategoryEntity> bizCategoryEntities = bizCategoryRepository.findByDepartmentId(departmentId);
                logger.info(bizCategoryEntities.toString());

                // 리스트에서 각 행을 순회하면서 id와 category를 해시맵에 저장
                for (BizCategoryEntity entity : bizCategoryEntities) {
                    categoryMap.put(entity.getId(), entity.getCategory());
                }
            }
            case "cals" -> {
                List<CalsCategoryEntity> calsCategoryEntities = calsCategoryRepository.findByDepartmentId(departmentId);
                logger.info(calsCategoryEntities.toString());

                for (CalsCategoryEntity entity : calsCategoryEntities) {
                    categoryMap.put(entity.getId(), entity.getCategory());
                }
            }
            case "ce" -> {
                List<CeCategoryEntity> ceCategoryEntities = ceCategoryRepository.findByDepartmentId(departmentId);
                logger.info(ceCategoryEntities.toString());

                for (CeCategoryEntity entity : ceCategoryEntities) {
                    categoryMap.put(entity.getId(), entity.getCategory());
                }
            }
            case "cns" -> {
                List<CnsCategoryEntity> cnsCategoryEntities = cnsCategoryRepository.findByDepartmentId(departmentId);
                logger.info(cnsCategoryEntities.toString());

                for (CnsCategoryEntity entity : cnsCategoryEntities) {
                    categoryMap.put(entity.getId(), entity.getCategory());
                }
            }
            case "css" -> {
                List<CssCategoryEntity> cssCategoryEntities = cssCategoryRepository.findByDepartmentId(departmentId);
                logger.info(cssCategoryEntities.toString());

                for (CssCategoryEntity entity : cssCategoryEntities) {
                    categoryMap.put(entity.getId(), entity.getCategory());
                }
            }
            case "etc" -> {
                List<EtcCategoryEntity> etcCategoryEntities = etcCategoryRepository.findByDepartmentId(departmentId);
                logger.info(etcCategoryEntities.toString());

                for (EtcCategoryEntity entity : etcCategoryEntities) {
                    categoryMap.put(entity.getId(), entity.getCategory());
                }
            }
            case "inmun" -> {
                List<InmunCategoryEntity> inmunCategoryEntities = inmunCategoryRepository.findByDepartmentId(departmentId);
                logger.info(inmunCategoryEntities.toString());

                for (InmunCategoryEntity entity : inmunCategoryEntities) {
                    categoryMap.put(entity.getId(), entity.getCategory());
                }
            }
            case "it" -> {
                List<ItCategoryEntity> itCategoryEntities = itCategoryRepository.findByDepartmentId(departmentId);
                logger.info(itCategoryEntities.toString());

                for (ItCategoryEntity entity : itCategoryEntities) {
                    categoryMap.put(entity.getId(), entity.getCategory());
                }
                logger.info(categoryMap.toString());
            }
            case "marsci" -> {
                List<MarsciCategoryEntity> marsciCategoryEntities = marsciCategoryRepository.findByDepartmentId(departmentId);
                logger.info(marsciCategoryEntities.toString());

                for (MarsciCategoryEntity entity : marsciCategoryEntities) {
                    categoryMap.put(entity.getId(), entity.getCategory());
                }
            }
            case "sadae" -> {
                List<SadaeCategoryEntity> sadaeCategoryEntities = sadaeCategoryRepository.findByDepartmentId(departmentId);
                logger.info(sadaeCategoryEntities.toString());

                for (SadaeCategoryEntity entity : sadaeCategoryEntities) {
                    categoryMap.put(entity.getId(), entity.getCategory());
                }
            }
            default -> logger.error("CategoryService: category를 찾을 수 없습니다.");
        }

        logger.info(categoryMap.toString());
        // 해시맵 반환
        return categoryMap;
    }
}