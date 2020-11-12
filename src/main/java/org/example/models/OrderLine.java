package org.example.models;

/**
 * Order line which maps to one article with certain quantity
 */
public class OrderLine {
    private Article article;
    double quantity;

    public OrderLine(Article article, double quantity) {
        this.article = article;
        this.quantity = quantity;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /**
     *
     * @return Out of taxes price for one orderLine (only one article)
     */
    public double getPrice() {
        return quantity * article.getPrice();
    }
}
