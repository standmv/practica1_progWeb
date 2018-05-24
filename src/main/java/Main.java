import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        int paragraphCounter = 1;
        int getFormCounter = 1;
        int postFormCounter = 1;

        try {
            System.out.println("Introduzca la url: ");
            Scanner sc = new Scanner(System.in);
            String url = sc.nextLine();
            Document document = Jsoup.connect(url).get();

            String[] lineNumbers = document.html().split("\n");
            System.out.println("Cantidad de lineas que tiene el recurso retornado: " + lineNumbers.length);


            Elements paragraphs = document.select("p");
            System.out.println("Cantidad de parrafos que tiene este recurso: " + paragraphs.size());
            System.out.println("Cantidad de imagenes por parrafo");
            for (Element image: paragraphs) {
                System.out.println("Parrafo #" + paragraphCounter + ": " + image.select("img").size());
                paragraphCounter++;
            }

            System.out.println("===========================================");
            Elements getForms = document.select("form[method*=get]");
            System.out.println("Cantidad de forms con metodo GET: " + getForms.size());
            for (Element input: getForms){
                Elements inputTag = input.select("input");
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
                System.out.println("Inputs del Form#" + getFormCounter + ": ");
                for (Element type:inputTag){
                    System.out.println("Contenido: " + type + " ||| Tipo de input: " + type.attr("type"));
                }
                getFormCounter++;
            }

            System.out.println("===========================================");
            Elements postForms = document.select("form[method*=post]");
            System.out.println("Cantidad de forms con metodo POST: " + postForms.size());
            for (Element inputTag: postForms){
                Elements postInputTag = inputTag.select("input");
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
                System.out.println("Inputs del Form#" + postFormCounter + ": ");
                for (Element type:postInputTag){
                    System.out.println("Contenido: " + type + " ||| Tipo de input: " + type.attr("type"));
                }
                postFormCounter++;

                Connection.Response response = Jsoup.connect(url)
                        .data("asignatura", "practica1")
                        .header("matricula", "20120412")
                        .method(Connection.Method.POST)
                        .execute();

                System.out.println("Respuesta de la petición para este formulario POST: " + response.body());
            }

        }
        catch (Exception e){
            System.out.println("La url no es valida");
        }

    }
}

