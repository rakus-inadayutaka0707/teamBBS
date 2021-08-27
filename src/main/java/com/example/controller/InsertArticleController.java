package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.form.ArticleForm;
import com.example.repository.ArticleRepository;

@Controller
@RequestMapping("/insert")
public class InsertArticleController {
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@ModelAttribute
	private ArticleForm setUpArticleForm() {
		return new ArticleForm();
	}

	/**
	 * 記事を登録する.
	 * 
	 * @param form  登録したい記事の内容
	 * @param model indexメソッドを呼ぶために必要
	 * @return 記事一覧画面へ
	 */
	@RequestMapping("")
	public String insert(ArticleForm form) {
		Article article = new Article();
		BeanUtils.copyProperties(form, article);
		articleRepository.insert(article);
		return "forward:/";
	}

}
