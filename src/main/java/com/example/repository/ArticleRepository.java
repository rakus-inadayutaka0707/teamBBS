package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;
import com.example.domain.Comment;

/**
 * articlesテーブルを操作するためのRepositoryクラス.
 * 
 * @author inada
 *
 */
@Repository
public class ArticleRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final String ARTICLETABLE = "articles";
	private static final String COMMENTTABLE = "comments";

	private static final ResultSetExtractor<List<Article>> ARTICLE_COMMENT_RESULTSET = (rs) -> {
		List<Article> articleList = new ArrayList<>();
		List<Comment> commentList = null;
		
		int beforeIdNum = 0;
		
		while (rs.next()) {
			
			int nowIdNum = rs.getInt("id");
			
			if(nowIdNum != beforeIdNum) {
				Article article = new Article();
				article.setId(rs.getInt("id"));
				article.setName(rs.getString("name"));
				article.setContent(rs.getString("content"));
				commentList = new ArrayList<>();
				article.setCommentList(commentList);
				articleList.add(article);
			}
			
			if(rs.getInt("com_id") != 0) {
				Comment comment = new Comment();
				comment.setId(rs.getInt("com_id"));
				comment.setName(rs.getString("com_name"));
				comment.setContent(rs.getString("com_content"));
				comment.setArticleId(rs.getInt("article_id"));
				commentList.add(comment);
			}
			
			beforeIdNum = nowIdNum;
		}
		return articleList;
	};

//	private static final RowMapper<Article> ARTICLE_ROW_MAPPER = (rs, i) -> {
//		Article article = new Article();
//		article.setId(rs.getInt("id"));
//		article.setName(rs.getString("name"));
//		article.setContent(rs.getString("content"));
//		return article;
//	};

	/**
	 * 記事情報を全て取得する.
	 * 
	 * @return 取得した記事情報
	 */
	public List<Article> findAll() {
		String sql = "select a.id,a.name,a.content,c.id as com_id,c.name as com_name,c.content as com_content,c.article_id from "
				+ ARTICLETABLE + " as a left outer join " + COMMENTTABLE
				+ " as c on a.id=c.article_id order by a.id desc, c.id desc;";
		List<Article> articleList = template.query(sql, ARTICLE_COMMENT_RESULTSET);
		return articleList;
	}

	/**
	 * 記事とデータベースに登録する.
	 * 
	 * @param article 登録したい記事情報
	 */
	public void insert(Article article) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);
		String sql = "insert into " + ARTICLETABLE + " (name,content) values (:name,:content);";
		template.update(sql, param);
	}

	/**
	 * 投稿した記事を削除する.
	 * 
	 * @param id 削除したい記事ID
	 */
	public void deleteById(int id) {
		String sql = "delete from " + ARTICLETABLE + " where id=:id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}
}
