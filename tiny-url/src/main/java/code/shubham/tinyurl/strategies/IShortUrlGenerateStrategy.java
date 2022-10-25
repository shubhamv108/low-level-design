package code.shubham.tinyurl.strategies;

public interface IShortUrlGenerateStrategy {

    String generate(String originalUrl, Integer shortUrlLength);

}
