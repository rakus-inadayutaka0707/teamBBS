package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Comment;
import com.example.form.CommentForm;
import com.example.repository.CommentRepository;

/**
 * @author fukushima
 *
 */
@Controller
@RequestMapping("/comment-insert")
public class InsertCommentController {

	@Autowired
	private CommentRepository commentRepository;

	/**
	 * コメントを登録する.
	 * 
	 * @param form  登録したいコメントの内容
	 * @return 記事一覧画面へ
	 */	
	@RequestMapping("")
	public String commentInsert(CommentForm form) {
		Comment comment = new Comment();
		BeanUtils.copyProperties(form, comment);
		comment.setArticleId(Integer.parseInt(form.getArticleId()));
		commentRepository.insert(comment);
		return "redirect:/";
	}
}
