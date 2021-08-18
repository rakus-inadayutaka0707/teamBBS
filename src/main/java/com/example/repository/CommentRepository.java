package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Comment;

/**
 * commentsテーブル情報を操作するためのRepositoryクラス.
 * 
 * @author inada
 *
 */
@Repository
public class CommentRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final String TABLENAME = "comments";

	private static final RowMapper<Comment> COMMENT_ROW_MAPPER = (rs, i) -> {
		Comment comment = new Comment();
		comment.setId(rs.getInt("id"));
		comment.setName(rs.getString("name"));
		comment.setContent(rs.getString("content"));
		comment.setArticleId(rs.getInt("article_id"));
		return comment;
	};

	/**
	 * コメントを取得する
	 * 
	 * @param articleId 取得したいコメントの記事ID
	 * @return 取得したコメント
	 */
	public List<Comment> findByArticleId(int articleId) {
		String sql = "select id,name,content,article_id from " + TABLENAME
				+ " where article_id=:articleId order by id desc;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		List<Comment> commentList = template.query(sql, param, COMMENT_ROW_MAPPER);
		return commentList;
	}

	/**
	 * コメントを登録する.
	 * 
	 * @param coment 登録したいコメント
	 */
	public void insert(Comment coment) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(coment);
		String sql = "insert into " + TABLENAME
				+ " (name,content,article_id) values (:name,:content,:articleId);";
		template.update(sql, param);
	}

	/**
	 * 記事IDが削除された際に削除するコメント.
	 * 
	 * @param articleId 削除した記事IDを持つコメント
	 */
	public void deleteByArticleId(int articleId) {
		String sql = "delete from " + TABLENAME + " where article_id=:articleId;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		template.update(sql, param);
	}
}
