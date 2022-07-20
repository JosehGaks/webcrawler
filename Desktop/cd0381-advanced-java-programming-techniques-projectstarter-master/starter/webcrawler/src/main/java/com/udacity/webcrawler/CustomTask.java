package com.udacity.webcrawler;

import com.udacity.webcrawler.parser.PageParser;
import com.udacity.webcrawler.parser.PageParserFactory;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.RecursiveAction;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CustomTask extends RecursiveAction {
    private final String url;
    private final Instant deadline;
    private final Integer maxDepth;
    private final ConcurrentHashMap<String, Integer> counts;
    private final ConcurrentSkipListSet<String> visitedUrls;
    private final List<Pattern> ignoredUrls;
    private final Clock clock;
    private final PageParserFactory parserFactory;
    public CustomTask(String url,
                      Instant deadline,
                      Integer maxDepth,
                      ConcurrentHashMap<String, Integer> counts,
                      ConcurrentSkipListSet<String> visitedUrls,
                      List<Pattern> ignoredUrls,
                      Clock clock,
                      PageParserFactory parserFactory) {
        this.url = url;
        this.deadline = deadline;
        this.maxDepth = maxDepth;
        this.counts = counts;
        this.visitedUrls = visitedUrls;
        this.ignoredUrls = ignoredUrls;
        this.clock = clock;
        this.parserFactory = parserFactory;
    }

    @Override
    protected void compute() {
        Object urlLock = new Object();
        if (maxDepth == 0 || clock.instant().isAfter(deadline)) {
            return;
        }
        for (Pattern pattern : ignoredUrls) {
            if (pattern.matcher(url).matches()) {
                return;
            }
        }
        synchronized (urlLock){
            if (visitedUrls.contains(url)) {
                return;
            }
            visitedUrls.add(url);
        }
        PageParser.Result result = parserFactory.get(url).parse();

        for (ConcurrentHashMap.Entry<String, Integer> e : result.getWordCounts().entrySet()) {
            counts.compute(e.getKey(),(k,v) -> (v == null) ? e.getValue() : v + e.getValue());
        }

        List<CustomTask> subtasks = new ArrayList<>();
        result.getLinks().forEach(
                url -> subtasks.add(new CustomTask(url,deadline,maxDepth - 1, counts, visitedUrls, ignoredUrls, clock, parserFactory))
        );
        invokeAll(subtasks);
    }
}
