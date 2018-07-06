package org.sandalfon.netheos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * User story 1 :

En tant qu'utilisateur ayant les droits "administrateur", je peux insérer une question / réponse dans la base de connaissances (FAQ) du produit. Une question / réponse est définie par :

Le libellé de la question ;
Le libellé de la réponse ;
La liste des tags associés.

User story 2 :

En tant qu'utilisateur ayant les droits "administrateur", je peux lister toutes les questions / réponses de la base de connaissances.

User story 3 :

En tant qu'utilisateur, je peux rechercher la réponse à une question sans avoir à saisir le texte exact correspondant à une question ou à une réponse de la base de connaissances.
 * 
 * 
 * @author Gilles VIEIRA
 * @version 1.1
 *
 */

@SpringBootApplication
public class Netheos extends SpringBootServletInitializer {
	  public static void main(String[] args) {
	      SpringApplication.run(Netheos.class, args);
	  }

	  @Override
	  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	      return builder.sources(Netheos.class);
	  }
}
