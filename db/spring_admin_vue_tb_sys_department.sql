CREATE TABLE tb_sys_department (
    id               INT AUTO_INCREMENT COMMENT '학과 id'
        PRIMARY KEY,
    name             VARCHAR(255) NOT NULL COMMENT '학과 이름',
    parent_id        INT          NOT NULL COMMENT '부모학과',
    level            TINYINT      NOT NULL COMMENT '학과 sort',
    description      VARCHAR(255) NOT NULL COMMENT '학과 Description',
    create_time      DATETIME     NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '생성 시간',
    last_update_time DATETIME     NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '마지막 수정 시간'
)
    COMMENT '[권한 관리]학과 테이블';

INSERT INTO spring_admin_vue.tb_sys_department (id, name, parent_id, level, description, create_time, last_update_time) VALUES (1, 'Alibaba Group', 0, 0, 'Alibaba Group', '2018-11-15 03:23:13', '2018-11-15 03:23:13');
INSERT INTO spring_admin_vue.tb_sys_department (id, name, parent_id, level, description, create_time, last_update_time) VALUES (2, 'Alibaba Cloud', 1, 1, 'Alibaba Cloud Technology', '2018-11-15 03:23:15', '2018-11-15 03:23:15');
INSERT INTO spring_admin_vue.tb_sys_department (id, name, parent_id, level, description, create_time, last_update_time) VALUES (3, '경영', 6, 1, '[Taobao] 비지니스', '2020-11-18 15:53:57', '2020-11-18 15:53:57');
INSERT INTO spring_admin_vue.tb_sys_department (id, name, parent_id, level, description, create_time, last_update_time) VALUES (4, '개발', 9, 1, '[Cainiao Network] Development학과', '2020-11-18 15:53:57', '2020-11-18 15:53:57');
INSERT INTO spring_admin_vue.tb_sys_department (id, name, parent_id, level, description, create_time, last_update_time) VALUES (5, '보안', 2, 2, '[Alibaba Cloud] 보안', '2020-11-18 15:53:57', '2020-11-18 15:53:57');
INSERT INTO spring_admin_vue.tb_sys_department (id, name, parent_id, level, description, create_time, last_update_time) VALUES (6, 'Taobao', 1, 2, 'Taobao Network Technology', '2018-11-15 03:23:26', '2018-11-15 03:23:26');
INSERT INTO spring_admin_vue.tb_sys_department (id, name, parent_id, level, description, create_time, last_update_time) VALUES (8, '고객지원', 6, 3, '[Taobao] 고객 서비스', '2020-11-18 15:53:57', '2020-11-18 15:53:57');
INSERT INTO spring_admin_vue.tb_sys_department (id, name, parent_id, level, description, create_time, last_update_time) VALUES (9, 'Cainiao Network', 1, 3, 'Cainiao Network', '2018-10-23 12:53:01', '2018-10-31 09:28:28');
INSERT INTO spring_admin_vue.tb_sys_department (id, name, parent_id, level, description, create_time, last_update_time) VALUES (14, '전자상거래', 6, 2, '[Taobao] 전자 상거래', '2020-11-18 15:53:57', '2020-11-18 15:53:57');
INSERT INTO spring_admin_vue.tb_sys_department (id, name, parent_id, level, description, create_time, last_update_time) VALUES (17, '운영 및 유지 관리', 2, 1, '[Alibaba Cloud] 운영 및 유지 관리', '2020-11-18 15:53:57', '2020-11-18 15:53:57');