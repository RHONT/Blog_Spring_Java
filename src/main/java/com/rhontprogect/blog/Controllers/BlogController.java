package com.rhontprogect.blog.Controllers;

import com.rhontprogect.blog.Model.Article;
import com.rhontprogect.blog.Repositories.ArticleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class BlogController {

    private final ArticleRepository articleRepository;

    public BlogController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/blog")
    public String blogMain(Model model){
        Iterable<Article> articles=articleRepository.findAll();
        model.addAttribute("articles",articles);
        return "blog";
    }

    @GetMapping("/blog/add")
    public String blogAdd(){

        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title,
                              @RequestParam String anonce,
                              @RequestParam String text){
        Article article=new Article(title,anonce,text);
        articleRepository.save(article);

        return "redirect:/blog ";
    }

    @GetMapping("/blog/{id_article}")
    public String readBlog(@PathVariable(value = "id_article") Long id,
                           Model model){

        Optional<Article> article=articleRepository.findById(id);
        if (article.isEmpty()){
            return "redirect:/blog";
        }
        model.addAttribute("article",article.get());
        return "blog-details";
    }

    @GetMapping("/blog/{id_article}/edit")
    public String editArticle(@PathVariable(value = "id_article") Long id,
                           Model model){

        Optional<Article> article=articleRepository.findById(id);
        if (article.isEmpty()){
            return "redirect:/blog";
        }
        model.addAttribute("article",article.get());
        return "blog-edit";
    }

    @PostMapping("/blog/{id_article}/edit")
    public String blogPostUpdate(@PathVariable(value = "id_article") Long id,
                                 @RequestParam String title,
                                 @RequestParam String anonce,
                                 @RequestParam String text){
        Optional<Article> article=articleRepository.findById(id);
        if (article.isEmpty()){
            return "redirect:/blog";
        }

        article.get().setTittle(title);
        article.get().setAnons(anonce);
        article.get().setFullText(text);
        articleRepository.save(article.get());

        return "redirect:/blog ";
    }

    @PostMapping("/blog/{id_article}/remove")
    public String blogPostDelete(@PathVariable(value = "id_article") Long id){
        articleRepository.deleteById(id);
        return "redirect:/blog ";
    }



}
