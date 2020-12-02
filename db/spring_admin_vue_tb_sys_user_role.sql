CREATE TABLE tb_sys_user_role (
    user_id INT NOT NULL COMMENT '사용자ID',
    role_id INT NOT NULL COMMENT '역할ID'
)
    COMMENT '[ 권한관리 ] 사용자 테이블 및 역할 테이블';

INSERT INTO spring_admin_vue.tb_sys_user_role (user_id, role_id) VALUES (8, 3);
INSERT INTO spring_admin_vue.tb_sys_user_role (user_id, role_id) VALUES (3, 1);
INSERT INTO spring_admin_vue.tb_sys_user_role (user_id, role_id) VALUES (10, 11);