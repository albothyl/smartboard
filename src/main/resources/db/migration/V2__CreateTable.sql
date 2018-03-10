USE smartboard;

CREATE TABLE `board` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '게시글 아이디',
  `title` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '게시글 제목',
  `content` varchar(2000) COLLATE utf8_bin NOT NULL COMMENT '게시글 내용',
  `writer` varchar(15) COLLATE utf8_bin NOT NULL COMMENT '글쓴이 아이디',
  `createdAt` datetime NOT NULL COMMENT '생성일시',
  `modifiedAt` datetime NOT NULL COMMENT '수정일시',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='게시판 정보';
