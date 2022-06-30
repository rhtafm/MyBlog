package wiki.jixing.myblog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
public class Article {
    private Integer id;
    private String title;
    private String content;
    private String summary;
    private String coverImg;
    private Integer status;
    private Integer views;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate updateTime;

    private Category category;
    private List<Tag> tags;
}
