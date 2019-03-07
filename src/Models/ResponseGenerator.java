package Models;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.Convert;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/**
 * Created by alexthor on 11.10.17.
 */
public class ResponseGenerator {

    public static StringBuilder generateResponse(ResourceBundle interfaceResource,
                                                 ResourceBundle productResource,
                                                 String tab, String product) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                        "        <p class='w3-xlarge w3-margin-left'>\n" +
                        getProductTitle(productResource) +
                        "        <p/>\n" +
                        "        <div class=\"w3-row\">\n" +
                        "            <div class=\"w3-half w3-container\">\n" +
                        "              <div class=\"w3-row w3-display-container w3-content\">\n" +
                        "                <button class=\"w3-button w3-display-left w3-hover-blue\" " +
                        "onclick=\"plusDivs(-1)\">&#10094;</button>\n" +
                        "                <button class=\"w3-button w3-display-right w3-hover-blue\" " +
                        "onclick=\"plusDivs(+1)\">&#10095;</button>\n" +
                        getProductImages(productResource) +
                        "              </div>\n" +
                        "            </div>\n" +
                        "            <div class=\"w3-half w3-container\">\n" +
                        "              <div class=\"w3-panel\">\n" +
                        "                <p class=\"w3-large\">" + getPrice(interfaceResource, productResource) + "</p>\n" +
                        "                <a href=\"cartProcess?action=add&product="
                        + product + "\" class=\"w3-btn w3-hover-black w3-blue\">"
                        + getButtonLabel(interfaceResource, "AddToCartButtonLabel") + "</a>\n" +
                        "              </div>\n" +
                        "              <div class=\"w3-row\">\n" +
                        "                <div class=\"w3-container\">\n" +
                        "                    <div class=\"w3-bar w3-black\">\n" +
                        "                        <button class=\"w3-bar-item w3-btn w3-hover-blue tablink " +
                        getButtonColor(tab, "Overview") + "\"" +
                        " style=\"width:33.33%;\" onclick=\"openTab(event,'overview')\">" + getTitle(interfaceResource, "OverviewLabel") +
                        "</button>\n" +
                        "                        <button class=\"w3-bar-item w3-btn w3-hover-blue tablink " +
                        getButtonColor(tab, "TechSpecs") + "\"" +
                        " style=\"width:33.33%;\" onclick=\"openTab(event,'techSpecs')\">" + getTitle(interfaceResource, "TechSpecsLabel") +
                        "</button>\n" +
                        "                        <button class=\"w3-bar-item w3-btn w3-hover-blue tablink " +
                        getButtonColor(tab, "Reviews") + "\"" +
                        " style=\"width:33.33%;\" onclick=\"openTab(event,'reviews')\">" + getTitle(interfaceResource, "ReviewsLabel") +
                        "</button>\n" +
                        "                    </div>\n" +
                        "\n" +
                        "                    <div id=\"overview\" class=\"w3-container product-details\"" +
                        " style=\"display:" + getDisplayProperty(tab, "Overview") + "\">\n" +
                        getOverview(productResource) +
                        "                    </div>\n" +
                        "\n" +
                        "                    <div id=\"techSpecs\" class=\"w3-container product-details\"" +
                        " style=\"display:" + getDisplayProperty(tab, "TechSpecs") + "\">\n" +
                        "                      <h3>" + getTitle(interfaceResource,"TechSpecsLabel") + "</h3>\n" +
                        "                      <table class=\"w3-table w3-bordered\">\n" +
                        getTechSpecs(productResource) +
                        "                        </table>\n" +
                        "                    </div>\n" +
                        "                      \n" +
                        "                    <div id=\"reviews\" class=\"w3-container product-details\"" +
                        " style=\"display:" + getDisplayProperty(tab, "Reviews") + "\">\n" +
                        "                      <h3>" + getTitle(interfaceResource, "ReviewsLabel") + "</h3>\n" +
                        getReviews(productResource) +
                        "                    </div>\n" +
                        "                </div>\n" +
                        "              </div>\n" +
                        "            </div>\n" +
                        "        </div>\n" +
                        "    <script src=\"scripts/interaction.js\"></script>\n"
        );
        return sb;
    }

    private static String doHeader(ResourceBundle interfaceResource, String product){
        return "        <div class=\"w3-bar w3-container\">\n" +
                "            <a href=\"/products?lang=" +
                interfaceResource.getString("Lang") +
                "\" class=\"w3-bar-item w3-left w3-button w3-xlarge\">\n" +
                "               Surface store\n" +
                "            </a>" +
                "            <button class=\"w3-bar-item w3-button w3-right w3-hover-blue\">"
                + getButtonLabel(interfaceResource, "CartButtonLabel") + "</button>\n" +
                "            <div class=\"w3-bar-item w3-dropdown-hover w3-right\">\n" +
                getCurrentLanguage(interfaceResource) +
                "                <div class=\"w3-dropdown-content w3-bar-block w3-border\">\n" +
                "                  <a href=\"localize?lang=ru" + "\" class=\"w3-bar-item w3-button\">Русский</a>\n" +
                "                  <a href=\"localize?lang=en" + "\" class=\"w3-bar-item w3-button\">English</a>\n" +
                "                  <a href=\"localize?lang=nl" + "\" class=\"w3-bar-item w3-button\">Nederlands</a>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <hr>";
    }

    private static String getButtonLabel(ResourceBundle interfaceResource, String button) {
        return interfaceResource.getString(button);
    }

    private static String getDisplayProperty(String tab, String block) {
        if(tab.equals(block))
            return "block;";
        return "none;";
    }

    private static String getTitle(ResourceBundle interfaceResource, String title) {
        return interfaceResource.getString(title);
    }

    private static String getButtonColor(String tab, String button) {
        if(tab.equals(button))
            return "w3-red";
        return "";
    }

    private static String generateScripts(){
        throw new NotImplementedException();
    }

    public static String getProductTitle(ResourceBundle productResource) {
        return productResource.getString("ProductTitle");
    }

    public static String getProductImages(ResourceBundle productResource) {
        StringBuilder result = new StringBuilder();
        int pictureAmount = Integer.parseInt(productResource.getString("ImagesCount"));
        String path = productResource.getString("ImagesPath");
        for (int i = 1; i <= pictureAmount; i++) {
            result.append("<img class=\"w3-image productImageSlides\" src=\"" + path + i + ".jpg\">\n");
        }
        return result.toString();
    }

    public static String getOverview(ResourceBundle productResource) {
        StringBuilder sb = new StringBuilder("<h3>"+productResource.getString("FirstParagraphTitle")+"</h3>\n" +
                            productResource.getString("FirstParagraph") +
                            "<h3>" + productResource.getString("SecondParagraphTitle") + "</h3>\n" +
                            productResource.getString("SecondParagraph")+
                            "<h3>"+ productResource.getString("Description") +"</h3>\n" +
                            "<ul>\n");
        String[] descriptionContent = productResource.getString("DescriptionContent").split("[;]");
        for (String descRow: descriptionContent) {
            sb.append("<li>" + descRow + "</li>\n");
        }
        sb.append("</ul>\n");
        return sb.toString();
    }

    public static String getTechSpecs(ResourceBundle productResource) {
        String[] specTitles = productResource.getString("SpecTitles").split("[;]");
        String[] specContents = productResource.getString("SpecContents").split("[;]");
        int length = specTitles.length;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(
                    "<tr><td>" +
                            specTitles[i] +
                            "</td><td>" +
                            specContents[i] +
                            "</td></tr>"
            );
        }
        return result.toString();
    }

    public static String getReviews(ResourceBundle productResource) {
        return "<p>" + productResource.getString("ReviewsContent") + "</p>\n";
    }

    public static String getPrice(ResourceBundle interfaceResource, ResourceBundle productResource) {
        Locale locale = interfaceResource.getLocale();
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        String form = formatter.format(Double.parseDouble(productResource.getString("Price")));
        return interfaceResource.getString("Price") + ": " + form;
    }

    public static String getCurrentLanguage(ResourceBundle interfaceResource) {
        Locale iLocale = interfaceResource.getLocale();
        String result = getLanguageByLocale(iLocale);
        return result;
    }

    public static String getLanguageByLocale(Locale locale){
        char[] charSet = locale.getDisplayLanguage(locale).toCharArray();
        charSet[0] = Character.toUpperCase(charSet[0]);
        String result = new String(charSet);
        return result;
    }

    public static Locale getLocale(String lang) {
        switch (lang){
            case "en":
                return Locale.US;
            case "nl":
                return new Locale("nl", "NL");
            case "ru":
                return new Locale("ru", "RU");
            default:
                return Locale.US;
        }
    }
}
