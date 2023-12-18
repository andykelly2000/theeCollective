package collective.com.theeCollective.controller;

import collective.com.theeCollective.dto.ArticleDto;
import collective.com.theeCollective.dto.AuthorDto;
import collective.com.theeCollective.dto.CustomerDto;
import collective.com.theeCollective.model.Article;
import collective.com.theeCollective.model.Author;
import collective.com.theeCollective.model.Customer;
import collective.com.theeCollective.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController {
    private ArticleService articleService;
    private AuthorService authorService;
    private CustomerService customerService;
    private Mail mail;

    @Autowired
    public AdminController(ArticleService articleService, AuthorService authorService, CustomerService customerService, Mail mail) {
        this.articleService = articleService;
        this.authorService = authorService;
        this.customerService = customerService;
        this.mail = mail;
    }

    @GetMapping("/")
    public String HomePage(Model model, HttpSession session){
        @SuppressWarnings("unchecked")
        String username = (String) session.getAttribute("username");
        String status = "yes";
        if (username == null) {
            status = "no";
        }
        model.addAttribute("status", status);
        model.addAttribute("username", username);


        List<ArticleDto> carouselArticles = articleService.findAllArticles();
        model.addAttribute("carouselArticles", carouselArticles);
        return "index";
    }

    @GetMapping("/Article/{ArticleID}/view")
    public String displayPage(@PathVariable("ArticleID") Long ArticleId, Model model){
        ArticleDto article = articleService.findById(ArticleId);
        model.addAttribute("article",article);
        int views = article.getViews() + 1;
        article.setViews(views);
        articleService.updateArticle(article);
        return "single-page";
    }

    @GetMapping("/Login")
    public String Login(Model model){
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "login";
    }

    @PostMapping("/Login")
    public String LoginManagement(@ModelAttribute("customer") Customer customer, HttpServletRequest request){
        Customer customer1 = customerService.loginUser(customer.getUsername());
        @SuppressWarnings("unchecked")
        String username = (String) request.getSession().getAttribute("username");
        if (username == null) {
            username = customer.getUsername();
            request.getSession().setAttribute("username", username);
        } else{
            request.getSession().setAttribute("username", customer.getUsername());
        }
        System.out.println("logged in");

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }

    @GetMapping("/Register")
    public String SignUp(Model model){
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "Register";
    }

    @PostMapping("/Register")
    public String SignedUp(@ModelAttribute("customer") Customer customer){
        customerService.saveCustomer(customer);
        String subject="Welcome to Thee-collective";
        String Body="Welcome to Thee-collective";
        mail.Registration(customer.getEmail(), subject, Body);
        return "redirect:/";
    }


    @GetMapping("/Admin/Article")
    public String adminArticle(Model model){
        List<ArticleDto> articles = articleService.findAllArticles();
        model.addAttribute("articles", articles);
        return "Admin-Article";
    }

    @GetMapping("/articles/search")
    public String searchArticle(@RequestParam(value = "query") String query, Model model){
        List<ArticleDto> articles = articleService.searchArticles(query);
        model.addAttribute("articles", articles);
        return "Admin-Article";
    }

    @GetMapping("/articles/{articleId}/edit")
    public String editArticle(@PathVariable("articleId") long articleId, Model model){
        ArticleDto article = articleService.findById(articleId);
        List<AuthorDto> authors = authorService.findAllAuthor();
        model.addAttribute("authors", authors);
        model.addAttribute("article", article);
        return "edit-article";
    }

    @PostMapping("/articles/{articleId}/edit")
    public String updateArticle(@PathVariable("articleId") long articleId, Model model,
                                @Valid @ModelAttribute("article") ArticleDto article,
                                BindingResult result
                                ){

        if(result.hasErrors()){
            List<AuthorDto> authors = authorService.findAllAuthor();
            model.addAttribute("authors", authors);
            return "edit-article";
        }
        article.setArticleId((int) articleId);
        articleService.updateArticle(article);
        return "redirect:/Admin/Article";
    }

    @GetMapping ("/newArticle")
    public String createArticle(Model model){
        Article article = new Article();
        List<AuthorDto> authors = authorService.findAllAuthor();
        model.addAttribute("article", article);
        model.addAttribute("authors", authors);
        return "new-article";
    }

    @PostMapping ("/newArticle")
    public String submitNewArticle(@ModelAttribute("article") Article article){
        articleService.saveArticle(article);
        return "redirect:/Admin/Article";
    }

    @GetMapping("article/{articleId}")
    public String articleDetail(@PathVariable("articleId") long articleId, Model model){
        ArticleDto articleDto = articleService.findById(articleId);
        model.addAttribute("article", articleDto);
        return "article-detail";
    }

    @GetMapping("/articles/{articleId}/delete")
    public String deleteArticle(@PathVariable("articleId") Long articleId){
        articleService.delete(articleId);
        return "redirect:/Admin/Article";
    }

    @GetMapping(value = "/openPdf/articleReport", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> articleReport() throws IOException{
        List<ArticleDto> articles = articleService.findAllArticles();
        ByteArrayInputStream bis = DatabasePDFService.articlePDFReport(articles);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=article.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }



    @GetMapping("/Admin/User")
    public String adminCustomer(Model model){
        List<CustomerDto> customers = customerService.findAllCustomers();
        model.addAttribute("customers", customers);
        return "Admin-Users";
    }

    @GetMapping("/users/search")
    public String searchUser(@RequestParam(value = "query") String query, Model model){
        List<CustomerDto> users = customerService.searchUser(query);
        model.addAttribute("users", users);
        return "Admin-Users";
    }

    @GetMapping("/users/{userId}/edit")
    public String editUser(@PathVariable("userId") long userId, Model model){
        CustomerDto user = customerService.findById(userId);
        model.addAttribute("user", user);
        return "edit-user";
    }

    @PostMapping("/users/{userId}/edit")
    public String updateuser(@PathVariable("userId") long userId, Model model,
                               @Valid @ModelAttribute("user") CustomerDto user,
                               BindingResult result
    ){
        if(result.hasErrors()){
            return "edit-user";
        }
        user.setCustomerId((int) userId);
        customerService.updateCustomer(user);
        return "redirect:/Admin/Customer";
    }

    @GetMapping ("/newUser")
    public String createUser(Model model){
        Customer user = new Customer();
        model.addAttribute("user", user);
        return "new-user";
    }

    @PostMapping ("/newUser")
    public String submitNewUser(@ModelAttribute("user") Customer customer){
        customerService.saveCustomer(customer);
        return "redirect:/Admin/User";
    }

    @GetMapping("user/{userId}")
    public String userDetail(@PathVariable("userId") long userId, Model model){
        CustomerDto customerDto = customerService.findById(userId);
        model.addAttribute("user", customerDto);
        return "user-detail";
    }

    @GetMapping("/users/{userId}/delete")
    public String deleteUser(@PathVariable("userId") Long userId){
        customerService.delete(userId);
        return "redirect:/Admin/User";
    }

    @GetMapping("/authors/search")
    public String searchAuthor(@RequestParam(value = "query") String query, Model model){
        List<AuthorDto> authors = authorService.searchAuthors(query);
        model.addAttribute("authors", authors);
        return "Admin-Author";
    }

    @GetMapping("/Admin/Author")
    public String adminAuthor(Model model){
        List<AuthorDto> authors = authorService.findAllAuthor();
        model.addAttribute("authors", authors);
        return "Admin-Author";
    }

    @GetMapping("/authors/{authorId}/edit")
    public String editAuthor(@PathVariable("authorId") long authorId, Model model){
        AuthorDto author = authorService.findById(authorId);
        model.addAttribute("author", author);
        return "edit-author";
    }

    @PostMapping("/authors/{authorId}/edit")
    public String updateAuthor(@PathVariable("authorId") long authorId, Model model,
                               @Valid @ModelAttribute("author") AuthorDto author,
                               BindingResult result
    ){
        if(result.hasErrors()){
            return "edit-article";
        }
        author.setAuthorId((int) authorId);
        authorService.updateAuthor(author);
        return "redirect:/Admin/Author";
    }

    @GetMapping ("/newAuthor")
    public String createAuthor(Model model){
        Author author = new Author();
        model.addAttribute("author", author);
        return "new-author";
    }

    @PostMapping ("/newAuthor")
    public String submitNewAuthor(@ModelAttribute("author") Author author){
        authorService.saveAuthor(author);
        return "redirect:/Admin/Author";
    }

    @GetMapping("author/{authorId}")
    public String authorDetail(@PathVariable("authorId") long authorId, Model model){
        AuthorDto authorDto = authorService.findById(authorId);
        model.addAttribute("author", authorDto);
        return "author-detail";
    }

    @GetMapping("/authors/{authorId}/delete")
    public String deleteAuthor(@PathVariable("authorId") Long authorId){
        authorService.delete(authorId);
        return "redirect:/Admin/Author";
    }
}
