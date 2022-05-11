package com.ridingmate.api.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    정비 관련 컨트롤러 등록 컨트롤러

    -리스트 : 바이크 별칭, 제조사, 모델명 디폴트
        연단위로 필터링
        연단위 정비 카운트, 총 가격 표출
        리스트는 title, content, 가격, 날짜 표출
    -등록 : title, content(이미지 x), 정비항목(list) 추가하면서 작성 가능하게, 날짜, 가격 입력받아서 저장, 이미지 최대 8
    -수정 : 등록과 동일하게
*/

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/fuel")
public class MaintenanceController {



}
