CREATE TABLE tb_sys_user (
    uid              INT(200) AUTO_INCREMENT COMMENT '사용자 ID'
        PRIMARY KEY,
    avatar           CHAR(255) NOT NULL COMMENT '화신',
    username         CHAR(50)  NOT NULL COMMENT '계좌 번호',
    email            CHAR(50)  NOT NULL COMMENT '사서함',
    nickname         CHAR(100) NOT NULL COMMENT '사용자 이름',
    password         CHAR(255) NOT NULL COMMENT '암호',
    gender           TINYINT   NOT NULL COMMENT '성별 [0.여 1. 남 2. 알 수 없음]',
    birthday         DATE      NOT NULL COMMENT '생일',
    status           TINYINT   NOT NULL COMMENT '상태 [0. 사용 안 함 1. 정상 2. 삭제 중]',
    create_time      DATETIME  NOT NULL COMMENT '시간 추가',
    last_update_time DATETIME  NOT NULL COMMENT '수정 시간',
    dept_id          INT       NOT NULL COMMENT '학과 id',
    CONSTRAINT UK_6i5ixxulo5s2i7qoksp54tgwl_username
        UNIQUE (username),
    CONSTRAINT UK_ulo5s2i7qoksp54tgwl_email
        UNIQUE (email)
)
    COMMENT '[권한 관리] 사용자 테이블';

INSERT INTO spring_admin_vue.tb_sys_user (uid, avatar, username, email, nickname, password, gender, birthday, status, create_time, last_update_time, dept_id) VALUES (3, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', 'root_admin', 'root_admin@163.com', '최고관리자', '$2a$10$F47JY5Yt2DGoPuG8Fra8XuyiA20Q9g3.4J5eKXB0DrmvacVO1Olya', 1, '2019-12-11', 1, '2018-11-18 19:18:50', '2018-11-18 19:18:50', 5);
INSERT INTO spring_admin_vue.tb_sys_user (uid, avatar, username, email, nickname, password, gender, birthday, status, create_time, last_update_time, dept_id) VALUES (8, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', 'xiaoxiannv', 'xiaoxiannv@qq.com', '작은요정', '$2a$10$9uByNfHn0vDteZ4lj0LlUe8lSJKepIsrFiTNoR6thF7xzZGaRzfY2', 0, '2000-01-12', 1, '2019-12-16 23:06:09', '2019-12-16 23:06:09', 8);
INSERT INTO spring_admin_vue.tb_sys_user (uid, avatar, username, email, nickname, password, gender, birthday, status, create_time, last_update_time, dept_id) VALUES (10, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', 'xiannva', 'xiannva@qq.com', '요정', '$2a$10$CWX3FQix1jXvvLLn.3KhJ.KfbEoBnNt23JlV71S4mfi99NreYyYW2', 0, '2000-02-08', 1, '2019-12-25 21:41:27', '2019-12-25 21:41:27', 1);