CREATE TABLE tb_sys_role (
    rid              INT AUTO_INCREMENT COMMENT '체계역할ID'
        PRIMARY KEY,
    role_name        CHAR(255)    NOT NULL COMMENT '체계역할이름',
    description      VARCHAR(255) NOT NULL COMMENT '체계역할기술',
    create_time      DATETIME     NOT NULL COMMENT '생성시간',
    last_update_time DATETIME     NOT NULL COMMENT '수정시각'
)
    COMMENT '[ 권한관리 ] 역할테이블';

INSERT INTO spring_admin_vue.tb_sys_role (rid, role_name, description, create_time, last_update_time) VALUES (1, 'ROLE_ROOT', 'admin', '2018-10-23 12:32:13', '2018-10-23 12:32:10');
INSERT INTO spring_admin_vue.tb_sys_role (rid, role_name, description, create_time, last_update_time) VALUES (2, 'ROLE_ADMIN', '관리자', '2018-10-23 12:32:31', '2018-10-23 12:32:29');
INSERT INTO spring_admin_vue.tb_sys_role (rid, role_name, description, create_time, last_update_time) VALUES (3, 'ROLE_WAITER', '웨이터', '2018-10-23 12:32:52', '2019-11-19 19:56:21');
INSERT INTO spring_admin_vue.tb_sys_role (rid, role_name, description, create_time, last_update_time) VALUES (11, 'ROLE_OPS', '운영자', '2019-12-12 23:44:17', '2019-12-12 23:44:17');
INSERT INTO spring_admin_vue.tb_sys_role (rid, role_name, description, create_time, last_update_time) VALUES (14, 'ROLE_TEST', '테스터', '2019-12-22 21:23:13', '2019-12-22 21:23:13');