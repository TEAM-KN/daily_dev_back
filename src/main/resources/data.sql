-- user init
INSERT INTO users(email, create_date, update_date, nickname, imageUrl, password, subscribeYn) VALUES('scnoh0617@gmail.com', '2023-04-05 12:28:00.191691', '2023-04-05 12:28:00.191691', '노짱', '', '{bcrypt}$2a$10$JtItiLfBo8GlDH5FFvwosOYqPTRHzycHZK1Be1ZeXzGU27Bbalmqi', 'Y');
INSERT INTO users(email, create_date, update_date, nickname, imageUrl, password, subscribeYn) VALUES('kya754@gmail.com', '2023-04-05 12:28:00.191691', '2023-04-05 12:28:00.191691', '', '', '{bcrypt}$2a$10$JtItiLfBo8GlDH5FFvwosOYqPTRHzycHZK1Be1ZeXzGU27Bbalmqi', 'Y');

-- site init
INSERT INTO dev.site (site_code, create_date, update_date, site_desc, site_name) VALUES('WOO', '2023-06-06 12:00:00.373009', '2023-06-06 12:00:00.373009', '우아한형제들 기술 블로그', '우아한형제들');
INSERT INTO dev.site (site_code, create_date, update_date, site_desc, site_name) VALUES('KAKAO', '2023-06-06 12:00:00.373009', '2023-06-06 12:00:00.373009', '카카오 기술 블로그', '카카오');
INSERT INTO dev.site (site_code, create_date, update_date, site_desc, site_name) VALUES('NAVER', '2023-06-06 12:00:00.373009', '2023-06-06 12:00:00.373009', '네이버 뉴스', '네이버');

-- site_users  init
INSERT INTO dev.user_sites (email, site_code) VALUES('scnoh0617@gmail.com', 'WOO');
INSERT INTO dev.user_sites (email, site_code) VALUES('scnoh0617@gmail.com', 'KAKAO');
INSERT INTO dev.user_sites (email, site_code) VALUES('scnoh0617@gmail.com', 'NAVER');
