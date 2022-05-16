package com.ridingmate.api.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
    연비 관련 컨트롤러 등록 컨트롤러

    -등록 : 날짜, 주유량, 현재주행거리, 주유가격 입력 받아서 저장
        -> 현재 주행거리 나의 바이크 주행거리로 최신화
        -> 연비 수학계산해서 처리
    -리스트 : 주유량, 주유가격, 최근 연비 표출
    -초기화 : 주유기록 db는 남겨놓은 상태로 주행거리 초기 셋팅을 입력받음 -> 다시 연비 측정 시작

*/

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/fuel")
public class FuelController {



}
