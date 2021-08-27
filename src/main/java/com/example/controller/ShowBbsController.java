package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.repository.ArticleRepository;

@Controller
@RequestMapping("/")
public class ShowBbsController {
	
	@Autowired
	private ArticleRepository articleRepository;
	
	/**
	 * 記事一覧画面を表示する
	 * 
	 * @param model 記事一覧情報
	 * @return 記事一覧画面を表示する
	 */
	@RequestMapping("")
	public String index(Model model) {
		List<Article> articleList = articleRepository.findAll();
		for (Article article : articleList) {
			System.out.println(article);
		}
		model.addAttribute("articleList", articleList);
		return "index";
	}
}
