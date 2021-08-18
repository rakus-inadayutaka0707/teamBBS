package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;

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

	private static final String TABLENAME = "articles";

	private static final RowMapper<Article> ARTICLE_ROW_MAPPER = (rs, i) -> {
		Article article = new Article();
		article.setId(rs.getInt("id"));
		article.setName(rs.getString("name"));
		article.setContent(rs.getString("content"));
		return article;
	};

	/**
	 * 記事情報を全て取得する.
	 * 
	 * @return 取得した記事情報
	 */
	public List<Article> findAll() {
		String sql = "select id,name,content from " + TABLENAME + " order by id desc;";
		List<Article> articleList = template.query(sql, ARTICLE_ROW_MAPPER);
		return articleList;
	}

	/**
	 * 記事とデータベースに登録する.
	 * 
	 * @param article 登録したい記事情報
	 */
	public void insert(Article article) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);
		String sql = "insert into " + TABLENAME + " (name,content) values (:name,:content);";
		template.update(sql, param);
	}

	/**
	 * 投稿した記事を削除する.
	 * 
	 * @param id 削除したい記事ID
	 */
	public void deleteById(int id) {
		String sql = "delete from " + TABLENAME + " where id=:id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}
}
