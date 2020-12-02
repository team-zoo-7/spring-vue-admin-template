package com.ifsaid.shark.util;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * Twitter의 분산 눈송이 알고리즘 SnowFlake는 초당 260,000 개의 정렬 가능한 ID를 생성합니다.
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 2.0
 * @date 2019/12/19 23:14
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

public class SnowFlakeUtils {

    private final static SnowFlake SNOW_FLAKE = new SnowFlake(2, 3);

    public static long getId() {
        return SNOW_FLAKE.nextId();
    }

    static class SnowFlake {

        /**
         * 시각 찌르기 시작
         */
        private final static long START_STMP = 1480166465631L;

        /**
         * 각 부분이 차지하는 비트 수
         */
        //일련 번호가 차지하는 자릿수
        private final static long SEQUENCE_BIT = 12;

        //컴퓨터 ID가 차지하는 자릿수
        private final static long MACHINE_BIT = 5;

        //데이터 센터가 차지하는 비트 수
        private final static long DATACENTER_BIT = 5;

        /**
         * 각 부품의 최대 값
         */
        private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
        private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
        private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

        /**
         * 왼쪽으로 각 부분의 변위
         */
        private final static long MACHINE_LEFT = SEQUENCE_BIT;
        private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
        private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

        //데이터 센터
        private long datacenterId;

        //기계 식별
        private long machineId;

        //일련 번호
        private long sequence = 0L;

        //마지막 시각 시각
        private long lastStmp = -1L;

        public SnowFlake(long datacenterId, long machineId) {
            if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
                throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
            }
            if (machineId > MAX_MACHINE_NUM || machineId < 0) {
                throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
            }
            this.datacenterId = datacenterId;
            this.machineId = machineId;
        }

        /**
         * 다음 ID 생성
         *
         * @return
         */
        public synchronized long nextId() {
            long currStmp = getNewstmp();
            if (currStmp < lastStmp) {
                throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
            }

            if (currStmp == lastStmp) {
                //동일한 밀리 초 내에 일련 번호가 자동으로 증가합니다.
                sequence = (sequence + 1) & MAX_SEQUENCE;
                //동일한 밀리 초의 시퀀스 수가 최대 값에 도달했습니다.
                if (sequence == 0L) {
                    currStmp = getNextMill();
                }
            } else {
                //다른 밀리 초에서 일련 번호는 0으로 설정됩니다.
                sequence = 0L;
            }

            lastStmp = currStmp;

            return (currStmp - START_STMP) << TIMESTMP_LEFT //시각 찌르기 부분
                    | datacenterId << DATACENTER_LEFT       //데이터 센터 부분
                    | machineId << MACHINE_LEFT             //기계 식별 부분
                    | sequence;                             //일련 번호 부분
        }

        private long getNextMill() {
            long mill = getNewstmp();
            while (mill <= lastStmp) {
                mill = getNewstmp();
            }
            return mill;
        }

        private long getNewstmp() {
            return System.currentTimeMillis();
        }

    }

}