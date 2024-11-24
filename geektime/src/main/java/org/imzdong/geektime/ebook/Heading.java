package org.imzdong.geektime.ebook;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Heading {

    private String title;
    private Integer playOrder;
    private String fileName;
    private List<Heading> subHeadings;


}
