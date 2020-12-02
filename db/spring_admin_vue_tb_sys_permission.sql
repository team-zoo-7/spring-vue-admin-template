CREATE TABLE tb_sys_permission (
    pid              INT AUTO_INCREMENT COMMENT '권한 고유 ID'
        PRIMARY KEY,
    parent_id        INT                 NOT NULL COMMENT '부모 ID',
    resources        CHAR(100)           NOT NULL COMMENT 'authority resource ',
    title            CHAR(100)           NOT NULL COMMENT 'Resources Name ',
    icon             CHAR(100)           NULL COMMENT 'resource icon ',
    type             CHAR(10) DEFAULT '' NOT NULL COMMENT '유형, 메뉴 또는 버튼',
    description      VARCHAR(255)        NOT NULL COMMENT 'Permission description ',
    create_time      DATETIME            NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '생성 시간',
    last_update_time DATETIME            NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 시간',
    CONSTRAINT t_resources
        UNIQUE (resources)
)
    COMMENT '[권한 관리] 권한 테이블';

INSERT INTO spring_admin_vue.tb_sys_permission (pid, parent_id, resources, title, icon, type, description, create_time, last_update_time) VALUES (1, 0, 'pre', '권한관리', 'pre', 'menu', '권한설정', '2019-12-14 14:26:00', '2019-12-14 14:26:00');
INSERT INTO spring_admin_vue.tb_sys_permission (pid, parent_id, resources, title, icon, type, description, create_time, last_update_time) VALUES (2, 0, 'sys', '시스템관리', 'sys', 'menu', '시스템관리', '2019-12-14 23:27:08', '2019-12-14 23:27:08');
INSERT INTO spring_admin_vue.tb_sys_permission (pid, parent_id, resources, title, icon, type, description, create_time, last_update_time) VALUES (3, 1, 'pre_perm', '권한', 'pre_perm', 'menu', '권한설정', '2020-11-19 10:24:07', '2020-11-19 10:24:07');
INSERT INTO spring_admin_vue.tb_sys_permission (pid, parent_id, resources, title, icon, type, description, create_time, last_update_time) VALUES (4, 1, 'pre_user', '사용자', 'pre_user', 'menu', '사용자관리', '2020-11-19 10:23:07', '2020-11-19 10:23:07');
INSERT INTO spring_admin_vue.tb_sys_permission (pid, parent_id, resources, title, icon, type, description, create_time, last_update_time) VALUES (5, 1, 'pre_role', '역할', 'pre_role', 'menu', '역할관리', '2020-11-19 10:23:07', '2020-11-19 10:23:07');
INSERT INTO spring_admin_vue.tb_sys_permission (pid, parent_id, resources, title, icon, type, description, create_time, last_update_time) VALUES (6, 1, 'pre_dept', '부서', 'pre_dept', 'menu', '부서관리', '2020-11-19 10:23:07', '2020-11-19 10:23:07');
INSERT INTO spring_admin_vue.tb_sys_permission (pid, parent_id, resources, title, icon, type, description, create_time, last_update_time) VALUES (7, 2, 'sys_dictionary', '사전컬렉션', 'sys_dictionary', 'menu', '사전컬렉션', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO spring_admin_vue.tb_sys_permission (pid, parent_id, resources, title, icon, type, description, create_time, last_update_time) VALUES (8, 2, 'sys_wechat', 'WeChat설정', 'sys_wechat', 'menu', 'WeChat설정', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO spring_admin_vue.tb_sys_permission (pid, parent_id, resources, title, icon, type, description, create_time, last_update_time) VALUES (9, 2, 'sys_logs', '시스템로그', 'sys_logs', 'menu', '시스템로그', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO spring_admin_vue.tb_sys_permission (pid, parent_id, resources, title, icon, type, description, create_time, last_update_time) VALUES (10, 3, 'pre_perm:create', '추가', 'pre_perm_create', 'button', '추가권한', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO spring_admin_vue.tb_sys_permission (pid, parent_id, resources, title, icon, type, description, create_time, last_update_time) VALUES (11, 3, 'pre_perm:update', '수정', 'pre_perm_update', 'button', '수정권한', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO spring_admin_vue.tb_sys_permission (pid, parent_id, resources, title, icon, type, description, create_time, last_update_time) VALUES (12, 3, 'pre_perm:delete', '삭제', 'pre_perm_delete', 'button', '삭제권한', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO spring_admin_vue.tb_sys_permission (pid, parent_id, resources, title, icon, type, description, create_time, last_update_time) VALUES (13, 4, 'pre_user:create', '추가', 'pre_user_create', 'button', '추가사용자', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO spring_admin_vue.tb_sys_permission (pid, parent_id, resources, title, icon, type, description, create_time, last_update_time) VALUES (14, 4, 'pre_user:update', '수정', 'pre_user_update', 'button', '수정사용자', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO spring_admin_vue.tb_sys_permission (pid, parent_id, resources, title, icon, type, description, create_time, last_update_time) VALUES (15, 4, 'pre_user:delete', '삭제', 'pre_user_delete', 'button', '삭제사용자', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO spring_admin_vue.tb_sys_permission (pid, parent_id, resources, title, icon, type, description, create_time, last_update_time) VALUES (16, 4, 'pre_user:update:roles', '역할', 'pre_user_update_roles', 'button', '사용자수정역할', '2020-11-18 15:44:22', '2020-11-18 15:44:22');
INSERT INTO spring_admin_vue.tb_sys_permission (pid, parent_id, resources, title, icon, type, description, create_time, last_update_time) VALUES (17, 5, 'pre_role:create', '추가', 'pre_role_create', 'button', '추가역할', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO spring_admin_vue.tb_sys_permission (pid, parent_id, resources, title, icon, type, description, create_time, last_update_time) VALUES (18, 5, 'pre_role:update', '수정', 'pre_role_update', 'button', '수정역할', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO spring_admin_vue.tb_sys_permission (pid, parent_id, resources, title, icon, type, description, create_time, last_update_time) VALUES (19, 5, 'pre_role:delete', '삭제', 'pre_role_delete', 'button', '삭제역할', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO spring_admin_vue.tb_sys_permission (pid, parent_id, resources, title, icon, type, description, create_time, last_update_time) VALUES (20, 5, 'pre_role:update:permissions', '권한', 'pre_role_update_permissions', 'button', '역할수정권한', '2020-11-18 15:44:22', '2020-11-18 15:44:22');
INSERT INTO spring_admin_vue.tb_sys_permission (pid, parent_id, resources, title, icon, type, description, create_time, last_update_time) VALUES (21, 6, 'pre_dept:create', '추가', 'pre_dept_create', 'button', '추가학과', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO spring_admin_vue.tb_sys_permission (pid, parent_id, resources, title, icon, type, description, create_time, last_update_time) VALUES (22, 6, 'pre_dept:update', '수정', 'pre_dept_update', 'button', '수정학과', '2018-11-17 10:20:54', '2020-11-16 13:57:41');
INSERT INTO spring_admin_vue.tb_sys_permission (pid, parent_id, resources, title, icon, type, description, create_time, last_update_time) VALUES (23, 6, 'pre_dept:delete', '삭제', 'pre_dept_delete', 'button', '삭제학과', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO spring_admin_vue.tb_sys_permission (pid, parent_id, resources, title, icon, type, description, create_time, last_update_time) VALUES (27, 2, 'sys_swagger2', 'API문서', 'sys_swagger2', 'menu', 'API문서', '2018-11-17 11:20:54', '2018-11-17 11:20:54');
INSERT INTO spring_admin_vue.tb_sys_permission (pid, parent_id, resources, title, icon, type, description, create_time, last_update_time) VALUES (38, 0, 'test_dwqdwq', '최상위권한 테스트', 'test_dwqdwq', 'menu', '최상위 권한 테스트', '2020-11-18 15:44:22', '2020-11-18 15:44:22');
INSERT INTO spring_admin_vue.tb_sys_permission (pid, parent_id, resources, title, icon, type, description, create_time, last_update_time) VALUES (41, 38, 'test_two_wdqwqewq', '보조권한 테스트', 'test_two_wdqwqewq', 'menu', '보조 권한 테스트', '2020-11-25 11:10:53', '2020-11-25 11:10:53');
INSERT INTO spring_admin_vue.tb_sys_permission (pid, parent_id, resources, title, icon, type, description, create_time, last_update_time) VALUES (42, 2, 'sys_china_area', '행정구역', 'sys_china_area', 'menu', '중국행정구역', '2019-12-25 22:50:59', '2019-12-25 22:50:59');