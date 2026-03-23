INSERT INTO board (id, board_name, status)
VALUES (1, '자유게시판', 'REGISTERED');

INSERT INTO board (id, board_name, status)
VALUES (2, '공지사항', 'REGISTERED');

INSERT INTO post (id, board_id, user_name, password, email, status, title, content, posted_at)
VALUES (1, 1, 'admin', '1234', 'admin@test.com', 'REGISTERED', '첫 번째 게시글', 'SimpleBoard 화면이 정상적으로 출력되는지 확인하는 테스트 게시글입니다.', CURRENT_TIMESTAMP());

INSERT INTO post (id, board_id, user_name, password, email, status, title, content, posted_at)
VALUES (2, 1, 'user1', '1234', 'user1@test.com', 'REGISTERED', '두 번째 게시글', '게시판과 게시글 CRUD API는 살아 있고, 이제 화면에서도 보이게 됩니다.', CURRENT_TIMESTAMP());

INSERT INTO post (id, board_id, user_name, password, email, status, title, content, posted_at)
VALUES (3, 2, 'manager', '1234', 'manager@test.com', 'REGISTERED', '공지 테스트', '공지사항 게시판 샘플 데이터입니다.', CURRENT_TIMESTAMP());