package com.example.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
import com.example.repository.ArticleRepository;
import com.example.repository.CommentRepository;

/**
 * 記事情報を操作するためのControllerクラス
 * 
 * @author inada
 *
 */
@Controller
@RequestMapping("/")
public class ArticleController {

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private CommentRepository commentRepository;

	@ModelAttribute
	private ArticleForm setUpArticleForm() {
		return new ArticleForm();
	}

	@ModelAttribute
	private CommentForm setUpCommentForm() {
		return new CommentForm();
	}

	/**
	 * 記事一覧画面を表示する
	 * 
	 * @param model 記事一覧情報
	 * @return 記事一覧画面を表示する
	 */
	@RequestMapping("")
	public String index(Model model) {
		List<Article> articleList = articleRepository.findAll();
//		for (Article article : articleList) {
//			List<Comment> commentList = commentRepository.findByArticleId(article.getId());
//			article.setCommentList(commentList);
//		}
		for (Article article : articleList) {
			System.out.println(article);
		}
		model.addAttribute("articleList", articleList);
		return "index";
	}

	/**
	 * 記事を登録する.
	 * 
	 * @param form  登録したい記事の内容
	 * @param model indexメソッドを呼ぶために必要
	 * @return 記事一覧画面へ
	 */
	@RequestMapping("/insert")
	public String insert(ArticleForm form) {
		Article article = new Article();
		BeanUtils.copyProperties(form, article);
		articleRepository.insert(article);
		return "redirect:/";
	}

	/**
	 * コメントを登録する.
	 * 
	 * @param form  登録したいコメントの内容
	 * @param model indexメソッドを呼ぶために必要
	 * @return 記事一覧画面へ
	 */
	@RequestMapping("/comment-insert")
	public String commentInsert(CommentForm form) {
		Comment comment = new Comment();
		BeanUtils.copyProperties(form, comment);
		comment.setArticleId(Integer.parseInt(form.getArticleId()));
		commentRepository.insert(comment);
		return "redirect:/";
	}

	/**
	 * 記事を削除する.
	 * 
	 * @param id 削除する記事ID
	 * @return 記事一覧画面へ
	 */
	@RequestMapping("/delete")
	public String delete(int id) {
		commentRepository.deleteByArticleId(id);
		articleRepository.deleteById(id);
		return "redirect:/";
	}
}
