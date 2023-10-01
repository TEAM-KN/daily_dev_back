-- user init
INSERT INTO users(email, create_date, update_date, nickname, image_url, password, subscribe_yn) VALUES('scnoh0617@gmail.com', '2023-04-05 12:28:00.191691', '2023-04-05 12:28:00.191691', '노짱', '', '{bcrypt}$2a$10$U0R6S0X0TBsGLiSpyk2rDe/sKIUm6ck2fDWFtm76rfetrFu8Yy/Di', 'Y');
INSERT INTO users(email, create_date, update_date, nickname, image_url, password, subscribe_yn) VALUES('schulnoh@gmail.com', '2023-04-05 12:28:00.191691', '2023-04-05 12:28:00.191691', '승짱', '', '{bcrypt}$2a$10$JtItiLfBo8GlDH5FFvwosOYqPTRHzycHZK1Be1ZeXzGU27Bbalmqi', 'Y');
INSERT INTO users(email, create_date, update_date, nickname, image_url, password, subscribe_yn) VALUES('pak4184@naver.com', '2023-04-05 12:28:00.191691', '2023-04-05 12:28:00.191691', '현민', '', '{bcrypt}$2a$10$JtItiLfBo8GlDH5FFvwosOYqPTRHzycHZK1Be1ZeXzGU27Bbalmqi', 'Y');

-- site init
INSERT INTO site (site_code, create_date, update_date, site_desc, site_name, batch_yn) VALUES('WOO', '2023-06-06 12:00:00.373009', '2023-06-06 12:00:00.373009', '우아한형제들 기술 블로그', '우아한형제들', 'Y');
INSERT INTO site (site_code, create_date, update_date, site_desc, site_name, batch_yn) VALUES('KAKAO', '2023-06-06 12:00:00.373009', '2023-06-06 12:00:00.373009', '카카오 기술 블로그', '카카오', 'Y');
INSERT INTO site (site_code, create_date, update_date, site_desc, site_name, batch_yn) VALUES('NAVER', '2023-06-06 12:00:00.373009', '2023-06-06 12:00:00.373009', '네이버 뉴스', '네이버', 'Y');
INSERT INTO site (site_code, create_date, update_date, site_desc, site_name, batch_yn) VALUES('DAANGN', '2023-06-06 12:00:00.373009', '2023-06-06 12:00:00.373009', '당근마켓 기술 블로그', '당근마켓', 'Y');
INSERT INTO site (site_code, create_date, update_date, site_desc, site_name, batch_yn) VALUES('LINE', '2023-06-06 12:00:00.373009', '2023-06-06 12:00:00.373009', '라인 기술 블로그', 'LINE', 'Y');
INSERT INTO site (site_code, create_date, update_date, site_desc, site_name, batch_yn) VALUES('GMARKET', '2023-06-06 12:00:00.373009', '2023-06-06 12:00:00.373009', '지마켓 기술 블로그', '지마켓', 'Y');

-- site_users  init
INSERT INTO user_sites (email, site_code) VALUES('scnoh0617@gmail.com', 'WOO');
INSERT INTO user_sites (email, site_code) VALUES('scnoh0617@gmail.com', 'KAKAO');
INSERT INTO user_sites (email, site_code) VALUES('scnoh0617@gmail.com', 'NAVER');
INSERT INTO user_sites (email, site_code) VALUES('scnoh0617@gmail.com', 'DAANGN');
INSERT INTO user_sites (email, site_code) VALUES('scnoh0617@gmail.com', 'LINE');
INSERT INTO user_sites (email, site_code) VALUES('scnoh0617@gmail.com', 'GMARKET');
INSERT INTO user_sites (email, site_code) VALUES('schulnoh@gmail.com', 'WOO');
INSERT INTO user_sites (email, site_code) VALUES('schulnoh@gmail.com', 'KAKAO');
INSERT INTO user_sites (email, site_code) VALUES('schulnoh@gmail.com', 'NAVER');
INSERT INTO user_sites (email, site_code) VALUES('schulnoh@gmail.com', 'DAANGN');
INSERT INTO user_sites (email, site_code) VALUES('schulnoh@gmail.com', 'LINE');
INSERT INTO user_sites (email, site_code) VALUES('schulnoh@gmail.com', 'GMARKET');