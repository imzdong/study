package org.imzdong.geektime.ebook;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.List;

@Setter
@Getter
public class Toc {

    private String title;
    private LinkedHashMap<String, List<String>> firstTitle;

}
